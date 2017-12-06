import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Nano Virus App");
        frame.setContentPane(new App().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 900);
        frame.setVisible(true);
    }
}

