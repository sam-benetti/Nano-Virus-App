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
}
