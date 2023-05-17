public class State {
    private final Board board;
    private final int[] place;

    public State(Board board, int[] place) {
        this.board = new Board(board);
        this.place = new int[2];
        this.place[0] = place[0];
        this.place[1] = place[1];
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

    public boolean isGoal() {
        return this.board.isGoal();
    }

    public Action[] actions() {
        int sum = 0, j = 0;
        Direction[] dirs = Direction.values();

        dirs[0] = place[0] + 1 < this.board.getN() ? dirs[0]: null;
        dirs[1] = place[0] - 1 >= 0 ? dirs[1]: null;
        dirs[2] = place[1] - 1 >= 0 ? dirs[0]: null;
        dirs[3] = place[1] + 1 < this.board.getM() ? dirs[2]: null;

        for (int i = 0; i <= 4; i++) {
            if (dirs[i] != null)
                sum++;
        }

        Action[] actionsArr = new Action[sum];

        for (int i = 0; i <= sum; i++) {
            while(j < 4 && dirs[j]==null){
                j++;
            }
            if (j<4)
                actionsArr[i] = new Action(this.board.getValue(this.place, dirs[j]), dirs[j]);
        }

        return actionsArr;
    }

    public State result(Action action){
        Board nextBoard = new Board(this.board);
        int[] newPlace = new int[2];
        newPlace[0] = place[0];
        newPlace[1] = place[1];
        //the NEW_PLce array is updated in move function
        nextBoard.move(action.getDir1(), newPlace);
        return new State(nextBoard, newPlace);

    }
}
