// A class for palindrome operations.
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> charDeque = new LinkedListDeque<>();
        int size = word.length();
        for (int i = 0; i < size; i++) {
            charDeque.addLast(word.charAt(i));
        }
        return charDeque;
    }

    private boolean isPalindromeRecursive(Deque<Character> charDeque) {
        if (charDeque.size() <= 1) {
            return true;
        }
        if (!(charDeque.removeFirst() == charDeque.removeLast())) {
            return false;
        }
        return isPalindromeRecursive(charDeque);
    }

    public boolean isPalindrome(String word) {
        Deque<Character> charDeque = wordToDeque(word);
        return isPalindromeRecursive(charDeque);
    }

    private boolean isPalindromeRecursive(Deque<Character> charDeque, CharacterComparator cc) {
        if (charDeque.size() <= 1) {
            return true;
        }
        if (!cc.equalChars(charDeque.removeFirst(), charDeque.removeLast())) {
            return false;
        }
        return isPalindromeRecursive(charDeque, cc);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> charDeque = wordToDeque(word);
        return isPalindromeRecursive(charDeque, cc);
    }
}
