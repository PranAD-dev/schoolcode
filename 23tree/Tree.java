
public class Tree
{
   private Node root;

   public Tree()
   {
      root = null;
   }

   private class Node
   {
      int key1;
      int key2;
      int numKeys;
      Node left;
      Node middle;
      Node right;
      int subtreeSize;

      Node(int key)
      {
         this.key1 = key;
         this.numKeys = 1;
         this.subtreeSize = 1;
      }
   }

   private class SplitResult
   {
      int promotedKey;
      Node leftChild;
      Node rightChild;

      SplitResult(int key, Node left, Node right)
      {
         this.promotedKey = key;
         this.leftChild = left;
         this.rightChild = right;
      }
   }

   public boolean insert(int x)
   {
      if(root == null)
      {
         root = new Node(x);
         return true;
      }
      if(findNode(root, x) != null)
         return false;
      SplitResult result = insertHelper(root, x);
      if(result != null)
      {
         Node newRoot = new Node(result.promotedKey);
         newRoot.left = result.leftChild;
         newRoot.middle = result.rightChild;
         newRoot.subtreeSize = 1;
         if(newRoot.left != null)
            newRoot.subtreeSize += newRoot.left.subtreeSize;
         if(newRoot.middle != null)
            newRoot.subtreeSize += newRoot.middle.subtreeSize;
         root = newRoot;
      }
      return true;
   }

   public int size()
   {
      if(root == null)
         return 0;
      return root.subtreeSize;
   }

   public int size(int x)
   {
      Node node = findNode(root, x);
      if(node == null)
         return 0;
      return node.subtreeSize;
   }

   public int get(int x)
   {
      return getHelper(root, x);
   }

   private Node findNode(Node node, int x)
   {
      if(node == null)
         return null;
      if(x == node.key1)
         return node;
      if(node.numKeys == 2 && x == node.key2)
         return node;
      if(x < node.key1)
         return findNode(node.left, x);
      if(node.numKeys == 1 || x < node.key2)
         return findNode(node.middle, x);
      return findNode(node.right, x);
   }

   private SplitResult insertHelper(Node node, int x)
   {
      if(node.left == null)
      {
         if(node.numKeys == 1)
         {
            if(x < node.key1)
            {
               node.key2 = node.key1;
               node.key1 = x;
            }
            else
            {
               node.key2 = x;
            }
            node.numKeys = 2;
            node.subtreeSize = 2;
            return null;
         }
         else
         {
            int a, b, c;
            if(x < node.key1)
            {
               a = x; b = node.key1; c = node.key2;
            }
            else if(x < node.key2)
            {
               a = node.key1; b = x; c = node.key2;
            }
            else
            {
               a = node.key1; b = node.key2; c = x;
            }
            return new SplitResult(b, new Node(a), new Node(c));
         }
      }

      SplitResult childSplit;
      if(x < node.key1)
         childSplit = insertHelper(node.left, x);
      else if(node.numKeys == 1 || x < node.key2)
         childSplit = insertHelper(node.middle, x);
      else
         childSplit = insertHelper(node.right, x);

      if(childSplit == null)
      {
         node.subtreeSize = node.numKeys;
         if(node.left != null) node.subtreeSize += node.left.subtreeSize;
         if(node.middle != null) node.subtreeSize += node.middle.subtreeSize;
         if(node.right != null) node.subtreeSize += node.right.subtreeSize;
         return null;
      }

      if(node.numKeys == 1)
      {
         int promoted = childSplit.promotedKey;
         if(promoted < node.key1)
         {
            node.key2 = node.key1;
            node.key1 = promoted;
            node.right = node.middle;
            node.left = childSplit.leftChild;
            node.middle = childSplit.rightChild;
         }
         else
         {
            node.key2 = promoted;
            node.middle = childSplit.leftChild;
            node.right = childSplit.rightChild;
         }
         node.numKeys = 2;
         node.subtreeSize = node.numKeys;
         if(node.left != null) node.subtreeSize += node.left.subtreeSize;
         if(node.middle != null) node.subtreeSize += node.middle.subtreeSize;
         if(node.right != null) node.subtreeSize += node.right.subtreeSize;
         return null;
      }
      else
      {
         int promoted = childSplit.promotedKey;
         int a, b, c;
         Node child1, child2, child3, child4;

         if(promoted < node.key1)
         {
            a = promoted; b = node.key1; c = node.key2;
            child1 = childSplit.leftChild;
            child2 = childSplit.rightChild;
            child3 = node.middle;
            child4 = node.right;
         }
         else if(promoted < node.key2)
         {
            a = node.key1; b = promoted; c = node.key2;
            child1 = node.left;
            child2 = childSplit.leftChild;
            child3 = childSplit.rightChild;
            child4 = node.right;
         }
         else
         {
            a = node.key1; b = node.key2; c = promoted;
            child1 = node.left;
            child2 = node.middle;
            child3 = childSplit.leftChild;
            child4 = childSplit.rightChild;
         }

         Node leftNode = new Node(a);
         leftNode.left = child1;
         leftNode.middle = child2;
         leftNode.subtreeSize = 1;
         if(child1 != null) leftNode.subtreeSize += child1.subtreeSize;
         if(child2 != null) leftNode.subtreeSize += child2.subtreeSize;

         Node rightNode = new Node(c);
         rightNode.left = child3;
         rightNode.middle = child4;
         rightNode.subtreeSize = 1;
         if(child3 != null) rightNode.subtreeSize += child3.subtreeSize;
         if(child4 != null) rightNode.subtreeSize += child4.subtreeSize;

         return new SplitResult(b, leftNode, rightNode);
      }
   }

   private int getHelper(Node node, int index)
   {
      int leftSize = 0;
      if(node.left != null)
         leftSize = node.left.subtreeSize;
      if(index < leftSize)
         return getHelper(node.left, index);
      if(index == leftSize)
         return node.key1;
      index = index - leftSize - 1;
      int midSize = 0;
      if(node.middle != null)
         midSize = node.middle.subtreeSize;
      if(index < midSize)
         return getHelper(node.middle, index);
      if(node.numKeys == 1)
         return getHelper(node.middle, index);
      if(index == midSize)
         return node.key2;
      index = index - midSize - 1;
      return getHelper(node.right, index);
   }
}
