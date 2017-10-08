/*
 * subclass of Piece that represents chess piece Rook
 */

public class Rook extends Piece {
    private Color color;

    public Rook(Color color) {
        super(color);
    }

    public String algebraicName() {
        return "R";
    }

    public String fenName() {
        if (getColor().equals("BLACK")) {
            return "r";
        }
        return "R";
    }

    public Square[] movesFrom(Square square) {
        //find attacked squares, check for bounds, then get rid of nulls/extras
        //Only acceptable squares are file: a-h rank: 1-8
        //ASCII: 56 == '8', 49 == '1', 104 == 'h', 97=='a'

        Square[] attackedSquares = new Square[16];

        char rank = square.getRank();
        char file = square.getFile();

        //goes along column/file
        for (int i = 1; i <= 8; i++) {
            if (i != Character.getNumericValue(rank)) {
                //converts rank to a number
                attackedSquares[i - 1] = new Square(file, (char) (i + 48));
            }
        }

        //goes along row/rank
        for (int i = 1; i <= 8; i++) {
            if (i != file - 96) {   //converts file to a number
                attackedSquares[i + 7] = new Square((char) (i + 96), rank);
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
    //     Piece rook = new Rook(Color.BLACK);
    //     assert rook.algebraicName().equals("R");
    //     assert rook.fenName().equals("r");
    //
    //     Square[] attackedSquares = rook.movesFrom(new Square("e4"));
    //     for (Square sq: attackedSquares) {
    //         System.out.println(sq);
    //     }
    // }
}
