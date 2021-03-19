package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws IOException
    {

        BufferedReader br = new BufferedReader(new FileReader("twoedges.txt"));
        Scanner scan = new Scanner(new File("twoedges.txt"));
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
                System.out.println(u + " " + v );
            }
            index++;
        }scan.close();

    }


}
