import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome(){

        assertFalse(palindrome.isPalindrome("coffee"));

        assertTrue(palindrome.isPalindrome("soros"));

        assertTrue(palindrome.isPalindrome(""));

        assertTrue(palindrome.isPalindrome("b"));

        assertFalse(palindrome.isPalindrome("coffefefefe"));
    }

    @Test
    public void testIsPalindromeOffOne() {
        CharacterComparator offByOne = new OffByOne();

        assertTrue(palindrome.isPalindrome("flake", offByOne));

        assertFalse(palindrome.isPalindrome("random", offByOne));

        assertTrue(palindrome.isPalindrome("a", offByOne));

        assertTrue(palindrome.isPalindrome("", offByOne));

        assertTrue(palindrome.isPalindrome("obn", offByOne));

    }

}