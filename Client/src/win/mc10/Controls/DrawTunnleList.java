package win.mc10.Controls;

import org.json.JSONObject;
import sun.font.FontDesignMetrics;
import win.mc10.Controls.Controls.ButtonControl;
import win.mc10.Controls.Controls.CircularButtonControl;
import win.mc10.Controls.Controls.ListControl;
import win.mc10.Controls.Listener.ButtonControlListener;
import win.mc10.Controls.Listener.CircularButtonListener;
import win.mc10.Controls.Listener.displayInterface;
import win.mc10.Main;
import win.mc10.proxy.ConnectTunnleServer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DrawTunnleList implements displayInterface {

    private boolean inint = true;

    public static CircularButtonControl AddTunnleCircularButton = new CircularButtonControl(220, 538, 55, 55);
    public static ButtonControl SwitchButton = new ButtonControl(880, 538, 55, 55, "开启");

    public static List<ListControl> listControlList = new ArrayList<>();
    public static ListControl SelectionListControl = null;

    public DrawTunnleList() {
        this.AddTunnleCircularButton.setIcon("./image/add.png");

        this.ControlListenerEvent();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.DisPlayInterface == DrawTunnleList.this) {
                    if(!inint) {
                        if(Main.frame != null) {
                            ControlEvent(Main.frame);
                            inint = true;
                        }
                    }
                }else {
                    if(inint) {
                        if(Main.frame != null) {
                            removeEvent(Main.frame);
                            inint = false;
                        }
                    }
                }
            }
        }, 0, 500);
    }

    public List<ListControl> getListControlList() {
        return listControlList;
    }

    public void addListControl(ListControl listControl) {
        listControlList.add(listControl);
        Main.frame.addMouseListener(listControl);
    }

    public void emptyListControl() {
        for(ListControl listControl : this.listControlList) {
            listControl.getListControlRectangle().setBounds(9999, 9999, 0, 0);
            Main.frame.removeMouseListener(listControl);
        }
        this.listControlList.removeAll(this.listControlList);
    }

    public void paint(Graphics g) {
        int currlenght = 0;
        int newX = 0;
        int newY = 0;

        for(int i = 0; i < listControlList.size(); i++) {
            if(i < 1) {
                listControlList.get(i).setLocation(159 + 55, 84 + 20);
                currlenght = 0;
                newY = 0;
            }else {
                if(currlenght < 3) {
                    newX = listControlList.get(i - 1).getX() + listControlList.get(i - 1).getWidth() + 5;
                    newY = listControlList.get(i - 1).getY();
                }else {
                    newX = 159 + 55;
                    newY = listControlList.get(i - 1).getY() + listControlList.get(i - 1).getHeight() + 5;
                    currlenght = 0;
                }
                listControlList.get(i).setLocation(newX, newY);
            }
            listControlList.get(i).setSize(240, 100);
            listControlList.get(i).paint(g);
            currlenght++;
        }

        if(listControlList.size() == 0) {
            if(Main.frame != null) {
                g.setColor(new Color(255, 255, 255, 150));
                int X = 159 + (((Main.frame.getWidth() - 161) / 2) - 161);
                int Y = 84 + (((Main.frame.getHeight() - 86) / 2) - 86);
                g.fillRoundRect(X, Y, 250, 150, 15, 15);

                g.setColor(new Color(0, 0, 0, 120));
                Font font = new Font("Microsoft YaHei", Font.BOLD, 20);
                g.setFont(font);
                FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);

                String Text = "哎呦，你还没端口呦！";
                g.drawString(Text, X + ((250 / 2) - (fontMetrics.stringWidth(Text) / 2)), Y + ((150 / 2) - (fontMetrics.getHeight() / 2)));
            }
        }
        g.setColor(new Color(255, 255, 255, 150));
        int frameX = 159 + 55;
        int frameY = 84 + ((Main.frame.getHeight() - 86) - 65);
        g.fillRoundRect(frameX, frameY, 730, 65, 15, 15);

        this.AddTunnleCircularButton.paint(g);
        this.SwitchButton.paint(g);

        if(this.SelectionListControl != null) {
            g.setColor(new Color(0, 0, 0, 50));
            int StateX = frameX + ((730 / 2) - (500 / 2));
            int StateY = frameY + ((65 / 2) - (55 / 2));
            g.fillRoundRect(StateX, StateY, 500, 55, 15, 15);

            g.setColor(new Color(255, 255, 255, 150));
            g.drawString("连接数:" + this.SelectionListControl.getConnectionNumber(), StateX + 10, StateY + 20);
            g.drawString("延迟:" + this.SelectionListControl.getDelay() + "ms", StateX + 10, StateY + 45);
            g.drawString("内网地址:" + this.SelectionListControl.getLocaladdress(), StateX + 130, StateY + 20);
            g.drawString("外网地址:" + this.SelectionListControl.getDomainName() + ":" + this.SelectionListControl.getTunnleport(), StateX + 130, StateY + 45);
        }
    }

    public void ControlEvent(Frame frame) {
        frame.addMouseListener(this.AddTunnleCircularButton);
        frame.addMouseListener(this.SwitchButton);

        for(ListControl listControl : listControlList) {
            frame.addMouseListener(listControl);
        }
    }

    public void removeEvent(Frame frame) {
        frame.removeMouseListener(this.AddTunnleCircularButton);
        frame.removeMouseListener(this.SwitchButton);

        for(ListControl listControl : listControlList) {
            frame.removeMouseListener(listControl);
        }
    }

    //  控件的事件
    public void ControlListenerEvent() {
        this.AddTunnleCircularButton.addCircularButtonEvent(new CircularButtonListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.DisPlayInterface = Main.drawTunnleRegister;
            }
        });

        this.SwitchButton.addButtonControlEvent(new ButtonControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "switchalltunnle");

                if(SwitchButton.getText().equals("开启")) {
                    SwitchButton.setText("关闭");
                    jsonObject.put("switch", "start");
                }else {
                    SwitchButton.setText("开启");
                    jsonObject.put("switch", "stop");
                    for(ConnectTunnleServer connectTunnleServer : Main.proxy.connectTunnleServers) {
                        connectTunnleServer.close();;
                    }
                    Main.proxy.connectTunnleServers.removeAll(Main.proxy.connectTunnleServers);
                }

                Main.proxy.sendMessage(jsonObject.toString());
            }
        });
    }
}
