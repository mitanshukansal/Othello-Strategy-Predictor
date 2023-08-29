import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int board[][];
    // add required class variables here

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        // Student can choose to add preprocessing here
        // System.out.println(turn);
        // for (int i = 0; i < 8; ++i) {
        //     for (int j = 0; j < 8; ++j) {
        //         if (board[i][j] != -1)
        //             System.out.print(" ");
        //         System.out.print(board[i][j] + " ");
        //     }
        //     System.out.println("");
        // }
    }

    // add required helper functions here

    public int boardScore() {
        /*
         * Complete this function to return num_black_tiles - num_white_tiles if turn =
         * 0,
         * and num_white_tiles-num_black_tiles otherwise.
         */
        int nb = 0, nw = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 0)
                    nb++;
                else if (board[i][j] == 1)
                    nw++;
            }
        }
        return (turn == 0) ? (nb - nw) : (nw - nb);
    }

    private boolean checkboard(int a, int b, int color) {
        int opp = (color == 0) ? 1 : 0;
        int i, j;
        // boolean up,down,left,right,ul,ur,dl,dr,flag;
        boolean dirn[] = new boolean[8], possible;
        dirn[0] = (a != 0 && board[a - 1][b] == opp) ? true : false;
        dirn[1] = (a != 7 && board[a + 1][b] == opp) ? true : false;
        dirn[2] = (b != 0 && board[a][b - 1] == opp) ? true : false;
        dirn[3] = (b != 7 && board[a][b + 1] == opp) ? true : false;
        dirn[4] = (a != 0 && b != 0 && board[a - 1][b - 1] == opp) ? true : false;
        dirn[5] = (a != 0 && b != 7 && board[a - 1][b + 1] == opp) ? true : false;
        dirn[6] = (a != 7 && b != 0 && board[a + 1][b - 1] == opp) ? true : false;
        dirn[7] = (a != 7 && b != 7 && board[a + 1][b + 1] == opp) ? true : false;
        possible = dirn[0];
        for (int k = 1; k < 8; k++) {
            possible = possible || dirn[k];
        }
        if (!possible)
            return false;
        // System.out.println(a + " " +b);
        for (int k = 0, iadd = 0, jadd = 0; k < 8; k++) {
            switch (k) {
                case 0:// up
                    iadd = -1;
                    jadd = 0;
                    break;
                case 1:// down
                    iadd = 1;
                    jadd = 0;
                    break;
                case 2:// left
                    iadd = 0;
                    jadd = -1;
                    break;
                case 3:// right
                    iadd = 0;
                    jadd = 1;
                    break;
                case 4:// up-left
                    iadd = -1;
                    jadd = -1;
                    break;
                case 5:// up-right
                    iadd = -1;
                    jadd = 1;
                    break;
                case 6:// down-left
                    iadd = 1;
                    jadd = -1;
                    break;
                case 7:// down-right
                    iadd = 1;
                    jadd = 1;
                    break;
            }
            // System.out.println(k+": "+dirn[k]);
            if (dirn[k]) {
                // System.out.println(a + " " + b + " " + k + " " + color);

                i = a + (2 * iadd);
                j = b + (2 * jadd);
                while (i >= 0 && j >= 0 && i < 8 && j < 8) {
                    // System.out.println(i+": "+iadd+";"+jadd+": "+j+" : "+k);
                    // System.out.println(board[i][j]);
                    if (board[i][j] == color) {
                        return true;
                        // System.out.println("possible: " + a + " " + b + " ");
                    } else if (board[i][j] == -1)
                        break;
                    i += iadd;
                    j += jadd;
                }
            }
        }
        return false;
    }

    private ArrayList<Integer> possible_moves(int color) {
        ArrayList<Integer> moves = new ArrayList<Integer>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == -1) {
                    // System.out.println("yes");
                    if (checkboard(i, j, color))
                        moves.add((8 * i) + j);
                }
            }
        }
        return moves;
    }

    private void makemove(int num, int color) {
        int a = num / 8, b = num % 8;
        int opp = (color == 0) ? 1 : 0;
        boolean dirn[] = new boolean[8], flag;
        board[a][b] = color;
        dirn[0] = (a != 0 && board[a - 1][b] == opp) ? true : false;
        dirn[1] = (a != 7 && board[a + 1][b] == opp) ? true : false;
        dirn[2] = (b != 0 && board[a][b - 1] == opp) ? true : false;
        dirn[3] = (b != 7 && board[a][b + 1] == opp) ? true : false;
        dirn[4] = (a != 0 && b != 0 && board[a - 1][b - 1] == opp) ? true : false;
        dirn[5] = (a != 0 && b != 7 && board[a - 1][b + 1] == opp) ? true : false;
        dirn[6] = (a != 7 && b != 0 && board[a + 1][b - 1] == opp) ? true : false;
        dirn[7] = (a != 7 && b != 7 && board[a + 1][b + 1] == opp) ? true : false;
        for (int k = 0, i, j, iadd = 0, jadd = 0; k < 8; k++) {
            switch (k) {
                case 0:// up
                    iadd = -1;
                    jadd = 0;
                    break;
                case 1:// down
                    iadd = 1;
                    jadd = 0;
                    break;
                case 2:// left
                    iadd = 0;
                    jadd = -1;
                    break;
                case 3:// right
                    iadd = 0;
                    jadd = 1;
                    break;
                case 4:// up-left
                    iadd = -1;
                    jadd = -1;
                    break;
                case 5:// up-right
                    iadd = -1;
                    jadd = 1;
                    break;
                case 6:// down-left
                    iadd = 1;
                    jadd = -1;
                    break;
                case 7:// down-right
                    iadd = 1;
                    jadd = 1;
                    break;
            }
            // System.out.println(k+": "+dirn[k]);
            if (dirn[k]) {
                i = a + (2 * iadd);
                j = b + (2 * jadd);
                flag = false;
                while (i >= 0 && j >= 0 && i < 8 && j < 8) {
                    if (board[i][j] == color) {
                        flag = true;
                        break;
                    } else if (board[i][j] == -1) {
                        break;
                    }
                    i += iadd;
                    j += jadd;
                }
                i = a + (iadd);
                j = b + (jadd);
                if (flag)
                    while (i >= 0 && j >= 0 && i < 8 && j < 8) {
                        if (board[i][j] == color) {
                            break;
                        }
                        board[i][j] = color;
                        i += iadd;
                        j += jadd;
                    }
            }
        }
        // for (int i = 0; i < 8; ++i) {
        // for (int j = 0; j < 8; ++j) {
        // if(board[i][j]!=-1)
        // System.out.print(" ");
        // System.out.print(board[i][j]+" ");
        // }
        // System.out.println("");
        // }
    }

    private int[][] mycopy(int[][] board) {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    private int bestMove_rec(int idx, int k) {
        int[][] b = mycopy(board);
        int t = (idx % 2 != 0) ? turn : turn ^ 1;
        ArrayList<Integer> moves = possible_moves(t);
        int orig_idx = idx;
        // System.out.println("no. of possible moves: " + moves.size());
        if (moves.size() == 0) {
            idx++;
            if(orig_idx==1)
                return -1;
            if (idx > k) {
                return (idx % 2 != 0) ? 65 : -65;
            }
            
            t = t ^ 1;
            moves = possible_moves(t);
        }
        int score = (idx % 2 == 0) ? 65 : -65;
        int reqscore = score, reqplace = -1;
        for (Integer place : moves) {
            // System.out.println(place/8+" "+place%8);
            // System.out.println(place);
            makemove(place, t);
            score = (idx == k) ? boardScore() : bestMove_rec(idx + 1, k);
            if(score==65 || score == -65)
                score = boardScore();
            // System.out.println("k:"+idx+":"+reqscore+ " " + score);
            if (idx % 2 != 0) {
                if (reqscore < score) {
                    reqscore = score;
                    reqplace = place;
                }
            } else {
                if (reqscore > score) {
                    reqscore = score;
                    reqplace = place;
                }
            }
            board = mycopy(b);
        }
        if (orig_idx == 1)
            return reqplace;
        return reqscore;
    }

    public int bestMove(int k) {
        /*
         * Complete this function to build a Minimax tree of depth k (current board
         * being at depth 0),
         * for the current player (siginified by the variable turn), and propagate
         * scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j;
         * return i*8+j
         * In case of ties, return the smallest integer value representing the tile with
         * best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the
         * boardScore() is the score
         * for the same player throughout the Minimax tree.
         */
        int[][] b = mycopy(board);
        int move = bestMove_rec(1, k);
        board = b;
        return move;
    }

    public ArrayList<Integer> fullGame(int k) {
        /*
         * Complete this function to compute and execute the best move for each player
         * starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and
         * the turn
         * at each step. In the end, modify the winner variable as required.
         */
        // int count=0;
        ArrayList<Integer> moves = new ArrayList<Integer>();
        int move = bestMove(k);
        do {
            while (move != -1) {
                // System.out.println("move: "+move + " " + (++count)+" "+turn);
                moves.add(move);
                // System.out.println((move/8+1) +" "+(move%8+1));
                makemove(move, turn);
                turn = turn ^ 1;
                move = bestMove(k);
            }
            turn = turn ^ 1;
            move = bestMove(k);
        } while (move != -1);
        int score = boardScore();
        if(score == 0)
            winner = -1;
        else
            winner = (score>0)?turn:turn^1;
        return moves;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }
}