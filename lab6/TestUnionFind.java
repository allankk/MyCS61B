import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestUnionFind {

    public static void main(String[] args) {

    }


    @Test
    public void testUnionFind() {

        UnionFind testUnion = new UnionFind(5);
        assertEquals(-1, testUnion.find(2));

    }

    @Test
    public void testSizeOf() {

        UnionFind testUnion = new UnionFind(5);
        testUnion.union(2, 3);
        assertEquals(2, testUnion.sizeOf(2));

        testUnion.union(2, 4);
        testUnion.union(3, 4);

        assertEquals(3, testUnion.sizeOf(3));

    }

}
