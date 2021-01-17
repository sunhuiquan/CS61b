import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'a'));
        assertTrue(offByOne.equalChars('\n', '\n'));
        assertTrue(offByOne.equalChars('5', '5'));
        assertFalse(offByOne.equalChars('a', '!'));
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("A", offByOne));
        assertTrue(palindrome.isPalindrome("asdffdsa", offByOne));
        assertTrue(palindrome.isPalindrome("aba", offByOne));
        assertFalse(palindrome.isPalindrome("adslgjljajge", offByOne));
        assertFalse(palindrome.isPalindrome("abccb", offByOne));
        assertTrue(palindrome.isPalindrome(null));
    }
}
