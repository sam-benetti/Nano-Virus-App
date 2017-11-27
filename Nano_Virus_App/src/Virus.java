public class Virus {

    private static Virus ourInstance = new Virus();
    private int[] currentCoordinates = new int[3];

    public static Virus getInstance(){
        return ourInstance;
    }

    private Virus() {
    }

    public int getCurrentCoordinates(int i) {
        return currentCoordinates[i];
    }

    public void setCurrentCoordinates(int coordinate, int i) {
        this.currentCoordinates[i] = coordinate;
    }


}
