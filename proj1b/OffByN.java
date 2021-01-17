//  A class for off-by-N comparators.
public class OffByN implements CharacterComparator {
    public OffByN(int N) {
        n = N;
    }

    private int n;

    @Override
    public boolean equalChars(char x, char y) {
        x = Character.toLowerCase(x);
        y = Character.toLowerCase(y);
        if ((x - y == n) || (y - x == n)) {
            return true;
        }
        return false;
    }
}
