import java.util.Arrays;
import java.util.function.BiPredicate;

public class Board {
    private Tile[][] tiles;
    private final int m;
    private final int n;

    public Board(String boardStr){
        int n=0;
        String[] boardRows = boardStr.split("|");
        this.m = boardRows.length;
        this.tiles = new Tile[this.m][];
        //splitting to rows
        for(int i = 0; i<this.m; i++){
            String[] tilesStrings = boardRows[i].split(" ");
            n = i==0 ? tilesStrings.length : n;
            this.tiles[i] = new Tile[n];
            // splitting to tiles in each row
            for(int j = 0; j < n; j++){
                //the cell marked _ in the string will be empty - with no tile in it
                if(tilesStrings[i]=="_"){
                    this.tiles[i][j] = null;
                }
                //otherwise, we create a tile with the fitting value
                else{
                    this.tiles[i][j] = new Tile(Integer.parseInt(tilesStrings[i]));
                }
            }
        }
        this.n = n;

    }
    //copy constractor
    public Board(Board board){
        this.m = board.m;
        this.n = board.n;
        this.tiles = new Tile[this.m][this.n];
        for(int i = 0;i < this.m;i++){
            for(int j = 0;j < this.n;j++){
                this.tiles[i][j] = board.tiles[i][j];
            }
        }
    }
    public int getM(){
        return this.m;
    }
    public int getN(){
        return this.n;
    }

    public boolean isGoal(){
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                if(this.tiles[i][j].getValue() != i*this.n+j){
                    return false;
                }
            }
        }
        return true;
    }

    public void move(Direction dir, int[] place){
        switch(dir){
            case UP:
                tiles[place[0]][place[1]] = tiles[place[0] + 1][place[1]];
                tiles[place[0] + 1][place[1]] = null;
                place[0] = place[0] + 1;
                break;
            case DOWN:
                tiles[place[0]][place[1]] = tiles[place[0] - 1][place[1]];
                tiles[place[0] - 1][place[1]] = null;
                place[0] = place[0] - 1;
                break;
            case LEFT:
                tiles[place[0]][place[1]] = tiles[place[0]][place[1] + 1];
                tiles[place[0]][place[1] + 1] = null;
                place[1] = place[1] + 1;
                break;
            case RIGHT:
                tiles[place[0]][place[1]] = tiles[place[0]][place[1] - 1];
                tiles[place[0]][place[1] - 1] = null;
                place[1] = place[1] - 1;
                break;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }
        Board board = (Board) other;
        return Arrays.deepEquals(tiles, board.tiles);
    }
    public int getValue(int[] place, Direction dir){
        switch(dir){
            case UP:
                tiles[place[0] + 1][place[1]].getValue();
                break;
            case DOWN:
                tiles[place[0] - 1][place[1]].getValue();
                break;
            case LEFT:
                tiles[place[0]][place[1] + 1].getValue();
                break;
            case RIGHT:
                tiles[place[0]][place[1] - 1].getValue();
                break;

        }
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
