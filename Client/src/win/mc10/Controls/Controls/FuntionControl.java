package win.mc10.Controls.Controls;

import win.mc10.Controls.DrawControl;
import win.mc10.Controls.Listener.FuntionControlListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.ArrayList;

public class FuntionControl implements MouseMotionListener, MouseListener {

    private String Text;
    private String IconImagePath = "";
    private int IconImageWidth;
    private int IconImageHeight;
    private boolean Animation = true;
    private boolean Prohibit = false;

    private boolean DisplayLock = false;

    private int R = 255;
    private int G = 255;
    private int B = 255;
    private int A = 150;

    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle MouseRectangle = null;
    private Rectangle FunctuinRectangle = null;

    private List<FuntionControlListener> funtionControlListenerLists = new ArrayList<>();

    public FuntionControl(int x, int y, int width, int height, String Text) {
        FunctuinRectangle = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.Text = Text;

        new MouseIntersect().start();
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public void setIcon(String path) {
        this.IconImagePath = path;
    }

    public void setIcon(String path, int width, int height) {
        this.IconImagePath = path;
        this.IconImageWidth = width;
        this.IconImageHeight = height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setAnimation(boolean animation) {
        this.Animation = animation;
    }

    public void setProhibit(boolean Prohibit) {
        this.Prohibit = Prohibit;

        if(Prohibit) {
            this.R = 205;
            this.G = 79;
            this.B = 183;
            this.A = 150;
        }else {
            this.R = 255;
            this.G = 255;
            this.B = 255;
            this.A = 150;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setLocation(int x, int y) {
        this.FunctuinRectangle.setLocation(x, y);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void addFuntionControlEvent(FuntionControlListener funtionControlListener) {
        this.funtionControlListenerLists.add(funtionControlListener);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(this.R, this.G, this.B, this.A));
        g.fillRect(this.x, this.y, this.width, this.height);

        g.setColor(new Color(0, 0, 0, 120));
        Font font = new Font("Microsoft YaHei", Font.BOLD, 16);
        g.setFont(font);
        g.drawString(this.Text, this.x + (this.width / 2) - 32, this.y + (this.height / 2) + 5);

        ImageIcon imageIcon = new ImageIcon(this.IconImagePath);
        Image image = imageIcon.getImage();
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
        if(!this.Text.equals("")) {
            if(this.IconImageWidth != 0 && this.IconImageHeight != 0) {
                graphics2D.drawImage(image, this.x + ((this.width / 2) - (this.IconImageWidth / 2)) - (this.width / 3), this.y + (this.height / 2) - (this.IconImageHeight / 2), this.IconImageWidth, this.IconImageHeight, null);
            }else {
                graphics2D.drawImage(image, this.x + ((this.width / 2) - (26 / 2)) - (this.width / 3), this.y + (this.height / 2) - (26 / 2), 26, 26, null);
            }
        }else {
            if(this.IconImageWidth != 0 && this.IconImageHeight != 0) {
                graphics2D.drawImage(image, this.x + ((this.width / 2) - (this.IconImageWidth / 2)), this.y + (this.height / 2) - (this.IconImageHeight / 2), this.IconImageWidth, this.IconImageHeight, null);
            }else {
                graphics2D.drawImage(image, this.x + ((this.width / 2) - (26 / 2)), this.y + (this.height / 2) - (26 / 2), 26, 26, null);
            }
        }
        if(this.DisplayLock) {
            graphics2D.drawImage(new ImageIcon("./image/lock.png").getImage(), (this.x + this.width) - 19,(this.y + this.height) - 22, 17, 20, null);
        }
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        if(DrawControl.CurrFuntionControl != null) {
            if(DrawControl.CurrFuntionControl == this) {
                g.setColor(new Color(205, 79, 183, 150));
                g.fillRect(this.x, this.y + ((this.height / 2) - ((this.height / 2) / 2)), 5, this.height / 2);
            }
        }

    }

    public Rectangle getFunctuinRectangle() {
        return this.FunctuinRectangle;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(MouseRectangle == null) {
            MouseRectangle = new Rectangle(e.getX(), e.getY(), 1, 1);
        }else {
            MouseRectangle.setLocation(e.getX(), e.getY());
        }

        if(this.ControlRectanglesIntersect()) {

        }
    }

    public void mouseEntered() {
        if(!this.Prohibit) {
            this.R = 255;
            this.G = 255;
            this.B = 255;
            this.A = 120;

            if(this.Animation) {
                for(int i = 0; i < 3; i++) {
                    this.setSize(this.getWidth() - i, this.getHeight() - i);
                    this.setLocation(this.getX() + (i + 1), this.getY() + (i + 1));
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }else {
            this.DisplayLock = true;
        }
    }

    public void mouseOut() {
        if(!this.Prohibit) {
            if(this.Animation) {
                for(int i = 0; i < 3; i++) {
                    this.setSize(this.getWidth() + i, this.getHeight() + i);
                    this.setLocation(this.getX() - (i + 1), this.getY() - (i + 1));
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            this.R = 255;
            this.G = 255;
            this.B = 255;
            this.A = 150;
        }else {
            this.DisplayLock = false;
        }
    }

    public boolean ControlRectanglesIntersect() {
        if(this.MouseRectangle != null) {
            if(this.FunctuinRectangle.intersects(this.MouseRectangle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(ControlRectanglesIntersect() && !Prohibit) {
            this.R = 255;
            this.G = 255;
            this.B = 255;
            this.A = 100;
            for(FuntionControlListener funtionControlListener : this.funtionControlListenerLists) {
                funtionControlListener.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(ControlRectanglesIntersect() && !Prohibit) {
            this.R = 255;
            this.G = 255;
            this.B = 255;
            this.A = 150;
            if(this.Animation) {
                DrawControl.CurrFuntionControl = this;
            }
            for(FuntionControlListener funtionControlListener : this.funtionControlListenerLists) {
                funtionControlListener.mouseReleased(e);
            }
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
                    if(ControlRectanglesIntersect()) {
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
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
