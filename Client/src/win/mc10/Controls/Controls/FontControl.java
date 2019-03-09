package win.mc10.Controls.Controls;

import sun.font.FontDesignMetrics;
import win.mc10.Controls.Listener.FontControlListener;
import win.mc10.Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;

public class FontControl implements MouseListener {

    private Color color = null;
    private Color LightColor = null;
    private int R = 255;
    private int G = 255;
    private int B = 255;
    private int A = 200;

    private int x;
    private int y;
    private int width;
    private int height;
    private String Text = "";
    private Font font = null;
    private FontMetrics fontMetrics = null;

    private List<FontControlListener> fontControlListenerList = new ArrayList<>();

    private Rectangle FontRectangle = new Rectangle();

    public FontControl(String title) {
        this.Text = title;
        this.font = new Font("Microsoft YaHei", Font.PLAIN, 20);
        fontMetrics = FontDesignMetrics.getMetrics(this.font);
        this.width = fontMetrics.stringWidth(this.Text);
        this.height = fontMetrics.getHeight();
        FontRectangle.setSize(this.width, this.height);

        new MouseIntersect().start();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        FontRectangle.setLocation(x, y);
    }

    public void setColor(Color color) {
        this.color = color;
        this.R = color.getRed();
        this.G = color.getGreen();
        this.B = color.getBlue();
    }

    public void setLightColor(Color lightColor) {
        this.LightColor = lightColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setFontSize(int size) {
        this.font = new Font("Microsoft YaHei", Font.BOLD, size);
        fontMetrics = FontDesignMetrics.getMetrics(this.font);
        this.width = fontMetrics.stringWidth(this.Text);
        this.height = fontMetrics.getHeight();
        FontRectangle.setSize(this.width, this.height);
    }

    public void setTitle(String title) {
        this.Text = title;
    }

    public void addFontControlEvent(FontControlListener fontControlListener) {
        fontControlListenerList.add(fontControlListener);
    }

    public void paint(Graphics g) {
        g.setFont(this.font);
        g.setColor(new Color(this.R, this.G, this.B, 100));
        g.drawString(this.Text, this.x, this.y + ((this.height / 2) + 5));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Main.MouseRectangle.intersects(this.FontRectangle)) {
            for(FontControlListener fontControlListener : this.fontControlListenerList) {
                fontControlListener.mouseClicked(e);
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

    public void mouseEntered() {
        if(this.LightColor != null) {
            this.R = LightColor.getRed();
            this.G = LightColor.getGreen();
            this.B = LightColor.getBlue();
        }else {
            this.R = 0;
            this.G = 255;
            this.B = 127;
            this.A = 200;
        }
    }

    public void mouseOut() {
        if(this.color != null) {
            this.R = color.getRed();
            this.G = color.getGreen();
            this.B = color.getBlue();
        }else {
            this.R = 255;
            this.G = 255;
            this.B = 255;
            this.A = 200;
        }
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
                    if(Main.MouseRectangle != null && FontControl.this.FontRectangle != null) {
                        if(Main.MouseRectangle.intersects(FontControl.this.FontRectangle)) {
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
