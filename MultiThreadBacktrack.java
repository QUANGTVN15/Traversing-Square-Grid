public class MultiThreadBacktrack {
    private static final int TOTAL_STEPS = 63;
    private static final int START = 0;        // top-left cell
    private static final int END = 7 * 8;      // bottom-left cell (index 56)

    // Moves: Up=-8, Down=+8, Left=-1, Right=+1
    private static final int[] UDIR = {-8};
    private static final int[] DDIR = {+8};
    private static final int[] LDIR = {-1};
    private static final int[] RDIR = {+1};
    private static final int[] ALLDIR = {-8, +8, -1, +1};

    // The number of valid paths
    private static volatile long count = 0;

    // Storing visited cell with a bitmask
    private static long visitedMask = 1L << START;

    // The allowed directions at each step
    private static int[][] allowedDirsPerStep = new int[TOTAL_STEPS][];

    // The number of initial steps that are run parallel in threads
    private static final int PARALLEL_DEPTH = 3;

    private static synchronized void incrementCount() {
        count++;
    }

    private static void precomputeDirsPerStep(String input) {
        // Precompute allowed directions
        for (int i = 0; i < TOTAL_STEPS; i++) {
            char c = input.charAt(i);
            switch (c) {
                case 'U' -> allowedDirsPerStep[i] = UDIR;
                case 'D' -> allowedDirsPerStep[i] = DDIR;
                case 'L' -> allowedDirsPerStep[i] = LDIR;
                case 'R' -> allowedDirsPerStep[i] = RDIR;
                case '*' -> allowedDirsPerStep[i] = ALLDIR;
                default -> throw new IllegalArgumentException("Invalid char: " + c);
            }
        }
    }

    // Check if next move is out of bound

    private static boolean isValidMove(int current, int dir) {
        if (dir == -8) {
            return current >= 8;
        } else if (dir == 8) {
            return current < 56;
        } else if (dir == -1) {
            return (current % 8) != 0;
        } else if (dir == 1) {
            return (current % 8) != 7;
        }
        return false;
    }

    // Sequential Backtracking
    private static void backtrack(int current, int step, long visitedMask) {
        // Base Case: All steps are taken and is at the end
        if (step == TOTAL_STEPS) {
            if (current == END) incrementCount();
            return;
        }

        int[] dirs = allowedDirsPerStep[step];
        for (int dir : dirs) {
            int next = current + dir;

            // Check if move is out of bound
            if (!isValidMove(current, dir)) continue;

            // Check if cell is visited
            long bit = 1L << next;
            if ((visitedMask & bit) != 0) continue;

            backtrack(next, step + 1, visitedMask | bit);
        }
    }


    /**
     * When step < PARALLEL_DEPTH, we spawn threads for each direction.
     * After PARALLEL_DEPTH, we do sequential backtracking.
     */
    private static void parallelBacktrack(int current, int step, long visitedMask) {
        if (step == TOTAL_STEPS) {
            if (current == END) incrementCount();
            return;
        }

        int[] dirs = allowedDirsPerStep[step];

        if (step < PARALLEL_DEPTH) {
            // Separate this step into a thread
            Thread[] threads = new Thread[dirs.length];
            int tCount = 0;

            for (final int dir : dirs) {
                final long cvisitedMask = visitedMask;

                int next = current + dir;
                if (!isValidMove(current, dir)) continue;
                long bit = 1L << next;
                if ((cvisitedMask & bit) != 0) continue;

                threads[tCount] = new Thread(() -> parallelBacktrack(next, step + 1, cvisitedMask | bit));
                threads[tCount].start();
                tCount++;
            }

            // Join all started threads
            for (int i = 0; i < tCount; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            // After PARALLEL_DEPTH steps, do sequential backtracking
            for (int dir : dirs) {
                int next = current + dir;
                if (!isValidMove(current, dir)) continue;

                long bit = 1L << next;
                if ((visitedMask & bit) != 0) continue;

                backtrack(next, step + 1, visitedMask | bit);
            }
        }
    }

    public static void main(String[] args) {
        // Input line
        String input = "*****DR******R******R********************R*D************L******";
        if (input.length() != TOTAL_STEPS) {
            System.err.println("Input must be exactly 63 chars");
            return;
        }

        precomputeDirsPerStep(input);

        long before = System.currentTimeMillis();

        // Use parallelBacktrack at the root:
        parallelBacktrack(START, 0, visitedMask);

        long after = System.currentTimeMillis();
        System.out.println("Input: " + input);
        System.out.println("Total paths: " + count);
        System.out.println("Time (ms): " + (after - before));

    }
}
