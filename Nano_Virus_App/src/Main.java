import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final int NUM_INITIAL_CELLS = 100;
    public static ArrayList<Cell> cells;
    public static Virus virus;
    public static int cycle = 0;

    public static void main(String [] args){

        boolean keepGoing = true;
        int tumorousCells = 0;
        createCells();
        createVirus();

       while(keepGoing){

           System.out.println("CYCLE: " + cycle);
            if(cycle%5 == 0){
                for(int i = 0; i < cells.size(); i++){
                    if(cells.get(i).getCellType() == CellType.TUMOROUS){
                        cells.get(i).infectNearestCell(cells);
                    }
                }
            }
           tumorousCells = returnNumTumorousCells(cells);

           if(tumorousCells == 0 || tumorousCells == cells.size()){
               keepGoing = false;
               if(tumorousCells == 0){
                   System.out.println("THE VIRUS HAS WON!!!");
               }
               else if(tumorousCells == cells.size()) {
                   System.out.println("THE TUMOR HAS WON!!!");
               }
           }
           virus.moveVirusToAnotherCell(cells);
           System.out.println(cells.get(virus.getCurrentCellPosition()).getCellType());
           virus.destroyIfTumorousCell(virus.getCurrentCellPosition(), cells);
           System.out.println("Size of cell array is: " + cells.size());
           System.out.println("Number of tumorous cells: " + returnNumTumorousCells(cells));


           System.out.println('\n');

           cycle++;

            //End simulation condition: All cells are tumorous or all tumorous cells have been destroyed

        }
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
        int randomIndex = ThreadLocalRandom.current().nextInt(1, NUM_INITIAL_CELLS + 1);
        int x = cells.get(randomIndex).getCoordinates(0);
        int y = cells.get(randomIndex).getCoordinates(1);
        int z = cells.get(randomIndex).getCoordinates(2);
        virus.setCurrentCellPosition(randomIndex);
        virus.setCurrentCoordinates(x,0);
        virus.setCurrentCoordinates(y,1);
        virus.setCurrentCoordinates(z,2);

        System.out.println("Virus is starting at Cell: " + randomIndex);
        System.out.println("Virus is starting at Cell: " + virus.getCurrentCellPosition());

    }

    public static int returnNumTumorousCells(ArrayList<Cell> cells){
        int count = 0;
        for(int i = 0; i < cells.size(); i++){
            if(cells.get(i).getCellType() == CellType.TUMOROUS){
                count++;
            }
        }
        return count;
    }
}
