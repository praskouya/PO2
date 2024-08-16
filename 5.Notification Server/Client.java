import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String serverAddress = "localhost";
        final int port = 12345;

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {


            System.out.println("Połączono z serwerem.");

            String notification;
	    do {
    		System.out.print("Co chcesz wyslac: ");
    		notification = userInput.readLine();
    		if (notification.trim().isEmpty()) {
        System.out.println("Notyfikacja nie moze byc pusta !");
    		}
		} while (notification.trim().isEmpty());

        
            int time = 0;
            boolean validTime = false;
            do {
                try {
                    System.out.print("Podaj czas odesłania notyfikacji (w sekundach): ");
                    time = Integer.parseInt(userInput.readLine());
                    if (time < 1) {
                        System.out.println("Niepoprawny czas. Podaj ponownie (czas musi być większy lub równy 1):");
                    } else {
                        validTime = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Niepoprawny czas. Podaj ponownie (musi być liczbą całkowitą):");
                }
            } while (!validTime);


            out.println(notification);
            out.println(time);

    
            String response = in.readLine();
            System.out.println("Serwer: " + response);

        } catch (UnknownHostException e) {
            System.err.println("Nieznany host: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Nie powiodlo sie :( " + e.getMessage());
        }
    }
}
