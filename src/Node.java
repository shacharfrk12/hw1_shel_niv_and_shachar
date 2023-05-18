public class Node {
    private final State state;
    private final Node parent;
    private final Action action;
    public Node(State state, Node parent, Action action){
        this.state = state;
        this.parent = parent;
        this.action = action;
    }
    public Node(State state){
        this.state = state;
        this.action = null;
        this.parent = null;
    }
    public Node[] expand(){
        Action[] actions = this.state.actions();
        int length = actions.length;
        Node[] nodes = new Node[length];
        for(int i = 0; i < length; i++){
            State newState = this.state.result(actions[i]);
            nodes[i] = new Node(newState, this, actions[i]);
        }
        return nodes;
    }

    public Action getAction(){
        return this.action;
    }
    public State getState(){
        return this.state;
    }
    public Node getParent(){
        return this.parent;
    }
    public int absoluteValue(int x){
        return (x > 0 ? x : -x) ;
    }

    public int heuristicValue(){
        Board board = state.getBoard();
        int m = board.getM();
        int n = board.getN();
        int sum = 0, col = 0, row = 0, currValue = 0 ;
        for(int i = 0; i < m ; i++){
            for(int j = 0 ; j < n; j++){
                 currValue = board.getValue(i, j).getValue();
                 col = (currValue % n);
                 row = (col == 0 ? currValue / n : (currValue / n) + 1);
                int distance = absoluteValue(i - row) + absoluteValue(j - col);
                sum += distance;
            }
        }
        return sum;
    }
}
