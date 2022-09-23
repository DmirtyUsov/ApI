// implementation of Binary Search Tree
// assume data types Key and Value exist

public class BST
{
    private class Node
    {
        private Key key;
        private Value val;
        private Node left, right;
        private int count; // number of nodes in subtree

        public Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    // Return value corresponding to given key, or null if no such key.
    public Value get(Key key)
    {
        Node x = root;

        while (x != null)
        {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
            {
                x = x.left;
            }
            else if (cmp > 0)
            {
                x = x.right;
            }
            else // cmp == 0
            {
                return x.val;
            }
        }
        return null;
    }
    // Associate value with key
    public void put(Key key, Value val)
    {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val)
    {
        if (x == null)
        {
            return new Node(key, val);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
        {
            x.left = put(x.left, key, val);
        }
        else if (cmp > 0)
        {
            x.right = put(x.right, key, val);
        }
        else // cmp == 0
        {
            x.val = val;
        }
        x.count = 1 + x.left.count + x.right.count;
        return x;
    }
    // min value
    public Key min()
    {
        Node x = min(root);
        if (x == null)
        {
            return null;
        }
        else
        {
            return x.key;
        }
    }
    private Key min(Node x)
    {
        if (x == null)
        {
            return null;
        }
        return min(x.left);
    }
    // Largest key ≤ a given key
    public Key floor(Key key)
    {
        Node x = floor(root, key);
        if (x == null)
        {
            return null;
        }
        else
        {
            return x.key;
        }
    }

    private Node floor(Node x, Key key)
    {
        if (x == null)
        {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
        {
            return floor(x.left, key);
        }
        
        Node t = floor(x.right, key);
        if (t != null)
        {
            return t;
        }
        else
        {
            return x;
        }
    }

    // Smallest key ≥ a given key
    public Key ceiling(Key key)
    {
        Node x = ceiling(root, key);
        if (x == null)
        {
            return null
        }
        return x.key;
    }
    // need to check
    private Key ceiling(Node x, Key key)
    {
        if (x == null)
        {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
        {
            return key;
        }
        if (cmp > 1)
        {
            return ceiling(x.right, key);
        }
        Node t = ceiling(x.right, key);
        if (t != null)
        {
            return t;
        }
        else
        {
            return x;
        }
    }

    // return the count at the root
    public int size()
    {
        return size(root);
    }

    private int size(Node x)
    {
        if (x == null)
        {
            return 0;
        }
        return x.count;
    }
    // How many keys < k
    public int rank(Key key)
    {
        return rank(key, root);
    }
    private int rank(Key key, Node x)
    {
        if (x == null)
        {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
        {
            return rank(key, x.left);
        }
        else if (cmp > 0)
        {
            return 1 + size(x.left) + rank(x.right);
        }
        else // cmp == 0
        {
            return size(x.left);
        }
    }

    public Iterable<Key> keys()
    {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }
    // yields keys in ascending order
    private void inorder(Node x, Queue<Key> q)
    {
        if (x == null)
        {
            return; 
        }
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    // delete the minimum key
    public void deleteMin()
    {
        root = deleteMin(root);
    }
    private Node deleteMin(Node x)
    {
        if (x.left == null)
        {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.count = 1 + x.left.count + x.right.count;
        return x;
    }
    // Hibbard deletion
    public void delete(Key key)
    {
        root = delete(root, key);
    }
    private Node delete(Node x, Key k)
    {
        if (x == null)
        {
            return null;
        }
        int cmp = key.compareTo(x.key);
        
        if (cmp < 0) // search for key
        {
            x.left = delete(left.x, key);
        }
        if (cmp > 0) // search for key
        {
            x.right = delete(right.x, key);
        }
        else // found key
        {
            // only one child
            if (x.right == null)
            {
                return x.left;
            }
            if (x.left == null)
            {
                return x.right;
            }
            // two childs
            // replace with successor
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
}
