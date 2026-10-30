import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int PORT = 6379;
    private static final CommandHandler handler = new CommandHandler();

    private static void handleClient(Socket socket) {
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             OutputStream out = socket.getOutputStream()) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("*")) {
                    int count = Integer.parseInt(line.substring(1));
                    List<String> args = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        reader.readLine(); // Skip the length descriptor line (e.g., "$4")
                        args.add(reader.readLine()); // Read the actual payload line
                    }

                    String response = handler.handle(args.toArray(new String[0]));
                    out.write(response.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Redis server on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setReuseAddress(true);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
