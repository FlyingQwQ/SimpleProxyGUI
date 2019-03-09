package win.mc10;

import com.sun.istack.internal.logging.Logger;
import win.mc10.DataCommunication.DataCommunicationServerAcceptSocketThread;
import win.mc10.MySQL.MySql;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    public static MySql mySql = null;

    private int DataCommunicationServerSocketPort = 2019;

    private ServerSocket DataCommunicationServerSocket = null;
    public static List<Socket> DataCommunicationSocketLists = new ArrayList<>();
    public static Map<Socket, String> LoginSocket = new HashMap<>();

    public Server() {
        try {
            this.DataCommunicationServerSocket = new ServerSocket(this.DataCommunicationServerSocketPort);
            new DataCommunicationServerAcceptSocketThread(this.DataCommunicationServerSocket, this.DataCommunicationSocketLists).start();
            Log.INFO("已创建客户端连接端口" + this.DataCommunicationServerSocketPort);
        } catch (IOException e) {
            Log.WARNING("通讯服务创建失败，请检查端口" + this.DataCommunicationServerSocketPort + "是否占用！");
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        mySql = new MySql();
    }

}
