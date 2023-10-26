package com.github.theshanachie.board_utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Adjacency List Reader for creating adjacency list from a properly formatted .txt file.
 */
public class AdjacencyListReader {

    private Map<Position, ArrayList<Position>> map; // internal adjacency multi-map.

    /**
     * Constructor for AdjacencyList.
     * Reads adjacency list from filename in correct Format.
     * @param path is the filepath to the adjacency list document to be read.
     * @throws Exception
     */
    public AdjacencyListReader( String path ) throws Exception
    {
        try {
            map = new HashMap<Position, ArrayList<Position>>();
            readFromFile(path);
        } catch (Exception ex) {
            throw new Exception("Problem reading adjacency list from path: " + path + " in AdjacencyListReader constructor", ex);
        }
    }

    /**
     * Get the internal adjacency list multimap.
     * @return the internal adjacency list multimap in the form 'Map<Position, ArrayList<Position>>'.
     */
    public Map<Position, ArrayList<Position>> get_adjacency_list() { return map; }

    /**
     * Read an adjacency list from a file.
     * @param file is the filepath to the adjacency list document to be read.
     * @throws Exception
     */
    private void readFromFile( String file ) throws Exception
    {
        try {
            Scanner in = new Scanner(new File(file));

            // reading a non-directed graph
            while (in.hasNext()) {
                // read the strings
                String s = in.next();
                String e = in.next();

                // get position values from strings
                Position start = read_position(s);
                Position end = read_position(e);

                // add new paths to the list
                addUnDirectedEdge(start, end);
            }
            in.close();
        } catch (Exception ex) {
            throw new Exception("Problem reading from path: " + file + "in function: readFromFile", ex);
        }
    }

    /**
     * Add an undirected edge to the multimap of adjacent points.
     * @param start is a Position object holding the start coordinates.
     * @param end is a Position object holding the end coordinates.
     */
    private void addUnDirectedEdge( Position start, Position end )
    {
        // Add start value.
        if (map.get(start) == null) {
            map.put(start, new ArrayList<Position>());
            map.get(start).add(end);
        } else if (!map.get(start).contains(end)) {
            map.get(start).add(end);
        }

        // Add end value.
        if (map.get(end) == null) {
            map.put(end, new ArrayList<Position>());
            map.get(end).add(start);
        } else if (!map.get(end).contains(start)) {
            map.get(end).add(start);
        }
    }

    /**
     * Read a position from a String, while it is in the format designated for adjacency list text files
     * @param str is a String in the format "(x:#, y:#)" where '#' is a whole positive integer. And the x, y values correspond to row, column values in a 2D java array.
     * @return a Position object, holding the row, col values.
     * @throws Exception
     */
    private Position read_position(String str) throws Exception
    {
        try {
            String[] strings = str.split("[(),x: y]+");
            return new Position( string_to_int(strings[0]), string_to_int(strings[1]) );
        } catch (Exception ex) {
            throw new Exception("Problem parsing position argument: " + str + " in function: read_position", ex);
        }
    }

    /**
     * If possible, transform a string into a primitive int value.
     * @param str is a string that looks like a primitive int value.
     * @return a positive primitive int value, or negative an int value if the String could not be parsed correctly.
     * @throws Exception
     */
    private int string_to_int( String str ) throws Exception
    {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex){
            throw new Exception("The value: " + str + " passed to function: string_to_int is not valid.", ex);
        }
    }
}
