import javax.swing.JButton;

public abstract class Player {
    protected String symbol;
    protected JButton[][] board;

    public Player(String symbol, JButton[][] board) {
        this.symbol = symbol;
        this.board = board;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract void makeMove();
}
