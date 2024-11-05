import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class JeuMemoire  extends JFrame {

    private final int ROWS = 4;
    private final int COLS = 5;
    private final int PAIRS = 10;

    private JButton[] buttons = new JButton[ROWS * COLS];
    private String[] values = new String[ROWS * COLS];
    private JButton firstSelected = null;
    private JButton secondSelected = null;
    private int pairsFound = 0;

    public JeuMemoire() {
        setTitle("Jeu de mémoire - Trouvez les paires !");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(ROWS, COLS));

        initializeGame();
    }

    private void initializeGame() {
        pairsFound = 0;
        ArrayList<String> cardValues = new ArrayList<>();
        for (int i = 1; i <= PAIRS; i++) {
            cardValues.add(String.valueOf(i));
            cardValues.add(String.valueOf(i));
        }

        Collections.shuffle(cardValues);
        values = cardValues.toArray(new String[0]);

        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(new CardListener(i));
            buttons[i] = button;
            add(button);
        }
    }

    private class CardListener implements ActionListener {
        private int index;
        public CardListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = buttons[index];

            if (firstSelected != null && secondSelected != null) {
                return;
            }


            clickedButton.setText(values[index]);
            clickedButton.setEnabled(false);

            if (firstSelected == null) {
                firstSelected = clickedButton;
            } else {
                secondSelected = clickedButton;

                if (firstSelected.getText().equals(secondSelected.getText())) {
                    pairsFound++;
                    firstSelected = null;
                    secondSelected = null;

                    if (pairsFound == PAIRS) {
                        showEndGameDialog();
                    }
                } else {
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            firstSelected.setText("");
                            firstSelected.setEnabled(true);
                            secondSelected.setText("");
                            secondSelected.setEnabled(true);
                            firstSelected = null;
                            secondSelected = null;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
    }

    private void showEndGameDialog() {
        int option = JOptionPane.showOptionDialog(
                this,
                "Bien joué ! Vous avez trouvé toutes les paires.",
                "Partie terminée",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[] {"Rejouer", "Quitter"},
                "Rejouer"
        );

        if (option == JOptionPane.YES_OPTION) {
            getContentPane().removeAll();
            initializeGame();
            revalidate();
            repaint();
        } else {
            System.exit(0);
        }
    }
}