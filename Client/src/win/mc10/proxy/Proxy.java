package win.mc10.proxy;

import org.json.JSONArray;
import org.json.JSONObject;
import win.mc10.Controls.Controls.ListControl;
import win.mc10.Controls.DrawControl;
import win.mc10.Controls.DrawLoginInterface;
import win.mc10.Controls.DrawRegisterInterface;
import win.mc10.Controls.DrawRemind;
import win.mc10.Controls.Enum.Remind;
import win.mc10.GlobalVariable;
import win.mc10.Main;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Proxy {

    private Socket DataCommunicationSocket = null;
    private BufferedWriter bufferedWriter = null;

    public List<ConnectTunnleServer> connectTunnleServers = new ArrayList<>();

    public void ConnectProxtCommunicationServer() {
        try {
            this.DataCommunicationSocket = new Socket(GlobalVariable.ProxyServerAddress, GlobalVariable.ProxyServerPort);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.DataCommunicationSocket.getOutputStream()));
            new AcceptDataCommunication().start();
            new HeartbeatThread().start();

            System.out.println("Proxy Connect to " + GlobalVariable.ProxyServerAddress);
        } catch(ConnectException e) {
            DrawRemind.addRemind("错误", "无法连接至代理服务器！", Remind.ERROR);

            System.out.println("Proxy connection failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String str) {
        try {
            if(this.bufferedWriter != null) {
                this.bufferedWriter.write(str + "\r\n");
                this.bufferedWriter.flush();
            }else {
                DrawRemind.addRemind("错误", "无法连接至代理服务器！", Remind.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DataHeadle(String str) {
        JSONObject jsonObject = new JSONObject(str);
        switch(jsonObject.getString("type")) {
            case "remind":
                this.remind(jsonObject);
                break;
            case "loginverification":
                this.loginverification(jsonObject);
                break;
            case "tunnlelists":
                this.tunnlelists(jsonObject);
                break;
            case "regresult":
                this.regresult(jsonObject);
                break;
            case "starttunnle":
                starttunnle(jsonObject);
                break;
            case "regtunnleresult":
                regtunnleresult(jsonObject);
                break;
        }
    }

    public void regtunnleresult(JSONObject jsonObject) {
        if(jsonObject.getBoolean("result")) {
            Main.drawTunnleRegister.ApplictionNametextBoxControl.setText("");
            Main.drawTunnleRegister.DomainNametextBoxControl.setText("");
            Main.drawTunnleRegister.TelePorttextBoxControl.setText("");
            Main.drawTunnleRegister.LocalAddresstextBoxControl.setText("");
            Main.drawTunnleRegister.LocalPorttextBoxControl.setText("");

            Main.DisPlayInterface = Main.drawTunnleList;

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("type",  "gettunnlelists");
            Main.proxy.sendMessage(jsonObject1.toString());
        }
    }

    public void starttunnle(JSONObject jsonObject) {
        for(ConnectTunnleServer connectTunnleServer : this.connectTunnleServers) {
            connectTunnleServer.close();;
        }
        this.connectTunnleServers.removeAll(this.connectTunnleServers);

        JSONArray jsonArray = jsonObject.getJSONArray("lists");
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            String[] localhost = jsonObject1.getString("Localaddress").split(":");
            ListControl StatelistControl = null;
            for(ListControl listControl : Main.drawTunnleList.getListControlList()) {
                if(listControl.getTransmissionPort() == jsonObject1.getInt("transmissionPort")) {
                    StatelistControl = listControl;
                }
            }
            ConnectTunnleServer connectTunnleServer = new ConnectTunnleServer(jsonObject1.getString("DomainName"), jsonObject1.getInt("tunnleport"), jsonObject1.getInt("transmissionPort"), localhost[0], Integer.parseInt(localhost[1]), StatelistControl);
            connectTunnleServer.start();
            this.connectTunnleServers.add(connectTunnleServer);
        }
    }

    public void remind(JSONObject jsonObject) {
        Remind remind = Remind.INFO;
        switch(jsonObject.getString("remindtype")) {
            case "info":
                remind = Remind.INFO;
                break;
            case "warning":
                remind = Remind.WARNING;
                break;
            case "error":
                remind = Remind.ERROR;
                break;
        }
        DrawRemind.addRemind(jsonObject.getString("title"), jsonObject.getString("content"), remind);
    }

    public void loginverification(JSONObject jsonObject) {
        boolean result = jsonObject.getBoolean("result");
        if(result) {
            DrawControl.funtionControl2.setProhibit(false);
            DrawControl.funtionControl4.setProhibit(false);
            GlobalVariable.isLogin = true;
            Main.DisPlayInterface = Main.drawHome;

            jsonObject = new JSONObject();
            jsonObject.put("type", "gettunnlelists");
            this.sendMessage(jsonObject.toString());

            DrawLoginInterface.UsertextBoxControl.setText("");
            DrawLoginInterface.PasstextBoxControl.setText("");
        }
    }

    public void tunnlelists(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("lists");
        Main.drawTunnleList.emptyListControl();
        for(int i = 0; i < jsonArray.length(); i++) {
            ListControl listControl = new ListControl();
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            listControl.setApplicationname(jsonObject1.getString("applicationname"));
            listControl.setDomainName(jsonObject1.getString("DomainName"));
            listControl.setLocaladdress(jsonObject1.getString("Localaddress"));
            listControl.setTransmissionPort(jsonObject1.getInt("transmissionPort"));
            listControl.setTunnleport(jsonObject1.getInt("tunnleport"));

            ConnectTunnleServer StateconnectTunnleServer = null;
            if(this.connectTunnleServers.size() > 0) {
                for(ConnectTunnleServer connectTunnleServer : this.connectTunnleServers) {
                    if(connectTunnleServer.transmissionPort == jsonObject1.getInt("transmissionPort")) {
                        StateconnectTunnleServer = connectTunnleServer;
                        break;
                    }
                }
                StateconnectTunnleServer.setListControl(listControl);
            }
            Main.drawTunnleList.addListControl(listControl);
        }
    }

    public void regresult(JSONObject jsonObject) {
        if(jsonObject.getBoolean("result")) {
            DrawRemind.addRemind("注册", "注册成功！", Remind.INFO);
            Main.DisPlayInterface = Main.drawLoginInterface;
            DrawRegisterInterface.UsertextBoxControl.setText("");
            DrawRegisterInterface.EmailtextBoxControl.setText("");
            DrawRegisterInterface.PasstextBoxControl.setText("");
            DrawRegisterInterface.RepeatPasstextBoxControl.setText("");
        }else {
            DrawRemind.addRemind("注册失败", "请联系管理员解决方法", Remind.ERROR);
        }
    }

    private class AcceptDataCommunication extends Thread {

        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Proxy.this.DataCommunicationSocket.getInputStream(), "utf-8"));
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    Proxy.this.DataHeadle(line);
                }
            } catch (SocketException e) {
                DrawRemind.addRemind("错误", "与代理服务器断开连接", Remind.ERROR);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class HeartbeatThread extends Thread {

        @Override
        public void run() {
            while(true) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "heartbeat");
                Proxy.this.sendMessage(jsonObject.toString());
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
