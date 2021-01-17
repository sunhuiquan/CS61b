// A class for palindrome operations.
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        Deque<Character> charDeque = new ArrayDeque<>();
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
        if (!charDeque.removeFirst().equals(charDeque.removeLast())) {
            return false;
        }
        return isPalindromeRecursive(charDeque);
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return true;
        }
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
        return isPalindromeRecursive(charDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return true;
        }
        Deque<Character> charDeque = wordToDeque(word);
        return isPalindromeRecursive(charDeque, cc);
    }
}
