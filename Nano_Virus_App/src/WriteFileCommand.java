import java.io.BufferedWriter;
import java.io.File;
import java.nio.Buffer;

public class WriteFileCommand implements Command {

    private WriteFileReceiver writeFileReceiver;
    private int cycle;
    private int numCells;
    private int virusPosition;
    private int numTumorous;

    public WriteFileCommand(WriteFileReceiver writeFileReceiver, int cycle, int numCells, int virusPosition, int numTumorous){
        this.writeFileReceiver = writeFileReceiver;
        this.cycle = cycle;
        this.numCells = numCells;
        this.virusPosition = virusPosition;
        this.numTumorous = numTumorous;
    }

    @Override
    public void execute() {
        this.writeFileReceiver.writeFile(cycle, numCells, virusPosition, numTumorous);
    }
}
