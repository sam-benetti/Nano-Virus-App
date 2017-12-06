import java.io.BufferedWriter;
import java.io.File;

public interface IWriteFileReceiver {
    void writeFile(int cycle, int numCells, int virusPosition, int numTumorous);
}
