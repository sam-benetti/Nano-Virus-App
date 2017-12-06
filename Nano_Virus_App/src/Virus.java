import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Virus {

    private static Virus ourInstance = new Virus();
    private int[] currentCoordinates = new int[3];
    private int currentCellPosition = 0;

    public static Virus getInstance() {
        return ourInstance;
    }

    private Virus() {
    }

    public int getCurrentCoordinates(int index) {
        return currentCoordinates[index];
    }

    public void setCurrentCoordinates(int coordinate, int index) {
        this.currentCoordinates[index] = coordinate;
    }

    public int getCurrentCellPosition() {
        return currentCellPosition;
    }

    public void setCurrentCellPosition(int currentCellPosition) {
        this.currentCellPosition = currentCellPosition;
    }

    public boolean moveVirusToAnotherCell(int index, ArrayList<Cell> cells) {
        double distance = 0;
        int x = cells.get(index).getCoordinates(0);
        int y = cells.get(index).getCoordinates(1);;
        int z = cells.get(index).getCoordinates(2);;
        boolean success = true;

        distance = Math.sqrt((Math.pow(x - currentCoordinates[0], 2)) +
                (Math.pow(y - currentCoordinates[1], 2)) +
                (Math.pow(z - currentCoordinates[2], 2)));

        if(distance < 5000){
            setCurrentCellPosition(index);
            setCurrentCoordinates(x, 0);
            setCurrentCoordinates(y, 1);
            setCurrentCoordinates(z, 2);
            System.out.println("Virus has moved to Cell: " + getCurrentCellPosition());

        } else if(distance > 5000) {
            System.out.println("Cell not in range, try another cell");
            success = false;
        }
    return success;
    }

    public ArrayList<Cell> destroyIfTumorousCell(int index, ArrayList<Cell> cells){
        if(cells.get(index).getCellType() == CellType.TUMOROUS){
            cells.remove(index);
            System.out.println("...................Tumor DESTROYED");
        }
        return cells;
    }
}
