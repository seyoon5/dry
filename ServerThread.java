// import java.io.*;
// import java.net.Socket;
// import java.util.ArrayList;
// import java.util.Vector;

// public class ServerThread extends Thread {

//     // public static Vector<Room> threads = new ArrayList<>();
//     private Socket socket;
//     private BufferedReader in;
//     private BufferedWriter out;

//     Room myRoom;
//     Vector<Room> roomV;

//     public ServerThread(Socket socket, Server server) {
//         this.roomV = server.roomV;
//         this.socket = socket;
//         try {
//             this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//             this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//             start();

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     @Override
//     public void run() {

//         String msg;

//         while (socket.isConnected()) {
//             myRoom = new Room();
//             roomV.add(myRoom);
//             myRoom.userV.add(this);
//             try {
//                 msg = in.readLine();
//                 if (msg == null) {
//                     return;
//                 }
//                 System.out.println("들어온 메세지 :" + msg);
//                 messageRoom(msg);
//             } catch (IOException e) {
//                 e.printStackTrace();
//                 System.out.println("서버 소켓 연결 끊김");
//                 break;
//             }
//         }
//     }

//     // if (!serverThread.nick.equals(serverThread.username)) //
//     // !clientHandler.clientUsername.equals(clientUsername)
//     public void messageRoom(String msg) {

//         for (int i = 0; i < myRoom.userV.size(); i++) {
//             ServerThread serverThread = myRoom.userV.get(i);
//             try {
//                 serverThread.out.write(msg);
//                 serverThread.out.newLine();
//                 serverThread.out.flush();
//             } catch (IOException e) {
//                 e.printStackTrace();
//                 myRoom.userV.remove(i--);
//                 System.out.println("클라이언트 접속 끊김");
//             }
//         }

//     }

//     // public void closeEverything(Socket socket, BufferedReader bufferedReader,
//     // BufferedWriter bufferedWriter) {
//     // try {
//     // if (bufferedReader != null) {
//     // bufferedReader.close();
//     // }
//     // if (bufferedWriter != null) {
//     // bufferedWriter.close();
//     // }
//     // if (socket != null) {
//     // socket.close();
//     // }
//     // } catch (IOException e) {
//     // e.printStackTrace();
//     // }
//     // }
// }
