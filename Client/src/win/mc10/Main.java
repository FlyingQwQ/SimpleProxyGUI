package win.mc10;

import win.mc10.Controls.*;
import win.mc10.Controls.Controls.TextBoxControl;
import win.mc10.Controls.Listener.displayInterface;
import win.mc10.proxy.Proxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Main extends Frame {
    public static Frame frame = null;

    public static Proxy proxy = new Proxy();      //代理

    public static int MouseEventType = 0;

    Point point = new Point();
    private Image image = null;
    public boolean init = false;
    private static int initAnimationTransparentNumber = 255;
    private UpdateThread updateThread = null;

    public static DrawControl drawControl = new DrawControl();        //绘制基本边框
    public static DrawLoginInterface drawLoginInterface = new DrawLoginInterface();       //绘制登录界面
    public static DrawRegisterInterface drawRegisterInterface = new DrawRegisterInterface();     //绘制注册界面
    public static DrawRemind drawRemind = new DrawRemind();       //绘制提醒
    public static DrawHome drawHome = new DrawHome();     //绘制主页面
    public static DrawTunnleList drawTunnleList = new DrawTunnleList();     //绘制隧道列表界面
    public static DrawTunnleRegister drawTunnleRegister = new DrawTunnleRegister();      //绘制隧道申请界面
    public static DrawState drawState = new DrawState();      //绘制状态界面
    public static DrawSetting drawSetting = new DrawSetting();      //绘制设置界面

    public static TextBoxControl CurrFocustextBoxControl = null;
    public static displayInterface DisPlayInterface = drawLoginInterface;

    public static Rectangle MouseRectangle = null;

    private int LogoYMove = this.getWidth() - 4;     //开始动画Logo栏移动
    private int LeftXMove = -10;
    private int RightXMove = 170;

    private int StartFont = 0;

    public Main() {
        this.setSize(1000, 600);
        this.setTitle("SimpleCT");
        this.setBackground(new Color(60, 63, 65));
        this.setIconImage(new ImageIcon("./image/icon.png").getImage());
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.frame = this;

        this.updateThread = new UpdateThread();
        new Thread(updateThread).start();

        //  添加控件事件
        drawControl.ControlEvent(this);
        drawLoginInterface.ControlEvent(this);
        drawRemind.ControlEvent(this);
        drawHome.ControlEvent(this);
        drawTunnleList.ControlEvent(this);
        drawRegisterInterface.ControlEvent(this);
        drawTunnleRegister.ControlEvent(this);
        drawState.ControlEvent(this);
        drawSetting.ControlEvent(this);

        proxy.ConnectProxtCommunicationServer();    //连接到代理

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MouseEventType = e.getButton();
                if(MouseEventType == MouseEvent.BUTTON1) {
                    point.x = e.getX();
                    point.y = e.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MouseEventType = 0;
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(MouseEventType == MouseEvent.BUTTON1) {
                    Point po = Main.this.getLocation();
                    setLocation(po.x + e.getX() - point.x, po.y + e.getY() - point.y);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(MouseRectangle == null) {
                    MouseRectangle = new Rectangle(e.getX(), e.getY(), 1, 1);
                }else {
                    MouseRectangle.setLocation(e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //  壁纸
        g.drawImage(new ImageIcon("./image/wallpaper.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

        if((initAnimationTransparentNumber - 1) > 80) {
            g.setColor(new Color(60, 63, 65, initAnimationTransparentNumber--));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }else {
            this.updateThread.ThreadSleepNumber = 15;
            init = true;
        }

        //  窗口边框线条颜色
        g.setColor(new Color(205, 79, 183, 150));
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

        if(init) {
            //  Logo
            g.setColor(new Color(43, 43, 43, 150));
            if(this.LogoYMove < 2) {
                this.LogoYMove++;
            }
            g.fillRect(2, this.LogoYMove, this.getWidth() - 4, 80);
            g.drawImage(new ImageIcon("./image/Logo.png").getImage(), 10, 20, 216, 49, null);

            //  左边框
            g.setColor(new Color(60, 63, 65, 150));
            if(this.LeftXMove < 2) {
                this.LeftXMove++;
            }
            g.fillRect(this.LeftXMove, 84, 155, this.getHeight() - 86);

            //  右边框
            g.setColor(new Color(60, 63, 65, 150));
            if(this.RightXMove > 159) {
                this.RightXMove--;
            }
            g.fillRect(this.RightXMove, 84, this.getWidth() - 161, this.getHeight() - 86);


            this.drawControl.paint(g);
            if(DisPlayInterface != null) {
                DisPlayInterface.paint(g);
            }
            this.drawRemind.paint(g);
        }else {
            g.setColor(new Color(60, 63, 65, 190));
            int X = (this.getWidth() / 2) - (400 / 2);
            int Y = (this.getHeight() / 2) - (150 / 2);
            g.fillRect(X, Y, 400, 150);

            if(this.StartFont < 150) {
                this.StartFont++;
            }
            g.setColor(new Color(84, 255, 159, this.StartFont));
            g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
            g.drawString("©2019 Simple V1.0.0", X + (X / 2) - 32, Y + (Y / 2) - 32);
        }
    }

    @Override
    public void update(Graphics g) {
        this.image = this.createImage(this.getWidth(), this.getHeight());
        this.paint(this.image.getGraphics());
        g.drawImage(this.image, 0, 0, null);
    }

    private class UpdateThread implements Runnable {

        public int ThreadSleepNumber = 0;

        @Override
        public void run() {
            while(true) {
                Main.this.repaint();
                Main.this.paint(new BufferedImage(Main.this.getWidth(), Main.this.getHeight(), BufferedImage.TYPE_INT_BGR).getGraphics());
                try {
                    Thread.sleep(ThreadSleepNumber);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("ClientVersion: " + GlobalVariable.ClientVersion);
        new Main();
    }
}
