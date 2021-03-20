import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String HOST = "127.0.0.1";
        final int PORT = 8888;
        Socket socket = null;

        //
        try {
            socket = new Socket(HOST, PORT);
            //
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String input = consoleReader.readLine();

            // Sent to server
            bw.write(input + "\n");
            bw.flush();

            String response = br.readLine();
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
