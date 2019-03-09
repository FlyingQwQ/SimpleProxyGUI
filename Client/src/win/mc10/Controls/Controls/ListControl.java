package win.mc10.Controls.Controls;

import sun.font.FontDesignMetrics;
import win.mc10.Controls.DrawTunnleList;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListControl implements MouseListener {

    private int R = 255;
    private int G = 255;
    private int B = 255;
    private int A = 150;

    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    private Image Stateimage = new ImageIcon("./image/offline.png").getImage();

    private boolean state = false;
    private String applicationname = "";
    private int tunnleport = 0;
    private int transmissionPort = 0;
    private String DomainName = "";
    private String Localaddress = "";
    private int delay = 0;
    private int ConnectionNumber = 0;

    private Rectangle ListControlRectangle = new Rectangle();

    public ListControl() {
        new MouseIntersect().start();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.ListControlRectangle.setLocation(x, y);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.ListControlRectangle.setSize(width, height);
    }

    public Rectangle getListControlRectangle() {
        return ListControlRectangle;
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

    public int getTransmissionPort() {
        return transmissionPort;
    }

    public String getApplicationname() {
        return applicationname;
    }

    public int getDelay() {
        return delay;
    }

    public String getLocaladdress() {
        return Localaddress;
    }

    public String getDomainName() {
        return DomainName;
    }

    public int getTunnleport() {
        return tunnleport;
    }

    public int getConnectionNumber() {
        return ConnectionNumber;
    }

    public void setApplicationname(String applicationname) {
        this.applicationname = applicationname;
    }

    public void setTunnleport(int tunnleport) {
        this.tunnleport = tunnleport;
    }

    public void setTransmissionPort(int transmissionPort) {
        this.transmissionPort = transmissionPort;
    }

    public void setDomainName(String domainName) {
        this.DomainName = domainName;
    }

    public void setLocaladdress(String localaddress) {
        this.Localaddress = localaddress;
    }

    public void setState(boolean state) {
        this.state = state;
        if(state) {
            Stateimage = new ImageIcon("./image/online.png").getImage();
        }else {
            Stateimage = new ImageIcon("./image/offline.png").getImage();
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setConnectionNumber(int connectionNumber) {
        ConnectionNumber = connectionNumber;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(this.R, this.G, this.B, this.A));
        g.fillRoundRect(this.x, this.y, this.width, this.height, 15, 15);
        if(DrawTunnleList.SelectionListControl == this) {
            g.setColor(new Color(205, 79, 183, 150));
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setStroke(new BasicStroke(3.0f));
            graphics2D.drawRoundRect(this.x - 1, this.y - 1, this.width + 1, this.height + 1, 15, 15);
        }

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
        graphics2D.drawImage(Stateimage, this.x + 4, this.y + 4, 22, 22, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        g.setColor(new Color(0, 0, 0, 120));

        Font font = new Font("Microsoft YaHei", Font.BOLD, 16);
        g.setFont(font);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        g.drawString(this.applicationname, this.x + ((this.width / 2) - (fontMetrics.stringWidth(this.applicationname) / 2)), this.y + (this.height / 6) + 5);

        font = new Font("Microsoft YaHei", Font.BOLD, 16);
        g.setFont(font);
        fontMetrics = FontDesignMetrics.getMetrics(font);
        g.drawString("外网地址", this.x + 4, this.y + (this.height / 2));

        font = new Font("Microsoft YaHei", Font.PLAIN, 16);
        g.setFont(font);
        fontMetrics = FontDesignMetrics.getMetrics(font);
        g.drawString(this.DomainName + ":" + this.tunnleport, this.x + (this.width - fontMetrics.stringWidth(this.DomainName + ":" + this.tunnleport)) - 4, this.y + (this.height / 2));

        font = new Font("Microsoft YaHei", Font.BOLD, 16);
        g.setFont(font);
        fontMetrics = FontDesignMetrics.getMetrics(font);
        g.drawString("内网地址", this.x + 4, this.y + (this.height - (this.height / 4)));

        font = new Font("Microsoft YaHei", Font.PLAIN, 16);
        g.setFont(font);
        fontMetrics = FontDesignMetrics.getMetrics(font);
        g.drawString(this.Localaddress, this.x + (this.width - fontMetrics.stringWidth(this.Localaddress)) - 4, this.y + (this.height - (this.height / 4)));
    }

    public void mouseEntered() {
        this.R = 255;
        this.G = 255;
        this.B = 255;
        this.A = 120;
    }

    public void  mouseOut() {
        this.R = 255;
        this.G = 255;
        this.B = 255;
        this.A = 150;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Main.MouseRectangle.intersects(this.ListControlRectangle)) {
            if(e.getButton() == 1) {
                DrawTunnleList.SelectionListControl = this;
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

    private class MouseIntersect extends Thread {
        /**
         *  判断鼠标是否在控件里面
         */

        @Override
        public void run() {

            boolean isOut = false;

            while(true) {
                try {
                    if(Main.MouseRectangle != null && ListControl.this.ListControlRectangle != null) {
                        if(Main.MouseRectangle.intersects(ListControl.this.ListControlRectangle)) {
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
