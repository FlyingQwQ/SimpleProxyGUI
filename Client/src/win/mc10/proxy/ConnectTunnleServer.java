package win.mc10.proxy;

import win.mc10.Controls.Controls.ListControl;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Date;

public class ConnectTunnleServer extends Thread {

    private HeartbeatThread TunnleServerSocketHeartbeat = null;
    private HeartbeatThread ProjectServerSocketHeartbeat = null;
    public PortTesting portTesting = null;

    private boolean isClose = false;

    private Socket TunnleServerSocket = null;       //连接到隧道服务器
    private Socket ProjectServerSocket = null; //连接到项目服务器

    private ListControl listControl = null;

    private String DomainName = "";
    private int tunnleport = 0;
    public int transmissionPort = 0;
    private String localhost = "";
    private int localhostport = 0;

    public ConnectTunnleServer(String DomainName, int tunnleport, int transmissionPort, String localhost, int localhostport, ListControl listControl) {
        this.DomainName = DomainName;
        this.tunnleport = tunnleport;
        this.transmissionPort = transmissionPort;
        this.localhost = localhost;
        this.localhostport = localhostport;
        this.listControl = listControl;

        this.portTesting = new PortTesting();
        this.portTesting.start();
    }

    public void setListControl(ListControl listControl) {
        this.listControl = listControl;
    }

    public void close() {
        this.isClose = true;
    }

    @Override
    public void run() {
        try {
            while(true) {
                this.TunnleServerSocket = new Socket(this.DomainName, this.transmissionPort);
                if(this.TunnleServerSocketHeartbeat != null) {
                    this.TunnleServerSocketHeartbeat.interrupt();
                    this.TunnleServerSocketHeartbeat = new HeartbeatThread(this.TunnleServerSocket);
                    this.TunnleServerSocketHeartbeat.start();
                }else {
                    this.TunnleServerSocketHeartbeat = new HeartbeatThread(this.TunnleServerSocket);
                    this.TunnleServerSocketHeartbeat.start();
                }
                while(this.TunnleServerSocket.getInputStream().available() < 1) {
                    Thread.sleep(100);
                }
                this.ProjectServerSocket = new Socket(this.localhost, this.localhostport);
                if(this.ProjectServerSocketHeartbeat != null) {
                    this.ProjectServerSocketHeartbeat.interrupt();
                    this.ProjectServerSocketHeartbeat = new HeartbeatThread(this.ProjectServerSocket);
                    this.ProjectServerSocketHeartbeat.start();
                }else {
                    this.ProjectServerSocketHeartbeat = new HeartbeatThread(this.ProjectServerSocket);
                    this.ProjectServerSocketHeartbeat.start();
                }

                new DataExchangeThread(this.ProjectServerSocket, this.TunnleServerSocket).start();
                new DataExchangeThread(this.TunnleServerSocket, this.ProjectServerSocket).start();
            }
        } catch (ConnectException e) {
            System.out.println("[" + new Date() + "][*] 无法连接到隧道通讯服务器，隧道连接端口" + this.tunnleport + "有误！");
        } catch (IllegalArgumentException e) {
            System.out.println("[" + new Date() + "][*] 无法连接到隧道通讯服务器，隧道连接端口" + this.tunnleport + "有误，端口格式有误");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.portTesting.interrupt();

        try {
            if(this.TunnleServerSocket != null) {
                this.TunnleServerSocket.close();
            }
            if(this.ProjectServerSocket != null) {
                this.ProjectServerSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class HeartbeatThread extends Thread {

        private Socket socket = null;

        public HeartbeatThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    this.socket.getOutputStream().write(0);
                    Thread.sleep(1000 * 5);
                } catch (IOException e) {
                    break;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public class PortTesting extends Thread {

        public void run() {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {

            }

            while(true) {
                if(ConnectTunnleServer.this.isClose) {
                    ConnectTunnleServer.this.listControl.setState(false);
                    break;
                }

                try {
                    Socket ProjectServerSocketc = new Socket(ConnectTunnleServer.this.localhost, ConnectTunnleServer.this.localhostport);

                    ProjectServerSocketc.close();

                    if(ConnectTunnleServer.this.listControl != null) {
                        ConnectTunnleServer.this.listControl.setState(true);
                    }
                } catch (IOException e) {
                    if(ConnectTunnleServer.this.listControl != null) {
                        ConnectTunnleServer.this.listControl.setState(false);
                    }
                }

                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
