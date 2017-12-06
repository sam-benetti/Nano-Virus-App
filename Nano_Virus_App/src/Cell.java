import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {

    private int[] coordinates = new int[]{generateRandomCoordinate(), generateRandomCoordinate(), generateRandomCoordinate()};
    private CellType cellType = CellType.returnRandomType();

    public int getCoordinates(int index){
        return coordinates[index];
    }

    public void setCoordinates(int coordinate, int index){
        this.coordinates[index] = coordinate;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    private int generateRandomCoordinate(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 5001);
        return randomNum;
    }

    private int findNearestCell (ArrayList<Cell> cells){
        double distance = 0;
        double min = 100000;
        int cellIndex = 0;
        int redBloodCells = returnNumRedBloodCells(cells);
        for(int i = 0; i < cells.size(); i++){
            //Tumors need to infect the Red Blood Cells, or White Blood Cells once all the Red Blood Cells are gone
            if(cells.get(i).cellType == CellType.REDBLOODCELL ||
                    (redBloodCells == 0 && cells.get(i).cellType == CellType.WHITEBLOODCELL)) {

                distance =  Math.sqrt((Math.pow(coordinates[0] - cells.get(i).getCoordinates(0), 2)) +
                            (Math.pow(coordinates[1] - cells.get(i).getCoordinates(1), 2)) +
                            (Math.pow(coordinates[2] - cells.get(i).getCoordinates(2), 2)));
                //Distance == 0 means that the virus is at that cell and should be excluded
                if(distance != 0){
                    if(distance < min){
                        min = distance;
                        cellIndex = i;
                    }
                }
            }
        }
        return  cellIndex;
    }

    public void infectNearestCell(ArrayList<Cell> cells){
        int index = 0;

        index = findNearestCell(cells);
        cells.get(index).setCellType(CellType.TUMOROUS);
    }

    private int returnNumRedBloodCells (ArrayList<Cell> cells){
        int count = 0;

        for (int i = 0; i < cells.size(); i++){
            if(cells.get(i).getCellType() == CellType.REDBLOODCELL){
                count++;
            }
        }
        return count;
    }
}
