import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class App {
    public JPanel pnlMain;
    private JPanel pnlButtons;
    private JButton btnMove;
    private JButton btnNothing;
    private JButton btnDestroy;
    private JScrollPane scrollMain;
    private JTextArea txtMain;
    private JTextField txtCycle;
    private JTextField txtTumors;
    private static final int NUM_INITIAL_CELLS = 100;
    private ArrayList<Cell> cells;
    private Virus virus;
    private int cycle = 0;

    public App() {

        WriteFileReceiver writeFileReceiver = new WriteFileReceiver();

        JOptionPane.showMessageDialog(pnlMain, "Press OK to Start");

        txtCycle.setText("CYCLE: " + cycle);
        btnDestroy.setEnabled(false);

        startApp();

        txtTumors.setText("Tumors remaining: " + returnNumTumorousCells(cells));
        txtMain.append("Virus is starting at cell: " + virus.getCurrentCellPosition() + " - " + cells.get(virus.getCurrentCellPosition()).getCellType() + "\n");
        txtMain.append("Please press a button...");
        txtMain.append("\n");
        txtMain.append("\n");

        btnMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cellNumber = Integer.parseInt(JOptionPane.showInputDialog(pnlMain, "Which cell would you like to move to?"));
                while (cellNumber < 0 || cellNumber > cells.size() - 1) {
                    JOptionPane.showMessageDialog(pnlMain, "Not a valid option");
                    cellNumber = Integer.parseInt(JOptionPane.showInputDialog(pnlMain, "Which cell would you like to move to?"));
                }
                boolean moved = virus.moveVirusToAnotherCell(cellNumber, cells);
                while (moved == false) {
                    JOptionPane.showMessageDialog(pnlMain, "Cell out of range, please choose another cell");
                    cellNumber = Integer.parseInt(JOptionPane.showInputDialog(pnlMain, "Which cell would you like to move to?"));
                    moved = virus.moveVirusToAnotherCell(cellNumber, cells);
                }
                txtMain.append("Virus has moved to cell: " + virus.getCurrentCellPosition() + " - " + cells.get(virus.getCurrentCellPosition()).getCellType() + "\n");
                if (cells.get(virus.getCurrentCellPosition()).getCellType() == CellType.TUMOROUS) {
                    btnDestroy.setEnabled(true);
                }
                cycle++;
                endCycle();
                writeToFile(writeFileReceiver, returnNumTumorousCells(cells));
                txtCycle.setText("CYCLE: " + cycle);
            }
        });

        btnDestroy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                virus.destroyIfTumorousCell(virus.getCurrentCellPosition(), cells);
                txtMain.append("Tumor Destroyed!" + "\n");
                btnDestroy.setEnabled(false);   //Disabled to prevent pressing once the tumor is destroyed and doesn't exist anymore
                txtTumors.setText("Tumors remaining: " + returnNumTumorousCells(cells));
                cycle++;
                endCycle();
                writeToFile(writeFileReceiver, returnNumTumorousCells(cells));
                txtCycle.setText("CYCLE: " + cycle);
            }
        });

        btnNothing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtMain.append("Cycle over..." + "\n");
                cycle++;
                endCycle();
                writeToFile(writeFileReceiver, returnNumTumorousCells(cells));
                txtCycle.setText("CYCLE: " + cycle);
            }
        });
    }

    private void startApp() {
        //Any files that may exist from previous runs of the app are deleted to make space for the new file, otherwise data is just appended to the old file
        File appDataFile = new File("Nano-Virus-Data.txt");
        if (appDataFile.exists()) {
            appDataFile.delete();
        }
        createCells();
        createVirus();
    }

    private void createCells() {
        cells = new ArrayList<Cell>();
        for (int i = 0; i < NUM_INITIAL_CELLS; i++) {
            Cell newCell = CellFactory.getCell();
            cells.add(newCell);
        }
    }

    private void createVirus() {
        virus = Virus.getInstance();
        int randomIndex = ThreadLocalRandom.current().nextInt(0, NUM_INITIAL_CELLS);
        int x = cells.get(randomIndex).getCoordinates(0);
        int y = cells.get(randomIndex).getCoordinates(1);
        int z = cells.get(randomIndex).getCoordinates(2);
        virus.setCurrentCellPosition(randomIndex);
        virus.setCurrentCoordinates(x, 0);
        virus.setCurrentCoordinates(y, 1);
        virus.setCurrentCoordinates(z, 2);
    }

    /* Increases tumors every 5th cycle and ends app if necessary */
    private void endCycle() {
        if (cycle % 5 == 0 && cycle > 0) {
            infectNearestCellsWithTumors();
        }
        int tumorousCells = returnNumTumorousCells(cells);
        if (tumorousCells == 0 || tumorousCells == cells.size()) {
            txtMain.append(endApp(tumorousCells));
        }
    }

    private void infectNearestCellsWithTumors() {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getCellType() == CellType.TUMOROUS) {
                cells.get(i).infectNearestCell(cells);
            }
        }
        txtTumors.setText("Tumors remaining: " + returnNumTumorousCells(cells));
    }
    /*App ends if there are no Tumorous cells or if all the cells are Tumorous */
    private String endApp(int tumorousCells) {
        String endMessage = "";

        btnMove.setEnabled(false);
        btnDestroy.setEnabled(false);
        btnNothing.setEnabled(false);
        if (tumorousCells == 0) {
            endMessage = "Virus Has Won!";
        } else if (tumorousCells == cells.size()) {
            endMessage = "The Tumor Has Won!";
        }
        return endMessage;
    }

    /* Uses command pattern to write to file */
    private void writeToFile(WriteFileReceiver writeFileReceiver, int tumorousCells) {
        WriteFileCommand writeFileCommand = new WriteFileCommand(writeFileReceiver, cycle, cells.size(), virus.getCurrentCellPosition(), tumorousCells);
        FileInvoker file = new FileInvoker(writeFileCommand);
        file.execute();
    }

    public int returnNumTumorousCells(ArrayList<Cell> cells){
        int count = 0;
        for(int i = 0; i < cells.size(); i++){
            if(cells.get(i).getCellType() == CellType.TUMOROUS){
                count++;
            }
        }
        return count;
    }
}
