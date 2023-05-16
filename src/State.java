public class State {
    private final Board board;

    public State(Board board){
        this.board = new Board(board);
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

    public boolean isGoal(){
        return this.board.isGoal();
    }
}
