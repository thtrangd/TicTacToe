import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HumanPlayer extends Player {

    public HumanPlayer(String symbol, JButton[][] board) {
        super(symbol, board);
    }

    public void makeMove(JButton tile) {
        if (tile.getText().equals("")) {
            tile.setText(symbol);
        }
    }
}
