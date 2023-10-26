package com.github.theshanachie.board_utils;

public enum BoardVal {
    FOX('f'), GOOSE('g'), INVALID('#'), EMPTY('o');

    char key;
    BoardVal(char key) { this.key = key; }

    /**
     * Translate a primitive character to an enum BoardVal. Each enum in BoardVal is mapped directly to a character.
     * @param value is a primitive character.
     * @return an enum in BoardVal that corresponds to value.
     * @throws IllegalArgumentException
     */
    public BoardVal getValue( char value ) throws Exception
    {
        return switch (value) {
            case 'o' -> BoardVal.EMPTY;
            case '#' -> BoardVal.INVALID;
            case 'g' -> BoardVal.GOOSE;
            case 'f' -> BoardVal.FOX;
            default -> throw new IllegalArgumentException();
        };
    }
}
