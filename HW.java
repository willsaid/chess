public static int deleteOldKnight(char[][] chessBoard, String move, char piece) {

    //in Nc3, or Nxc3, turns the '3' into [5] and 'c' into [2]
    int arrayRowIndex = 8 - Character.getNumericValue(move.charAt(2));
    int arrayColumnIndex = move.charAt(1) - 97;
    int oldRow, oldColumn;

    if (arrayRowIndex + 2 <= 7 && arrayColumnIndex - 1 >= 0
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
}
