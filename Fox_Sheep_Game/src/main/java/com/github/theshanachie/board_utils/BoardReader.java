package com.github.theshanachie.board_utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Board Reader for creating Board Array from a properly formatted .txt file.
 */
public class BoardReader {

    private BoardVal[][] board; // internal board array.

    /**
     * Constructor for the Class Board.
     * Reads Board from filename in correct Format.
     * @param path is String containing the file path to read from.
     * @throws Exception
     */
    public BoardReader( String path ) throws Exception
    {
        try {
            board = readFromFile(path);
        } catch (Exception ex) {
            throw new Exception("Error reading from file: " + path + " in BoardReader Constructor.", ex);
        }
    }

    /**
     * Get the internal 2d BoardVal Array.
     * @return the internal two-dimensional array of type BoardVal
     */
    public BoardVal[][] getBoard() { return board; }

    /**
     * Read from a file path and generate the game board.
     * @param path is String containing the file path to read from.
     * @return two-dimensional array of type BoardVal
     * @throws Exception
     */
    private BoardVal[][] readFromFile( String path ) throws Exception
    {
        try {
            char[][] grid = readRawFromFile(path);
            return translateGridContents( grid );
        } catch (Exception ex) {
            throw new Exception("Error reading from file: " + path + " in function: readFromFile.", ex);
        }
    }

    /**
     * Create the internal board from an array of primitive characters. Each enum in the new array is mapped from a specific primitive character.
     * @param grid is a two-dimensional primitive character array.
     * @return a two-dimensional array of enum BoardVal, where each enum corresponds to a specific character.
     * @throws Exception
     */
    private BoardVal[][] translateGridContents( char[][] grid ) throws Exception
    {
        // Create a grid with a valid size.
        BoardVal[][] result = new BoardVal[grid.length][grid[0].length];

        // iterate through all grid/board positions.
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                BoardVal tempVal = translateChar(grid[i][j]);
                if (tempVal == null) throw new Exception("Invalid Character within argument: grid, passed to function: translateGridContents");
                result[i][j] = tempVal;
            }
        }

        // return the grid of Enums.
        return result;
    }

    /**
     * Read from a file and retrieve a two-dimensional array of primitive characters.
     * Each line in the file at 'path' should be of equivalent length.
     * @param path is String containing the file path to read from.
     * @return a two-dimensional array of primitive characters.
     * @throws Exception
     */
    private char[][] readRawFromFile( String path ) throws Exception
    {
        try {
            Scanner in = new Scanner(new File( path ));
            ArrayList<String> lineList = new ArrayList<String>();
            int len = -1;

            // reading a non-directed graph
            while ( in.hasNextLine() )
            {
                String line = in.nextLine();

                // check that the line length is valid.
                if (len <= 0) len = line.length();
                else if (len != line.length()) {
                    in.close();
                    throw new Exception("Error reading from file: " + path + " in function: readRawFromFile. Each line in the file at 'path' should be of equivalent length.");
                }

                // add each line to the final list.
                lineList.add(line);
            }

            // create 1d array and append items to final array
            if ( !lineList.isEmpty() )
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
                throw new Exception("Error reading from file: " + path + " in function: readRawFromFile.");
            }

        } catch (IOException ex) {
            throw new Exception("Error reading from file: " + path + " in function: readRawFromFile.", ex);
        }
    }

    /**
     * Translate a primitive character to an enum BoardVal. Each enum in BoardVal is mapped directly to a character.
     * @param value is a primitive character.
     * @return an enum in BoardVal that corresponds to value.
     */
    private BoardVal translateChar( char value )
    {
        return switch (value) {
            case 'o' -> BoardVal.EMPTY;
            case '#' -> BoardVal.INVALID;
            case 'g' -> BoardVal.GOOSE;
            case 'f' -> BoardVal.FOX;
            default -> null;
        };
    }
}
