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

    public void moveVirusToAnotherCell(ArrayList<Cell> cells) {
        double distance = 0;
        int x = 0;
        int y = 0;
        int z = 0;
        int randomIndex = 0;

        while (distance == 0 || distance >= 5000) {
            randomIndex = ThreadLocalRandom.current().nextInt(1, cells.size());
            x = cells.get(randomIndex).getCoordinates(0);
            y = cells.get(randomIndex).getCoordinates(1);
            z = cells.get(randomIndex).getCoordinates(2);
            distance = Math.sqrt((Math.pow(x - currentCoordinates[0], 2)) +
                    (Math.pow(y - currentCoordinates[1], 2)) +
                    (Math.pow(z - currentCoordinates[2], 2)));
        }
        setCurrentCellPosition(randomIndex);
        setCurrentCoordinates(x, 0);
        setCurrentCoordinates(y, 1);
        setCurrentCoordinates(z, 2);
        System.out.println("Virus has moved to Cell: " + getCurrentCellPosition());

    }

    public ArrayList<Cell> destroyIfTumorousCell(int index, ArrayList<Cell> cells){
        if(cells.get(index).getCellType() == CellType.TUMOROUS){
            cells.remove(index);
            System.out.println("...................Tumor DESTROYED");
        }
        return cells;
    }
}
