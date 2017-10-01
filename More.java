public class More{



    public static void disambiguate(char[][] chessBoard, String move,
            boolean white, char piece) {

        //pass in only uppercase
        char colorPiece;
        if (white) {
            colorPiece = piece;
        } else {
            colorPiece = (char) (piece + 32);
        }

        boolean allBlanks = true;
        boolean blank = true;

        if (move.charAt(1) == 'a' || move.charAt(1) == 'b'
                || move.charAt(1) == 'c' || move.charAt(1) == 'd'
                || move.charAt(1) == 'e' || move.charAt(1) == 'f'
                || move.charAt(1) == 'g' || move.charAt(1) == 'h') {

            //checks like a rook.
            //But we already know the column it's coming from.

            int arrayRowIndex = 8 - Character.getNumericValue(
                    move.charAt(3));
            int arrayColumnIndex = move.charAt(2) - 97;
            int oldQueenColumn = move.charAt(1) - 97;
            int oldQueenRow;

            for (int i = 0; i < 8; i++) {
                if (white) {
                    if (chessBoard[i][oldQueenColumn] == colorPiece) {
                        //check for pieces in the way - like bishop
                        chessBoard[i][oldQueenColumn] = ' ';
                        chessBoard[8 - Character.getNumericValue(
                            move.charAt(3))][move.charAt(2) - 97] = colorPiece;
                    }
                } else {
                    if (chessBoard[i][move.charAt(1) - 97] == colorPiece) {
                        chessBoard[i][move.charAt(1) - 97] = ' ';
                        chessBoard[8 - Character.getNumericValue(
                            move.charAt(3))][move.charAt(2) - 97] = colorPiece;
                    }
                }
            }
        } else if (move.charAt(1) == '1' || move.charAt(1) == '2'
                    || move.charAt(1) == '3' || move.charAt(1) == '4'
                    || move.charAt(1) == '5' || move.charAt(1) == '6'
                    || move.charAt(1) == '7' || move.charAt(1) == '8') {

            //Q1e1
            for (int i = 0; i < 8; i++) {
                if (white) {
                    if (chessBoard[8 - Character.getNumericValue(
                            move.charAt(1))][i] == colorPiece) {
                        chessBoard[8 - Character.getNumericValue(
                            move.charAt(1))][i] = ' ';
                    }
                    chessBoard[8 - Character.getNumericValue(
                        move.charAt(3))][move.charAt(2) - 97] = colorPiece;
                } else {
                    if (chessBoard[8 - Character.getNumericValue(
                            move.charAt(1))][i] == colorPiece) {
                        chessBoard[8 - Character.getNumericValue(
                            move.charAt(1))][i] = ' ';
                    }
                    chessBoard[8 - Character.getNumericValue(
                        move.charAt(3))][move.charAt(2) - 97] = colorPiece;
                }
            }
        }
    }












}
