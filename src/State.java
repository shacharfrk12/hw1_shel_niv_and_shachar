public class State {
    private final Board board;

    public State(Board board) {
        this.board = new Board(board);
    }

    public Board getBoard() {
        return new Board(board);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof State)) {
            return false;
        }
        State otherState = (State) other;
        return board.equals(otherState.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

    /**
     * checks if the state of the board is identical to target board - meaning the solution of the game
     * @return true if it is identical, false otherwize
     */
    public boolean isGoal() {
        int m = board.getM();
        int n = board.getM();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(board.getValue(i, j) != i*n+j+1){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * generates all possible moves from the current state in the next order of directions:
     * UP, DOWN, RIGHT, LEFT
     * @return array of these possible actions
     */
    public Action[] actions() {
        int sum = 0, j = 0;
        //generating an array of directions
        Direction[] dirs = Direction.values();
        int[] place = board.getPlace();
        //checking if the moves are valid - if not, we flag them as null
        dirs[0] = place[0] + 1 < this.board.getM() ? dirs[0]: null;
        dirs[1] = place[0] - 1 >= 0 ? dirs[1]: null;
        dirs[2] = place[1] - 1 >= 0 ? dirs[2]: null;
        dirs[3] = place[1] + 1 < this.board.getN() ? dirs[3]: null;

        //counting how many moves are possible
        for (int i = 0; i < 4; i++) {
            if (dirs[i] != null)
                sum++;
        }

        //defining array of actions
        Action[] actionsArr = new Action[sum];

        //creating actions
        for (int i = 0; i < sum; i++) {
            //finding the next first valid direction for move
            while(j < 4 && dirs[j]==null){
                j++;
            }
            //if found direction, create action and place it in the array
            if (j<4)
                actionsArr[i] = new Action(this.board.getValue(dirs[j]), dirs[j]);
            j++;
        }

        return actionsArr;
    }

    /**
     * receives action and creates a new state of the board after the actions performance
     * @param action an action object
     * @return the state of the board after performing action
     */
    public State result(Action action){
        Board nextBoard = new Board(this.board);
        nextBoard.move(action.getDir1());
        return new State(nextBoard);

    }
}
