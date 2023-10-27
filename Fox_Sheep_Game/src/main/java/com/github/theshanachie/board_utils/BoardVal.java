package com.github.theshanachie.board_utils;

public enum BoardVal {
    FOX('f'), GOOSE('g'), INVALID('#'), EMPTY('o');

    public char key;
    BoardVal(char key) { this.key = key; }
}
