/*
*
* By Will Said
*
* September 24, 2017
*
* Georgia Institute of Technology
*
* Takes in an input file of chess moves in PGN format
* and prints out the final board positions in FEN notation.
*
* Example:
*
* INPUT file: "morphy-isouard-karl-1958",
*   which contains the moves and tag values:
*
*    [Event "A Night at the Opera"]
*    [Date "1958"]
*    [White "Morphy, Paul"]
*    [Black "Comte Isouard de Vauvenargues and Karl II, Duke of Brunswick"]
*    1. e4 e5 2. Nf3 d6 3. d4 Bg4 4. dxe5 Bxf3 5. Qxf3 dxe5 6. Bc4 Nf6
*    7. Qb3 Qe7 8. Nc3 c6 9. Bg5 b5 10. Nxb5 cxb5 11. Bxb5+ Nbd7 12. O-O-O Rd8
*    13. Rxd7 Rxd7 14. Rd1 Qe6 15. Bxd7+ Nxd7 16. Qb8+ Nxb8 17. Rd8#
*
*
* Run from the command line, this program would OUTPUT:
*
*
*    PS C:\Users\wills\cs1331\Homework1>
*        java PgnReader morphy-isouard-karl-1958.pgn
*
*    Event: A Night at the Opera
*    Site: NOT GIVEN
*    Date: 1958
*    Round: NOT GIVEN
*    White: Morphy, Paul
*    Black: Comte Isouard de Vauvenargues and Karl II, Duke of Brunswick
*    Result: NOT GIVEN
*    Final Position:
*    1n1Rkb1r/p4ppp/4q3/4p1B1/4P3/8/PPP2PPP/2K5
*
*
* This program determines the final position of games that include:
*   Castling
*   Pawn Promotions
*   En Passant
*   Disambiguation of starting file or rank
*   Pinned Pieces and prevention against illegal moves
*   And more
*/




import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PgnReader {

    public static void main(String[] args) {

        //the PGN input file is now this String
        String game = fileContent(args[0]);

        //Prints out the tag values given in the input
        System.out.println();
        System.out.format("Event: %s%n", tagValue("Event", game));
        System.out.format("Site: %s%n", tagValue("Site", game));
        System.out.format("Date: %s%n", tagValue("Date", game));
        System.out.format("Round: %s%n", tagValue("Round", game));
        System.out.format("White: %s%n", tagValue("White", game));
        System.out.format("Black: %s%n", tagValue("Black", game));
        System.out.format("Result: %s%n", tagValue("Result", game));

        //prints out what the board looks like at the end of the game
        System.out.println("Final Position:");
        System.out.println(finalPosition(game));
        System.out.println();
    }


    /*
     * Finds the tagName tag pair in a PGN game and return its value.
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
     */



    public static String tagValue(String tagName, String game) {
        for (int i = 0; i < game.length(); i++) {
            for (int j = game.length(); j > i; j--) {
                if (game.substring(i, j).equals(tagName)) {
                    for (int k = j + 2; k < game.length(); k++) {
                        if (game.charAt(k) == '\"') {
                            return game.substring(j + 2, k);
                        }
                    }
                }
            }
        }
        return "NOT GIVEN";
    }

    /**
     * Play out the moves in game and return a String with the game's
     * final position in Forsyth-Edwards Notation (FEN).
     * @see http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm#c16.1
     */





    public static String finalPosition(String game) {

        char[][] chessBoard = {
            {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};

        String[] moves = findMoves(game);

        makeTheMoves(chessBoard, moves);

        return printFinalPosition(chessBoard);

    }







    public static String[] findMoves(String game) {

        int startOfGame = 0;
        for (int i = 0; i < game.length(); i++) {
            if (game.charAt(i) == '1' && game.charAt(i + 1) == '.') {
                startOfGame = i;
                break;
            }
        }
        //new string, of the game without tag values
        String allMovesString = game.substring(startOfGame);
        //separates string wherever the regular expression " " is
        String[] movesIncludingMoveNumber = allMovesString.split(" ");

        // for (int i = 0; i < movesIncludingMoveNumber.length; i++) {
        //     System.out.println(movesIncludingMoveNumber[i]);
        // }

    /*  Prints
        1.
        e4
        e5
        2.
        Nf3
        Nc6
        ...        */
        //Now i will get rid of the move numbers like (1.) and (2.).
        String movesStringWithSpaces = "";
        for (int i = 1; i < movesIncludingMoveNumber.length; i++) {
            if (i % 3 != 0) {
                movesStringWithSpaces += movesIncludingMoveNumber[i] + " ";
            }
        }
        String[] moves = movesStringWithSpaces.split(" ");

        /* Prints:

        And now i have what i want: each element in the moves array is a move.
        */

        return moves;
    }







    public static void makeTheMoves(char[][] chessBoard, String[] moves) {

        for (int i = 0; i < moves.length; i++) {
            //PAWNS. example: e4
            if (moves[i].charAt(0) == 'a' || moves[i].charAt(0) == 'b'
                    || moves[i].charAt(0) == 'c' || moves[i].charAt(0) == 'd'
                    || moves[i].charAt(0) == 'e' || moves[i].charAt(0) == 'f'
                    || moves[i].charAt(0) == 'g' || moves[i].charAt(0) == 'h') {

                if (i % 2 == 0) {  //white's move. true.
                    movePawn(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    movePawn(chessBoard, moves[i], false);
                }
            } else if (moves[i].charAt(0) == 'Q') {
                //QUEENS. example: QH4
                if (i % 2 == 0) {  //white's move. true.
                    moveQueen(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    moveQueen(chessBoard, moves[i], false);
                }
            } else if (moves[i].charAt(0) == 'B') {
                //BISHOPS. example: Bc4
                if (i % 2 == 0) {  //white's move. true.
                    moveBishop(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    moveBishop(chessBoard, moves[i], false);
                }
            } else if (moves[i].charAt(0) == 'K') {
                //KINGS. example: KE2
                if (i % 2 == 0) {  //white's move. true.
                    moveKing(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    moveKing(chessBoard, moves[i], false);
                }
            } else if (moves[i].charAt(0) == 'N') {
                //KNIGHTS. Nf3, Nxb4
                if (i % 2 == 0) {  //white's move. true.
                    moveKnight(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    moveKnight(chessBoard, moves[i], false);
                }
            } else if (moves[i].charAt(0) == 'O' || moves[i].charAt(0) == '0') {
                //castling, O-O-O O-O
                if (i % 2 == 0) {  //white's move. true.
                    castle(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    castle(chessBoard, moves[i], false);
                }
            } else if (moves[i].charAt(0) == 'R') {
                //ROOKS. Re8
                if (i % 2 == 0) {  //white's move. true.
                    moveRook(chessBoard, moves[i], true);
                } else if (i % 2 != 0) {
                    moveRook(chessBoard, moves[i], false);
                }
            }
        }
    }








    public static void moveRook(char[][] chessBoard, String move1,
            boolean white) {

        String move = getRidOfX(move1);


        //disambiguate
        //R1e7 or Rae7+ or Ra1e7
        // boolean doubleDisambiguates = false;

        if (move.length() >= 4 && (move.charAt(2) == 'a'
                || move.charAt(2) == 'b' || move.charAt(2) == 'c'
                || move.charAt(2) == 'd' || move.charAt(2) == 'e'
                || move.charAt(2) == 'f' || move.charAt(2) == 'g'
                || move.charAt(2) == 'h')) {

            disambiguate(chessBoard, move, white, 'R');

        } else if (move.charAt(1) == '1' || move.charAt(1) == '2'
                        || move.charAt(1) == '3' || move.charAt(1) == '4'
                        || move.charAt(1) == '5' || move.charAt(1) == '6'
                        || move.charAt(1) == '7' || move.charAt(1) == '8') {

            disambiguate(chessBoard, move, white, 'R');

        } else {    //no disambiguation

            if (white) {
                checkLikeRook(chessBoard, move, 'R');
            } else {
                checkLikeRook(chessBoard, move, 'r');
            }

        }
    }










    public static int checkLikeRook(char[][] chessBoard, String move,
            char piece) {

        //in Rc3, or Rxc3:
        // turns the '3' into [5] <- (arrayRowIndex)
        //  and 'c' into [2] <- (arrayColumnIndex)

        int arrayRowIndex = (8 - Character.getNumericValue(move.charAt(2)));
        int arrayColumnIndex = (move.charAt(1) - 97);
        boolean blank = true;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {

                if ((chessBoard[row][column] == piece)
                        && ((row == arrayRowIndex)
                        || (column == arrayColumnIndex))) {
                    if (row == arrayRowIndex) {
                        if (column < arrayColumnIndex) {
                            blank = true;
                            for (int i = column + 1;
                                i < arrayColumnIndex; i++) {
                                if (chessBoard[row][i] != ' ') {
                                    blank = false;
                                }
                            }
                            if (blank) {
                                chessBoard[row][column] = ' ';
                                chessBoard[arrayRowIndex]
                                    [arrayColumnIndex] = piece;
                                return 0;
                            }
                        } else {
                            blank = true;
                            for (int i = column - 1;
                                i > arrayColumnIndex; i--) {
                                if (chessBoard[row][i] != ' ') {
                                    blank = false;
                                }
                            }
                            if (blank) {
                                chessBoard[row][column] = ' ';
                                chessBoard[arrayRowIndex]
                                    [arrayColumnIndex] = piece;
                                return 0;
                            }
                        }
                    } else if (column == arrayColumnIndex) {
                        blank = true;
                        if (row < arrayRowIndex) {
                            for (int i = row + 1;
                                i < arrayRowIndex; i++) {
                                if (chessBoard[i][arrayColumnIndex]
                                    != ' ') {
                                    blank = false;
                                }
                            }
                            if (Math.abs(row - arrayRowIndex) <= 1) {
                                blank = true;
                            }
                            if (blank) {
                                chessBoard[row][column] = ' ';
                                chessBoard[arrayRowIndex]
                                    [arrayColumnIndex] = piece;
                                return 0;
                            }
                        } else {
                            blank = true;
                            if (row - arrayRowIndex > 0) {
                                for (int i = row - 1;
                                        i > arrayRowIndex;
                                        i--) {
                                    if (chessBoard[i][arrayColumnIndex]
                                            != ' ') {
                                        blank = false;
                                    }
                                }
                            }
                            if (Math.abs(row - arrayRowIndex) <= 1) {
                                blank = true;
                            }
                            if (blank) {
                                chessBoard[row][column] = ' ';
                                chessBoard[arrayRowIndex]
                                    [arrayColumnIndex] = piece;
                                return 0;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }










    public static void castle(char[][] chessBoard, String move, boolean white) {
        boolean queenSide = false;
        if (move.contains("O-O-O") || move.contains("0-0-0")) {
            queenSide = true;
        }

        if (queenSide) {
            if (white) {
                chessBoard[7][2] = 'K';
                chessBoard[7][3] = 'R';
                chessBoard[7][4] = ' ';
                chessBoard[7][0] = ' ';
            } else {  //blacks
                chessBoard[0][2] = 'k';
                chessBoard[0][3] = 'r';
                chessBoard[0][4] = ' ';
                chessBoard[0][0] = ' ';
            }
        } else { //king side castle
            if (white) {
                chessBoard[7][6] = 'K';
                chessBoard[7][5] = 'R';
                chessBoard[7][4] = ' ';
                chessBoard[7][7] = ' ';
            } else {  //blacks
                chessBoard[0][6] = 'k';
                chessBoard[0][5] = 'r';
                chessBoard[0][4] = ' ';
                chessBoard[0][7] = ' ';
            }
        }
    }







    public static void moveKnight(char[][] chessBoard, String move1,
        boolean white) {

        String move = getRidOfX(move1);
        int arrayRowIndex = 8 - Character.getNumericValue(move.charAt(2));
        int arrayColumnIndex = move.charAt(1) - 97;

        boolean ambiguous = false;
        //check for DISAMBIGUATION
        //
        if (move.length() >= 4
                    && (move.charAt(1) == '1' || move.charAt(1) == '2'
                    || move.charAt(1) == '3' || move.charAt(1) == '4'
                    || move.charAt(1) == '5' || move.charAt(1) == '6'
                    || move.charAt(1) == '7' || move.charAt(1) == '8')) {
            //N1c2
            disambiguate(chessBoard, move, white, 'N');

        } else if (move.length() >= 4
                    && (move.charAt(1) == 'a' || move.charAt(1) == 'b'
                    || move.charAt(1) == 'c' || move.charAt(1) == 'd'
                    || move.charAt(1) == 'e' || move.charAt(1) == 'f'
                    || move.charAt(1) == 'g' || move.charAt(1) == 'h')) {
            //Nbd7
            disambiguate(chessBoard, move, white, 'N');

        } else {  //no disambiguations. Nxf2. Row = 2, column = 5

            if (white) {
                deleteOldKnight(chessBoard, move, 'N');
                chessBoard[arrayRowIndex][arrayColumnIndex] = 'N';
            } else {  //blacks move
                deleteOldKnight(chessBoard, move, 'n');
                chessBoard[arrayRowIndex][arrayColumnIndex] = 'n';
            }
        }
    }






    public static int deleteOldKnight(char[][] chessBoard, String move,
            char piece) {

        //in Nc3, or Nxc3, turns the '3' into [5] and 'c' into [2]
        int arrayRowIndex = 8 - Character.getNumericValue(move.charAt(2));
        int arrayColumnIndex = move.charAt(1) - 97;

        if ((arrayRowIndex - 2 >= 0) && (arrayColumnIndex - 2 >= 0)
                && (arrayColumnIndex + 1 <= 7)
                && (chessBoard[arrayRowIndex - 1][arrayColumnIndex - 2]
                    == piece)
                &&  (chessBoard[arrayRowIndex - 2][arrayColumnIndex + 1]
                    == piece)) {
            chessBoard[arrayRowIndex - 1][arrayColumnIndex - 2] = ' ';
            return 0;

        } else if (arrayRowIndex + 2 <= 7 && arrayColumnIndex - 1 >= 0
            && chessBoard[arrayRowIndex + 2][arrayColumnIndex - 1]
                    == piece) {
            chessBoard[arrayRowIndex + 2][arrayColumnIndex - 1] = ' ';
            return 0;

        } else if (arrayRowIndex + 2 <= 7
                && arrayColumnIndex + 1 <= 7
                && chessBoard[arrayRowIndex + 2][arrayColumnIndex + 1]
                        == piece) {
            chessBoard[arrayRowIndex + 2][arrayColumnIndex + 1] = ' ';
            return 0;

        } else if (arrayRowIndex - 2 >= 0 && arrayColumnIndex - 1 >= 0
                && chessBoard[arrayRowIndex - 2][arrayColumnIndex - 1]
                    == piece) {
            chessBoard[arrayRowIndex - 2][arrayColumnIndex - 1] = ' ';
            return 0;

        } else if (arrayRowIndex - 2 >= 0 && arrayColumnIndex + 1 <= 7
                && chessBoard[arrayRowIndex - 2][arrayColumnIndex + 1]
                    == piece) {
            chessBoard[arrayRowIndex - 2][arrayColumnIndex + 1] = ' ';
            return 0;

        } else if (arrayRowIndex + 1 <= 7 && arrayColumnIndex - 2 >= 0
                && chessBoard[arrayRowIndex + 1][arrayColumnIndex - 2]
                    == piece) {
            chessBoard[arrayRowIndex + 1][arrayColumnIndex - 2] = ' ';
            return 0;

        } else if (arrayRowIndex + 1 <= 7 && arrayColumnIndex + 2 <= 7
                && chessBoard[arrayRowIndex + 1][arrayColumnIndex + 2]
                    == piece) {
            chessBoard[arrayRowIndex + 1][arrayColumnIndex + 2] = ' ';
            return 0;

        } else if (arrayRowIndex - 1 >= 0 && arrayColumnIndex - 2 >= 0
            && chessBoard[arrayRowIndex - 1][arrayColumnIndex - 2]
                    == piece) {
            chessBoard[arrayRowIndex - 1][arrayColumnIndex - 2] = ' ';
            return 0;

        } else if (arrayRowIndex - 1 >= 0 && arrayColumnIndex + 2 <= 7
            && chessBoard[arrayRowIndex - 1][arrayColumnIndex + 2]
                    == piece) {
            chessBoard[arrayRowIndex - 1][arrayColumnIndex + 2] = ' ';
            return 0;
        }
        return -1;
    }





    public static void moveKing(char[][] chessBoard, String move1,
            boolean white) {

        String move = getRidOfX(move1);

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (white) {
                    if (chessBoard[row][column] == 'K') {

                        chessBoard[row][column] = ' ';
                        chessBoard[8 - Character.getNumericValue(
                                move.charAt(2))][move.charAt(1) - 97] = 'K';

                    }
                } else {
                    if (chessBoard[row][column] == 'k') {

                        chessBoard[row][column] = ' ';
                        chessBoard[8 - Character.getNumericValue(
                            move.charAt(2))][move.charAt(1) - 97] = 'k';
                    }
                }
            }
        }
    }








    public static void moveBishop(char[][] chessBoard, String move1,
            boolean white) {

        String move = getRidOfX(move1);

        //disambiguate
        //Bae1 or B1e1

        if (move.length() >= 4 && (move.charAt(2) == 'a'
                || move.charAt(2) == 'b' || move.charAt(2) == 'c'
                || move.charAt(2) == 'd' || move.charAt(2) == 'e'
                || move.charAt(2) == 'f' || move.charAt(2) == 'g'
                || move.charAt(2) == 'h')) {

            disambiguate(chessBoard, move, white, 'B');

        } else if (move.charAt(1) == '1' || move.charAt(1) == '2'
                    || move.charAt(1) == '3' || move.charAt(1) == '4'
                    || move.charAt(1) == '5' || move.charAt(1) == '6'
                    || move.charAt(1) == '7' || move.charAt(1) == '8') {

            disambiguate(chessBoard, move, white, 'B');

        } else {

            if (white) {
                moveLikeBishop(chessBoard, move, 'B');
            } else {
                moveLikeBishop(chessBoard, move, 'b');
            }

        }
    }












    public static int moveLikeBishop(char[][] chessBoard, String move,
            char piece) {

        int arrayRowIndex = 8 - Character.getNumericValue(move.charAt(2));
        int arrayColumnIndex = move.charAt(1) - 97;

        int oldRow, oldColumn;
        boolean allBlanks = true;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {

                if (row == column) {
                    if (arrayRowIndex >= row
                            && arrayColumnIndex >= column
                            && chessBoard[arrayRowIndex - row]
                                [arrayColumnIndex - column] == piece) {
                        oldRow = arrayRowIndex - row;
                        oldColumn = arrayColumnIndex - column;
                        //direction += "downright";
                        //bishop moved down right
                        for  (int r = oldRow + 1,
                                c = oldColumn + 1;
                                r < Math.abs(arrayRowIndex - row);
                                r++, c++) {
                            if (chessBoard[r][c] != ' ') {
                                allBlanks = false;
                            }
                        }
                        if (allBlanks) {
                            chessBoard[arrayRowIndex]
                                [arrayColumnIndex] = piece;
                            chessBoard[oldRow][oldColumn]
                                = ' ';
                            return 0;
                        }
                    } else if ((arrayRowIndex + row <= 7)
                                && (arrayColumnIndex + column <= 7)
                                && chessBoard[arrayRowIndex + row]
                                    [arrayColumnIndex + column]
                                        == piece) {
                        oldRow = arrayRowIndex + row;
                        oldColumn = arrayColumnIndex + column;
                        //direction += "up left";
                        allBlanks = true;
                        for  (int r = oldRow - 1,
                                c = oldColumn - 1;
                                r < Math.abs(arrayRowIndex - row);
                                r++, c++) {
                            if (chessBoard[r][c] != ' ') {
                                allBlanks = false;
                            }
                        }
                        if (allBlanks) {
                            chessBoard[arrayRowIndex]
                                [arrayColumnIndex] = piece;
                            chessBoard[oldRow]
                                [oldColumn] = ' ';
                            return 0;
                        }
                    } else if ((arrayRowIndex + row <= 7)
                                && arrayColumnIndex >= column
                                && chessBoard[arrayRowIndex + row]
                                    [arrayColumnIndex - column]
                                        == piece) {
                        oldRow = arrayRowIndex + row;
                        oldColumn = arrayColumnIndex - column;
                        //direction += "up right";
                        allBlanks = true;
                        for (int r = oldRow - 1,
                                c = oldColumn + 1;
                                r < Math.abs(arrayRowIndex - row);
                                r++, c++) {
                            if (chessBoard[r][c] != ' ') {
                                allBlanks = false;
                            }
                        }
                        if (allBlanks) {
                            chessBoard[arrayRowIndex]
                                [arrayColumnIndex] = piece;
                            chessBoard[oldRow]
                                [oldColumn] = ' ';
                            return 0;
                        }
                    } else if (arrayRowIndex >= row
                            && (arrayColumnIndex + column <= 7)
                            && chessBoard[arrayRowIndex - row]
                                [arrayColumnIndex + column] == piece) {
                        oldRow = arrayRowIndex - row;
                        oldColumn = arrayColumnIndex + column;
                        //direction += "down left";
                        allBlanks = true;
                        for (int r = oldRow + 1,
                            c = oldColumn - 1;
                            r < Math.abs(arrayRowIndex - row);
                            r++, c++) {
                            if (chessBoard[r][c] != ' ') {
                                allBlanks = false;
                            }
                        }
                        if (allBlanks) {
                            chessBoard[arrayRowIndex][arrayColumnIndex]
                                = piece;
                            chessBoard[oldRow][oldColumn]
                                = ' ';
                            return 0;
                        }
                    }
                }
            }
        }
        return -1;
    }










    public static void moveQueen(char[][] chessBoard, String move1,
            boolean white) {

        // Qa1, Qxa1, Qaxa1, Q1xa1, Qa2xa1

        String move = getRidOfX(move1);

        //disambiguate
        //Qae1 or Q1e1
        boolean disambiguates = false;

        if (move.length() >= 4 && (move.charAt(2) == 'a'
                || move.charAt(2) == 'b' || move.charAt(2) == 'c'
                || move.charAt(2) == 'd' || move.charAt(2) == 'e'
                || move.charAt(2) == 'f' || move.charAt(2) == 'g'
                || move.charAt(2) == 'h')) {

            disambiguate(chessBoard, move, white, 'Q');

        } else if (move.charAt(1) == '1' || move.charAt(1) == '2'
                    || move.charAt(1) == '3' || move.charAt(1) == '4'
                    || move.charAt(1) == '5' || move.charAt(1) == '6'
                    || move.charAt(1) == '7' || move.charAt(1) == '8') {

            disambiguate(chessBoard, move, white, 'Q');

        } else {
            //no disambiguation Qa2

            int arrayRowIndex, arrayColumnIndex;
            arrayRowIndex = 8 - Character.getNumericValue(move.charAt(2));
            arrayColumnIndex = move.charAt(1) - 97;

            int oldQueenRow, oldQueenColumn;
            boolean allBlanks = true;
            boolean blank = true;

            char piece;
            if (white) {
                piece = 'Q';
            } else {
                piece = 'q';
            }

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {

                    if ((arrayRowIndex >= row
                            && arrayColumnIndex >= column
                            && chessBoard[arrayRowIndex - row]
                                [arrayColumnIndex - column] == piece)
                            || ((arrayRowIndex + row <= 7)
                                && (arrayColumnIndex + column <= 7)
                                && chessBoard[arrayRowIndex + row]
                                    [arrayColumnIndex + column]
                                        == piece)
                            || ((arrayRowIndex + row <= 7)
                                && arrayColumnIndex >= column
                                && chessBoard[arrayRowIndex + row]
                                    [arrayColumnIndex - column]
                                        == piece)
                            || (arrayRowIndex >= row
                                && (arrayColumnIndex + column <= 7)
                                && chessBoard[arrayRowIndex - row]
                                    [arrayColumnIndex + column] == piece)) {

                        moveLikeBishop(chessBoard, move, piece);

                    } else if ((chessBoard[row][column] == piece)
                            && ((row == arrayRowIndex)
                            || (column == arrayColumnIndex))) {

                        checkLikeRook(chessBoard, move, piece);
                    }

                }
            }
        }
    }










    public static void movePawn(char[][] chessBoard, String move,
                boolean white) {


        boolean hasX = false;
        if (move.contains("x")) {
            hasX = true;
        }

        //in c3, or dxc3:
        // turns the '3' into [5] <- (arrayRowIndex)
        //  and 'c' into [2] <- (arrayColumnIndex)
        int arrayRowIndex = 0;
        int arrayColumnIndex = 0;

        if (hasX) {
            arrayRowIndex = (8 - Character.getNumericValue(move.charAt(3)));
            arrayColumnIndex = (move.charAt(2) - 97);
        } else {
            arrayRowIndex = (8 - Character.getNumericValue(move.charAt(1)));
            arrayColumnIndex = (move.charAt(0) - 97);
        }




        //  EN PASSANT
        // exd6e.p.
        //
        if (chessBoard[arrayRowIndex][arrayColumnIndex] == ' '
            && (move.charAt(1) == 'x')) {
            if (white) {
                chessBoard[arrayRowIndex + 1][arrayColumnIndex] = ' ';
            } else {
                chessBoard[arrayRowIndex - 1][arrayColumnIndex] = ' ';
            }
        }

        if ((move.length() >= 3) && (move.charAt(2) == 'Q'
                || move.charAt(2) == 'R' || move.charAt(2) == 'B'
                || move.charAt(2) == 'N')) {

                //PAWN PROMOTIONS
                //d8Q, f8N, b1=B, hxg1R are pawn promotions
                //
                //d8Q or f8N
            if (white) {
                chessBoard[arrayRowIndex + 1][arrayColumnIndex] = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex] = move.charAt(2);
            } else { //black
                chessBoard[arrayRowIndex - 1][arrayColumnIndex] = ' ';
                //next line turns uppercase to lowercase
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = (char) (move.charAt(2) + 32);
            }
        } else if (hasX && (move.length() >= 5) && (move.charAt(4) == 'Q'
                    || move.charAt(4) == 'R' || move.charAt(4) == 'B'
                    || move.charAt(4) == 'N')) {

            // hxg1R or dxc8Q
            if (white) {
                chessBoard[arrayRowIndex + 1][move.charAt(0) - 97] = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = move.charAt(4);

            } else { //black
                chessBoard[arrayRowIndex - 1][move.charAt(0) - 97] = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = (char) (move.charAt(4) + 32);
            }
        } else if ((move.length() >= 4) && (move.contains("="))
                    && (move.charAt(3) == 'Q' || move.charAt(3) == 'R'
                    || move.charAt(3) == 'B'
                    || move.charAt(3) == 'N')) {
                        // b1=B
            if (white) {
                chessBoard[arrayRowIndex + 1]
                    [move.charAt(0) - 97] = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = move.charAt(3);
            } else { //black
                chessBoard[arrayRowIndex - 1]
                    [move.charAt(0) - 97] = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = (char) (move.charAt(3) + 32);
            }
        } else if (hasX && (move.length() >= 6) && (move.contains("="))
                    && (move.charAt(5) == 'Q' || move.charAt(5) == 'R'
                    || move.charAt(5) == 'B' || move.charAt(5) == 'N')) {
                        // bxa1=B
            if (white) {
                chessBoard[arrayRowIndex + 1][move.charAt(0) - 97]
                    = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = move.charAt(5);
            } else { //black
                chessBoard[arrayRowIndex - 1][move.charAt(0) - 97]
                    = ' ';
                chessBoard[arrayRowIndex][arrayColumnIndex]
                    = (char) (move.charAt(5) + 32);
            }

        } else if (white) {
            //to convert e4 or c5 to array index:
                //[8 - chess row][column letter - 97]
            //if a pawn captures, there will be an 'x':
            if (move.charAt(1) == 'x') {
                chessBoard[8 - Character.getNumericValue(move.charAt(3))]
                        [move.charAt(2) - 97] = 'P';
                chessBoard[8 - Character.getNumericValue((move.charAt(3) - 1))]
                        [move.charAt(0) - 97] = ' ';
            } else {   //no capture
                chessBoard[8 - Character.getNumericValue(move.charAt(1))]
                        [move.charAt(0) - 97] = 'P';
                //checks if it is a two move pawn
                if (chessBoard[8 - Character.getNumericValue(
                        (move.charAt(1) - 1))][move.charAt(0) - 97] == ' ') {
                    chessBoard[8 - Character.getNumericValue(
                        (move.charAt(1) - 2))][move.charAt(0) - 97] = ' ';
                } else {  //single move forward
                    chessBoard[8 - Character.getNumericValue(
                        (move.charAt(1) - 1))][move.charAt(0) - 97] = ' ';
                }
            }
        } else { //black move
            if (move.charAt(1) == 'x') {
                chessBoard[8 - Character.getNumericValue(
                        move.charAt(3))][move.charAt(2) - 97] = 'p';
                chessBoard[8 - Character.getNumericValue(
                        (move.charAt(3) + 1))][move.charAt(0) - 97] = ' ';
                //only difference with black is +1^
            } else {   //no capture
                chessBoard[8 - Character.getNumericValue(move.charAt(1))]
                        [move.charAt(0) - 97] = 'p';
                //checks if it is a two move pawn
                if (chessBoard[8 - Character.getNumericValue(
                        (move.charAt(1) + 1))][move.charAt(0) - 97] == ' ') {
                    chessBoard[8 - Character.getNumericValue(
                        (move.charAt(1) + 2))][move.charAt(0) - 97] = ' ';
                } else {  //single move forward
                    chessBoard[8 - Character.getNumericValue(
                        (move.charAt(1) + 1))][move.charAt(0) - 97] = ' ';
                }
            }
        }
    }










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







    public static String getRidOfX(String move1) {

        //gets rid of 'x'
        String move = "";
        int x = move1.indexOf('x');
        if (x > 0) {
            move = move1.substring(0, x) + move1.substring(x + 1);
        } else {
            move = move1;
        }

        return move;
    }









    //prints out the final position of the chess board in FEN notation.
    public static String printFinalPosition(char[][] chessBoard) {

        // //prints out my chessboard array
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                System.out.print(chessBoard[row][column] + " ");
            }
            System.out.println();
        }

        System.out.println();


        String fen = "";
        for (int row = 0; row < chessBoard.length; row++) {
            int columnCounter = 1;
            for (int column = 0; column < chessBoard[row].length;
                    column += columnCounter) {
                columnCounter = 1;
                if (chessBoard[row][column] == ' ') {
                    columnCounter = 0;
                    int emptyCounter = 0;
                    for (int i = column; i < chessBoard[row].length; i++) {
                        if (chessBoard[row][i] == ' ') {
                            emptyCounter++;
                            columnCounter++;
                        } else {
                            break;
                        }
                    }
                    fen += emptyCounter;
                } else {
                    fen += chessBoard[row][column];
                }
            }
            fen += "/";
        }

        return fen.substring(0, fen.length() - 1);
    }



    /*
     * Reads the file named by path and returns its content as a String.
     */

    public static String fileContent(String path) {
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }
        return sb.toString();
    }
}
