package xo;

import javax.swing.*;

public class GameButton extends JButton {
    private MainPanel game;

    public GameButton(MainPanel game) {
        this.game = game;


        //lambda
        addActionListener((e -> {
            if (this.getText().equals("") && !game.isFinished()) {
                this.setText(game.getAndNextTurn());
                if (game.isFinished()) {
                    JOptionPane.showMessageDialog(game, game.getWinner() + " wins!");
                }
            }
        }));
    }

}
