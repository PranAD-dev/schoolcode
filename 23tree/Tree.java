/**
   This class implements a binary search tree whose
   nodes hold objects that implement the Comparable
   interface.
*/
public class Tree
{  
   private Node root;
   private int size;

 
   public Tree()
   {
      root = null;
      size = 0;
   }

   public int size()
   {
      return size;
   }

   public int get(int x)
   {
      return helpGet(root, x);
   }

   private int helpGet(Node node, int x)
   {
      int leftCount = helpCountKeys(node.left);

      if (x < leftCount)
      {
         return helpGet(node.left, x);
      }
      x -= leftCount;

      if (x == 0)
      {
         return node.key1;
      }
      x--;

      int middleCount = helpCountKeys(node.middle);

      if (x < middleCount)
      {
         return helpGet(node.middle, x);
      }
      x -= middleCount;

      if (x == 0)
      {
         return node.key2;
      }
      x--;

      return helpGet(node.right, x);
   }

   public int size(int x)
   {
      Node found = helpfindNode(root, x);
      if (found == null)
      {
         return 0;
      }
      return helpCountKeys(found);
   }
   private Node helpfindNode(Node node, int x)
   {
      if (node==null)
      {
         return null;
      }
      if (x==node.key1)
      {
         return node;
      }
      if (x==node.key2)
      {
         return node;
      }
      if (node.left==null)
      {
         return null;
      }
      if (x<node.key1)
      {
         return helpfindNode(node.left, x);
      }
      else if (node.numKeys==1 || x<node.key2)
      {
         return helpfindNode(node.middle, x);
      }
      else
      {
         return helpfindNode(node.right, x);
      }
   }

   private int helpCountKeys(Node node)
   {
      if (node == null)
      {
         return 0;
      }
      int count = node.numKeys;
      count += helpCountKeys(node.left);
      count += helpCountKeys(node.middle);
      count += helpCountKeys(node.right);
      return count;
   }
   public boolean insert(int x)
   {
      if (root == null)
      {
         root = new Node(x);
         size++;
         return true;
      }

      if (helpfindNode(root, x) != null)
      {
         return false;
      }

      Node leaf = findLeaf(root, x);

      if (leaf.numKeys == 1)
      {
         if (x < leaf.key1)
         {
            leaf.key2 = leaf.key1;
            leaf.key1 = x;
         }
         else
         {
            leaf.key2 = x;
         }
         leaf.numKeys = 2;
         size++;
         return true;
      }

      int small, mid, big;
      if (x < leaf.key1)
      {
         small = x;
         mid = leaf.key1;
         big = leaf.key2;
      }
      else if (x < leaf.key2)
      {
         small = leaf.key1;
         mid = x;
         big = leaf.key2;
      }
      else
      {
         small = leaf.key1;
         mid = leaf.key2;
         big = x;
      }

      Node leftNew = new Node(small);
      Node rightNew = new Node(big);

      pushUp(leaf.parent, mid, leftNew, rightNew);
      size++;
      return true;
   }

   private Node findLeaf(Node node, int x)
   {
      if (node.left == null)
      {
         return node;
      }
      if (x < node.key1)
      {
         return findLeaf(node.left, x);
      }
      else if (node.numKeys == 1 || x < node.key2)
      {
         return findLeaf(node.middle, x);
      }
      else
      {
         return findLeaf(node.right, x);
      }
   }

   private void pushUp(Node parent, int val, Node leftChild, Node rightChild)
   {
      if (parent == null)
      {
         root = new Node(val);
         root.left = leftChild;
         root.middle = rightChild;
         leftChild.parent = root;
         rightChild.parent = root;
         return;
      }

      if (parent.numKeys == 1)
      {
         if (val < parent.key1)
         {
            parent.key2 = parent.key1;
            parent.key1 = val;
            parent.right = parent.middle;
            parent.left = leftChild;
            parent.middle = rightChild;
         }
         else
         {
            parent.key2 = val;
            parent.middle = leftChild;
            parent.right = rightChild;
         }
         parent.numKeys = 2;
         leftChild.parent = parent;
         rightChild.parent = parent;
         return;
      }
   }
 

   class Node
   {
      public int key1;
      public int key2;
      public int numKeys;
      public Node left;
      public Node middle;
      public Node right;
      public Node parent;

      Node()
      {
      }

      Node(int key)
      {
         this.key1 = key;
         this.numKeys = 1;
      }

     
      
   }
}


