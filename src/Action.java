public class Action {
    private final int tile;
    private final Direction dir1;
    public Action(int value, Direction direction){
         this.tile = value;
         this.dir1 = direction;
    }
    public Direction getDir1(){
        return this.dir1;
    }

    public int getTile(){
        return this.tile;
    }
    @Override
    public String toString(){
        return("Move " +  this.tile + " " +  this.dir1.toString().toLowerCase());
    }

}
