import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft = 10;
    private int score = 0;

    private JTextField userInputField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        generateRandomNumber();

        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Create a panel for user input and button
        JPanel inputPanel = new JPanel(new BorderLayout());
        userInputField = new JTextField();
        guessButton = new JButton("Guess");
        inputPanel.add(userInputField, BorderLayout.CENTER);
        inputPanel.add(guessButton, BorderLayout.EAST);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for result and attempts
        JPanel statusPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        resultLabel = new JLabel("", SwingConstants.CENTER);
        attemptsLabel = new JLabel("Attempts left: " + attemptsLeft, SwingConstants.CENTER);
        statusPanel.add(resultLabel);
        statusPanel.add(attemptsLabel);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for the score
        JPanel scorePanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.SOUTH);

        // Add action listener to the button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
    }

    private void checkGuess() {
        int userGuess;
        try {
            userGuess = Integer.parseInt(userInputField.getText());
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid number.");
            userInputField.setText("");
            return;
        }

        attemptsLeft--;
        attemptsLabel.setText("Attempts left: " + attemptsLeft);

        if (userGuess == randomNumber) {
            resultLabel.setText("Congratulations! You've guessed the number.");
            score += attemptsLeft * 10; // Adjust score based on attempts left
            scoreLabel.setText("Score: " + score);
            newRound();
        } else if (userGuess < randomNumber) {
            resultLabel.setText("Higher!");
        } else {
            resultLabel.setText("Lower!");
        }

        if (attemptsLeft == 0) {
            resultLabel.setText("Sorry, you've run out of attempts. The correct number was: " + randomNumber);
            newRound();
        }

        userInputField.setText("");
    }

    private void newRound() {
        attemptsLeft = 10;
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        resultLabel.setText(""); // Clear result label
        generateRandomNumber();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}
