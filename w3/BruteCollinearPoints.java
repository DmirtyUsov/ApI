/*
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q,
 * between p and r, and between p and s are all equal.
 */

import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints
{
    private ArrayList<LineSegment> mySegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        double pqSlope, prSlope, psSlope;

        checkPointArray(points);

        for (int p = 0; p < points.length - 3; p++)
        {
            for (int q = p + 1; q < points.length - 2; q++)
            {
                pqSlope = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < points.length - 1; r++)
                {
                    prSlope = points[p].slopeTo(points[r]);
                    if (pqSlope == prSlope)
                    {
                        for (int s = r + 1; s < points.length; s++)
                        {
                            psSlope = points[p].slopeTo(points[s]);
                            if (pqSlope == psSlope)
                            {
                                // sort points of line
                                int[] line = {p, q, r, s};
                                int swap;
                                for (int i = 0; i < line.length; i++)
                                {
                                    int min = i;
                                    for (int j = i + 1; j < line.length; j++)
                                    {
                                        if (points[line[j]].compareTo(points[line[min]]) < 0)
                                        {
                                            min = j;
                                        }
                                    }
                                    swap = line[i];
                                    line[i] = line[min];
                                    line[min] = swap;
                                }
                                // add new segment
                                mySegments.add(new LineSegment(points[line[0]], points[line[3]]));
                            }
                        }

                    }
                }
            }

        }

    }

    // the number of line segments
    public int numberOfSegments()
    {
        return mySegments.size();
    }
    
    // the line segments
    public LineSegment[] segments()
    {
        return mySegments.toArray(new LineSegment[mySegments.size()]);
    }

    // throw an IllegalArgumentException if the argument to the constructor is null,
    // if any point in the array is null,
    // or if the argument to the constructor contains a repeated point.
    private void checkPointArray(Point[] points)
    {
        if (points == null)
        {
            throw new IllegalArgumentException("argument is Null");
        }
        else if (points.length == 0)
        {
            throw new IllegalArgumentException("0 points");
        }
        else
        {
            if (points.length > 1)
            {
                for (int i = 0; i < points.length-1; i++)
                {
                    if (points[i] == null)
                    {
                        throw new IllegalArgumentException("null point");
                    }
    
                        for (int k = i+1; k < points.length; k++)
                        {
                            if (points[k] == null)
                            {
                                throw new IllegalArgumentException("null point");
                            }
                            else if (points[i].compareTo(points[k]) == 0)
                            {
                                throw new IllegalArgumentException("repeated point");
                            }
                        }
                }
            }
            else if (points[0] == null)
            {
                throw new IllegalArgumentException("null point");
            }
        }
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //    p.draw();
        // }
        // StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            // segment.draw();
        }
        // StdDraw.show();
    }
}

/*
 * to resolve [AUTOGRADER ISSUE]The file 'LineSegment.java' was not submitted.
 */
class LineSegment
{
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param  p one endpoint
     * @param  q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *         is <tt>null</tt>
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
    }

    
    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this line segment
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Throws an exception if called. The hashCode() method is not supported because
     * hashing has not yet been introduced in this course. Moreover, hashing does not
     * typically lead to good *worst-case* performance guarantees, as required on this
     * assignment.
     *
     * @throws UnsupportedOperationException if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}
