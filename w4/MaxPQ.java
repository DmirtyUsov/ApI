// priority queue
// Binary heap implementation

public class MaxPQ<Key extends Comparable<Key>>
{
    private Key[] pq;
    private int N;

    // fixed capacity for simplicity
    public MaxPQ(int capacity)
    {
        pq = (Key[]) new Comparable[capacity +1];
    }

    // array helper funstions
    private boolean less(int i, int j)
    {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j)
    {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    // heap helper functions

    // Exchange key in child with key in parent.
    // Repeat until heap order restored.
    private void swim(int k)
    {
        while (k > 1 && less(k/2, k))
        {
            exch(k, k/2);
            k = k/2;
        }
    }

    // Exchange key in parent with key in larger child.
    // Repeat until heap order restored.
    private void sink(int k)
    {
        while (2 * k < N)
        {
            int j = 2 * k; // child
            if (j < N && less(j, j+1))
            {
                j++; // other child
            }
            if(!less(k,j))
            {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    // pq ops
    public boolean isEmpty()
    {
        return N == 0;
    }

    public void insert(Key key)
    {
        pq[++N] = key;
        swim(N);
    }

    public Key delMax()
    {
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null; // prevent loitering
        return max;
    }
}
