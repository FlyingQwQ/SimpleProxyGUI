package win.mc10.Controls.Controls;

import win.mc10.Controls.Listener.CircularButtonListener;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class CircularButtonControl implements MouseListener {

    private String IconPath = "";

    private int X;
    private int Y;
    private int Width;
    private int Height;

    private List<CircularButtonListener> circularButtonControlList = new ArrayList<>();

    private Rectangle circularButtonRectangle = new Rectangle();

    public CircularButtonControl(int x, int y, int width, int height) {
        this.circularButtonRectangle.setBounds(x, y, width, height);
        this.X = x;
        this.Y = y;
        this.Width = width;
        this.Height = height;
    }

    public void setIcon(String path) {
        this.IconPath = path;
    }

    public void setLocation(int x, int y) {
        this.X = x;
        this.Y = y;
        this.circularButtonRectangle.setLocation(x, y);
    }

    public void setSize(int width, int height) {
        this.Width = width;
        this.Height = height;
        this.circularButtonRectangle.setSize(width, height);
    }

    public void addCircularButtonEvent(CircularButtonListener circularButtonListener) {
        circularButtonControlList.add(circularButtonListener);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 0, 50));
        g.fillOval(this.X, this.Y, this.Width, this.Height);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
        g.drawImage(new ImageIcon(this.IconPath).getImage(), this.X + ((this.Width / 2) - ((this.Height / 2) / 2)), this.Y + ((this.Width / 2) - ((this.Width / 2) / 2)), this.Height / 2, this.Width / 2, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Main.MouseRectangle != null) {
            if(Main.MouseRectangle.intersects(this.circularButtonRectangle)) {
                for(CircularButtonListener circularButtonListener : this.circularButtonControlList) {
                    circularButtonListener.mouseClicked(e);
                }
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
}