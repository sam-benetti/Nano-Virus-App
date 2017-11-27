import java.util.concurrent.ThreadLocalRandom;

public class Cell {

    private int[] coordinates = new int[]{generateRandomCoordinate(), generateRandomCoordinate(), generateRandomCoordinate()};
    private CellType cellType = CellType.returnRandomType();


    public int getCoordinates(int i){
        return coordinates[i];
    }

    public void setCoordinates(int coordinate, int i){
        this.coordinates[i] = coordinate;
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
