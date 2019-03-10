package win.mc10.Controls;

import win.mc10.Controls.Controls.FontControl;
import win.mc10.Controls.Listener.FontControlListener;
import win.mc10.Controls.Listener.displayInterface;
import win.mc10.GlobalVariable;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class DrawHome implements displayInterface {

    private boolean inint = true;

    private FontControl fontControl1 = new FontControl("登录");

    public DrawHome() {
        fontControl1.setFontSize(18);
        this.fontControl1.setLocation(860 - (this.fontControl1.getWidth()), 5);



        this.ControlListenerEvent();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.DisPlayInterface == DrawHome.this) {
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
        g.setColor(new Color(255, 255, 255, 150));
        int X = 161 + ((Main.frame.getWidth() - 161) / 2 - (400 / 2));
        int Y = 86;
        g.fillRoundRect(X, Y, 400, 120, 15, 15);
        g.setColor(new Color(0, 0, 0, 150));
        Font font = new Font("Microsoft YaHei", Font.BOLD, 20);
        g.setFont(font);
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        String str = "该版本为测试版本，很不稳定";
        g.drawString(str, X + ((X / 2) - (fm.stringWidth(str) / 2)), Y + ((Y / 2) - (fm.getHeight() / 2)));

        if(!GlobalVariable.isLogin) {
            this.fontControl1.paint(g);
        }

    }

    public void ControlEvent(Frame frame) {
        frame.addMouseListener(this.fontControl1);
    }

    public void removeEvent(Frame frame) {
        frame.removeMouseListener(this.fontControl1);
    }

    public void ControlListenerEvent() {
        fontControl1.addFontControlEvent(new FontControlListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.DisPlayInterface = Main.drawLoginInterface;
            }
        });
    }
}
