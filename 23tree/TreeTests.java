
import static org.junit.Assert.*;
import org.junit.Test;

public class TreeTests
{
   @Test
   public void emptyTree()
   {
      Tree t = new Tree();
      assertEquals(0, t.size());
   }

   @Test
   public void insertOne()
   {
      Tree t = new Tree();
      assertTrue(t.insert(5));
      assertEquals(1, t.size());
      assertEquals(5, t.get(0));
   }

   @Test
   public void insertDuplicate()
   {
      Tree t = new Tree();
      assertTrue(t.insert(5));
      assertFalse(t.insert(5));
      assertEquals(1, t.size());
   }

   @Test
   public void insertTwo()
   {
      Tree t = new Tree();
      t.insert(10);
      t.insert(5);
      assertEquals(2, t.size());
      assertEquals(5, t.get(0));
      assertEquals(10, t.get(1));
   }

   @Test
   public void insertThreeCausesSplit()
   {
      Tree t = new Tree();
      t.insert(10);
      t.insert(20);
      t.insert(15);
      assertEquals(3, t.size());
      assertEquals(10, t.get(0));
      assertEquals(15, t.get(1));
      assertEquals(20, t.get(2));
   }

   @Test
   public void insertAscending()
   {
      Tree t = new Tree();
      for(int i = 1; i <= 7; i++)
         t.insert(i);
      assertEquals(7, t.size());
      for(int i = 0; i < 7; i++)
         assertEquals(i + 1, t.get(i));
   }

   @Test
   public void insertDescending()
   {
      Tree t = new Tree();
      for(int i = 7; i >= 1; i--)
         t.insert(i);
      assertEquals(7, t.size());
      for(int i = 0; i < 7; i++)
         assertEquals(i + 1, t.get(i));
   }

   @Test
   public void duplicatesIgnored()
   {
      Tree t = new Tree();
      t.insert(3);
      t.insert(1);
      t.insert(2);
      assertFalse(t.insert(1));
      assertFalse(t.insert(2));
      assertFalse(t.insert(3));
      assertEquals(3, t.size());
   }

   @Test
   public void sizeOfSubtree()
   {
      Tree t = new Tree();
      t.insert(10);
      t.insert(20);
      t.insert(15);
      // after inserting 10, 20, 15: root is 15, left is 10, right is 20
      assertEquals(3, t.size(15));
      assertEquals(1, t.size(10));
      assertEquals(1, t.size(20));
   }

   @Test
   public void sizeOfMissing()
   {
      Tree t = new Tree();
      t.insert(5);
      assertEquals(0, t.size(99));
   }

   @Test
   public void getMinAndMax()
   {
      Tree t = new Tree();
      t.insert(50);
      t.insert(20);
      t.insert(80);
      t.insert(10);
      t.insert(30);
      assertEquals(10, t.get(0));
      assertEquals(80, t.get(t.size() - 1));
   }

   @Test
   public void largerTree()
   {
      Tree t = new Tree();
      int[] vals = {5, 3, 8, 1, 4, 7, 9, 2, 6};
      for(int v : vals)
         t.insert(v);
      assertEquals(9, t.size());
      for(int i = 0; i < 9; i++)
         assertEquals(i + 1, t.get(i));
   }

   @Test
   public void sizeEmptyTree()
   {
      Tree t = new Tree();
      assertEquals(0, t.size(42));
   }
}
