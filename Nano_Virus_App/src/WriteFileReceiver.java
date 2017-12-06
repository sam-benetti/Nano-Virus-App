import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class WriteFileReceiver implements IWriteFileReceiver {

   private BufferedWriter openFile() {
        File appDataFile = new File("./data/Nano-Virus-Data.txt");
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(appDataFile, true));
        } catch(IOException e){
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public void writeFile(int cycle, int numCells, int virusPosition, int numTumorous) {
        BufferedWriter writer = openFile();
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
        } finally{
            closeFile(writer);
        }
    }

    private void closeFile(BufferedWriter writer) {
        try{
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
