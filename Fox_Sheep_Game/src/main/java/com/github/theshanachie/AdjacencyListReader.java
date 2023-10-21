package com.github.theshanachie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdjacencyListReader {

    private Map<Position, ArrayList<Position>> hm = null; // internal adjacency multi-map.

    /**
     * Constructor for AdjacencyList.
     * Reads adjacency list from filename in format -- TBD
     * @param path is the filepath to the adjacency list document to be read.
     */
    public AdjacencyListReader( String path )
    {
        hm = new HashMap<Position, ArrayList<Position>>();
        readFromFile(path);
    }

    /**
     * Get the internal adjacency list multi-map.
     * @return the internal adjacency list multi-map in the form 'Map<Position, ArrayList<Position>>'.
     */
    public Map<Position, ArrayList<Position>> get_adjacency_list() { return hm; }

    private void readFromFile( String file )
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
            in.closse();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Add an undirected edge to the multi-map of adjacent points.
     * @param start is a Position object holding the start coordinates.
     * @param end is a Position object holding the end coordinates.
     */
    private void addUnDirectedEdge( Position start, Position end)
    {
        // Add start value.
        if (hm.get(start) == null) {
            hm.put(start, new ArrayList<Position>());
            hm.get(start).add(end);
        } else if (!hm.get(start).contains(end)) {
            hm.get(start).add(end);
        }

        // Add end value.
        if (hm.get(end) == null) {
            hm.put(end, new ArrayList<Position>());
            hm.get(end).add(start);
        } else if (!hm.get(end).contains(start)) {
            hm.get(end).add(start);
        }
    }

    /**
     * Read a position from a String, while it is in the format designated for adjacency list text files
     * @param str is a String in the format "(x:#, y:#)" where '#' is a whole positive integer. And the x, y values correspond to row, column values in a 2D java array.
     * @return a Position object, holding the row, col values.
     */
    private Position read_position( String str) 
    {
        String[] strings = str.split("[(),x: y]+");
        return new Position( string_to_int(strings[0]), string_to_int(strings[1]) );
    }

    /**
     * If possible, transform a string into a primitive int value.
     * @param str is a string that looks like a primitive int value.
     * @return a positive primitive int value, or negative a int value if the String could not be parsed correctly.
     */
    private int string_to_int( String str )
    {
        try{
            int number = Integer.parseInt(str);
            return number;
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
            System.err.println("The value: " + str + " passed to funtion: string_to_int is not valid.");
            return -1;
        }
    }

//    public void print_adjacency_list()
//    {
//        for (Map.Entry<Position, ArrayList<Position>> entry : hm.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//    }
}
