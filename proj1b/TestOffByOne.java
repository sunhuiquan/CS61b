import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('\n', '\n'));
        assertTrue(offByOne.equalChars('5', '5'));
        assertFalse(offByOne.equalChars('a', '!'));
    }
}
