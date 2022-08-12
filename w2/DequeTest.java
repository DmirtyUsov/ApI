import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class DequeTest {
    public static void main(String[] args)
    {
        int job;
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            job = StdRandom.uniform(0, 4);
            StdOut.println("Job " + job + " for " + s + " Size: " + deque.size());
            if (job == 0)
            {
                deque.addFirst(s);
            }
            if (job == 1)
            {
                deque.addLast(s);
            }
            if (job == 2)
            {
                deque.addLast(s);
                deque.removeFirst();
            }
            if (job == 3)
            {
                if (!deque.isEmpty())
                {
                    deque.removeLast();
                }
                deque.addFirst(s);
            }
        }
        for (String s : deque)
        {
            StdOut.println(s);
        }
    }
}
