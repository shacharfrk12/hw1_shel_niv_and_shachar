public class State {
    private final Board board;

    public State(Board board) {
        this.board = new Board(board);
    }

    public State(State state){
        this.board = new Board(state.getBoard());
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
     * @return true if it is identical, false otherwise
     */
    public boolean isGoal() {
        int m = board.getM();
        int n = board.getN();
        int count = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(board.getValue(i, j) != count){
                    return false;
                }
                count++;
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
        int[] place = board.getLocation(board.getM() * board.getN());

        //checking if the moves are valid - if not, we flag them as null
        dirs[0] = (place[0] + 1 < this.board.getM()) ? dirs[0]: null;
        dirs[1] = (place[0] - 1 >= 0) ? dirs[1]: null;
        dirs[2] = (place[1] - 1 >= 0) ? dirs[2]: null;
        dirs[3] = (place[1] + 1 < this.board.getN()) ? dirs[3]: null;

        //counting how many moves are possible
        for (int i = 0; i < 4; i++) {
            if (dirs[i] != null)
                sum++;
        }

        //defining array of actions
        Action[] actionsArr = new Action[sum];

        //up
        if(dirs[0] != null){
            actionsArr[j] = new Action(this.board.getValue(place[0] + 1, place[1]), dirs[0]);
            j++;
        }
        //down
        if(dirs[1] != null){
            actionsArr[j] = new Action(this.board.getValue(place[0] - 1, place[1]), dirs[1]);
            j++;
        }
        //right
        if(dirs[2] != null){
            actionsArr[j] = new Action(this.board.getValue(place[0], place[1]-1), dirs[2]);
            j++;
        }
        //left
        if(dirs[3] != null){
            actionsArr[j] = new Action(this.board.getValue(place[0], place[1]+1), dirs[3]);
        }

        /*
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
        }*/

        return actionsArr;
    }

    /**
     * receives action and creates a new state of the board after the actions performance
     * @param action an action object
     * @return the state of the board after performing action
     */
    public State result(Action action){
        Board nextBoard = new Board(this.board);
        int[] location = nextBoard.getLocation(action.getTile());
        nextBoard.move(location[0], location[1], action.getDir1());
        return (new State(nextBoard));

    }
}
