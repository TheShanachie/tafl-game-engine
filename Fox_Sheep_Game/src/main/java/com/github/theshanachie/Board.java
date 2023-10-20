package com.github.theshanachie;

import java.util.ArrayList;

public class Board {
    enum BoardVal {
        FOX,
        GOOSE,
        INVALID,
        EMPTY
    }

    private BoardVal[][] board;
    private static ArrayList<ArrayList<Position>> adj_list;

    public Board() {
        board = new BoardVal[9][9];
        board = initialize_board( board );
        adj_list = initialize_adjacency( board );
    }

    private BoardVal[][] initialize_board( BoardVal[][] board ) 
    {
        return null;
    }

    private ArrayList<ArrayList<Position>> initialize_adjacency( BoardVal[][] board ) 
    {
        return null;
    }
}
