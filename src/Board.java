import java.util.Arrays;
import java.util.function.BiPredicate;

public class Board {
    private Tile[][] tiles; //array of tiles
    private final int m; //row number
    private final int n; //column number

    /**
     * constructor that creates board according to input string
     * @param boardStr string representation of board where lines
     *                 are seperated by | and _ is the empty space
     */
    public Board(String boardStr){
        //splitting to rows
        String[] boardRows = boardStr.split("\\|");
        this.m = boardRows.length;
        //n - number of columns according to first row
        this.n = boardRows[0].split(" ").length;
        //going over rows
        this.tiles = new Tile[this.m][this.n];
        for(int i = 0; i<this.m; i++){
            // splitting to tiles in each row
            String[] tilesStrings = boardRows[i].split(" ");
            //going over tiles
            for(int j = 0; j < n; j++){
                //the cell marked _ in the string will be empty - with no tile in it
                if(tilesStrings[j].equals("_")){
                    // blank doesn't have a value, so we set it to be the value of
                    // the location of the right position for the blank tile
                    this.tiles[i][j] = new Tile(n*m);
                }
                //otherwise, we create a tile with the fitting value
                else{
                    this.tiles[i][j] = new Tile(Integer.parseInt(tilesStrings[j]));
                }
            }
        }
    }

    /***
     *  copy constructor
     * @param board a board to duplicate
     */
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
    //target board constructor
    public Board(int m, int n ){
        int counter = 1;
        tiles = new Tile[m][n];
        this.m = m;
        this.n = n;
        for(int i = 0;  i < m; i++){
            for(int j = 0; j < n ; j++){
                tiles[i][j] = new Tile(counter);
                counter++;
            }
        }
    }
    public int[] getLocation(int value){
        int[] location = new int[2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <n; j++) {
                if(this.tiles[i][j].getValue() == value){
                    location[0] = i;
                    location[1] = j;
                    return location;
                }
            }

        }
        return location;
    }
    public int getM(){
        return this.m;
    }
    public int getN(){
        return this.n;
    }

    /**
     * checks the value of the tile that will move according to the direction
     * @param dir one of 4 directions define in Direction enum -
     *                   UP, DOWN, RIGHT, LEFT
     * @return value of the tile
     */

    /*
    public int getValue(Direction dir){
        switch(dir){
            case UP:
                return tiles[place[0] + 1][place[1]].getValue();
            case DOWN:
                return tiles[place[0] - 1][place[1]].getValue();
            case RIGHT:
                return tiles[place[0]][place[1] - 1].getValue();
            case LEFT:
                return tiles[place[0]][place[1] + 1].getValue();

        }
        return -1;
    }
    */

    /**
     * the function returns the value of the tile in x, y
     * @param x row index of the wanted space
     * @param y column index of the wanted space
     * @return value of tile
     */
    public int getValue(int x , int y){
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
    public void move(int i, int j, Direction dir){
        switch(dir){
            case UP:
                swap(i, j, i-1, j);
                break;
            case DOWN:
                swap(i, j, i+1, j);
                break;
            case RIGHT:
                swap(i, j, i, j+1);
                break;
            case LEFT:
                swap(i, j, i, j-1);
                break;
        }
    }

    private void swap(int firstRow, int firstCol, int secondRow, int secondCol){
        Tile temp = this.tiles[firstRow][firstCol];
        this.tiles[firstRow][firstCol] = this.tiles[secondRow][secondCol];
        this.tiles[secondRow][secondCol] = temp;
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
