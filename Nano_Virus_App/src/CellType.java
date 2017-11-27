import java.util.concurrent.ThreadLocalRandom;

public enum CellType {

    TUMOROUS,
    REDBLOODCELL,
    WHITEBLOODCELL;

    public static CellType returnRandomType(){
        CellType cell = null;

        int randomInt = ThreadLocalRandom.current().nextInt(1, 101);

        if(randomInt > 0 && randomInt <= 5){
            cell = TUMOROUS;
        }
        if(randomInt > 5 && randomInt <= 30){
            cell = WHITEBLOODCELL;
        }
        if(randomInt > 30 && randomInt <= 100) {
            cell = REDBLOODCELL;
        }
        return cell;
    }
}
