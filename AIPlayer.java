import javax.swing.JButton;
import java.util.Random;

public class AIPlayer extends Player {

    public AIPlayer(String symbol, JButton[][] board) {
        super(symbol, board);
    }

    @Override
    public void makeMove() {
        Random rand = new Random();
        int r, c;
        do {
            r = rand.nextInt(3);
            c = rand.nextInt(3);
        } while (!board[r][c].getText().equals(""));

        board[r][c].setText(symbol);
    }
}
