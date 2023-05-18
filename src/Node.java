public class Node {
    private final State state;
    private final Node parent;
    private final Action action;

    /**
     * standard constructor
     * @param state a state of the board
     * @param parent the state that was before the last action
     * @param action the last action
     */
    public Node(State state, Node parent, Action action){
        this.state = state;
        this.parent = parent;
        this.action = action;
    }

    /**
     * partial constructor for the first node in the game
     * @param state state of the board in this first node
     */
    public Node(State state){
        this.state = state;
        this.action = null;
        this.parent = null;
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

    /**
     * expands node to all possible nodes that can come from it
     * @return an array of next nodes
     */
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

    /**
     * calculates the absolute value of x
     * @param x number
     * @return absolute value of x
     */
    public static int absoluteValue(int x){
        return (x > 0 ? x : -x) ;
    }

    /**
     * calculates heuristic value - that represents the distance of the current state of the board from
     * the target board
     * the lower tha returned value - the better the board is
     *
     * @return heuristic value
     */
    public int heuristicValue(){
        Board board = state.getBoard();
        int m = board.getM();
        int n = board.getN();
        int sum = 0, col, row, currValue, distance;
        //calculating sum of manhattan distances
        for(int i = 0; i < m ; i++){
            for(int j = 0 ; j < n; j++){
                currValue = board.getValue(i, j);
                //the target board place for this value
                col = (currValue % n);
                row = (col == 0 ? currValue / n : (currValue / n) + 1);
                //manhattan distance between this node and target
                distance = Node.absoluteValue(i - row) + Node.absoluteValue(j - col);
                sum += distance;
            }
        }
        return sum;
    }
}
