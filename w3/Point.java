/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y)
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
    * Draws this point to standard draw.
    */
    public void draw()
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

        /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that)
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

        /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that)
    {
        /* YOUR CODE HERE */
        double slope; // = numerator/denominator
        if (that == null)
        {
            throw new IllegalArgumentException("argument to slopeTo is null");
        }
        double denominator = that.x - this.x;
        double numerator = that.y - this.y;

        if (numerator == 0 && denominator == 0)
        {
            slope = Double.NEGATIVE_INFINITY;
        }
        else if (numerator == 0)
        {
            slope = 0.0;
        }
        else if (denominator == 0)
        {
            slope = Double.POSITIVE_INFINITY;
        }
        else
        {
            slope = numerator / denominator;
        }

        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that)
    {
        /* YOUR CODE HERE */
        int yDiff = this.y - that.y;
        int xDdiff = this.x - that.x;
        int result; 
        
        if (yDiff == 0)
        {
            result = xDdiff;
        }
        else
        {
            result = yDiff;
        }
        
        return result;
    }

        /**
     * The slopeOrder() method should return a comparator
     * that compares its two argument points by the slopes
     * they make with the invoking point (x0, y0).
     * Formally, the point (x1, y1) is less than the point (x2, y2)
     * if and only if the slope (y1 ??? y0) / (x1 ??? x0)
     * is less than the slope (y2 ??? y0) / (x2 ??? x0).
     * Treat horizontal, vertical, and degenerate line segments
     * as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder()
    {
        /* YOUR CODE HERE */
        return new Comparator<Point>()
        {
            @Override
            public int compare(Point p1, Point p2)
            {
                if (p1 == null || p2 == null) {
                    throw new IllegalArgumentException("argument to compare constructor is null");
                }
                double d1 = slopeTo(p1);
                double d2 = slopeTo(p2);

                return Double.compare(d1, d2);
            }
        };
    }

      /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString()
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
}
