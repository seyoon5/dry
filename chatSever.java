import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class chatSever {

  ArrayList<user_data> dataList;
  user_data uData;
  String dbReceiver = null;
  String dbSender = null;

  public static ArrayList<DataOutputStream> m_OutputList;

  private ServerSocket severSocket = null;

  public chatSever() {
    System.out.println("생성자 작동");

    dataList = new ArrayList<>();
    // clients 동기화, 멀티쓰레드 환경에서 필요함
    Collections.synchronizedList(dataList);
  }

  public static void main(String[] args) {
    System.out.println("main 작동");
    m_OutputList = new ArrayList<DataOutputStream>();
    new chatSever().start();
  }

  /**
   * 서버 시작부분
   */
  private void start() {
    System.out.println("new chatSever().start(); 작동");
    int port = 8080;
    Socket socket = null;

    try {
      severSocket = new ServerSocket(port);
      System.out.println("서버 소캣 생성 : 접속 대기중");
      while (true) {
        socket = severSocket.accept();
        new MultiThread(socket).start();

        m_OutputList.add(new DataOutputStream(socket.getOutputStream()));
        System.out.println("outputList size : " + m_OutputList.size());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 사용자가 접속할때마다 쓰레드를 생성한다.
  class MultiThread extends Thread {
    Socket socket = null;
    DataInputStream read;
    // DataOutputStream write;

    public MultiThread(Socket socket) {
      System.out.println("MultiThread 생성자 작동");

      this.socket = socket;

      // 데이터 입출력을 위한 스트림 실행
      try {
        read = new DataInputStream(socket.getInputStream());
        // write = new DataOutputStream(socket.getOutputStream());

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
      System.out.println("MultiThread run");
      try {
        while (true) {
          String something = read.readUTF(); //// 0 receiver, 1 sender, 2 receiverProfile 3 senderProfile 4 msg
          if (something == null) {
            return;
          }
          String[] filter;
          filter = something.split("@");

          String receiver = filter[0];
          String sender = filter[1];
          String receiverProfile = null;

          if (filter[2] == null) {
            receiverProfile = "null";
          } else {
            receiverProfile = filter[2];
          }

          String senderProfile = filter[3];
          String msg = filter[4];
          String roomIdx = filter[5];
          String time = filter[6];
          // String readCnt = filter[6];
          // if (filter[6] == null) {
          // readCnt = "null";
          // } else {
          // readCnt = filter[6];
          // }

          // String user = receiver + "#" + sender;

          System.out.println("1. client 에서 받은 메세지 : " + receiver + ", " + sender + ", " + receiverProfile + ", "
              + senderProfile + ", " + msg + ", " + roomIdx + ", " + time);

          // SimpleDateFormat mFormat = new SimpleDateFormat("aa hh:mm");
          // long mNow;
          // Date mDate;
          // mNow = System.currentTimeMillis();
          // mDate = new Date(mNow);
          // String time = mFormat.format(mDate);

          // 방 생성 및 roomNo 받기
          // makeRoom(user, receiverProfile, sender, receiver, senderProfile);
          // saveRoomMsg(roomNo, msg, time);

          // String roomNo = getRoomNo(sender, receiver);
          String user = getUser(roomIdx);
          saveMsg(senderProfile, sender, msg, time, roomIdx);
          // updateCnt1(roomIdx, sender, receiver);
          // updateCnt2(roomIdx, sender, receiver);
          // String cnt = getCnt(roomIdx);
          // System.out.println("서버로 보내줄 cnt :" + cnt);

          // 클라이언트의 정보를 만들어서 리스트에 추가

          // if (dataList.size() < 1) {
          // 여기에 클라이언트 생성자 생성, 리스트에 추가
          // } else {
          // for (int i = 0; i < dataList.size(); i++) {
          // int intRoom = dataList.get(i).roomNo;
          // System.out.println("intRoom :" + intRoom);
          // if (intRoom != Integer.parseInt(roomIdx)) {
          // dataList.add(uData);
          // }
          // }
          // }

          // uData = new user_data(Integer.parseInt(roomNo), receiver);
          // dataList.add(uData);

          // System.out.println("데이타 리스트 사이즈" + dataList.size());

          // 소켓 없이 클라이언트로 메세지 전달
          for (int i = 0; i < chatSever.m_OutputList.size(); i++) {
            chatSever.m_OutputList.get(i).writeUTF(roomIdx + "@" + sender + "@" + msg +
                "@" + time + "@" + user + "@" + receiver + "@" + receiverProfile + "@" + senderProfile);
            chatSever.m_OutputList.get(i).flush();
            System.out.println("서버에서 client 로 보낸 메세지: " + roomIdx + "@" + sender + "@" +
                msg + "@" + time + "@" + user + "@" + receiver);
          }

          // 소켓으로 구분해서 해당 클라이언트로 메세지 전달
          // System.out.println("dataList.size() : " + dataList.size());
          // for (int i = 0; i < dataList.size(); i++) {
          // try {
          // System.out.println("방번호 : " + roomNo);
          // System.out.println("방번호 확인 : " + roomIdx);
          // System.out.println("보낸이 : " + sender);
          // System.out.println("매세지 : " + msg);
          // System.out.println("time : " + time);
          // if (Integer.parseInt(roomIdx) == dataList.get(i).roomNo) { // 방 번호 확인

          // // OutputStream dos = dataList.get(i).write;
          // DataOutputStream out = new DataOutputStream(socket.getOutputStream());

          // // 클라이언트에 메세지를 전송
          // out.writeUTF(roomNo + "@" + sender + "@" + msg + "@" + time);

          // }
          // } catch (Exception e) {
          // e.printStackTrace();
          // }
          // }

          // System.out.println("2 while 전");
          // while (read != null) {
          // System.out.println("3 in while");
          // try {
          // msg = read.readUTF();
          // System.out.println("4 read.UTF : msg");
          // sendMsg(roomNo + "@" + sender + "@" + msg + "@" + time);
          // // roomNo + "@" + sender + "@" + msg + "@" + time
          // System.out.println("3 sendMsg : " + roomNo + "@" + sender + "@" + msg + "@" +
          // time);
          // } catch (EOFException e) {
          // System.out.println("EOF Exception");
          // e.printStackTrace();
          // break;
          // } catch (Exception e) {
          // e.printStackTrace();
          // System.out.println("서버 소켓 연결 끊김");
          // break;
          // }
          // }
          // 방에 접속했다는 메세지 보내고 싶으면 여기서 메소드 만들어서 처리해보자
          // enteredUser()
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 서버가 클라이언트로 메세지 보내기
   */
  // void sendMsg(String msg) {
  // System.out.println("void sendMsg");
  // // msg = 0방번호, 1받는 사람, 2보낸사람, 3메세지
  // String[] filter = msg.split("@");
  // String roomNo = filter[0];
  // String sender = filter[1];
  // String message = filter[2];
  // String time = filter[3];

  // // System.out.println("dataList.size() : " + dataList.size());
  // // for (int i = 0; i < dataList.size(); i++) {
  // // try {
  // // System.out.println("방번호 : " + roomNo);

  // // System.out.println("보낸이 : " + sender);
  // // System.out.println("매세지 : " + message);
  // // System.out.println("time : " + time);
  // // if (Integer.parseInt(roomNo) == dataList.get(i).roomNo) { // 방 번호 확인

  // // OutputStream dos = dataList.get(i).write;
  // // DataOutputStream out = new DataOutputStream(dos);

  // // // 클라이언트에 메세지를 전송
  // // out.writeUTF(roomNo + "@ " + sender + "@" + message + "@" + time);
  // // }
  // // } catch (Exception e) {
  // // e.printStackTrace();
  // // }
  // // }

  // }

  // public void updateCnt2(String roomNo, String c_sender, String c_receiver) {
  // String driver = "com.mysql.cj.jdbc.Driver";
  // String url =
  // "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
  // String id = "root";
  // String pwd = "123qwe123";
  // Connection con = null;
  // PreparedStatement pstmt = null;
  // String read_cnt_sender = null;
  // String read_cnt_receiver = null;

  // try {
  // Class.forName(driver);
  // con = DriverManager.getConnection(url, id, pwd);

  // if (dbSender.equals(c_sender) || dbReceiver.equals(c_receiver)) {
  // String query = "update chat_room set read_cnt_sender =
  // chat_room.read_cnt_sender +1 where idx = '" + roomNo
  // + "'";
  // pstmt = con.prepareStatement(query);
  // pstmt.executeUpdate();
  // } else if (dbSender.equals(c_receiver) || dbReceiver.equals(c_sender)) {

  // }
  // System.out.println("updateCnt 2");
  // } catch (Exception e) {
  // e.printStackTrace();
  // } finally {
  // try {
  // pstmt.close();
  // con.close();
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }

  // }

  // private void updateCnt1(String roomNo, String c_sender, String c_receiver) {
  // String driver = "com.mysql.cj.jdbc.Driver";
  // String url =
  // "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
  // String id = "root";
  // String pwd = "123qwe123";
  // Connection con = null;
  // PreparedStatement pstmt = null;
  // ResultSet rs = null;
  // String read_cnt_sender = null;
  // String read_cnt_receiver = null;

  // try {
  // Class.forName(driver);
  // con = DriverManager.getConnection(url, id, pwd);
  // // String query = "update chat_room set chat_room.read_cnt = (select
  // // count(*)-count(readed) from chat where room_num = '"
  // // + roomNo + "') where chat_room.idx = '" + roomNo + "'";
  // String query = "select * from chat_room where idx ='" + roomNo + "'";
  // pstmt = con.prepareStatement(query);
  // rs = pstmt.executeQuery();
  // while (rs.next()) {
  // dbReceiver = rs.getString("receiver");
  // dbSender = rs.getString("sender");
  // System.out.println("updateCnt, receiver" + dbReceiver);
  // System.out.println("updateCnt, sender" + dbSender);
  // }

  // // if (sender.equals(c_sender) || receiver.equals(c_receiver)) {
  // // query = "update chat_room set read_cnt_sender = chat_room.read_cnt_sender
  // +1
  // // where idx = '" + roomNo + "'";
  // // pstmt = con.prepareStatement(query);
  // // pstmt.executeUpdate();
  // // } else if (sender.equals(c_receiver) || receiver.equals(c_sender)) {

  // // }

  // System.out.println("updateCnt 1");
  // } catch (Exception e) {
  // e.printStackTrace();
  // } finally {
  // try {
  // pstmt.close();
  // con.close();
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
  // }

  // private String getCnt(String roomIdx) {
  // String driver = "com.mysql.cj.jdbc.Driver";
  // String url =
  // "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
  // String id = "root";
  // String pwd = "123qwe123";
  // String read_cnt = "null";
  // Connection con = null;
  // PreparedStatement pstmt = null;
  // ResultSet rs = null;
  // try {
  // Class.forName(driver);
  // con = DriverManager.getConnection(url, id, pwd);
  // String query = "select * from chat_room where idx ='" + roomIdx + "'";
  // pstmt = con.prepareStatement(query);
  // rs = pstmt.executeQuery();
  // while (rs.next()) {
  // read_cnt = rs.getString("read_cnt");
  // System.out.println("read_cnt :" + read_cnt);
  // }
  // } catch (Exception e) {
  // e.printStackTrace();
  // } finally {
  // try {
  // rs.close();
  // pstmt.close();
  // con.close();
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // }
  // return read_cnt;
  // }

  private void saveMsg(String profile, String sender, String contents, String time, String roomNo) {

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
    String id = "root";
    String pwd = "123qwe123";
    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, id, pwd);
      String query = "insert into chat(profile, identity, contents, time, room_num)"
          + "values(?,?,?,?,?)";
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, profile);
      pstmt.setString(2, sender);
      pstmt.setString(3, contents);
      pstmt.setString(4, time);
      pstmt.setString(5, roomNo);
      pstmt.executeUpdate();
      System.out.println("saveMsg succeed");
      saveCount(roomNo, sender);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        pstmt.close();
        con.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  // update chat_room set content = '$content', time = 55 where idx = 21

  public String getUser(String roomIdx) {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
    String id = "root";
    String pwd = "123qwe123";
    String user = "null";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, id, pwd);
      String query = "select * from chat_room where idx =" + roomIdx;
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        user = rs.getString("user");
        System.out.println("rs.getString(user):" + user);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        pstmt.close();
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }

  private void saveCount(String roomNo, String sender) {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
    String id = "root";
    String pwd = "123qwe123";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, id, pwd);
      System.out.println("saveCount index NO:" + roomNo);
      String query = "select * from chat_room where idx ='" + roomNo + "'";
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        dbReceiver = rs.getString("receiver");
        dbSender = rs.getString("sender");
        System.out.println("dbReceiver" + dbReceiver);
        System.out.println("dbSender" + dbSender);
      }
      if (sender.equals(dbSender)) {
        System.out.println("dbSender 1 if" + dbSender);
        Connection con1 = null;
        PreparedStatement pstmt1 = null;
        try {
          Class.forName(driver);
          con1 = DriverManager.getConnection(url, id, pwd);
          String query1 = "update chat_room set read_cnt_receiver = chat_room.read_cnt_receiver +1 where idx = '"
              + roomNo
              + "'";
          pstmt1 = con1.prepareStatement(query1);
          pstmt1.executeUpdate();
          System.out.println("count 증가 성공");
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            pstmt1.close();
            con1.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      } else {
        System.out.println("dbReceiver 1 else if" + dbReceiver);
        System.out.println("dbSender 1 else if" + dbSender);
        Connection con2 = null;
        PreparedStatement pstmt2 = null;
        try {
          Class.forName(driver);
          con2 = DriverManager.getConnection(url, id, pwd);
          String query2 = "update chat_room set read_cnt_sender = chat_room.read_cnt_sender +1 where idx = '"
              + roomNo
              + "'";
          pstmt2 = con2.prepareStatement(query2);
          pstmt2.executeUpdate();
          System.out.println("count 증가 성공2");
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            pstmt2.close();
            con2.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        pstmt.close();
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }

  // private void saveRoomMsg(String roomNo, String message, String time) {
  // String driver = "com.mysql.cj.jdbc.Driver";
  // String url =
  // "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
  // String id = "root";
  // String pwd = "123qwe123";
  // Connection con = null;
  // PreparedStatement pstmt = null;

  // try {
  // Class.forName(driver);
  // con = DriverManager.getConnection(url, id, pwd);
  // String query = "update chat_room set content=?, time=? where idx=?";
  // pstmt = con.prepareStatement(query);
  // pstmt.setString(1, message);
  // pstmt.setString(2, time);
  // pstmt.setString(3, roomNo);
  // pstmt.executeUpdate();
  // System.out.println("update succeed");

  // } catch (Exception e) {
  // e.printStackTrace();
  // } finally {
  // try {
  // pstmt.close();
  // con.close();
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
  // }

  public String getRoomNo(String sender, String receiver) {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
    String id = "root";
    String pwd = "123qwe123";
    String roomNo = "null";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, id, pwd);
      String query = "select * from chat_room where sender ='" + sender
          + "' and receiver ='" + receiver
          + "' or sender ='" + receiver
          + "' and receiver ='" + sender
          + "'";
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        roomNo = rs.getString("idx");
        System.out.println("rs.getString(idx):" + roomNo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        pstmt.close();
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return roomNo;
  }

  public void makeRoom(String user, String receiverProfile, String sender, String receiver, String senderProfile) {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
    String id = "root";
    String pwd = "123qwe123";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String roomNo = null;

    try {
      Class.forName(driver);
      con = DriverManager.getConnection(url, id, pwd);
      String query = "select * from chat_room where sender ='" + sender
          + "' and receiver ='" + receiver
          + "' or sender ='" + receiver
          + "' and receiver ='" + sender
          + "'";
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        roomNo = rs.getString("idx");
        System.out.println("rs.getString(idx):" + roomNo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        pstmt.close();
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (roomNo == null) {
      try {
        Class.forName(driver);
        con = DriverManager.getConnection(url, id, pwd);
        String query = "insert into chat_room(user, receiver_profile, sender, receiver, content, sender_profile)"
            + "values(?,?,?,?,?,?)";
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, user);
        pstmt.setString(2, receiverProfile);
        pstmt.setString(3, sender);
        pstmt.setString(4, receiver);
        pstmt.setString(5, "");
        pstmt.setString(6, senderProfile);
        pstmt.executeUpdate();
        System.out.println("make room succeed");
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          pstmt.close();
          con.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}

// public String checkRoom(String roomNo) {
// String driver = "com.mysql.cj.jdbc.Driver";
// String url =
// "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
// String id = "root";
// String pwd = "123qwe123";
// String idx = null;
// Connection con = null;
// PreparedStatement pstmt = null;
// ResultSet rs = null;
// try {
// Class.forName(driver);
// con = DriverManager.getConnection(url, id, pwd);
// String query = "select*from chat_room where idx=?";
// pstmt = con.prepareStatement(query);
// pstmt.setString(1, roomNo);
// rs = pstmt.executeQuery();
// while (rs.next()) {
// idx = rs.getString("idx");
// System.out.println("idx:" + idx);
// }
// } catch (Exception e) {
// e.printStackTrace();
// } finally {
// try {
// rs.close();
// pstmt.close();
// con.close();
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }
// return idx;
// }