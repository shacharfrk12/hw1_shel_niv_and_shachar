public class Action {
    private final int tile;
    private final Direction dir1;
    public Action(int value, Direction direction){
         tile = value;
         dir1 = direction;
    }
    public Direction getDir1(){
        return dir1;
    }
    @Override
    public String toString(){
        return("Move " +  tile + " " +  dir1);
    }

}
