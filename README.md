# chess

Takes in an input file of chess moves in PGN format
and prints out the final board positions in FEN notation.

Please see PgnReader.java, the main class in this repo, for more details.

# example usage

given an INPUT file: "morphy-isouard-karl-1958.pgn",
   which contains the moves and tag values:

```
    [Event "A Night at the Opera"]
    [Date "1958"]
    [White "Morphy, Paul"]
    [Black "Comte Isouard de Vauvenargues and Karl II, Duke of Brunswick"]
    1. e4 e5 2. Nf3 d6 3. d4 Bg4 4. dxe5 Bxf3 5. Qxf3 dxe5 6. Bc4 Nf6
    7. Qb3 Qe7 8. Nc3 c6 9. Bg5 b5 10. Nxb5 cxb5 11. Bxb5+ Nbd7 12. O-O-O Rd8
    13. Rxd7 Rxd7 14. Rd1 Qe6 15. Bxd7+ Nxd7 16. Qb8+ Nxb8 17. Rd8#
```

 Run from the command line from the local directory with:

```
 java PgnReader morphy-isouard-karl-1958.pgn
 ```

this program will OUTPUT:

```
    Event: A Night at the Opera
    Site: NOT GIVEN
    Date: 1958
    Round: NOT GIVEN
    White: Morphy, Paul
    Black: Comte Isouard de Vauvenargues and Karl II, Duke of Brunswick
    Result: NOT GIVEN
    Final Position:
    1n1Rkb1r/p4ppp/4q3/4p1B1/4P3/8/PPP2PPP/2K5
```

 This program determines the final position of games that include:
   Castling
   Pawn Promotions
   En Passant
   Disambiguation of starting file or rank
   Pinned Pieces and prevention against illegal moves
   And more
   
 # why this was challenging
 The difficulty in programming this is that the PGN notation followed is half of what is needed to know how to move the piece: for example, an opening move may be "1. e4 e5", which represents White moving his king pawn forward two spaces, and Black moving his queen pawn forward two spaces. What is lacking from this notation is the square the piece originated from. It would be much easier to program pgn notation in the form of "1.e2-e4 e7-e5"

