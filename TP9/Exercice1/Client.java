
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 7585;

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server!");

            boolean gameRunning = true;

            while (gameRunning) {
                System.out.print("Guess the number between 0 and 100: ");
                int guess = scanner.nextInt();

                outputStream.write(guess); // Send guess to the server
                int response = inputStream.read(); // Read server's response

                switch (response) {
                    case 0:
                        System.out.println("Congratulations, you guessed the number!");
                        gameRunning = false; // Exit the game
                        break;
                    case 1:
                        System.out.println("Go Higher!");
                        break;
                    case -1:
                        System.out.println("Go Lower!");
                        break;
                    default:
                        System.out.println("Unexpected response from server. Exiting.");
                        gameRunning = false;
                        break;
                }
            }

            System.out.println("Exiting the game. Goodbye!");

        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    }
}
