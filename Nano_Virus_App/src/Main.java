import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final int NUM_INITIAL_CELLS = 100;
    public static ArrayList<Cell> cells;
    public static Virus virus;
    public static int cycle = 0;

    public static void main(String[] args) {

        boolean keepGoing = true;
        int tumorousCells = 0;
        createCells();
        createVirus();
        Scanner input = new Scanner(System.in);
        WriteFileReceiver writeFileReceiver = new WriteFileReceiver();

        File appDataFile = new File("./data/Nano-Virus-Data.txt");
        if(appDataFile.exists()){
            appDataFile.delete();
        }

            while(keepGoing) {

                System.out.println("CYCLE: " + cycle);
                if (cycle % 5 == 0) {
                    for (int i = 0; i < cells.size(); i++) {
                        if (cells.get(i).getCellType() == CellType.TUMOROUS) {
                            cells.get(i).infectNearestCell(cells);
                        }
                    }
                }
                tumorousCells = returnNumTumorousCells(cells);

                if (tumorousCells == 0 || tumorousCells == cells.size()) {
                    keepGoing = false;
                    if (tumorousCells == 0) {
                        System.out.println("THE VIRUS HAS WON!!!");
                    } else if (tumorousCells == cells.size()) {
                        System.out.println("THE TUMOR HAS WON!!!");
                    }
                }
                System.out.println("Player move: 1 - Move Virus, 2 - Destroy Tumor, 3 - End Turn");
                int choice = input.nextInt();
                switch(choice){
                    case 1:
                        System.out.println("Which cell would you like to move to?");
                        int cellNumber = input.nextInt();
                        boolean moved = virus.moveVirusToAnotherCell(cellNumber, cells);
                        while(moved == false) {
                            System.out.println("Choose another cell: ");
                            cellNumber = input.nextInt();
                            moved = virus.moveVirusToAnotherCell(cellNumber, cells);
                        }
                        System.out.println(cells.get(cellNumber).getCellType());
                        break;
                    case 2:
                        System.out.println(cells.get(virus.getCurrentCellPosition()).getCellType());
                        virus.destroyIfTumorousCell(virus.getCurrentCellPosition(), cells);
                        break;
                    case 3:
                        System.out.println("End of round");

                }
                System.out.println("Size of cell array is: " + cells.size());
                System.out.println("Number of tumorous cells: " + returnNumTumorousCells(cells));

                //writeCycleDataToFile(writer, cycle, cells.size(), virus.getCurrentCellPosition(), tumorousCells);
                WriteFileCommand writeFileCommand = new WriteFileCommand(writeFileReceiver, cycle, cells.size(), virus.getCurrentCellPosition(), tumorousCells);
                FileInvoker file = new FileInvoker(writeFileCommand);
                file.execute();

                System.out.println('\n');

                cycle++;
            }

                input.close();

    }

    public static void createCells(){
        cells = new ArrayList<Cell>();
        for(int i = 0; i < NUM_INITIAL_CELLS; i++) {
            Cell newCell = CellFactory.getCell();
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

    public static void writeCycleDataToFile(BufferedWriter writer, int cycle, int numCells, int virusPosition, int numTumorous){

        try{
            writer.write("Cycle: " + cycle);
            writer.newLine();
            writer.write("Cells left: " + numCells);
            writer.newLine();
            writer.write("Current virus position: " + virusPosition);
            writer.newLine();
            writer.write("Number of tumorous cells left: " + numTumorous);
            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    }

