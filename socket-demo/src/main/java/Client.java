import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String HOST = "127.0.0.1";
        final int PORT = 8888;
        Socket socket = null;
        BufferedWriter bw = null;
        //
        try {
            socket = new Socket(HOST, PORT);
            //
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String input = consoleReader.readLine();

                // Sent to server
                bw.write(input + "\n");
                bw.flush();

                String response = br.readLine();
                System.out.println(response);

                if (input.equals("quit")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    bw.close();    // it will flush and close socket
                    System.out.println("close socket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
