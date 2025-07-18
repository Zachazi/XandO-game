import java.awt.*;
import javax.swing.*;

public class XandO {
    JFrame welcomeFrame, gameFrame;
    JTextField player1Field, player2Field;
    String player1Name = "Player 1";
    String player2Name = "Player 2";
    String currentPlayer;
    JButton[][] board = new JButton[3][3];
    JLabel statusLabel, scoreLabel;
    boolean gameOver = false;
    int turns = 0;
    int player1Score = 0;
    int player2Score = 0;

    public static void main(String[] args) {
        new XandO();
    }

    XandO() {
        showWelcomeScreen();
    }

    void showWelcomeScreen() {
        welcomeFrame = new JFrame("Welcome to XandO game");
        welcomeFrame.setSize(400, 300);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLayout(new GridLayout(4, 1));
        welcomeFrame.setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to XandO game", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeFrame.add(welcomeLabel);

        player1Field = new JTextField();
        player1Field.setToolTipText("Enter Player 1 Name");
        player2Field = new JTextField();
        player2Field.setToolTipText("Enter Player 2 Name");
        welcomeFrame.add(player1Field);
        welcomeFrame.add(player2Field);

        JButton continueButton = new JButton("Continue");
    continueButton.addActionListener(e -> {
        player1Name = player1Field.getText().trim().isEmpty() ? "Player 1" : player1Field.getText().trim();
        player2Name = player2Field.getText().trim().isEmpty() ? "Player 2" : player2Field.getText().trim();
        currentPlayer = player1Name;
        welcomeFrame.dispose();
        initGame();
    });
    welcomeFrame.add(continueButton);

    welcomeFrame.setVisible(true);
}

void initGame() {
    gameFrame = new JFrame("XandO game");
    gameFrame.setSize(600, 700);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setLayout(new BorderLayout());
    gameFrame.setLocationRelativeTo(null);
    gameFrame.setResizable(true);

    JPanel topPanel = new JPanel(new GridLayout(3, 1));

    statusLabel = new JLabel(currentPlayer + "'s Turn", SwingConstants.CENTER);
    statusLabel.setFont(new Font("Arial", Font.BOLD, 32));
    statusLabel.setOpaque(true);
    statusLabel.setBackground(Color.darkGray);
    statusLabel.setForeground(Color.white);
    topPanel.add(statusLabel);

    scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
    scoreLabel.setOpaque(true);
    scoreLabel.setBackground(Color.lightGray);
    scoreLabel.setForeground(Color.black);
    topPanel.add(scoreLabel);

    JButton resetScoreButton = new JButton("Reset Scores");
    resetScoreButton.setFocusable(false);
    resetScoreButton.addActionListener(e -> {
        player1Score = 0;
        player2Score = 0;
        scoreLabel.setText(getScoreText());
    });
    topPanel.add(resetScoreButton);

    gameFrame.add(topPanel, BorderLayout.NORTH);

    JPanel boardPanel = new JPanel(new GridLayout(3, 3));
    boardPanel.setBackground(Color.darkGray);
    gameFrame.add(boardPanel, BorderLayout.CENTER);

    for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
            JButton button = new JButton();
            board[r][c] = button;
            button.setFont(new Font("Arial", Font.BOLD, 120));
            button.setFocusable(false);
            button.setBackground(Color.darkGray);
            button.setForeground(Color.white);

            final int row = r, col = c;
            button.addActionListener(e -> {
                if (gameOver || !button.getText().equals("")) return;

                button.setText(currentPlayer.equals(player1Name) ? "X" : "O");
                turns++;
                checkWinner();

                if (!gameOver) {
                    currentPlayer = currentPlayer.equals(player1Name) ? player2Name : player1Name;
                    statusLabel.setText(currentPlayer + "'s Turn");
                }
            });

            boardPanel.add(button);
        }
    }

    gameFrame.setVisible(true);
}

void checkWinner() {
    for (int i = 0; i < 3; i++) {
        // Check rows
        if (!board[i][0].getText().equals("") &&
                board[i][0].getText().equals(board[i][1].getText()) &&
                board[i][1].getText().equals(board[i][2].getText())) {
                highlightWinningLine(new JButton[]{board[i][0], board[i][1], board[i][2]});
                showWinner(currentPlayer);
                return;
            }
            // Check columns
            if (!board[0][i].getText().equals("") &&
                    board[0][i].getText().equals(board[1][i].getText()) &&
                    board[1][i].getText().equals(board[2][i].getText())) {
                highlightWinningLine(new JButton[]{board[0][i], board[1][i], board[2][i]});
                showWinner(currentPlayer);
                return;
            }
        }

        // Diagonals
        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            highlightWinningLine(new JButton[]{board[0][0], board[1][1], board[2][2]});
            showWinner(currentPlayer);
            return;
        }
        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            highlightWinningLine(new JButton[]{board[0][2], board[1][1], board[2][0]});
            showWinner(currentPlayer);
            return;
        }

        if (turns == 9) {
            JOptionPane.showMessageDialog(gameFrame, "It's a Tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            restartGame();
        }
    }

    void highlightWinningLine(JButton[] buttons) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.green);
        }
    }

   void showWinner(String winner) {
        gameOver = true;
        if (winner.equals(player1Name)) player1Score++;
        else if (winner.equals(player2Name)) player2Score++;

        disableBoard();
        scoreLabel.setText(getScoreText());

        JOptionPane.showMessageDialog(gameFrame, winner + " Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        restartGame();
    }

    void disableBoard() {
        for (JButton[] row : board)
            for (JButton btn : row)
                btn.setEnabled(false);
    }

    void restartGame() {
        int response = JOptionPane.showConfirmDialog(gameFrame, "Do you want to play again?", "Restart", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            turns = 0;
            gameOver = false;
            currentPlayer = player1Name;
            statusLabel.setText(currentPlayer + "'s Turn");
            scoreLabel.setText(getScoreText());

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setText("");
                    board[r][c].setEnabled(true);
                    board[r][c].setBackground(Color.darkGray);
                }
            }
        } else {
            System.exit(0);
        }
    }

    String getScoreText() {
        return player1Name + " (X): " + player1Score + "  |  " + player2Name + " (O): " + player2Score;
    }
}
