import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int itemsInQueue;
    private int tail;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        itemsInQueue = 0;
        tail = 0;
        queue = (Item[]) new Object[3];

    }
    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return itemsInQueue == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return itemsInQueue;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        int oldIndex = 0;
        int newTail = 0;
        while (newTail != itemsInQueue)
        {
            if (queue[oldIndex] != null)
            {
                copy[newTail] = queue[oldIndex];
                newTail++;
            }
            oldIndex++;
        }
        tail = newTail;
        queue = copy;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("item is null");
        }

        if (tail == queue.length)
        {
            resize(2 * queue.length); // items < tail??
        }
        queue[tail++] = item;
        itemsInQueue++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (itemsInQueue == 0)
        {
            throw new NoSuchElementException("queue is empty");
        }

        int index = -1;
        Item item = null;
        while (item == null)
        {
            index = StdRandom.uniform(0, tail+1);
            item = queue[index];
        }

        queue[index] = null;
        itemsInQueue--;

        if (itemsInQueue == queue.length/4)
        { 
            resize(queue.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (itemsInQueue == 0)
        {
            throw new NoSuchElementException("queue is empty");
        }

        int index = -1;
        Item item = null;
        while (item == null)
        {
            index = StdRandom.uniform(0, tail+1);
            item = queue[index];
        }
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new ArrayIterator();

    }
    // independent iterator over items in random order
    private class ArrayIterator implements Iterator<Item>
    {
        private int currentTail;
        private final int itemsForIterator;
        private final Item[] queueIter;

        public ArrayIterator()
        {
            itemsForIterator = itemsInQueue;
            queueIter = (Item[]) new Object[itemsForIterator];

            int index = StdRandom.uniform(0, itemsForIterator);
            currentTail = 0; // to full new array
            int i = 0;
            // make new array with random order
            
            while (i < itemsForIterator)
            {
                while (queueIter[index] != null) // not yet used position
                {
                    index = StdRandom.uniform(0, itemsForIterator);
                }

                while (queue[currentTail] == null) // not empty position
                {
                    currentTail++;
                }
                queueIter[index] = queue[currentTail];
                i++;
                currentTail++;
            }
            currentTail = 0;
        }

        public boolean hasNext()
        {
            return currentTail < itemsForIterator;
        }

        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("no more items to return");
            }
            Item item = queueIter[currentTail];
            currentTail++;
            return item;
        }

        public void remove()
        {
            throw new UnsupportedOperationException("not supported"); 
        }

    }

    // unit testing
    public static void main(String[] args)
    {
        RandomizedQueue<String> randomQ = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            randomQ.enqueue(s);
        }
        StdOut.println("Sample " + randomQ.sample());
        for (String s : randomQ)
        {
            StdOut.println(s);
        }
        StdOut.println(randomQ.dequeue() + " - removed, is empty: " + randomQ.isEmpty());
        for (String s : randomQ)
        {
            StdOut.println(s);
        }

    }
}
