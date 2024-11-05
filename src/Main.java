import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JeuMemoire jeu = new JeuMemoire();
            jeu.setVisible(true);
        });
    }
}
