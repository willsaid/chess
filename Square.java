/*
 * Author: Will Said
 *
 * Date Released: 10/07/2017
 * github @willsaid
 *
 *
 *
 * This is a class to represent squares on a chess board.
 * Square is instantiable and has the following constructors and methods:
 *
 *  A public constructor Square(char file, char rank) which uses a file
 *    name such as 'a' and rank name such as '1' to initialize instance
 *    variables that store the file and rank (as chars), as well as
 *    the String name of the square that would be returned by toString()
 *    (see below).
 *
 *  A public constructor Square(String name) which uses a square name such as
 *    "a1" to initialize the instance variables described in the other
 *    constructor above.
 *
 *  A public instance method toString() which returns a String representation
 *     of the square name, e.g., "a1".
 *
 *  A properly written equals method that overrides the equals method from
 *     java.lang.Object and returns true for Square objects that have the
 *     same file and rank values, false otherwise.
 *
 */

public class Square {

    private char file;
    private char rank;
    private String fileAndRank;

    public Square(char file, char rank) {
        this.file = file;  //e or f etc
        this.rank = rank;  // 1 or 2 etc
        fileAndRank = Character.toString(file) + rank;
    }

    public Square(String name) {
        fileAndRank = name;
        file = name.charAt(0);
        rank = name.charAt(1);
    }

    public String toString() {
        return fileAndRank;
    }

    public char getFile() {
        return file;
    }

    public char getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Square)) {
            return false;
        }
        Square otherSquare = (Square) other;
        return this.fileAndRank.equals(otherSquare.fileAndRank);
    }

    // public static void main(String[] args) {
    //     Square a1 = new Square("a1");
    //     Square otherA1 = new Square('a', '1');
    //     Square h8 = new Square("h8");
    //     assert a1.equals(otherA1);
    //     assert !a1.equals(h8);
    // }
}
