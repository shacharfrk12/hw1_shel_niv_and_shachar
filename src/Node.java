public class Node {
    private final State state;
    private final State fatherState;
    private final Action action;
    public Node(State state, State fatherState, Action action){
        this.state = state;
        this.fatherState = fatherState;
        this.action = action;
    }
    public Node(State state){
        this.state = state;
        this.action = null;
        this.fatherState = null;
    }
    public Node[] expand(){
        Action[] actions = this.state.actions();
        int length = actions.length;
        Node[] nodes = new Node[length];
        for(int i = 0; i < length; i++){
            State newState = this.state.result(actions[i]);
            nodes[i] = new Node(newState, this.state, actions[i]);
        }
        return nodes;
    }

    public Action getAction(){
        return this.action;
    }

    public int heuristicValue(){
        return 0;
    }

}
