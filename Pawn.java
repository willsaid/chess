/*
 * subclass of Piece that represents chess piece Pawn,
 * even though it is technically not a piece
 */

public class Pawn extends Piece {
    private Color color;

    public Pawn(Color color) {
        super(color);
    }

    public String algebraicName() {
        return "";
    }

    public String fenName() {
        if (getColor().equals("BLACK")) {
            return "p";
        }
        return "P";
    }

    public Square[] movesFrom(Square square) {
        //find attacked squares, check for bounds, then get rid of nulls/extras
        //Only acceptable squares are file: a-h, rank: 1-8
        //ASCII: 56 == '8', 49 == '1', 104 == 'h', 97=='a'
        Square[] nullSquare = new Square[0]; //if reached the last row
        Square[] attackedSquares = new Square[2];
        boolean doubleJump = false;
        if (getColor().equals("BLACK")) {
            if (square.getRank() == '1') {
                return nullSquare;
            }
            attackedSquares[0] = new Square(square.getFile(),
                (char) (square.getRank() - 1));
            if (square.getRank() == '7') {
                attackedSquares[1] = new Square(square.getFile(),
                    (char) (square.getRank() - 2));
                doubleJump = true;
            }
        } else {  //WHITE
            if (square.getRank() == '8') {
                return nullSquare;
            }
            attackedSquares[0] = new Square(square.getFile(),
                (char) (square.getRank() + 1));
            if (square.getRank() == '2') {
                attackedSquares[1] = new Square(square.getFile(),
                    (char) (square.getRank() + 2));
                doubleJump = true;
            }
        }

        if (doubleJump) {
            return attackedSquares;
        }

        Square[] arrayWithoutNulls = new Square[1];
        arrayWithoutNulls[0] = attackedSquares[0];
        return arrayWithoutNulls;
    }

    // public static void main(String[] args) {
    //     Piece pawn = new Pawn(Color.BLACK);
    //     assert pawn.algebraicName().equals("");
    //     assert pawn.fenName().equals("p");
    //
    //     Square[] attackedSquares = pawn.movesFrom(new Square("e1"));
    //     for (Square sq: attackedSquares) {
    //         System.out.println(sq);
    //     }
    // }
}
