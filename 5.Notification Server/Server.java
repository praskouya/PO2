import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) {
        final int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serwer nasłuchuje na porcie " + port );

            ExecutorService executor = Executors.newCachedThreadPool(); // Utworzenie puli wątków

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie przyjęte.");

                // Obsługa połączenia klienta w osobnym wątku
                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Cos nie powiodlo sie :( " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {


            String notification = in.readLine();
            int time = Integer.parseInt(in.readLine());
           
            System.out.println("Notyfikacja o danej tresci: " + notification + " czeka w kolejce przez " + time + " sekund");

         
            Thread.sleep(1000 * time);
            out.println("Udalo sie yaaay!");

        } catch (IOException | InterruptedException e) {
            System.err.println("Cos nie powiodlo sie :(  " + e.getMessage());
        }
    }
}
