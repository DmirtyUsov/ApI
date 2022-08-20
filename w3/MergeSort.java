import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MergeSort {

    // This class should not be instantiated.
    private MergeSort() { }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        assert isSorted(a, lo, mid);  // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi);  // precondition: a[mid..hi] sorted

        // copy
        for (int k = lo; k <= hi; k++)
        {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid)
            {
                a[k] = aux[j++];
            }
            else if (j > hi)
            {
                a[k] = aux[i++];
            }
            else if (less(aux[j], aux[i]))
            {
                a[k] = aux[j++];
            }
            else
            {
                a[k] = aux[i++];
            }
        }
        assert isSorted(a, lo, hi);  // postcondition: a[lo..hi] sorted
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a)
    {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    // is v < w ? Helper function
    private static boolean less(Comparable v, Comparable w)
    {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi)
    {
        for (int i = lo + 1; i < hi; i++)
        {
            if (less(a[i], a[i-1]))
            {
                return false;
            }
        }
        return true;
    }
    // print array to standard output
    private static void show(Comparable[] a)
    {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i]);
        }
        StdOut.println("");
    }

    public static void main(String[] args)
    {
        String[] a = StdIn.readAllStrings();
        MergeSort.sort(a);
        show(a);
    }
}
