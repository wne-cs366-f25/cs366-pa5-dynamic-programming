package edu.wne.cs366;

/**
 * CS 366 - Programming Assignment #5: Dynamic Programming
 * Simplified Knapsack Problem
 *
 * Name: [Your name here]
 * Date: [Date]
 *
 * This program implements the simplified knapsack problem using dynamic programming.
 * The knapsack problem: Given n items with sizes and a knapsack capacity k,
 * find the subset of items that maximizes the total size without exceeding capacity.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Knapsack {

    // Instance variables
    private int n;              // number of items
    private int k;              // knapsack capacity
    private int[] sizes;        // array of item sizes
    private boolean[][] exist;  // exist[i][j] = true if subset of first i items sums to exactly j
    private boolean[][] belong; // belong[i][j] = true if item i is used in the solution for (i,j)

    /**
     * Constructor - initializes the knapsack problem
     * @param n number of items
     * @param k knapsack capacity
     * @param sizes array of item sizes
     */
    public Knapsack(int n, int k, int[] sizes) {
        this.n = n;
        this.k = k;
        this.sizes = sizes;

        // Allocate the DP tables
        // Note: We use (n+1) x (k+1) to handle 0-indexed items more easily
        // exist[i][j] represents subproblem using items 0..i-1 with capacity j
        this.exist = new boolean[n + 1][k + 1];
        this.belong = new boolean[n + 1][k + 1];
    }

    /**
     * Implements the dynamic programming algorithm for the knapsack problem.
     * Fills in the exist[][] and belong[][] tables using bottom-up approach.
     *
     * Algorithm:
     * - exist[i][j] = true if we can make exactly j using items from 0..i-1
     * - belong[i][j] = true if item i-1 is included in the optimal solution for (i,j)
     *
     * Base case: exist[0][0] = true (empty set sums to 0)
     * Recurrence: For each item i and capacity j:
     *   - If exist[i-1][j] is true, then exist[i][j] = true (don't use item i)
     *   - If j >= sizes[i-1] and exist[i-1][j-sizes[i-1]] is true,
     *     then exist[i][j] = true and belong[i][j] = true (use item i)
     */
    public void dynamicKnapsack() {
        // TODO: Implement the dynamic programming algorithm
        //
        // Hints:
        // 1. Initialize base case: exist[0][0] = true, all other exist[0][j] = false
        // 2. Use nested loops: outer loop for i (items), inner loop for j (capacity)
        // 3. For each (i,j):
        //    - Check if we can achieve j without using item i-1
        //    - Check if we can achieve j by using item i-1
        //    - Set exist[i][j] and belong[i][j] accordingly
        // 4. Follow the pseudocode from the class handout closely

        // Base case
        exist[0][0] = true;
        for (int j = 1; j <= k; j++) {
            exist[0][j] = false;
        }

        // Fill the DP table
        // YOUR CODE HERE

    }

    /**
     * Traces back through the DP tables to find which items were selected.
     * Returns an ArrayList of item indices that form the optimal solution.
     *
     * Algorithm:
     * 1. Start at row n, find largest column col where exist[n][col] is true
     * 2. While col > 0:
     *    - Find the row where belong[row][col] is true
     *    - Add that item to the solution
     *    - Subtract the item's size from col
     *    - Move to row-1
     *
     * @return ArrayList of item indices in the solution (0-indexed)
     */
    public ArrayList<Integer> findSolution() {
        ArrayList<Integer> solution = new ArrayList<>();

        // TODO: Implement the traceback algorithm
        //
        // Hints:
        // 1. Find the largest j <= k where exist[n][j] is true
        //    (This is the maximum achievable size)
        // 2. Starting from row n and column j:
        //    - While col > 0:
        //      - Find row where belong[row][col] is true (move up if false)
        //      - Add item (row-1) to solution
        //      - Subtract sizes[row-1] from col
        //      - Move to row-1
        // 3. Return the list of selected items

        // Find the optimal capacity achieved
        int col = k;
        while (col > 0 && !exist[n][col]) {
            col--;
        }

        // If no solution exists
        if (col == 0) {
            return solution;
        }

        // Traceback to find which items are in the solution
        // YOUR CODE HERE

        return solution;
    }

    /**
     * Prints the exist and belong tables (useful for debugging)
     */
    public void printTables() {
        System.out.println("\nexist table:");
        System.out.print("      ");
        for (int j = 0; j <= k; j++) {
            System.out.printf("%5d ", j);
        }
        System.out.println();

        for (int i = 0; i <= n; i++) {
            System.out.printf("%3d | ", i);
            for (int j = 0; j <= k; j++) {
                System.out.printf("%5s ", exist[i][j] ? "T" : "F");
            }
            System.out.println();
        }

        System.out.println("\nbelong table:");
        System.out.print("      ");
        for (int j = 0; j <= k; j++) {
            System.out.printf("%5d ", j);
        }
        System.out.println();

        for (int i = 0; i <= n; i++) {
            System.out.printf("%3d | ", i);
            for (int j = 0; j <= k; j++) {
                System.out.printf("%5s ", belong[i][j] ? "T" : "F");
            }
            System.out.println();
        }
    }

    /**
     * Reads item sizes from a file
     * @param filename name of file containing item sizes (one per line)
     * @param n number of items to read
     * @return array of item sizes
     */
    public static int[] readItemsFromFile(String filename, int n) {
        int[] items = new int[n];

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < n; i++) {
                if (scanner.hasNextInt()) {
                    items[i] = scanner.nextInt();
                } else {
                    System.err.println("Error: Not enough integers in file");
                    System.exit(1);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + filename);
            System.exit(1);
        }

        return items;
    }

    /**
     * Main method - handles input/output and runs the algorithm
     * Usage: java Knapsack <filename>
     * Then provide n and k via standard input
     */
    public static void main(String[] args) {
        // Check command-line arguments
        if (args.length != 1) {
            System.err.println("Usage: java Knapsack <filename>");
            System.err.println("Then provide n and k via standard input");
            System.exit(1);
        }

        String filename = args[0];

        // Read n and k from standard input
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter number of items (n): ");
        int n = stdin.nextInt();
        System.out.print("Enter knapsack capacity (k): ");
        int k = stdin.nextInt();

        // Read item sizes from file
        int[] sizes = readItemsFromFile(filename, n);

        // Print input for verification
        System.out.println("\nInput:");
        System.out.println("Number of items: " + n);
        System.out.println("Knapsack capacity: " + k);
        System.out.print("Item sizes: [");
        for (int i = 0; i < n; i++) {
            System.out.print(sizes[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println("]");

        // Create Knapsack instance and run algorithm
        Knapsack knapsack = new Knapsack(n, k, sizes);

        // Run the DP algorithm
        knapsack.dynamicKnapsack();

        // Uncomment the following line to print DP tables for debugging
        // knapsack.printTables();

        // Find the solution
        ArrayList<Integer> solution = knapsack.findSolution();

        // Print the solution
        System.out.println("\nDynamic Programming Solution:");
        int totalSize = 0;
        for (int itemIndex : solution) {
            System.out.println("Item " + itemIndex + ", size = " + sizes[itemIndex]);
            totalSize += sizes[itemIndex];
        }
        System.out.println("Total size = " + totalSize);

        stdin.close();
    }
}
