package com.github.theshanachie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdjacencyList {
    private Map<Position, ArrayList<Position>> hm;

    public AdjacencyList( String file )
    {
        hm = new HashMap<Position, ArrayList<Position>>();
        readFromFile(file);
    }

    public void readFromFile( String file ) 
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
        } catch (IOException e) {
            System.err.println(e);
        }
    }

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

    private Position read_position( String str) 
    {
        String[] strings = str.split("[(),x: y]+");
        return new Position( string_to_int(strings[0]), string_to_int(strings[1]) );
    }

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

    public void print_adjacency_list()
    {
        for (Map.Entry<Position, ArrayList<Position>> entry : hm.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
