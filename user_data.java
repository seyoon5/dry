import java.io.DataOutputStream;
import java.net.Socket;

public class user_data {

    Socket userSocket;
    int roomNo;
    String user;
    DataOutputStream write;

    public user_data(Socket userSocket, int roomNo, String user, DataOutputStream write) {

        this.userSocket = userSocket;
        this.roomNo = roomNo;
        this.user = user;
        this.write = write;

    }

}