import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        StdOut.println(k);
        RandomizedQueue<String> randomQ = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            randomQ.enqueue(s);
        }
        while (k !=0)
        {
            StdOut.println(randomQ.dequeue());
            k--;
        }
    }
}
