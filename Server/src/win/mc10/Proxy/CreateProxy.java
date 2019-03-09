package win.mc10.Proxy;

import win.mc10.Log;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class CreateProxy extends Thread{

    private ServerSocket ProxyDataTunnelServerSocket = null;
    private ServerSocket ProjectClientServerSocket = null;

    public CreateProxy(int transmissionPort, int tunnlePort) {
        try {
            this.ProxyDataTunnelServerSocket = new ServerSocket(transmissionPort);
            this.ProjectClientServerSocket = new ServerSocket(tunnlePort);
            start();
        } catch (BindException e) {
            Log.ERROR("端口 " + transmissionPort + ":" + tunnlePort + " 绑定失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ServerSocket getProjectClientServerSocket() {
        return ProjectClientServerSocket;
    }

    public ServerSocket getProxyDataTunnelServerSocket() {
        return ProxyDataTunnelServerSocket;
    }

    @Override
    public void run() {
        Socket ProjectClientSocket = null;
        Socket ProxyDataTunnelSocket = null;

        while(true) {
            try {
                ProxyDataTunnelSocket = this.ProxyDataTunnelServerSocket.accept();
                ProjectClientSocket = this.ProjectClientServerSocket.accept();

                new DataExchangeThread(ProxyDataTunnelSocket, ProjectClientSocket).start();
                new DataExchangeThread(ProjectClientSocket, ProxyDataTunnelSocket).start();
            } catch (SocketException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if(ProjectClientSocket != null) {
                ProjectClientSocket.close();
            }
            if(ProxyDataTunnelSocket != null) {
                ProxyDataTunnelSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
