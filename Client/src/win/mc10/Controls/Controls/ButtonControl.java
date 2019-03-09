package win.mc10.Controls.Controls;

import win.mc10.Controls.Listener.ButtonControlListener;
import win.mc10.Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class ButtonControl implements MouseMotionListener, MouseListener {

    private int R = 0;
    private int B = 0;
    private int G = 0;
    private int A = 50;

    private int x;
    private int y;
    private int width;
    private int height;

    private String Text = "";
    private Rectangle ButtonControlRectangle = null;

    private List<ButtonControlListener> buttonControlListenerList = new ArrayList<>();

    public ButtonControl(int x, int y, int width, int height, String Text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.Text = Text;

        this.ButtonControlRectangle = new Rectangle(x, y, width, height);
        new MouseIntersect().start();
    }

    public void addButtonControlEvent(ButtonControlListener buttonControlListener) {
        this.buttonControlListenerList.add(buttonControlListener);
    }

    public void setText(String text) {
        Text = text;
    }

    public String getText() {
        return Text;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(this.R, this.G, this.B, this.A));
        g.fillRoundRect(this.x, this.y, this.width, this.height, 15, 15);
        g.setColor(new Color(0, 0, 0, 100));
        Font font = new Font("Microsoft YaHei", Font.BOLD, 16);
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
        g.setFont(font);
        g.drawString(this.Text, this.x + ((this.width / 2) - (fm.stringWidth(this.Text) / 2)), this.y + (this.height / 2) + (fm.getHeight() / 2 / 2));


    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(Main.MouseRectangle != null) {
            if(Main.MouseRectangle.intersects(this.ButtonControlRectangle)) {

            }
        }
    }

    public void mouseEntered() {
        this.R = 0;
        this.G = 0;
        this.B = 0;
        this.A = 30;
    }

    public void mouseOut() {
        this.R = 0;
        this.G = 0;
        this.B = 0;
        this.A = 50;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Main.MouseRectangle.intersects(this.ButtonControlRectangle)) {
            for(ButtonControlListener buttonControlListener : this.buttonControlListenerList) {
                buttonControlListener.mouseClicked(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(Main.MouseRectangle != null) {
            if(Main.MouseRectangle.intersects(this.ButtonControlRectangle)) {
                this.R = 0;
                this.G = 0;
                this.B = 0;
                this.A = 100;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(Main.MouseRectangle.intersects(this.ButtonControlRectangle)) {
            this.R = 0;
            this.G = 0;
            this.B = 0;
            this.A = 30;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class MouseIntersect extends Thread {
        /**
         *  判断鼠标是否在控件里面
         */

        @Override
        public void run() {

            boolean isOut = false;

            while(true) {
                try {
                    if(Main.MouseRectangle != null && ButtonControl.this.ButtonControlRectangle != null) {
                        if(Main.MouseRectangle.intersects(ButtonControl.this.ButtonControlRectangle)) {
                            if(!isOut) {
                                mouseEntered();
                                isOut = true;
                            }
                        }else {
                            if(isOut) {
                                mouseOut();
                                isOut = false;
                            }
                        }
                    }
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
