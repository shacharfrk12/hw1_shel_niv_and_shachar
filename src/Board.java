import java.util.Arrays;
import java.util.function.BiPredicate;

public class Board {
    private Tile[][] tiles; //array of tiles
    private final int m; //row number
    private final int n; //column number
    private int[] place; //row,col position of empty space

    /**
     * constructor that creates board according to input string
     * @param boardStr string representation of board where lines
     *                 are seperated by | and _ is the empty space
     */
    public Board(String boardStr){
        int n=0;
        this.place = new int[2];
        //splitting to rows
        String[] boardRows = boardStr.split("\\|");
        this.m = boardRows.length;
        this.tiles = new Tile[this.m][];
        //going over rows
        for(int i = 0; i<this.m; i++){
            // splitting to tiles in each row
            String[] tilesStrings = boardRows[i].split(" ");
            //n - number of columns according to first row
            n = (i==0 ? tilesStrings.length : n);
            this.tiles[i] = new Tile[n];
            //going over tiles
            for(int j = 0; j < n; j++){
                //the cell marked _ in the string will be empty - with no tile in it
                if(tilesStrings[j].equals("_")){
                    this.tiles[i][j] = null;
                    this.place[0] = i;
                    this.place[1] = j;
                }
                //otherwise, we create a tile with the fitting value
                else{
                    this.tiles[i][j] = new Tile(Integer.parseInt(tilesStrings[j]));
                }
            }
        }
        this.n = n;
    }

    /***
     *  copy constructor
     * @param board a board to duplicate
     */
    public Board(Board board){
        this.m = board.m;
        this.n = board.n;
        this.place = board.getPlace();
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

    public int[] getPlace(){
        int[] copyPlace = new int[2];
        copyPlace[0] = place[0];
        copyPlace[1] = place[1];
        return copyPlace;
    }

    /**
     * checks the value of the tile that will move according to the direction
     * @param dir one of 4 directions define in Direction enum -
     *                   UP, DOWN, RIGHT, LEFT
     * @return value of the tile
     */
    public int getValue(Direction dir){
        switch(dir){
            case UP:
                return tiles[place[0] + 1][place[1]].getValue();
            case DOWN:
                return tiles[place[0] - 1][place[1]].getValue();
            case LEFT:
                return tiles[place[0]][place[1] + 1].getValue();
            case RIGHT:
                return tiles[place[0]][place[1] - 1].getValue();
        }
        return -1;
    }

    /**
     * the function returns the value of the tile in x, y
     * @param x row index of the wanted space
     * @param y column index of the wanted space
     * @return value of tile
     */
    public int getValue(int x , int y){
        if(tiles[x][y] == null)
            // null doesn't have a value, so we set it to be the value of
            // the location of the right position for the null tile
            return n * m;
        return tiles[x][y].getValue();
    }

    /**
     * moves a tile to the empty space according to the direction given
     * (for example - for direction UP move will move the tile under
     * the empty space up - to replace it)
     * also, move changes place attribute value to the new empty spot location
     *
     * @param dir one of 4 directions define in Direction enum -
     *            UP, DOWN, RIGHT, LEFT
     */
    public void move(Direction dir){
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

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
