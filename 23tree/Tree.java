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
      return root.helpGet(x);
   }

   public int size(int x)
   {
      Node found= root.find(x);
      if (!found.containsKey(x))
      {
         return 0;
      }
      return found.countKeys();
   }

   public boolean insert(int x)
   {
      if (root == null)
      {
         root = new Node();
         root.addKey(x);
         size++;
         return true;
      }

      Node leaf=root.find(x);
      if (leaf.containsKey(x))
      {
         return false;
      }
      leaf.addKey(x);

      if (leaf.numKeys <= 2)
      {
         size++;
         return true;
      }

      pushUp(leaf);
      size++;
      return true;
   }

   private void pushUp(Node node)
   {
      Node result = node.split();
      Node parent = node.parent;

      if (parent== null)
      {
         root = result;
         return;
      }
      int spot = parent.childIndex(node);
      parent.addKeyAndChild(spot, result.keys[0], result.children[0], result.children[1]);

      if (parent.numKeys > 2)
      {
         pushUp(parent);
      }
   }


   class Node
   {
      public int[] keys;
      public int numKeys;
      public Node[] children;
      public Node parent;

      Node()
      {
         keys = new int[3];
         children = new Node[4];
         numKeys = 0;
      }

      Node getChild(int x)
      {
         for (int i=0; i<numKeys; i++)
         {
            if (x<keys[i])
            {
               return children[i];
            }
         }
         return children[numKeys];
      }

      boolean isLeaf()
      {
         if (children[0]==null) {
            return true;
         }
         else {
            return false;
         }
      }

      boolean containsKey(int x)
      {
         for (int i = 0; i<numKeys; i++)
         {
            if (keys[i]==x)
            {
               return true;
            }
         }
         return false;
      }


      Node find(int x)
      {
         if (isLeaf()||containsKey(x))
         {
            return this;
         }
         return getChild(x).find(x);
      }

      int childIndex(Node child)
      {
         for (int i=0; i<=numKeys; i++)
         {
            if (children[i] == child)
            {
               return i;
            }
         }
         return -1;
      }

      void addKey(int x)
      {
         int i = numKeys - 1;
         while (i>=0 && keys[i]>x)
         {
            keys[i+1] = keys[i];
            i--;
         }
         keys[i+1] = x;
         numKeys++;
      }


      void addKeyAndChild(int spot, int key, Node leftChild, Node rightChild)
      {
         for (int i=numKeys-1; i>=spot;i--)
         {
            keys[i+1] = keys[i];
         }
         keys[spot] = key;

         for (int i = numKeys; i > spot; i--)
         {
            children[i+1] = children[i];
         }
         children[spot] = leftChild;
         children[spot + 1] = rightChild;
         leftChild.parent = this;
         rightChild.parent = this;
         numKeys++;
      }

      Node split()
      {
         Node up = new Node();
         up.addKey(keys[1]);
         Node left = new Node();
         left.addKey(keys[0]);
         Node right = new Node();
         right.addKey(keys[2]);
         left.children[0] = children[0];
         left.children[1] = children[1];
         right.children[0] = children[2];
         right.children[1] = children[3];
         if (children[0] != null)
         {
            children[0].parent = left;
            children[1].parent = left;
            children[2].parent = right;
            children[3].parent = right;
         }

         up.children[0] = left;
         up.children[1] = right;
         left.parent = up;
         right.parent = up;

         return up;
      }

      int countKeys()
      {
         int count = numKeys;
         for (int i=0; i<= numKeys; i++)
         {
            if (children[i] != null)
            {
               count += children[i].countKeys();
            }
         }
         return count;
      }

      int helpGet(int x)
      {
         for (int i =0; i<numKeys; i++)
         {
            int childCount=0;
            if (children[i]!=null)
            {
               childCount = children[i].countKeys();
            }
            if (x<childCount)
            {
               return children[i].helpGet(x);
            }
            x -= childCount;

            if (x== 0)
            {
               return keys[i];
            }
            x--;
         }
         return children[numKeys].helpGet(x);
      }
   }
}
