package win.mc10.Controls;

import org.json.JSONObject;
import win.mc10.Controls.Controls.ButtonControl;
import win.mc10.Controls.Controls.TextBoxControl;
import win.mc10.Controls.Enum.Remind;
import win.mc10.Controls.Listener.ButtonControlListener;
import win.mc10.Controls.Listener.displayInterface;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class DrawTunnleRegister implements displayInterface {

    private boolean inint = true;

    public static TextBoxControl ApplictionNametextBoxControl = new TextBoxControl(428, 200, 300, 50);
    public static TextBoxControl DomainNametextBoxControl = new TextBoxControl(428, 260, 300, 50);
    public static TextBoxControl TelePorttextBoxControl = new TextBoxControl(428, 320, 300, 50);
    public static TextBoxControl LocalAddresstextBoxControl = new TextBoxControl(428, 380, 300, 50);
    public static TextBoxControl LocalPorttextBoxControl = new TextBoxControl(428, 440, 300, 50);

    private ButtonControl RegbuttonControl = new ButtonControl(428, 500, 140, 55, "创建");

    public DrawTunnleRegister() {
        this.ApplictionNametextBoxControl.setIcon("./image/appname.png");
        this.ApplictionNametextBoxControl.setIconSize(30, 32);
        this.ApplictionNametextBoxControl.setRemind("请输入应用名称");

        this.DomainNametextBoxControl.setIcon("./image/domainname.png");
        this.DomainNametextBoxControl.setIconSize(36, 36);
        this.DomainNametextBoxControl.setRemind("请输入域名");

        this.TelePorttextBoxControl.setIcon("./image/teleport.png");
        this.TelePorttextBoxControl.setIconSize(34, 34);
        this.TelePorttextBoxControl.setRemind("请输入外网端口");

        this.LocalAddresstextBoxControl.setIcon("./image/domainname.png");
        this.LocalAddresstextBoxControl.setIconSize(34, 34);
        this.LocalAddresstextBoxControl.setRemind("请输入内网地址");

        this.LocalPorttextBoxControl.setIcon("./image/teleport.png");
        this.LocalPorttextBoxControl.setIconSize(34, 34);
        this.LocalPorttextBoxControl.setRemind("请输入内网端口");

        this.ControlListenerEvent();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.DisPlayInterface == DrawTunnleRegister.this) {
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

    public void paint(Graphics g) {
        g.setColor(new Color(255, 255, 255, 200));
        int X = 159 + (((Main.frame.getWidth() - 161) / 2) - (400 / 2));
        int Y = 84 + (((Main.frame.getHeight() - 86) / 2) - (450 / 2));

        g.fillRoundRect(X, Y, 400, 450, 15, 15);

        g.setColor(new Color(0, 0, 0, 120));
        Font font = new Font("Microsoft YaHei", Font.BOLD, 26);
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        g.setFont(font);
        String Title = "创 建 隧 道";
        g.drawString(Title, X + ((400 / 2) - (SwingUtilities.computeStringWidth(fm, Title) / 2)), Y + (300 / 7));

        g.drawLine(X + (400 / 10), Y + (400 / 6), X + (400 - (400 / 10)), Y + (400 / 6));

        ApplictionNametextBoxControl.paint(g);
        DomainNametextBoxControl.paint(g);
        TelePorttextBoxControl.paint(g);
        LocalAddresstextBoxControl.paint(g);
        LocalPorttextBoxControl.paint(g);
        RegbuttonControl.paint(g);
    }

    public void ControlEvent(Frame frame) {
        this.ApplictionNametextBoxControl.setSuperFrame(frame);
        frame.addMouseListener(this.ApplictionNametextBoxControl);
        frame.addMouseMotionListener(this.ApplictionNametextBoxControl);

        this.DomainNametextBoxControl.setSuperFrame(frame);
        frame.addMouseListener(this.DomainNametextBoxControl);
        frame.addMouseMotionListener(this.DomainNametextBoxControl);

        this.TelePorttextBoxControl.setSuperFrame(frame);
        frame.addMouseListener(this.TelePorttextBoxControl);
        frame.addMouseMotionListener(this.TelePorttextBoxControl);

        this.LocalAddresstextBoxControl.setSuperFrame(frame);
        frame.addMouseListener(this.LocalAddresstextBoxControl);
        frame.addMouseMotionListener(this.LocalAddresstextBoxControl);

        this.LocalPorttextBoxControl.setSuperFrame(frame);
        frame.addMouseListener(this.LocalPorttextBoxControl);
        frame.addMouseMotionListener(this.LocalPorttextBoxControl);

        frame.addMouseMotionListener(this.RegbuttonControl);
        frame.addMouseListener(this.RegbuttonControl);
    }

    public void removeEvent(Frame frame) {
        frame.removeMouseListener(this.ApplictionNametextBoxControl);
        frame.removeMouseMotionListener(this.ApplictionNametextBoxControl);

        frame.removeMouseListener(this.DomainNametextBoxControl);
        frame.removeMouseMotionListener(this.DomainNametextBoxControl);

        frame.removeMouseListener(this.TelePorttextBoxControl);
        frame.removeMouseMotionListener(this.TelePorttextBoxControl);

        frame.removeMouseListener(this.LocalAddresstextBoxControl);
        frame.removeMouseMotionListener(this.LocalAddresstextBoxControl);

        frame.removeMouseListener(this.LocalPorttextBoxControl);
        frame.removeMouseMotionListener(this.LocalPorttextBoxControl);

        frame.removeMouseMotionListener(this.RegbuttonControl);
        frame.removeMouseListener(this.RegbuttonControl);
    }

    //  控件的事件
    public void ControlListenerEvent() {
        this.RegbuttonControl.addButtonControlEvent(new ButtonControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!ApplictionNametextBoxControl.getText().equals("") && !DomainNametextBoxControl.equals("") && !TelePorttextBoxControl.equals("") && !LocalAddresstextBoxControl.equals("") && !LocalPorttextBoxControl.equals("")) {
                    if(Integer.parseInt(TelePorttextBoxControl.getText()) <= 65535 && Integer.parseInt(LocalPorttextBoxControl.getText()) <= 65535) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", "regtunnle");
                        jsonObject.put("applicationname", ApplictionNametextBoxControl.getText());
                        jsonObject.put("domainname", DomainNametextBoxControl.getText());
                        jsonObject.put("teleport", Integer.parseInt(TelePorttextBoxControl.getText()));
                        jsonObject.put("localaddress", LocalAddresstextBoxControl.getText() + ":" + LocalPorttextBoxControl.getText());
                        Main.proxy.sendMessage(jsonObject.toString());
                    }else {
                        DrawRemind.addRemind("提醒", "端口超出最大范围65535", Remind.INFO);
                    }
                }else {
                    DrawRemind.addRemind("提醒", "资料不能为空！", Remind.INFO);
                }
            }
        });
    }
}
