import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;

public class Main {

  private static void handleClient(Socket clientSocket) {
    try{
      String resp = "+PONG\r\n";
      InputStream inputStream = clientSocket.getInputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;

        while((bytesRead = inputStream.read(buffer))!=-1) {
          clientSocket.getOutputStream().write(resp.getBytes());
        }
    }
    catch(IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
    finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }

  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment the code below to pass the first stage
    ServerSocket serverSocket = null;
    int port = 6379;
    try {
      serverSocket = new ServerSocket(port);

      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      while(true) {          
        // Wait for connection from client.
        Socket clientSocket = serverSocket.accept();
        new Thread(()->handleClient(clientSocket)).start();           
      }
    }
    catch(IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
