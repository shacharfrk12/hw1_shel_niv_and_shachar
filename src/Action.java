public class Action {
    int tile;
    //int[] place = new int[2];
    Direction dir1;
    public Action(int value, Direction direction /*, int x, int y*/ ){
         tile = value;
         dir1 = direction;
       //  place[0] = x;
       // place[1] = y;
    }
    /*void move(int tile, Direction dir1, int[] place, Board currentBoard){
        switch (dir1){
            case UP:
                currentBoard[place[0]][place[1]] = NULL;

            case DOWN:

            case LEFT:

            case RIGHT:
        }
    }
    */
    @Override
    public String toString(){
        return("Move" + " tile" + " dir1");
    }

}
