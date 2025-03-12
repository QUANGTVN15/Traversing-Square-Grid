public class Test {
    // Represent grid with 2D array
    int gridSize = 4;
    int[][] arr = new int[gridSize][gridSize];

    // Number of valid paths
    int paths = 0;

    // Represent the X Y coordinates
    int x = 0;
    int y = 0;

    // Max and min X Y
    final int MAX = gridSize - 1;
    final int MIN = 0;

    // End cell coordinates
    final int END_X = MAX;
    final int END_Y = MIN;

    // Move count and order
    int moveCount = 0;
    final int MOVES = (gridSize * gridSize) - 1;
    int visitOrder = 1;

    // Track the path directions
    StringBuilder directions = new StringBuilder();

    // Visit a cell
    void visit() {
        arr[x][y] = visitOrder++;
    }

    // Leave a cell
    void leave() {
        arr[x][y] = 0;
        visitOrder--;
    }

    // Check if next cell is valid
    boolean canMove(int newX, int newY) {
        // Check cell is in bounds + cell is not visited
        return newX >= MIN && newX <= MAX && newY >= MIN && newY <= MAX && arr[newX][newY] == 0;
    }

    // Print the path matrix
    void printPath(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.printf("%2d ", anInt); // Print values aligned
            }
            System.out.println();
        }
        System.out.println("Path Directions: " + directions.toString());
        System.out.println();
    }

    void checkPaths() {
        // Valid if all moves are made and reached the end
        if (moveCount == MOVES && x == END_X && y == END_Y) {
            paths++;
            printPath(arr);
            return;
        }

        // Try moving in all directions
        int[] dx = {-1, 1, 0, 0}; // Up, Down, Left, Right
        int[] dy = {0, 0, -1, 1};
        char[] dir = {'U', 'D', 'L', 'R'};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (canMove(newX, newY)) {
                // Move to the new cell
                x = newX;
                y = newY;
                moveCount++;
                visit();
                directions.append(dir[i]); // Add direction

                // Recursion
                checkPaths();

                // Backtrack
                leave();
                moveCount--;
                x -= dx[i];
                y -= dy[i];
                directions.deleteCharAt(directions.length() - 1); // Remove direction
            }
        }
    }

    public Test() {
        arr[0][0] = 1;  // The first cell is always visited
    }

    public static void main(String[] args) {
        Test test = new Test();
        long before = System.currentTimeMillis();
        test.checkPaths();
        long after = System.currentTimeMillis();
        System.out.println("Number of valid paths: " + test.paths);
        System.out.println("Time (ms): " + (after - before));
    }
}
