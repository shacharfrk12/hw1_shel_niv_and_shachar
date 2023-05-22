public class Node {
    private final State state;
    private Node parent;
    private Action action;

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

            //State newState = new State(this.state);
            //nodes[i] = new Node(newState.result(actions[i]), this, actions[i]);
        }
        return nodes;
    }

    /**
     * calculates the absolute value of x
     * @param x number
     * @return absolute value of x
     */
    public static int absoluteValue(int x){
        return (x >= 0 ? x : (-1) * x) ;
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

        //boolean[] isConflictedRow = new boolean[n*m+1];
        //boolean[] isConflictedCol = new boolean[n*m+1];

        //for (int i = 0; i <= n*m; i++) {
           // isConflictedRow[i] = false;
           // isConflictedCol[i] = false;
        //}
        Board goalBoard = new Board(m, n);

        //calculating sum of manhattan distances
        for(int i = 0; i < m ; i++){
            for(int j = 0 ; j < n; j++){
                currValue = board.getValue(i, j);
                //the target board place for this value
                //col = (((currValue % n) == 0) ? (n - 1) : (currValue % n) - 1) ;
                //row = ((col == n - 1) ? ((currValue / n) - 1) : (currValue / n) );
                int[] location = goalBoard.getLocation(currValue);
                row = location[0];
                col = location[1];
                //manhattan distance between this node and target
                distance = absoluteValue(i - row) + absoluteValue(j - col);
                sum += distance;

                //linearConflict(board, i, j, currValue, col, row, isConflictedRow, isConflictedCol);
                //linearConflict(board, i, j, currValue, col, row);
            }
        }

        //sum += conflictsCounter(isConflictedRow, n*m+1) + conflictsCounter(isConflictedCol, n*m+1);

        //last tile
        //sum += (lastTile(board) ? 0 : 2);

        //corner tiles
        //sum+= cornerTile(board);

        sum += linearConflict(board);

        return sum;

    }

    public static int linearConflict(Board board){
        int m = board.getM();
        int n = board.getN();
        int max, currValue, supposedCol, supposedRow, count=0;
        int[] location;

        for (int i = 0; i < m; i++) {
            max = 0;
            for (int j = 0; j < n; j++) {
                currValue = board.getValue(i, j);
                //location = getSupposedLocation(currValue, n);
                //supposedRow = location[1];

                if(currValue<max && currValue>i*n && currValue<(i+1)*n){
                    count+=2;
                }
                if(currValue >= max){
                    max = currValue;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            max = 0;
            for (int j = 0; j < m; j++) {
                currValue = board.getValue(j, i);
                //location = getSupposedLocation(currValue, n);
                //supposedCol = location[0];

                if( (currValue<max) && (( (i != n-1) && (currValue % n == i+1) ) || ( (i == n-1) && (currValue % n == 0) ) )){
                    count+=2;
                }
                if(currValue >= max){
                    max = currValue;
                }
            }
        }
        return count;
    }

    public static int[] getSupposedLocation(int value, int numOfCols){
        int[] location = new int[2];
        location[0] = (((value % numOfCols) == 0) ? (numOfCols - 1) : (value % numOfCols) - 1) ;
        location[1] = ((location[0] == numOfCols - 1) ? ((value / numOfCols) - 1) : (value / numOfCols) );
        return location;
    }

    public static int linearConflict2(Board board, int i, int j, int currValue, int col, int row){
        int m = board.getM();
        int n = board.getN();
        int secondValue, secondCol, secondRow, count=0;

        if(row == i) {
            for (int l = j; l < n; l++) {
                secondValue = board.getValue(i, l);
                //the target board place for this value
                secondCol = ((currValue % n) == 0 ? n - 1 : (currValue % n) - 1) ;
                secondRow = (secondCol == n - 1 ? (currValue / n) - 1 : (currValue / n) );

                if((secondRow == i) && (secondValue < currValue)){
                    count++;
                    break;
                }
            }
        }

        if(col == j){
            for (int l = i; l < m; l++) {
                secondValue = board.getValue(l, j);
                //the target board place for this value
                secondCol = ((currValue % n) == 0 ? n - 1 : (currValue % n) - 1) ;

                if((secondCol == j) && (secondValue < currValue) ){
                    count++;
                    break;
                }
            }
        }


        return count*2;
    }

    public static void linearConflict1(Board board, int i, int j, int currValue, int col, int row, boolean[] isConflictedRow, boolean[] isConflictedCol){
        int m = board.getM();
        int n = board.getN();
        int secondValue, secondCol, secondRow, sum=0;

        if(row == i) {
            for (int l = j; l < n; l++) {
                secondValue = board.getValue(i, l);
                //the target board place for this value
                secondCol = ((currValue % n) == 0 ? n - 1 : (currValue % n) - 1) ;
                secondRow = (secondCol == n - 1 ? (currValue / n) - 1 : (currValue / n) );

                if((secondRow == i) && (secondValue < currValue) && (!isConflictedRow[secondValue])){
                    isConflictedRow[secondValue] = true;
                }
            }
        }

        if(col == j){
            for (int l = i; l < m; l++) {
                secondValue = board.getValue(l, j);
                //the target board place for this value
                secondCol = ((currValue % n) == 0 ? n - 1 : (currValue % n) - 1) ;

                if((secondCol == j) && (secondValue < currValue) && (isConflictedCol[secondValue])){
                    isConflictedCol[secondValue] = true;
                }
            }
        }
    }

    public static int conflictsCounter(boolean[] isConflicted, int length){
        int count = 0;
        for (int i = 1; i < length; i++) {
            if(isConflicted[i])
                count++;
        }
        return count * 2;
    }
    public static boolean lastTile(Board board){
        int m = board.getM();
        int n = board.getN();
        int currValue = board.getValue(m-1, n-1);
        if(currValue != (m - 1) * n &&currValue != m*n - 1){
            return false;
        }
        return true;
    }

    public static int cornerTile(Board board){
        int m = board.getM();
        int n = board.getN();
        int count = 0;

        //left-up
        if(board.getValue(0, 0) != 1)
            count++;
        //right-up
        if(board.getValue(0, n-1) != n)
            count++;
        //left-down
        if(board.getValue(m-1, 0) != ((m-1) * n) + 1)
            count++;
        //right-down
        if(board.getValue(m-1, n-1) != m * n)
            count++;

        return count*2;
    }
}
