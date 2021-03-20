
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;
        // bind to port
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("Server is listening on port " + DEFAULT_PORT);

            while (true) {

                Socket socket = serverSocket.accept();   // Blocking...
                System.out.println("Client " + socket.getPort() + " connected");

                //
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                //
                String msg = null;
                while ((msg = br.readLine()) != null) {
                    System.out.println("Client " + socket.getPort() + " : " + msg);
                    bw.write("Serer:" + msg + "\n");
                    bw.flush();

                    if(msg.equals("quit")){
                        System.out.println("Client " + socket.getPort() + " is disconnected" );
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("close server socket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
