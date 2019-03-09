package win.mc10.DataCommunication;

import org.json.JSONArray;
import org.json.JSONObject;
import win.mc10.Log;
import win.mc10.MySQL.MySql;
import win.mc10.Proxy.CreateProxy;
import win.mc10.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcceptSocketDataHandle extends Thread{

    private List<CreateProxy> createProxyList = new ArrayList<>();

    private Socket socket = null;
    private BufferedWriter bufferedWriter = null;
    private HeartbeatThread heartbeatThread = null;

    public AcceptSocketDataHandle (Socket socket) {
        this.socket = socket;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
            this.heartbeatThread = new HeartbeatThread();
            this.heartbeatThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String str) {
        try {
            this.bufferedWriter.write(str + "\r\n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            this.heartbeatThread.interrupt();
        }
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                this.DataHandle(line);
            }
        } catch(SocketTimeoutException e) {

        } catch (SocketException e) {
            for(CreateProxy createProxy : createProxyList) {
                try {
                    createProxy.getProjectClientServerSocket().close();
                    createProxy.getProxyDataTunnelServerSocket().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            Log.INFO("用户" + Server.LoginSocket.get(this.socket) + "与服务器断开连接!");
            Server.LoginSocket.remove(this.socket);
            Server.DataCommunicationSocketLists.remove(this.socket);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            this.socket.close();
            bufferedReader.close();
            this.bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DataHandle(String str) {
        JSONObject writeJSON = null;

        JSONObject jsonObject = new JSONObject(str);
        switch(jsonObject.getString("type")) {
            case "register":
                Register(jsonObject);
                break;
            case "login":
                Login(jsonObject);
                break;
            case "regtunnle":
                RegTunnle(jsonObject);
                break;
            case "gettunnlelists":
                GetTunnleList(jsonObject);
                break;
            case "switchalltunnle":
                SwitchAllTunnle(jsonObject);
                break;
        }

    }

    public void RegTunnle(JSONObject jsonObject) {
        String applicationname = jsonObject.getString("applicationname");
        String domainname = jsonObject.getString("domainname");
        int teleport = jsonObject.getInt("teleport");
        String localaddress = jsonObject.getString("localaddress");
        int transmissionPort = 0;

        int Number = 0;
        int count = 0;

        ResultSet TunnleMaximumTesting = MySql.ExecutSQL("select * from tunnle where username = '" + Server.LoginSocket.get(this.socket) + "'");
        try {
            TunnleMaximumTesting.beforeFirst();
            while(TunnleMaximumTesting.next()) {
                Number++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(Number < 12) {
            ResultSet teleportTesting = MySql.ExecutSQL("select * from tunnle where tunnleport = " + teleport);
            try {
                teleportTesting.beforeFirst();
                while(teleportTesting.next()) {
                    count = teleportTesting.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(count == 0) {
                ResultSet rs = MySql.ExecutSQL("select * from transmissionportsetting where id = '1'");
                try {
                    rs.next();
                    int TransmissionPortStart = rs.getInt("TransmissionPortStart");
                    int TransmissionPortEnd = rs.getInt("TransmissionPortEnd");
                    int CurrTransmissionPort = rs.getInt("CurrTransmissionPort");
                    if((CurrTransmissionPort + 1) <= TransmissionPortEnd) {
                        transmissionPort = CurrTransmissionPort;
                        PreparedStatement preparedStatement = MySql.ExecutPreparedSQL("update transmissionportsetting set CurrTransmissionPort=" + (CurrTransmissionPort + 1) + " where id=1");
                        preparedStatement.executeUpdate();
                    }else {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("type", "remind");
                        jsonObject1.put("title", "创建失败");
                        jsonObject1.put("content", "无法创建隧道，请联系管理员");
                        jsonObject1.put("remindtype", "error");
                        this.sendMessage(jsonObject.toString());
                        Log.WARNING("用户" + Server.LoginSocket.get(this.socket) + "创建隧道失败");
                        return;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                PreparedStatement preparedStatement = MySql.ExecutPreparedSQL("insert into tunnle(applicationname,DomainName,tunnleport,Localaddress,transmissionPort,username) values(?,?,?,?,?,?)");
                try {
                    preparedStatement.setString(1, applicationname);
                    preparedStatement.setString(2, domainname);
                    preparedStatement.setInt(3, teleport);
                    preparedStatement.setString(4, localaddress);
                    preparedStatement.setInt(5, transmissionPort);
                    preparedStatement.setString(6, Server.LoginSocket.get(this.socket));
                    preparedStatement.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "regtunnleresult");
                jsonObject1.put("result", true);
                this.sendMessage(jsonObject1.toString());
                Log.WARNING("用户" + Server.LoginSocket.get(this.socket) + "隧道创建成功");
            }else {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "remind");
                jsonObject1.put("title", "创建失败");
                jsonObject1.put("content", "该外网端口已存在");
                jsonObject1.put("remindtype", "warning");
                this.sendMessage(jsonObject1.toString());
                Log.WARNING("用户" + Server.LoginSocket.get(this.socket) + "隧道创建失败，该外网端口已存在");
            }
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("type", "remind");
            jsonObject1.put("title", "创建失败");
            jsonObject1.put("content", "创建数量达到上限");
            jsonObject1.put("remindtype", "warning");
            this.sendMessage(jsonObject1.toString());
            Log.WARNING("用户" + Server.LoginSocket.get(this.socket) + "隧道创建失败，创建数量达到上限");
        }

    }

    public void SwitchAllTunnle(JSONObject jsonObject) {
        String Switch = jsonObject.getString("switch");

        if(Switch.equals("start")) {
            ResultSet rs = MySql.ExecutSQL("select * from tunnle where username = '" + Server.LoginSocket.get(this.socket) + "'");
            try {
                jsonObject = new JSONObject();
                jsonObject.put("type", "starttunnle");

                JSONArray jsonArray = new JSONArray();
                rs.beforeFirst();
                while(rs.next()) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", rs.getInt("id"));
                    jsonObject1.put("applicationname", rs.getString("applicationname"));
                    jsonObject1.put("transmissionPort", rs.getInt("transmissionPort"));
                    jsonObject1.put("tunnleport", rs.getInt("tunnleport"));
                    jsonObject1.put("DomainName", rs.getString("DomainName"));
                    jsonObject1.put("Localaddress", rs.getString("Localaddress"));
                    jsonArray.put(jsonObject1);

                    this.CreateTunnle(rs.getInt("transmissionPort"), rs.getInt("tunnleport"));
                }
                jsonObject.put("lists", jsonArray);
                this.sendMessage(jsonObject.toString());
                Log.INFO("用户" + Server.LoginSocket.get(this.socket) + "进行了开启隧道操作");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(Switch.equals("stop")) {
            for(CreateProxy createProxy : createProxyList) {
                try {
                    createProxy.getProjectClientServerSocket().close();
                    createProxy.getProxyDataTunnelServerSocket().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            Log.INFO("用户" + Server.LoginSocket.get(this.socket) + "进行了关闭隧道操作");
        }
    }

    public void GetTunnleList(JSONObject jsonObject) {
        ResultSet rs = MySql.ExecutSQL("select * from tunnle where username = '" + Server.LoginSocket.get(this.socket) + "'");
        try {
            jsonObject = new JSONObject();
            jsonObject.put("type", "tunnlelists");

            JSONArray jsonArray = new JSONArray();
            rs.beforeFirst();
            while(rs.next()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", rs.getInt("id"));
                jsonObject1.put("applicationname", rs.getString("applicationname"));
                jsonObject1.put("transmissionPort", rs.getInt("transmissionPort"));
                jsonObject1.put("tunnleport", rs.getInt("tunnleport"));
                jsonObject1.put("DomainName", rs.getString("DomainName"));
                jsonObject1.put("Localaddress", rs.getString("Localaddress"));
                jsonArray.put(jsonObject1);
            }

            jsonObject.put("lists", jsonArray);
            this.sendMessage(jsonObject.toString());
            Log.INFO("用户" + Server.LoginSocket.get(this.socket) + "进行了获取隧道列表操作");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Login(JSONObject jsonObject) {
        JSONObject writeJSON = null;
        ResultSet rs = MySql.ExecutSQL("select * from account where username = '" + jsonObject.getString("username") + "'");
        try {
            if(rs.next()) {
                writeJSON = new JSONObject();
                JSONObject writeRemindJSON = new JSONObject();

                writeJSON.put("type", "loginverification");
                writeRemindJSON.put("type", "remind");

                if(jsonObject.getString("password").equals(rs.getString("password"))) {
                    writeJSON.put("result", true);
                    Server.LoginSocket.put(this.socket, jsonObject.getString("username"));
                    Log.INFO("用户" + jsonObject.getString("username") + "登录成功！");
                }else {
                    writeJSON.put("result", false);

                    writeRemindJSON.put("title", "登录失败");
                    writeRemindJSON.put("content", "输入的账号或密码有误!");
                    writeRemindJSON.put("remindtype", "warning");
                    this.sendMessage(writeRemindJSON.toString());
                    Log.WARNING("用户" + jsonObject.getString("username") + "登录失败！输入的密码有误");
                }
                this.sendMessage(writeJSON.toString());
            }else {
                writeJSON = new JSONObject();
                JSONObject writeRemindJSON = new JSONObject();

                writeJSON.put("type", "loginverification");
                writeJSON.put("result", false);

                writeRemindJSON.put("type", "remind");
                writeRemindJSON.put("title", "登录失败");
                writeRemindJSON.put("content", "输入的账号或密码有误!");
                writeRemindJSON.put("remindtype", "warning");

                this.sendMessage(writeJSON.toString());
                this.sendMessage(writeRemindJSON.toString());
                Log.WARNING("用户" + jsonObject.getString("username") + "登录失败！未找到该用户");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Register(JSONObject jsonObject) {
        ResultSet rs = MySql.ExecutSQL("select * from account where username = '" + jsonObject.getString("username") + "'");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("type", "regresult");

        int count = 0;

        try {
            while(rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(count == 0) {
            PreparedStatement preparedStatement = MySql.ExecutPreparedSQL("insert into account(username,mail,password) values(?,?,?)");
            try {
                preparedStatement.setString(1, jsonObject.getString("username"));
                preparedStatement.setString(2, jsonObject.getString("mail"));
                preparedStatement.setString(3, jsonObject.getString("password"));
                preparedStatement.executeUpdate();
                jsonObject1.put("result", true);
                Log.INFO("用户" + jsonObject.getString("username") + "注册成功,邮箱:" + jsonObject.getString("mail") + ",密码:" + jsonObject.getString("password"));
            } catch (SQLException e1) {
                jsonObject1.put("result", false);
            }
            this.sendMessage(jsonObject1.toString());
        }else {
            jsonObject1.put("result", false);
            this.sendMessage(jsonObject1.toString());

            jsonObject1 = new JSONObject();
            jsonObject1.put("type", "remind");
            jsonObject1.put("title", "注册");
            jsonObject1.put("content", "该用户已存在！");
            jsonObject1.put("remindtype", "warning");
            this.sendMessage(jsonObject1.toString());
            Log.WARNING("用户" + jsonObject.getString("username") + "注册失败,该用户已存在！");
        }
    }

    public void CreateTunnle(int transmissionPort, int tunnleport) {
        CreateProxy createProxy = new CreateProxy(transmissionPort, tunnleport);
        this.createProxyList.add(createProxy);
    }

    private class HeartbeatThread extends Thread {

        @Override
        public void run() {
            while(true) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "heartbeat");
                AcceptSocketDataHandle.this.sendMessage(jsonObject.toString());
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

}
