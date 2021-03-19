package com.company;

import java.io.*;
import java.util.*;

public class testTwo{
    static String fileName = "triangle.txt";
    static int N = -1, E = -1;
    static List<List<Integer>> graph = new ArrayList<>();

    public static void main(String[] args) {
        File myObj = new File(fileName);
        Scanner myReader;
        try {
            myReader = new Scanner(myObj);
            if (myReader.hasNext() == false) {
                System.out.println("Empty File");
                System.exit(0);
            }
            while (myReader.hasNextLine()) {
                String line[] = myReader.nextLine().split("\\t");
                /* Since every line of the file has only two tab-seperated
                 * integers. If length of array, 'line' is not 2, the input
                 * format is not right.*/
                if (line.length != 2) {
                    System.out.println("Graph format incorrect!");
                    System.exit(0);
                }
                /* tryParseInteger(String text) function ensures that if the
                 * graph is invalid in the way that it does not contain the
                 * right input, it will be detected.*/
                int u = tryParseInteger(line[0]);
                int v = tryParseInteger(line[1]);
                if (N == -1) {
                    N = u;
                    E = v;
                    for (int i = 0; i < N; i++)
                        graph.add(new ArrayList<Integer>());
                    // the next E lines will contain the edges
                }else {
                    // it is an edge
                    List<Integer> temp = graph.get(u);
                    temp.add(v);
                    graph.set(u, temp);
                    temp = graph.get(v);
                    temp.add(u);
                    graph.set(v, temp);
                    // Add edges u-v and v-u
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        }
        // Process the graph
        boolean visited[] = new boolean[N];
        for (int startVertex = 0; startVertex < N; startVertex ++) {
            // start dfs from startVertex if it is not yet visited
            if (!visited[startVertex]) {
                dfs(startVertex, visited);
            }
        }
        /* if the control reaches here, and there is any vertex such
         * that visited[vertex] = false then, the graph is not connected
         * as in a connected graph every vertex is reachable from
         * every other vertex */
        boolean disconnected = false;
        for (int i = 0; i < N; i++) {
            if (!visited[i])
                disconnected = true;
        }
        if (disconnected)
            System.out.println("The Graph is disconnected.");
        else
            System.out.println("The Graph is connected.");
    }

    public static void dfs(int u, boolean [] visited) {
        if (visited[u])
            return;
        visited[u] = true;
        for (int v: graph.get(u))
            if (!visited[v])
                dfs(v, visited);
        return;
    }
    public static int tryParseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Graph Input Invalid");
            System.exit(0);
        }
        return -1;
    }
}
