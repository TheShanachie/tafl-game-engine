package com.github.theshanachie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardReader {

    private BoardVal[][] board; // internal board array.

    public BoardReader( String path )
    {

    }

    public BoardVal[][] getBoard() { return board; }

    private void readFromFile( String path )
    {

    }

    private void readGridSize( char[][] grid )
    {

    }

    private void readGridContents( String path )
    {

    }

    private char[][] readRawFromFile( String path )
    {
        try {
            Scanner in = new Scanner(new File( path ));
            ArrayList<String> lineList = new ArrayList<String>();
            int len = -1;

            // reading a non-directed graph
            while (in.hasNextLine())
            {
                String line = in.nextLine();

                // check that the line length is valid.
                if (len <= 0) len = line.length();
                else if (len != line.length()) {
                    in.close();
                    return null;
                }

                // add each line to the final list.
                lineList.add(line);
            }

            // create 1d array and append items to final array
            if (lineList.size() > 0)
            {
                // break down the list
                char[][] grid = new char[lineList.size()][lineList.get(0).length()];
                for (int i = 0; i < lineList.size(); i++)
                {
                    char[] arr = lineList.get( i ).toCharArray();
                    grid[i] = arr;
                }

                // return the 2d char grid.
                in.close();
                return grid;
            } else {
                in.close();
                return null;
            }

        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }
}
