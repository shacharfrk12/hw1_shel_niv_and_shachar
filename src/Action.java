public class action {
    int tile;
    Direction dir1;
    action(int value, Direction direction){
         tile = value;
         dir1 = direction;
    }
    @Override
    public String toString(){
        return("Move" + " tile" + " dir1");
    }

}
