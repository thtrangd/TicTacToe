import javax.swing.JButton;

public class HumanPlayer extends Player {

    public HumanPlayer(String symbol, JButton[][] board) {
        super(symbol, board);
    }

    public void makeMove(JButton tile) {
        if (tile.getText().equals("")) {
            tile.setText(symbol);
        }
    }

    @Override
    public void makeMove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
