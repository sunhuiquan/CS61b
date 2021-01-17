// A class for off-by-1 comparators.
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return (x == (y - 1)) || ((x - 1) == y);
    }
}
