import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(5);
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testOffByN() {
        assertTrue(offByN.equalChars('a', 'f'));
        assertTrue(offByN.equalChars('f', 'a'));
        assertFalse(offByN.equalChars('f', 'h'));
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("aa", offByN));
        assertTrue(palindrome.isPalindrome("af", offByN));
    }

}
