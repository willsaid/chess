/*
 * Tests the Piece and Square classes, and the subclasses of Piece,
 *  to ensure that they work properly.
 */

 public class ClassesTester {
    public static void main(String[] args) {
    Piece knight = new Knight(Color.BLACK);
    assert knight.algebraicName().equals("N");
    assert knight.fenName().equals("n");
    Square[] attackedSquares = knight.movesFrom(new Square("f6"));
    for (Square sq: attackedSquares) {
        System.out.println(sq);
    }
    Square a1 = new Square("a1");
    Square otherA1 = new Square('a', '1');
    Square h8 = new Square("h8");
    assert a1.equals(otherA1);
    assert !a1.equals(h8);
    }
}
