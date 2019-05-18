

public class TestSort {


    /* Test the Sort.sort methor */
    public static void testSort(){
      String[] input = {"i", "have", "an", "egg"};
      String[] expected = {"an", "egg", "have", "i"};

      Sort.sort(input);

      org.junit.Assert.assertArrayEquals(expected, input);

    }

    public static void testswap() {
        String[] input = {"i", "have", "an", "egg"};
        int a = 0;
        int b = 2;
        String[] expected = {"an", "have", "i", "egg"};

        Sort.swap(input, a, b);

        org.junit.Assert.assertArrayEquals(expected, input);

        String[] input2 = {"this", "is", "a", "test"};
        int a2 = 1;
        int b2 = 2;
        String[] expected2 = {"this", "a", "is", "test"};

        Sort.swap(input2, a2, b2);

        org.junit.Assert.assertArrayEquals(expected2, input2);
    }


    // Test the Sort.findSmallest method
    public static void testfindSmallest(){
        String[] input = {"i", "have", "an", "egg"};
        int expected = 2;

        int actual = Sort.findSmallest(input, 0);

        org.junit.Assert.assertEquals(expected, actual);

        String[] input2 = {"there", "are", "many", "pigs"};
        int expected2 = 2;
        int actual2 = Sort.findSmallest(input2, 2);

        org.junit.Assert.assertEquals(expected2, actual2);

    }

    public static void main(String[] args){
        testSort();
    }



}
