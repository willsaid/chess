/*
 *  subclass of Piece that represents chess piece Queen
 */

public class Queen extends Piece {
    private Color color;

    public Queen(Color color) {
        super(color);
    }

    public String algebraicName() {
        return "Q";
    }

    public String fenName() {
        if (getColor().equals("BLACK")) {
            return "q";
        }
        return "Q";
    }

    public Square[] movesFrom(Square square) {
        //find attacked squares, check for bounds, then get rid of nulls/extras
        //Only acceptable squares are file: a-h rank: 1-8
        //ASCII: 56 == '8', 49 == '1', 104 == 'h', 97=='a'

        Square[] attackedSquares = new Square[45];

        char rank = square.getRank();
        char file = square.getFile();
        int rankIndex = rank - 48;  //converts '1' to int 1
        int fileIndex = file - 96;  //converts 'a' to int 1

        //check like bishop first
        for (int i = 1; i <= 7; i++) {
            if (fileIndex + i <= 8 && rankIndex + i <= 8) {
                attackedSquares[i]
                    = new Square((char) (file + i), (char) (rank + i));
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (fileIndex + i <= 8 && rankIndex - i >= 1) {
                attackedSquares[i + 7]
                    = new Square((char) (file + i), (char) (rank - i));
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (fileIndex - i >= 1 && rankIndex - i >= 1) {
                attackedSquares[i + 14]
                    = new Square((char) (file - i), (char) (rank - i));
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (fileIndex - i >= 1 && rankIndex + i <= 8) {
                attackedSquares[i + 21]
                    = new Square((char) (file - i), (char) (rank + i));
            }
        }
        //check like rook now
        //goes along column/file
        for (int i = 1; i <= 8; i++) {
            if (i != Character.getNumericValue(rank)) {
                //converts rank to a number
                attackedSquares[i + 28] = new Square(file, (char) (i + 48));
            }
        }

        //goes along row/rank
        for (int i = 1; i <= 8; i++) {
            if (i != file - 96) {   //converts file to a number
                attackedSquares[i + 36] = new Square((char) (i + 96), rank);
            }
        }

        Square[] arrayWithNullsAtEnd = new Square[attackedSquares.length];
        int count = 0;
        for (int i = 0; i < attackedSquares.length; i++) {
            if (attackedSquares[i] != null) {
                arrayWithNullsAtEnd[count] = attackedSquares[i];
                count++;
            }
        }
        Square[] arrayWithoutNulls = new Square[count];
        for (int i = 0; i < count; i++) {
            arrayWithoutNulls[i] = arrayWithNullsAtEnd[i];
        }

        return arrayWithoutNulls;
    }

    // public static void main(String[] args) {
    //     Piece queen = new Queen(Color.BLACK);
    //     assert queen.algebraicName().equals("Q");
    //     assert queen.fenName().equals("q");
    //
    //     Square[] attackedSquares = queen.movesFrom(new Square("e4"));
    //     for (Square sq: attackedSquares) {
    //         System.out.println(sq);
    //     }
    //     Square a1 = new Square("a1");
    //     Square otherA1 = new Square('a', '1');
    //     Square h8 = new Square("h8");
    //     assert a1.equals(otherA1);
    //     assert !a1.equals(h8);
    // }
}
