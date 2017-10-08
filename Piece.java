/*
 * Author: Will Said
 *
 * Date Released: 10/07/2017
 * github @willsaid
 *
 *
 *
 * This is the Piece Super class that represents all chess pieces.
 * It includes:
 *  A public constructor that takes a Color parameter and stores its value in
 *     an instance variable
 *
 *  A public getColor() instance method that returns the Color of the piece
 *
 *  A public abstract instance method algebraicName() which returns a String
 *    containing the algebraic name of the piece, e.g., "" for pawns,
 *    or one of "K", "Q", "B", "N", "R".
 *
 *  A public abstract instance method fenName() which returns a String
 *    containing the FEN name for the piece.
 *
 *  A public abstract instance method movesFrom(Square square) which returns a
 *    Square[] containing all the squares the piece could move to, on
 *    a chess board containing only the piece.
 *
 */

public abstract class Piece {

    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public String getColor() {
        return color.name();
    }

    public abstract String algebraicName();
    public abstract String fenName();
    public abstract Square[] movesFrom(Square square);

}
