import java.util.Arrays;

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
