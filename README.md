Traversing-Square-Grid Problem

The Following Problem is part of the Data Structures and Algorithms course:


**Problem Details:**

Important: You cannot use the classes/interfaces defined in the Java Collections Framework and external libraries. You can use/extend the examples/solutions I shared on our course's Microsoft Teams page. You are not allowed to use code copied from the Internet.

Problem: Imagine an 8x8 grid as a mysterious forest as shown in Fig. 1, and you are the explorer. The task is to use a sequence of directions (D for Down, U for Up, L for Left, R for Right) to guide you through the forest. Occasionally, an asterisk * appears in the sequence, allowing you to choose any direction at that step. Here’s an important catch, you need to visit each cell of the grid exactly once and in exactly 63 moves for the 8x8 grid of forest. The start cell (indicated by S in Fig. 1) will be at row 0 and column 0, and the end cell (indicated by E in Fig. 1) will be at row 7 and column 0.

![image](https://github.com/user-attachments/assets/e9445c62-56d0-46e9-86c3-bcf9fc5f57b9)

Fig1.jpg

**Technical Description:**

You are given a description of a path and your task is to optimize the calculation of the total number of paths that match the description. For example, the path of Fig. 1 consists of the following 63 directions:

DDDDDDRUUUUUURDDDDDDRUUUUUURDDDDDDRUUUUUURRDLDRDLDRDLDRDLLLLLLL

You must ensure that the paths are precise, avoiding revisiting any cell or stepping outside the grid.  

Input: The input line has 63 characters of D, U, L, R and *.

Output: Total number of paths.

Execution time requirement: Use Java System currentTimeMillis() method to display the execution time of your code in milliseconds (ms). Refer to Java documentation on currentTimeMillis()Links to an external site..

Hint: For an 8x8 grid, there are a total of 8,934,966 possible paths.

Sample program executions are shown in Fig. 2.
______________________________________________________________________________________________________________________________________________________________________________
Input: ***************************************************************

Total paths: 8934966

Time (ms): 35270

 
Input: *****DR******R******R********************R*D************L******

Total paths: 5739

Time (ms): 18000
______________________________________________________________________________________________________________________________________________________________________________

Fig. 2: Sample executions (note: The displayed execution time in the sample executions does not reflect the actual time taken).

Ensure that your Java code has a separate method dedicated to explore all possible paths. This method should handle the pathfinding logic, allowing the main method to remain clean and focused on initializing input and displaying results. 

You can validate the input data to ensure it contains the correct number of characters and that each character is either 'U', 'D', 'L', 'R', or '*'.

Testing: you can generate random input data to test your work.

Note:

If you are not able to get the answer due to timeout, you can reduce the grid size (for example, grid size to 6 x 6), but certainly, you cannot earn the full score since your program experiences a timeout. 😊.
Improve the time complexity of your code, and this will subsequently reduce the execution time. 👍
Items 1 and 2 above are related. 😊

Video Link for Use of our solution: https://youtu.be/247PQT7mZW8

