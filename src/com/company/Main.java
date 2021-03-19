package com.company;
//Jay Thompson
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main
{
    public static String E;
    public static String fileName;

    public static void main(String[] args)
    {
        System.out.println("Please enter file name: ");
        Scanner scan = new Scanner(System.in);
        fileName = scan.nextLine();
        scanMethod(fileName);
    }

    public static void scanMethod(String fileName){
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            String line;
            while((line = br.readLine()) != null)
            {
                try{
                    E = line.split("\t")[1].trim();
                    EHandler(E);
                    break;
                }catch(ArrayIndexOutOfBoundsException exce){
                    System.out.println("File does not adhere to file format standards.");
                }

            }
        }catch(IOException e){
            System.out.println("Graph File not found. Please try again.");
            Scanner scan = new Scanner(System.in);
            fileName = scan.nextLine();
            scanMethod(fileName);
        }

    }

    public static void EHandler(String E)
    {
        try{
            int e = Integer.parseInt(E);
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            int lineCount = 0;
            while(br.readLine() != null){ lineCount++; }
            int linesNeeded = e + 1;
            if(linesNeeded != lineCount)
            {
                System.out.println("File does not adhere to file format standards.");
                System.exit(0);
            }
            Graph graph = new Graph(e);
            Scanner scan = new Scanner(new File(fileName));
            String str;

            int index = 0;
            while(scan.hasNext())
            {
                str = scan.nextLine();
                if(index > 0)
                {
                    String line[] = str.split("\t");
                    int u = Integer.parseInt(line[0]);
                    int v = Integer.parseInt(line[1]);
                    graph.addEdge(u, v);

                }
                index++;
            }
            scan.close();
            if(graph.isGraphConnected() == true){
                System.out.println("Graph is Connected.");
            }else {
                System.out.println("Graph is not Connected.");
            }
        }catch(IOException ex){
            System.out.println("Graph not found. Please try again.");
            Scanner scan = new Scanner(System.in);
            fileName = scan.nextLine();
            scanMethod(fileName);
        }
    }
}


//graph class tutorial: https://www.youtube.com/watch?v=52NDeYkVek0
//please ignore all comments beyond this, they're personal notes I took while watching the video
class Graph
{
    //becuase of the set up of the edges (ie there being (u, v) it's better to have a
    //list in a list. it can alternatively be done with an arraylist inside an arraylist
    List<List<Integer>> graph;
    boolean here[]; //this lets you know where you are
    int nodes; //amount of nodes

    Graph(int nodes)
    {
        this.nodes = nodes;
        graph = new ArrayList<>();
        here = new boolean[nodes];
        //this counts the nodes
        for (int i = 0; i < nodes; i++) {graph.add(i, new ArrayList<>());}
    }

    public void addEdge(int a, int b)
    {
        try{
            //because this is an undirected graph, it has it going both ways
            graph.get(a).add(b);
            graph.get(b).add(a);
        }catch(IndexOutOfBoundsException exc) {
            //this is for graphs that are particularly wack
            System.out.println("Graph is not connected.");
            System.exit(0);
        }
    }

    public boolean isGraphConnected()
    {
        int startIndex = 0;
        DFS(startIndex);

        for(int i = 0; i < here.length; i++)
        {
            if(!here[i])
            {
                return false;
            }
        }
        return true;
    }

    public void DFS(int start)
    {
        //this tracks all of the neighbors, and if you've been there it checks it as seen
        //the reason its a stack is so that its easier to count
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        here[start] = true;

        while(!stack.isEmpty())
        {
            Integer node = stack.pop();
            List<Integer> neighborList = graph.get(node);
            for(Integer horse: neighborList)
            {
                if(!here[horse])
                {
                    stack.push(horse);
                    here[horse] = true;
                }
            }
        }
    }
}
