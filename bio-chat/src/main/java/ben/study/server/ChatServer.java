package ben.study.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    //端口常量
    private int PORT = 8888;
    private final String QUIT = "quit";
    private ServerSocket serverSocket;
    private Map<Integer, Writer> clientMap;
    // Thread pool
    private ExecutorService executorService;

    public ChatServer() {
        this.clientMap = new HashMap<Integer, Writer>();
        executorService = Executors.newFixedThreadPool(10);
    }

    //开启服务
    public void star() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("server开启成功,等待连接");
            //轮询一直接受新的连接
            while (true) {
                //等待客户端连接
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort() + "已连接");

                // If use thread
                //new Thread(new ChatHandler(this, socket)).start();

                //将当前任务交给线程池
                executorService.execute(new ChatHandler(this, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //向所有客戶端转发消息(除发送者所在客户端)
    public synchronized void forWard(Socket socket, String msg) throws IOException {
        for (int port : clientMap.keySet()) {
            if (port == socket.getPort()) continue;
            Writer writer = clientMap.get(port);
            writer.write(msg);
            writer.flush();
        }
    }

    //检查用户是否准备退出
    public boolean readToQuit(String msg) {
        return QUIT.equals(msg);
    }

    //记录client
    public synchronized void addClient(Socket socket) throws IOException {
        if (socket != null) {
            //获取client的write
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            //将client添加到map
            clientMap.put(socket.getPort(), writer);

            System.out.println(socket.getPort() + "已上线");
        }
    }

    //移除client
    public synchronized void removeClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            if (clientMap.containsKey(socket.getPort())) {
                clientMap.get(socket.getPort()).close();
            }
            clientMap.remove(socket.getPort());

            System.out.println(port + "已下线");
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.star();
    }
}