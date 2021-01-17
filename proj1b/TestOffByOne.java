import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('5', '4'));
        assertFalse(offByOne.equalChars('a', '!'));
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("A", offByOne));
        assertTrue(palindrome.isPalindrome("adfeb", offByOne));
        assertFalse(palindrome.isPalindrome("aba", offByOne));
        assertFalse(palindrome.isPalindrome("adslgjljajge", offByOne));
        assertTrue(palindrome.isPalindrome("abcab", offByOne));
    }

    @Test
    public void testSome() {
        char c = '!';
        c = Character.toLowerCase(c);
        assertEquals('!', c);
    }
}
