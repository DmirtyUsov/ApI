import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board
{
    private final int boardSize;
    private int[] board;
    private int idxBlank;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        boardSize = tiles.length;
        board = new int[(boardSize * boardSize) + 1];
        board[0] = -1; // not use 0 pos. For simplicity 
        int idx = 1; // index from 1
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                board[idx] = tiles[i][j];
                if (board[idx] == 0)
                {
                    idxBlank = idx;
                }
                idx++;
            }
        }

    }

    // string representation of this board
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append(boardSize + "\n");
        int idx = 1;
        while (idx < board.length)
        {
            for (int col = 0; col < boardSize; col++)
            {
                result.append(board[idx] + " ");
                idx++;
            }
            result.append("\n");
        }
        return result.toString();
    }
    
    // board dimension n
    public int dimension()
    {
        return boardSize;
    }

    // number of tiles out of place
    public int hamming()
    {
        int idx = 1;
        int hamming = 0;
        while (idx < board.length)
        {
            if (board[idx] != idx)
            {
                if (board[idx] != 0) // ignore blank
                {
                    hamming++;
                }
            }
            idx++;
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int manhattan = 0;
        int idx = 1;
        int rightRow, rightCol, wrongRow, wrongCol, vertical, horizontal; 

        while (idx < board.length)
        {

            if (board[idx] != 0 && board[idx] != idx) // exclude blank and correct positions
            {
                wrongRow = ((idx -1) / boardSize);
                wrongCol = (idx-1) % boardSize;
                rightRow = ((board[idx]-1) / boardSize);
                rightCol = ((board[idx]-1) % boardSize);
                // distance between right and wrong
                vertical = wrongRow - rightRow;
                horizontal = wrongCol - rightCol;
                
                // make absolute value
                if (vertical < 0)
                {
                    vertical *= -1;
                }
                if (horizontal < 0)
                {
                    horizontal *= -1;
                }
                manhattan = manhattan + vertical + horizontal;
            }
            idx++;
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return manhattan() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y)
    {
        // self check
        if (this == y)
        {
            return true;
        }
        // null check 
        if (y == null)
        {
            return false;
        }
        // type check and cast
        if (getClass() != y.getClass())
        {
            return false;
        }
        Board that = (Board) y;
        // check dimension
        if (this.dimension() != that.dimension())
        {
            return false;
        }

        // check arrays
        for (int idx = 1; idx < board.length; idx++)
        {
            if (this.board[idx] != that.board[idx])
            {
                return false;
            }
        }
        // all checked. equal!
        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        ArrayList<Board> neighbors = new ArrayList<>();
        // blank to left, right, up, down 
        int[] moves = new int[] {-1, 1, -boardSize, boardSize};
        int move = 0;
        
        // dec by 1 for correct board sizing
        int rowEdge = (idxBlank - 1) % boardSize; // 0 is start, (boardSize-1) is end of row

        for (int i = 0; i < moves.length; i++)
        {
            move = idxBlank + moves[i];
            if (move > 0 && move < board.length)
            {
                if (moves[i] == -1 && rowEdge == 0)
                {  // left move. row of tile changed
                    continue;
                }
                if (moves[i] == 1 && rowEdge == (boardSize-1))
                {  // right move. row of tile changed
                    continue;
                }
                // create neighbor
                int[] newBoard = board.clone();
                // swap tiles for new neighbor
                newBoard[idxBlank] = newBoard[move];
                newBoard[move] = 0; // new blank

                int[][] tiles = new int[boardSize][boardSize];
                int idx = 1;
                for (int row = 0; row < boardSize; row++)
                {
                    for (int col = 0; col < boardSize; col++)
                    {
                        tiles[row][col] = newBoard[idx++];
                    }
                }
                neighbors.add(new Board(tiles));
            }
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        // create neighbor
        int[] newBoard = board.clone();
        
        // swap tiles
        int tile1 = idxBlank - 1;
        int tile2 = idxBlank + 1;
        if (tile1 < 1)
        {
            tile1 = board.length - 1;
        }
        if (tile2 > board.length - 1)
        {
            tile2 = 1;
        }

        int temp = newBoard[tile1];
        newBoard[tile1] = newBoard[tile2];
        newBoard[tile2] = temp;

        int[][] tiles = new int[boardSize][boardSize];
        int idx = 1;
        for (int row = 0; row < boardSize; row++)
        {
            for (int col = 0; col < boardSize; col++)
            {
                tiles[row][col] = newBoard[idx++];
            }
        }
        return new Board(tiles);
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
        StdOut.println(initial.toString());
        StdOut.println("Manhattan " + initial.manhattan());
        StdOut.println("Hamming " + initial.hamming());
        StdOut.println("Twin -------");
        StdOut.println(initial.twin().toString());
        StdOut.println("Neighbors -------");

        for (Board neighbors : initial.neighbors())
        {
            StdOut.println(neighbors);
        }
    }
}
