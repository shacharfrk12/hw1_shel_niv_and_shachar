public class Tile {
    private final int value;
    public Tile(int tileValue){
        this.value = tileValue;
    }
    int getValue(){
        return this.value;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tile)) {
            return false;
        }
        Tile tile = (Tile) other;
        return value == tile.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}