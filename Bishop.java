/*
 *  subclass of Piece that represents chess piece Bishop
 */

public class Bishop extends Piece {
    private Color color;

    public Bishop(Color color) {
        super(color);
    }

    public String algebraicName() {
        return "B";
    }

    public String fenName() {
        if (getColor().equals("BLACK")) {
            return "b";
        }
        return "B";
    }

    public Square[] movesFrom(Square square) {
        //find attacked squares, check for bounds, then get rid of nulls/extras
        //Only acceptable squares are file: a-h rank: 1-8
        //ASCII: 56 == '8', 49 == '1', 104 == 'h', 97=='a'

        Square[] attackedSquares = new Square[28];

        char rank = square.getRank();
        char file = square.getFile();
        int rankIndex = rank - 48;  //converts '1' to int 1
        int fileIndex = file - 96;  //converts 'a' to int 1

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
    //     Piece bishop = new Bishop(Color.BLACK);
    //     assert bishop.algebraicName().equals("B");
    //     assert bishop.fenName().equals("b");
    //
    //     Square[] attackedSquares = bishop.movesFrom(new Square("a1"));
    //     for (Square sq: attackedSquares) {
    //         System.out.println(sq);
    //     }
    // }
}
