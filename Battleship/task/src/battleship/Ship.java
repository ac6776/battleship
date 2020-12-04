package battleship;

public class Ship {
    private int id;
    private String name;
    private int numCells;

    public Ship(int id, String name, int numCells) {
        this.id = id;
        this.name = name;
        this.numCells = numCells;
    }

    public String getName() {
        return name;
    }

    public int getNumCells() {
        return numCells;
    }

    public int getId() {
        return id;
    }
}