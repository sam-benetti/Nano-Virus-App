import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final int NUM_INITIAL_CELLS = 100;
    public static ArrayList<Cell> cells;
    public static Virus virus;

    public static void main(String [] args){
        createCells();
        createVirus();
    }

    public static void createCells(){
        cells = new ArrayList<Cell>();
        for(int i = 0; i < NUM_INITIAL_CELLS; i++) {
            Cell newCell = new Cell();
            cells.add(newCell);
        }
    }

    public static void createVirus(){
        virus = Virus.getInstance();
        int randomIndex = ThreadLocalRandom.current().nextInt(1, 101);
        int x = cells.get(randomIndex).getCoordinates(0);
        int y = cells.get(randomIndex).getCoordinates(1);
        int z = cells.get(randomIndex).getCoordinates(2);

        virus.setCurrentCoordinates(x,0);
        virus.setCurrentCoordinates(y,1);
        virus.setCurrentCoordinates(z,2);

        System.out.println("Virus is starting at Cell: " + randomIndex);
    }
}
