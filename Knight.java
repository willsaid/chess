/*
*   subclass of Piece that represents chess piece Knight
*/

public class Knight extends Piece {
    private Color color;

    public Knight(Color color) {
        super(color);
    }

    public String algebraicName() {
        return "N";
    }

    public String fenName() {
        if (getColor().equals("BLACK")) {
            return "n";
        }
        return "N";
    }

    public Square[] movesFrom(Square square) {
        //find attacked squares, check for bounds, then get rid of nulls/extras
        //Only acceptable squares are file: a-h rank: 1-8
        //ASCII: 56 == '8', 49 == '1', 104 == 'h', 97=='a'

        Square[] attackedSquares = new Square[8];

        char rank = square.getRank();
        char file = square.getFile();
        int rankIndex = rank - 48;  //converts '1' to int 1
        int fileIndex = file - 96;  //converts 'a' to int 1

        if (rankIndex + 2 <= 8 && fileIndex - 1 >= 1) {
            attackedSquares[0]
                = new Square((char) (file - 1), (char) (rank + 2));
        }
        if (rankIndex + 2 <= 8 && fileIndex + 1 <= 8) {
            attackedSquares[1]
                = new Square((char) (file + 1), (char) (rank + 2));
        }
        if (rankIndex - 2 >= 1 && fileIndex - 1 >= 1) {
            attackedSquares[2]
                = new Square((char) (file - 1), (char) (rank - 2));
        }
        if (rankIndex - 2 >= 1 && fileIndex + 1 <= 8) {
            attackedSquares[3]
                = new Square((char) (file + 1), (char) (rank - 2));
        }
        if (rankIndex + 1 <= 8 && fileIndex - 2 >= 1) {
            attackedSquares[4]
                = new Square((char) (file - 2), (char) (rank + 1));
        }
        if (rankIndex + 1 <= 8 && fileIndex + 2 <= 8) {
            attackedSquares[5]
                = new Square((char) (file + 2), (char) (rank + 1));
        }
        if (rankIndex - 1 >= 1 && fileIndex - 2 >= 1) {
            attackedSquares[6]
                = new Square((char) (file - 2), (char) (rank - 1));
        }
        if (rankIndex - 1 >= 1 && fileIndex + 2 <= 8) {
            attackedSquares[7]
                = new Square((char) (file + 2), (char) (rank - 1));
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
    //
    // public static void main(String[] args) {
    //     Piece knight = new Knight(Color.BLACK);
    //     assert knight.algebraicName().equals("N");
    //     assert knight.fenName().equals("n");
    //
    //     Square[] attackedSquares = knight.movesFrom(new Square("h8"));
    //     for (Square sq: attackedSquares) {
    //         System.out.println(sq);
    //     }
    // }
}
