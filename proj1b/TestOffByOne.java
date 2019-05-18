import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars(){
        assertFalse(offByOne.equalChars('s', 's'));

        assertFalse(offByOne.equalChars('s', 'a'));

        assertTrue(offByOne.equalChars('a', 'b'));

        assertFalse(offByOne.equalChars('a', 'c'));

        assertTrue(offByOne.equalChars('r', 'q'));
    }


    // Your tests go here.
}