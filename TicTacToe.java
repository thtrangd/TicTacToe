import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 800;
    int boardHeight = 850; 

    JFrame frame = new JFrame("3SIS Tic-Tac-Toe");
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
    int playerXWins = 0;
    int playerOWins = 0;
    JLabel scoreLabel;

    // Constructor to initialize the TicTacToe game
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
    
        textLabel.setFont(new Font("Monaco", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Let's go, " + currentPlayer.getSymbol());
        textLabel.setOpaque(true);
    
        textPanel.setLayout(new BorderLayout());
        scoreLabel = new JLabel("X: " + playerXWins + "  O: " + playerOWins, JLabel.CENTER);
        scoreLabel.setFont(new Font("Monaco", Font.BOLD, 30));
        scoreLabel.setForeground(new Color(78, 78, 223));
        textPanel.add(scoreLabel, BorderLayout.NORTH);
        textPanel.add(textLabel, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.NORTH);
    
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(204, 204, 255));
        frame.add(boardPanel, BorderLayout.CENTER);
    
        bottomPanel.setLayout(new FlowLayout());
    
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Monaco", Font.BOLD, 30));
        playAgainButton.setForeground(new Color(204, 103, 255));
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        bottomPanel.add(playAgainButton);

        JButton endButton = new JButton("End");
        endButton.setFont(new Font("Monaco", Font.BOLD, 30));
        endButton.setForeground(new Color(204, 103, 255));
        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        bottomPanel.add(endButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
    
                textLabel.setBackground(new Color(204, 204, 255)); // Color of textLabel
                textLabel.setForeground(new Color(78, 78, 223));   // Color of "Let's go, X, Y"
                tile.setFont(new Font("Monaco", Font.BOLD, 120));
                tile.setForeground(new Color(204, 103, 255));     // Set color to pink for X and O
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
                board[r][c].setForeground(new Color(204, 103, 255));     // Color of X and O when reset again
            }
        }
    
        bottomPanel.getComponent(0).setVisible(false); // Hide play again button
        scoreLabel.setText("X: " + playerXWins + "  O: " + playerOWins); // Update score on reset
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
                if (board[r][0].getText().equalsIgnoreCase("x")) {
                    playerXWins++;
                } else {
                    playerOWins++;
                }
                scoreLabel.setText("X: " + playerXWins + "  O: " + playerOWins); // Update score on win
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
                if (board[0][c].getText().equalsIgnoreCase("x")) {
                    playerXWins++;
                } else {
                    playerOWins++;
                }
                scoreLabel.setText("X: " + playerXWins + "  O: " + playerOWins); // Update score on win
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
            if (board[0][0].getText().equalsIgnoreCase("x")) {
                playerXWins++;
            } else {
                playerOWins++;
            }
            scoreLabel.setText("X: " + playerXWins + "  O: " + playerOWins); // Update score on win
            return;
        }

        if (board[2][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[0][2].getText()) &&
            !board[2][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[2 - i][i]);
            }
            gameOver = true;
            bottomPanel.getComponent(0).setVisible(true); // Show play again button
            if (board[2][0].getText().equalsIgnoreCase("x")) {
                playerXWins++;
            } else {
                playerOWins++;
            }
            scoreLabel.setText("X: " + playerXWins + "  O: " + playerOWins); // Update score on win
            return;
        }

        if (turns == 9) {
            textLabel.setText("It's a tie!");
            gameOver = true;
            bottomPanel.getComponent(0).setVisible(true); // Show play again button
        }
    }

    void setWinner(JButton button) {
        button.setForeground(new Color(255, 0, 0)); // Winner's color is red
        textLabel.setText(currentPlayer.getSymbol() + " - my champion ٩(♡ε♡ )۶");
    }
}
