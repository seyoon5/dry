import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable {

    public static ArrayList<ServerThread> threads = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nick;
    private String username;

    public ServerThread(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nick = bufferedReader.readLine();
            this.username = bufferedReader.readLine();
            System.out.println("nick: " + nick);
            System.out.println("username: " + username);
            threads.add(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    // if (!serverThread.nick.equals(serverThread.username)) //
    // !clientHandler.clientUsername.equals(clientUsername)

    public void broadcastMessage(String messageToSend) {
        System.out.println("broadcastMessage() " + messageToSend);
        for (ServerThread serverThread : threads) {
            try {
                if(!serverThread.nick.equals(nick)){
                serverThread.bufferedWriter.write(messageToSend);
                serverThread.bufferedWriter.newLine();
                serverThread.bufferedWriter.flush();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeClientHandler() {
        threads.remove(this);
        broadcastMessage("대화종료");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
