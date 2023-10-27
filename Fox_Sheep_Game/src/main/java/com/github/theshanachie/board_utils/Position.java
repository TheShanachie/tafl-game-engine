package com.github.theshanachie.board_utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Position {
        public int col, row;
        public Position( int col, int row ) {
            this.col = col;
            this.row = row;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position))
                return false;
            if (obj == this)
                return true;

            Position rhs = (Position) obj;
            return new EqualsBuilder().
                    // if deriving: appendSuper(super.equals(obj)).
                    append(row, rhs.row).
                    append(col, rhs.col).
                    isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                    // if deriving: appendSuper(super.hashCode()).
                    append(col).
                    append(row).
                    toHashCode();
        }
    }