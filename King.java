/*
*   subclass of Piece that represents chess piece King
*/

public class King extends Piece {
    private Color color;

    public King(Color color) {
        super(color);
    }

    public String algebraicName() {
        return "K";
    }

    public String fenName() {
        if (getColor().equals("BLACK")) {
            return "k";
        }
        return "K";
    }

    public Square[] movesFrom(Square square) {
        //find attacked squares, check for bounds, then get rid of nulls/extras
        //Only acceptable squares are file: a-h, rank: 1-8
        Square[] attackedSquares = new Square[8];
        if (square.getRank() + 1 <= 56) {   //56 == '8' in ASCII
            attackedSquares[0] = new Square(square.getFile(),
                (char) (square.getRank() + 1));

            if (square.getFile() + 1 <= 104) {  //104 == 'h'
                attackedSquares[1] = new Square((char) (square.getFile() + 1),
                    (char) (square.getRank() + 1));
            }
            if (square.getFile() - 1 >= 97) {  //97 == 'a'
                attackedSquares[2] = new Square((char) (square.getFile() - 1),
                    (char) (square.getRank() + 1));
            }
        }
        if (square.getRank() - 1 >= 49) {   //49 == '1' in ASCII
            attackedSquares[3] = new Square(square.getFile(),
                (char) (square.getRank() - 1));

            if (square.getFile() + 1 <= 104) {  //104 == 'h'
                attackedSquares[4] = new Square((char) (square.getFile() + 1),
                    (char) (square.getRank() - 1));
            }
            if (square.getFile() - 1 >= 97) {  //97 == 'a'
                attackedSquares[5] = new Square((char) (square.getFile() - 1),
                    (char) (square.getRank() - 1));
            }
        }
        if (square.getFile() + 1 <= 104) {  //104 == 'h'
            attackedSquares[6] = new Square((char) (square.getFile() + 1),
                square.getRank());
        }
        if (square.getFile() - 1 >= 97) {  //97 == 'a'
            attackedSquares[7] = new Square((char) (square.getFile() - 1),
                square.getRank());
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
    //     Piece king = new King(Color.BLACK);
    //     assert king.algebraicName().equals("K");
    //     assert king.fenName().equals("k");
    //
    //     Square[] attackedSquares = king.movesFrom(new Square("e5"));
    //     for (Square sq: attackedSquares) {
    //         System.out.println(sq);
    //     }
    // }
}
