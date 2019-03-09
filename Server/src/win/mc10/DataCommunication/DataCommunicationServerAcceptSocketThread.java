package win.mc10.DataCommunication;

import win.mc10.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class DataCommunicationServerAcceptSocketThread extends Thread {

    private ServerSocket serverSocket = null;
    private List<Socket> lists = null;

    public DataCommunicationServerAcceptSocketThread(ServerSocket serverSocket, List<Socket> lists) {
        this.serverSocket = serverSocket;
        this.lists = lists;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket AcceptSocket = this.serverSocket.accept();
                new AcceptSocketDataHandle(AcceptSocket).start();
                this.lists.add(AcceptSocket);
                Log.INFO("用户地址：" + AcceptSocket.getInetAddress().getHostAddress() + "连接至代理！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
