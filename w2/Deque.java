import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item>{
    
    private class Node
    {
        Item item;
        Node prev;
        Node next;
    }

    private Node first, last;
    private int size; 

    // construct an empty deque
    public Deque(){
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("item is null");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        oldFirst.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("item is null");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        
        if (isEmpty())
        {
            first = last;
        }
        else
        {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty())
        {
            last = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    { 
        if (isEmpty())
        {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last.prev.next = null;
        last = last.prev;
        size--;
        if (isEmpty())
        {
            first = null;
        }
        return item;
    }
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        {
            return current != null;
        }

        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("no more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove()
        {
            throw new UnsupportedOperationException("not supported"); 
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    // unit testing
    public static void main(String[] args)
    {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            deque.addLast(s);
        }
        String first =  deque.removeFirst();
        String last = deque.removeLast();
        deque.addFirst(first + " " + last + " Total " + deque.size());
        for (String s : deque)
        {
            StdOut.println(s);
        }
    }
}
