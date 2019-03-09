package win.mc10.Controls;

import org.json.JSONObject;
import win.mc10.Controls.Controls.ButtonControl;
import win.mc10.Controls.Controls.FontControl;
import win.mc10.Controls.Controls.TextBoxControl;
import win.mc10.Controls.Enum.Remind;
import win.mc10.Controls.Listener.ButtonControlListener;
import win.mc10.Controls.Listener.FontControlListener;
import win.mc10.Controls.Listener.displayInterface;
import win.mc10.GlobalVariable;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class DrawLoginInterface implements displayInterface {

    private Frame frame = null;
    private boolean inint = true;

    public static TextBoxControl UsertextBoxControl = new TextBoxControl(428, 266, 300, 50);
    public static TextBoxControl PasstextBoxControl = new TextBoxControl(428, 325, 300, 50);

    public static ButtonControl LoginButtonControl = new ButtonControl(428, 400, 140, 55, "登录");

    public static FontControl RegisterfontControl = new FontControl("立即注册");

    public DrawLoginInterface() {
        this.UsertextBoxControl.setIcon("./image/user.png");
        this.PasstextBoxControl.setIcon("./image/lock.png");

        this.UsertextBoxControl.setRemind("请输入用户名");
        this.PasstextBoxControl.setRemind("请输入密码");
        this.UsertextBoxControl.setText("test2");
        this.PasstextBoxControl.setText("123");

        this.PasstextBoxControl.setPassword(true);

        RegisterfontControl.setColor(new Color(0, 0, 0, 100));
        RegisterfontControl.setLightColor(new Color(0, 0, 255, 150));
        RegisterfontControl.setFontSize(16);
        RegisterfontControl.setLocation(600, 430);

        this.ControlListenerEvent();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.DisPlayInterface == DrawLoginInterface.this) {
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
        if(!GlobalVariable.isLogin) {
            g.setColor(new Color(255, 255, 255, 200));
            int X = 159 + (((this.frame.getWidth() - 161) / 2) - (400 / 2));
            int Y = 84 + (((this.frame.getHeight() - 86) / 2) - (300 / 2));

            g.fillRoundRect(X, Y, 400, 300, 15, 15);

            g.setColor(new Color(0, 0, 0, 120));
            Font font = new Font("Microsoft YaHei", Font.BOLD, 26);
            FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
            g.setFont(font);
            String Title = "登 录";
            g.drawString(Title, X + ((400 / 2) - (SwingUtilities.computeStringWidth(fm, Title) / 2)), Y + (300 / 7));

            g.drawLine(X + (400 / 10), Y + (300 / 5), X + (400 - (400 / 10)), Y + (300 / 5));

            this.UsertextBoxControl.paint(g);
            this.PasstextBoxControl.paint(g);
            this.LoginButtonControl.paint(g);
            this.RegisterfontControl.paint(g);
        }
    }

    public void ControlEvent(Frame frame) {
        this.frame = frame;
        removeEvent(Main.frame);

        this.UsertextBoxControl.setSuperFrame(frame);
        frame.addMouseMotionListener(this.UsertextBoxControl);
        frame.addMouseListener(this.UsertextBoxControl);

        this.PasstextBoxControl.setSuperFrame(frame);
        frame.addMouseMotionListener(this.PasstextBoxControl);
        frame.addMouseListener(this.PasstextBoxControl);

        frame.addMouseMotionListener(this.LoginButtonControl);
        frame.addMouseListener(this.LoginButtonControl);

        frame.addMouseListener(this.RegisterfontControl);
    }

    public void removeEvent(Frame frame) {
        this.frame = frame;

        frame.removeMouseMotionListener(this.UsertextBoxControl);
        frame.removeMouseListener(this.UsertextBoxControl);

        frame.removeMouseMotionListener(this.PasstextBoxControl);
        frame.removeMouseListener(this.PasstextBoxControl);

        frame.removeMouseMotionListener(this.LoginButtonControl);
        frame.removeMouseListener(this.LoginButtonControl);

        frame.removeMouseListener(this.RegisterfontControl);
    }

    //  控件的事件
    public void ControlListenerEvent() {
        this.LoginButtonControl.addButtonControlEvent(new ButtonControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!DrawLoginInterface.this.UsertextBoxControl.getText().equals("") && !DrawLoginInterface.this.PasstextBoxControl.getText().equals("")) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type", "login");
                    jsonObject.put("username", DrawLoginInterface.this.UsertextBoxControl.getText());
                    jsonObject.put("password", DrawLoginInterface.this.PasstextBoxControl.getText());
                    Main.proxy.sendMessage(jsonObject.toString());
                }else {
                    Main.drawRemind.addRemind("登录", "账号或密码不能为空", Remind.WARNING);
                }
            }
        });

        RegisterfontControl.addFontControlEvent(new FontControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.DisPlayInterface = Main.drawRegisterInterface;
            }
        });
    }

}
