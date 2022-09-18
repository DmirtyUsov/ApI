import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    /* 
    * We define a search node of the game to be
    * a board,
    * the number of moves made to reach the board,
    * and the previous search node
    */
    private class SearchNode
    {
        private final int moves;
        private final int priorityFuncManhatann;
        private final Board board;
        private final SearchNode prevSearchNode;

        public SearchNode(Board board, int moves, SearchNode prevSearchNode)
        {
            if (board == null)
            {
                throw new IllegalArgumentException("argument is null");
            }
            this.board = board;
            this.priorityFuncManhatann = board.manhattan() + moves;
            this.moves = moves;
            this.prevSearchNode = prevSearchNode;
        }
    }
    private class OrderManhattan implements Comparator<SearchNode>
    {

        @Override
        public int compare(Solver.SearchNode node1, Solver.SearchNode node2) {

            int result = 0; // equal
            
            if (node1.priorityFuncManhatann < node2.priorityFuncManhatann)
            {
                result = -1;
            }
            else if (node1.priorityFuncManhatann > node2.priorityFuncManhatann)
            {
                result = 1;
            }
            return result;
        }

    }

    private int move = 0; // first step for initaial board
    private ArrayList<Board> boardsSequence;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        isSolvable = true;
        Comparator<SearchNode> orderManhattan = new OrderManhattan();

        SearchNode node = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>(orderManhattan);
        
        // for paralle check isSolvable
        SearchNode nodeTwin = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> queueTwin = new MinPQ<SearchNode>(orderManhattan);
        
        boardsSequence = new ArrayList<>();

        while (!node.board.isGoal())
        {
            move++;

            for (Board neighbors : node.board.neighbors())
            {
                if (node.prevSearchNode == null)
                {
                    queue.insert(new SearchNode(neighbors, move, node));
                }
                else if (!neighbors.equals(node.prevSearchNode.board))
                {
                    queue.insert(new SearchNode(neighbors, move, node));
                }
            }
            node = queue.delMin();

            // parallel check for IsSolvable -----
            for (Board neighborsTwin : nodeTwin.board.neighbors())
            {
                if (nodeTwin.prevSearchNode == null)
                {
                    queueTwin.insert(new SearchNode(neighborsTwin, move, nodeTwin));
                }
                else if (!neighborsTwin.equals(nodeTwin.prevSearchNode.board))
                {
                    queueTwin.insert(new SearchNode(neighborsTwin, move, nodeTwin));
                }
            }
            nodeTwin = queueTwin.delMin();
            if (nodeTwin.board.isGoal())
            {
                isSolvable = false;
                move = -1;
                boardsSequence.clear();
                break;
            }
            // -------------
        }

        if (isSolvable)
        { // solution is to backtrack over the nodes' stored previous search nodes
            move = 0;
            boardsSequence.add(node.board); // current board is goal

            while (node.prevSearchNode != null)
            {
                move++;
                node = node.prevSearchNode;
                boardsSequence.add(0, node.board); // add before prev board
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        if (isSolvable)
        {
            return boardsSequence;
        }
        else
        {
            return null;
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
    
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
            {
                StdOut.println(board);
            }
        }
    }
}
