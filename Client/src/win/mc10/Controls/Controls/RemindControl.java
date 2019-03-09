package win.mc10.Controls.Controls;

import win.mc10.Controls.DrawRemind;
import win.mc10.Controls.Enum.Remind;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class RemindControl implements MouseListener {

    private String Text = "";
    private String Content = "";

    private Rectangle RemindControlRectangle = null;
    private int x;
    public int y;
    private int width;
    public int height;
    public Remind remind;

    private boolean isColose = false;
    private int CloseX = 0;

    public Frame frame;

    public RemindControl(String Text, String Content, Remind remind) {
        this.Text = Text;
        this.Content = Content;
        this.remind = remind;
        this.RemindControlRectangle  = new Rectangle(0, 0, 0, 0);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                RemindControl.this.Close();
            }
        }, 1000 * 10);
    }

    public void setSuperFrame(Frame frame) {
        this.frame = frame;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.RemindControlRectangle.setLocation(x, y);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.RemindControlRectangle.setSize(width, height);
    }

    public void paint(Graphics g) {
        switch (this.remind) {
            case INFO:
                g.setColor(new Color(255, 255, 255, 200));
                break;
            case ERROR:
                g.setColor(new Color(255, 0, 0, 200));
                break;
            case WARNING:
                g.setColor(new Color(255, 255, 0, 200));
                break;
        }

        if(!RemindControl.this.isColose) {
            g.fillRoundRect(x, y, width, height, 15, 15);
        }else {
            g.fillRoundRect(this.CloseX, y, width, height, 15, 15);
            if(this.CloseX > 0) {
                this.x = this.CloseX;
            }
        }

        g.setColor(new Color(0, 0, 0, 120));
        Font font = new Font("Microsoft YaHei", Font.PLAIN, 16);
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        g.setFont(font);
        g.drawString(this.Text, this.x + ((this.width / 2) - (SwingUtilities.computeStringWidth(fm, this.Text) / 2)), this.y + (this.height / 5));

        g.drawLine(this.x + (this.width / 8), this.y + (this.height / 4), this.x + (this.width - (this.width / 8)), this.y + (this.height / 4));
        g.drawString(this.Content, this.x + ((this.width / 2) - (SwingUtilities.computeStringWidth(fm, this.Content) / 2)), this.y + (this.height / 2));
    }

    public void Close() {
        RemindControl.this.frame.removeMouseListener(RemindControl.this);
        if(!RemindControl.this.isColose) {
            RemindControl.this.isColose = true;
            new AnimationThread().start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.RemindControlRectangle != null) {
            if(Main.MouseRectangle.intersects(this.RemindControlRectangle)) {
                this.Close();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class AnimationThread extends Thread {

        public void run() {
            while(RemindControl.this.CloseX < RemindControl.this.frame.getWidth()) {
                if(RemindControl.this.CloseX == 0) {
                    RemindControl.this.CloseX = RemindControl.this.x;
                }
                RemindControl.this.CloseX++;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DrawRemind.remindControlList.remove(RemindControl.this);
            RemindControl.this.RemindControlRectangle.setLocation(10000, 1000);
            RemindControl.this.RemindControlRectangle = null;
        }
    }
}
