package win.mc10.Controls.Controls;

import sun.font.FontDesignMetrics;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class TextBoxControl implements KeyListener, MouseListener, MouseMotionListener {

    private Frame frame = null;
    private String Text = "";
    private boolean Password = false;
    private String ImagePath = "";
    private String remind = "";

    private int ImageWidth = 0;
    private int ImageHeight = 0;

    private long OldTime = 0;

    private boolean isTyping = false;
    private boolean DisPlayCursor = false;

    private int x;
    private int y;
    private int width;
    private int height;

    private Rectangle TextBoxControlRectangle = null;

    public TextBoxControl(int x, int y, int width, int height) {
        this.TextBoxControlRectangle = new Rectangle(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.CurrFocustextBoxControl == TextBoxControl.this) {
                    if(!isTyping) {
                        if(TextBoxControl.this.DisPlayCursor) {
                            TextBoxControl.this.DisPlayCursor = false;
                        }else {
                            TextBoxControl.this.DisPlayCursor = true;
                        }
                    }else {
                        if((System.currentTimeMillis() - TextBoxControl.this.OldTime) > 200) {
                            TextBoxControl.this.isTyping = false;
                        }
                    }
                }
            }
        }, 20, 500);
    }

    public void setIcon(String path) {
        this.ImagePath = path;
    }

    public void setIconSize(int width, int height) {
        this.ImageWidth = width;
        this.ImageHeight = height;
    }

    public void setSuperFrame(Frame frame) {
        this.frame = frame;
    }

    public void setPassword(boolean password) {
        this.Password = password;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getText() {
        return this.Text;
    }

    public void paint(Graphics g) {
        if(Main.CurrFocustextBoxControl == this) {
            g.setColor(new Color(0, 0, 0, 30));
        }else {
            g.setColor(new Color(0, 0, 0, 50));
        }

        g.fillRoundRect(this.x, this.y, this.width, this.height, 15, 15);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
        int Imagewidth = (this.ImageWidth != 0) ? this.ImageWidth : 35;
        int Imageheight = (this.ImageHeight != 0) ? this.ImageHeight : 39;
        graphics2D.drawImage(new ImageIcon(this.ImagePath).getImage(), this.x + ((300 / 10) - (Imagewidth / 2)), this.y + ((50 / 2) - (Imageheight / 2)), Imagewidth, Imageheight, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        g.setColor(new Color(0, 0, 0, 100));
        Font font = new Font("Microsoft YaHei", Font.BOLD, 18);
        g.setFont(font);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        if(this.Password) {
            String PassText = "";
            for(int i = 0; i < this.Text.length(); i++) {
                PassText += "*";
            }
            g.drawString(PassText, this.x + (this.width / 5), this.y + (this.height / 2) + (fontMetrics.getHeight() / 2 / 2));
            ((Graphics2D) g).setStroke(new BasicStroke(2.0f));
            if(this.DisPlayCursor) {
                g.drawLine(this.x + (this.width / 5) + fontMetrics.stringWidth(PassText) + 2, this.y + 15, this.x + (this.width / 5) + fontMetrics.stringWidth(PassText) + 2, this.y + (this.height / 2) + 10);
            }
        }else {
            g.drawString(Text, this.x + (this.width / 5), this.y + (this.height / 2) + (fontMetrics.getHeight() / 2 / 2));
            ((Graphics2D) g).setStroke(new BasicStroke(2.0f));
            if(this.DisPlayCursor) {
                g.drawLine(this.x + (this.width / 5) + fontMetrics.stringWidth(this.Text) + 2, this.y + 15, this.x + (this.width / 5) + fontMetrics.stringWidth(this.Text) + 2, this.y + (this.height / 2) + 10);
            }
        }

        if(Main.CurrFocustextBoxControl != this) {
            if(this.Text.equals("")) {
                font = new Font("Microsoft YaHei", Font.BOLD, 16);
                g.setFont(font);
                g.drawString(this.remind, this.x + (this.width / 5), this.y + (this.height / 2) + 7);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if((System.currentTimeMillis() - this.OldTime) < 1000) {
            this.isTyping = true;
            this.DisPlayCursor = true;
        }

        if(("" + e.getKeyChar()).equals("\b")) {
            String newText = "";
            for(int i = 0; i < this.Text.length(); i++) {
                if((newText.length() + 1) < this.Text.length()) {
                    newText += this.Text.charAt(i);
                }
            }
            this.Text = newText;
        }else {
            if((System.currentTimeMillis() - this.OldTime) < 1000) {
                this.isTyping = true;
                this.DisPlayCursor = true;
            }
            if(this.Text.length() < 19) {
                this.Text += e.getKeyChar();
            }
        }

        this.OldTime = System.currentTimeMillis();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Main.MouseRectangle.intersects(this.TextBoxControlRectangle)) {
            if(Main.CurrFocustextBoxControl != null) {
                this.frame.removeKeyListener(Main.CurrFocustextBoxControl);
            }
            Main.CurrFocustextBoxControl = this;
            this.frame.addKeyListener(this);
            this.DisPlayCursor = true;
        }else {
            if(Main.CurrFocustextBoxControl == this) {
                this.frame.removeKeyListener(Main.CurrFocustextBoxControl);
                Main.CurrFocustextBoxControl = null;
            }
            this.DisPlayCursor = false;
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
