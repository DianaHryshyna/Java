package AI;

import View.Board;


public class Main {

    private Main() {}

    public static void alphaBetaPruning (Board board) {
        AlphaBetaPruning.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }
}