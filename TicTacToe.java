import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 700;
    int boardHeight = 750; 
    

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel bottomPanel = new JPanel();

    
    JButton[][] board = new JButton[3][3];
    Player playerX;
    Player playerO;
    Player currentPlayer;

    boolean gameOver = false;
    int turns = 0;

    public static void main(String[] args) {
        new TicTacToe();
    }

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
    
    
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(3, 1));
        welcomePanel.setBackground(new Color(204, 204, 255));

        JLabel welcomeLabel = new JLabel("Ready to rumble?", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Monaco", Font.BOLD, 50));
        welcomeLabel.setForeground(new Color(78, 78, 223));
        welcomePanel.add(welcomeLabel);

        JButton playWithHumanButton = new JButton("Human (๑•̀ㅂ•́)ﻭ✧");
        playWithHumanButton.setFont(new Font("Monaco", Font.BOLD, 30));
        playWithHumanButton.setForeground(new Color(78, 78, 223));
        playWithHumanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initializeGame(new HumanPlayer("x", board), new HumanPlayer("o", board));
            }
        });
        welcomePanel.add(playWithHumanButton);
    


        JButton playWithAIButton = new JButton("Robot (๑˃̵ᴗ˂̵)ﻭ");
        playWithAIButton.setFont(new Font("Monaco", Font.BOLD, 30));
        playWithAIButton.setForeground(new Color(78, 78, 223));
        playWithAIButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initializeGame(new HumanPlayer("x", board), new AIPlayer("o", board));
            }
        });
        welcomePanel.add(playWithAIButton);

        frame.add(welcomePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void initializeGame(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;

        frame.getContentPane().removeAll();
        frame.repaint();
    

        //textLabel.setBackground(new Color(255, 192, 203)); // Pink
        //textLabel.setForeground(new Color(128, 0, 128));   // Purple
        textLabel.setFont(new Font("Monaco", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Let's go, " + currentPlayer.getSymbol());
        //textLabel.setForeground(new Color(204, 153, 255));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(253, 195, 244));
        frame.add(boardPanel, BorderLayout.CENTER);

        bottomPanel.setLayout(new FlowLayout());
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Monaco", Font.BOLD, 30));
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        bottomPanel.add(playAgainButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                textLabel.setBackground(new Color(253, 195, 244)); // Pink
                textLabel.setForeground(new Color(78, 78, 223));
                tile.setFont(new Font("Monaco", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer.getSymbol());
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                switchPlayer();
                                if (currentPlayer instanceof AIPlayer) {
                                    currentPlayer.makeMove();
                                    turns++;
                                    checkWinner();
                                    if (!gameOver) {
                                        switchPlayer();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }

        frame.revalidate();
        frame.repaint();
    }

    private void resetGame() {
        turns = 0;
        gameOver = false;
        currentPlayer = playerX;
        textLabel.setText("Let's go, " + currentPlayer.getSymbol());
    
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.pink);
                board[r][c].setForeground(new Color(204, 103, 255));    
                 }
        }
    
        bottomPanel.getComponent(0).setVisible(false); // Hide play again button
        frame.revalidate();
        frame.repaint();
    }
    


    void switchPlayer() {
        currentPlayer = currentPlayer == playerX ? playerO : playerX;
        textLabel.setText("Let's go, " + currentPlayer.getSymbol());
    }

    void checkWinner() {
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;

            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                bottomPanel.getComponent(0).setVisible(true); // Show play again button
                return;
            }
        }

        // vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;

            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                bottomPanel.getComponent(0).setVisible(true); // Show play again button
                return;
            }
        }

        // diagonally
        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            bottomPanel.getComponent(0).setVisible(true); // Show play again button
            return;
        }

        // anti-diagonally
        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            bottomPanel.getComponent(0).setVisible(true); // Show play again button
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
            bottomPanel.getComponent(0).setVisible(true); // Show play again button
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(new Color(255, 0, 127));
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer.getSymbol() + "-my champion ٩(♡ε♡ )۶");
    }

    void setTie(JButton tile) {
        tile.setForeground(new Color(255, 102, 255));
        tile.setBackground(Color.gray);
        textLabel.setText("It's a draw (~_~メ)");
    }
}
