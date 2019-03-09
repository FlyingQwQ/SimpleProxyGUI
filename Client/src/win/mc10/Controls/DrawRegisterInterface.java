package win.mc10.Controls;

import org.json.JSONObject;
import win.mc10.Controls.Controls.ButtonControl;
import win.mc10.Controls.Controls.FontControl;
import win.mc10.Controls.Controls.TextBoxControl;
import win.mc10.Controls.Enum.Remind;
import win.mc10.Controls.Listener.ButtonControlListener;
import win.mc10.Controls.Listener.FontControlListener;
import win.mc10.Controls.Listener.displayInterface;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class DrawRegisterInterface implements displayInterface {

    private boolean inint = true;

    public static TextBoxControl UsertextBoxControl = new TextBoxControl(428, 226, 300, 50);
    public static TextBoxControl EmailtextBoxControl = new TextBoxControl(428, 285, 300, 50);
    public static TextBoxControl PasstextBoxControl = new TextBoxControl(428, 345, 300, 50);
    public static TextBoxControl RepeatPasstextBoxControl = new TextBoxControl(428, 405, 300, 50);

    private ButtonControl RegbuttonControl = new ButtonControl(428, 470, 140, 55, "注册");

    public static FontControl backloginfontControl = new FontControl("返回登录");

    public DrawRegisterInterface() {
        this.UsertextBoxControl.setIcon("./image/user.png");
        this.EmailtextBoxControl.setIcon("./image/mail.png");
        this.PasstextBoxControl.setIcon("./image/lock.png");
        this.RepeatPasstextBoxControl.setIcon("./image/lock.png");

        this.UsertextBoxControl.setRemind("请输入用户名");
        this.EmailtextBoxControl.setRemind("请输入邮箱地址");
        this.PasstextBoxControl.setRemind("请输入密码");
        this.RepeatPasstextBoxControl.setRemind("请输入重复密码");

        this.EmailtextBoxControl.setIconSize(40, 34);

        this.PasstextBoxControl.setPassword(true);
        this.RepeatPasstextBoxControl.setPassword(true);

        this.backloginfontControl.setColor(new Color(0, 0, 0, 100));
        this.backloginfontControl.setLightColor(new Color(0, 0, 255, 150));
        this.backloginfontControl.setFontSize(16);
        this.backloginfontControl.setLocation(600, 500);

        this.ControlListenerEvent();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.DisPlayInterface == DrawRegisterInterface.this) {
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

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(255, 255, 255, 200));
        int X = 159 + (((Main.frame.getWidth() - 161) / 2) - (400 / 2));
        int Y = 84 + (((Main.frame.getHeight() - 86) / 2) - (400 / 2));

        g.fillRoundRect(X, Y, 400, 400, 15, 15);

        g.setColor(new Color(0, 0, 0, 120));
        Font font = new Font("Microsoft YaHei", Font.BOLD, 26);
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        g.setFont(font);
        String Title = "注 册";
        g.drawString(Title, X + ((400 / 2) - (SwingUtilities.computeStringWidth(fm, Title) / 2)), Y + (300 / 7));

        g.drawLine(X + (400 / 10), Y + (400 / 6), X + (400 - (400 / 10)), Y + (400 / 6));

        this.UsertextBoxControl.paint(g);
        this.EmailtextBoxControl.paint(g);
        this.PasstextBoxControl.paint(g);
        this.RepeatPasstextBoxControl.paint(g);
        this.RegbuttonControl.paint(g);
        this.backloginfontControl.paint(g);
    }

    public void ControlEvent(Frame frame) {
        this.UsertextBoxControl.setSuperFrame(frame);
        frame.addMouseMotionListener(this.UsertextBoxControl);
        frame.addMouseListener(this.UsertextBoxControl);

        this.EmailtextBoxControl.setSuperFrame(frame);
        frame.addMouseMotionListener(this.EmailtextBoxControl);
        frame.addMouseListener(this.EmailtextBoxControl);

        this.PasstextBoxControl.setSuperFrame(frame);
        frame.addMouseMotionListener(this.PasstextBoxControl);
        frame.addMouseListener(this.PasstextBoxControl);

        this.RepeatPasstextBoxControl.setSuperFrame(frame);
        frame.addMouseMotionListener(this.RepeatPasstextBoxControl);
        frame.addMouseListener(this.RepeatPasstextBoxControl);

        frame.addMouseMotionListener(this.RegbuttonControl);
        frame.addMouseListener(this.RegbuttonControl);

        frame.addMouseListener(this.backloginfontControl);
    }

    public void removeEvent(Frame frame) {
        frame.removeMouseMotionListener(this.UsertextBoxControl);
        frame.removeMouseListener(this.UsertextBoxControl);

        frame.removeMouseMotionListener(this.EmailtextBoxControl);
        frame.removeMouseListener(this.EmailtextBoxControl);

        frame.removeMouseMotionListener(this.PasstextBoxControl);
        frame.removeMouseListener(this.PasstextBoxControl);

        frame.removeMouseMotionListener(this.RepeatPasstextBoxControl);
        frame.removeMouseListener(this.RepeatPasstextBoxControl);

        frame.removeMouseMotionListener(this.RegbuttonControl);
        frame.removeMouseListener(this.RegbuttonControl);

        frame.removeMouseListener(this.backloginfontControl);
    }

    public void ControlListenerEvent() {

        this.RegbuttonControl.addButtonControlEvent(new ButtonControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = DrawRegisterInterface.this.UsertextBoxControl.getText();
                String mail = DrawRegisterInterface.this.EmailtextBoxControl.getText();
                String password = DrawRegisterInterface.this.PasstextBoxControl.getText();
                String repeatpassword = DrawRegisterInterface.this.RepeatPasstextBoxControl.getText();
                if(!username.equals("") && !mail.equals("") && !password.equals("") && !repeatpassword.equals("")) {
                    if(repeatpassword.equals(password)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", "register");
                        jsonObject.put("username", username);
                        jsonObject.put("mail", mail);
                        jsonObject.put("password", password);
                        Main.proxy.sendMessage(jsonObject.toString());
                    }else {
                        DrawRemind.addRemind("注册", "两次输入的密码不一致！", Remind.WARNING);
                    }
                }else {
                    DrawRemind.addRemind("注册", "注册资料不能为空！", Remind.WARNING);
                }
            }
        });

        this.backloginfontControl.addFontControlEvent(new FontControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.DisPlayInterface = Main.drawLoginInterface;
            }
        });
    }
}
