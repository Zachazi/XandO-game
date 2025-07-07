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
