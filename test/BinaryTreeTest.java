import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryTreeTest {
    @Test
    public void remove() {
        BinaryTree tree = new BinaryTree();


        tree.add(20);
        tree.add(14);
        tree.add(16);
        tree.add(10);
        tree.add(15);
        tree.add(0);
        tree.add(11);
        tree.add(25);
        tree.add(40);

        assertEquals(true, tree.remove(0));
        assertEquals(false, tree.contains(0));
        assertEquals(true, tree.remove(16));
        assertEquals(false, tree.contains(16));
        assertEquals(true, tree.remove(14));
        assertEquals(false, tree.contains(14));
        assertEquals(true, tree.remove(25));
        assertEquals(false, tree.contains(25));
        assertEquals(false, tree.contains(100));
        assertEquals(false, tree.remove(100));
        assertEquals(true, tree.remove(20));
        assertEquals(false, tree.remove(20));
        assertEquals(false, tree.contains(20));


    }



}