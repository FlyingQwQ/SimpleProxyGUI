package win.mc10.Controls;

import win.mc10.Controls.Controls.FuntionControl;
import win.mc10.Controls.Listener.FuntionControlListener;
import win.mc10.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawControl {

    private Frame frame = null;

    public static FuntionControl CurrFuntionControl = null;

    public static FuntionControl funtionControl1 = new FuntionControl(5, 87, 149, 70, " 首 页 ");
    public static  FuntionControl funtionControl2 = new FuntionControl(5, 160, 149, 70, " 列 表 ");
    public static  FuntionControl funtionControl3 = new FuntionControl(5, 306, 149, 70, " 设 置 ");
    public static FuntionControl funtionControl4 = new FuntionControl(5, 233, 149, 70, " 状 态 ");

    private FuntionControl funtionCloseContorl = new FuntionControl(935, 0, 65, 35, "");
    private FuntionControl funtionMinimumContorl = new FuntionControl(870, 0, 65, 35, "");

    public DrawControl() {
        funtionControl1.setIcon("./image/home.png");
        funtionControl2.setIcon("./image/list.png");
        funtionControl3.setIcon("./image/setting.png");
        funtionControl4.setIcon("./image/state.png");

        funtionControl2.setProhibit(true);
        funtionControl4.setProhibit(true);

        funtionCloseContorl.setIcon("./image/close.png", 16, 16);
        funtionCloseContorl.setAnimation(false);

        funtionMinimumContorl.setIcon("./image/minimum.png", 20, 3);
        funtionMinimumContorl.setAnimation(false);

        this.ControlListenerEvent();
    }

    //  通过窗口的事件打造控件事件
    public void ControlEvent(Frame frame) {
        this.frame = frame;

        frame.addMouseListener(this.funtionControl1);
        frame.addMouseListener(this.funtionControl2);
        frame.addMouseListener(this.funtionControl3);
        frame.addMouseListener(this.funtionControl4);
        frame.addMouseListener(this.funtionCloseContorl);
        frame.addMouseListener(this.funtionMinimumContorl);

        frame.addMouseMotionListener(this.funtionControl1);
        frame.addMouseMotionListener(this.funtionControl2);
        frame.addMouseMotionListener(this.funtionControl3);
        frame.addMouseMotionListener(this.funtionControl4);
        frame.addMouseMotionListener(this.funtionCloseContorl);
        frame.addMouseMotionListener(this.funtionMinimumContorl);
    }

    //  绘画控件
    public void paint(Graphics g) {
        this.funtionControl1.paint(g);
        this.funtionControl2.paint(g);
        this.funtionControl3.paint(g);
        this.funtionControl4.paint(g);

        this.funtionCloseContorl.paint(g);
        this.funtionMinimumContorl.paint(g);
    }

    //  控件的事件
    public void ControlListenerEvent() {
        funtionControl1.addFuntionControlEvent(new FuntionControlListener() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Main.DisPlayInterface = Main.drawHome;
            }
        });

        funtionControl2.addFuntionControlEvent(new FuntionControlListener() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Main.DisPlayInterface = Main.drawTunnleList;
            }
        });

        funtionControl3.addFuntionControlEvent(new FuntionControlListener() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("type",  "gettunnlelists");
//                Main.proxy.sendMessage(jsonObject.toString());

                Main.DisPlayInterface = Main.drawSetting;
            }
        });

        funtionControl4.addFuntionControlEvent(new FuntionControlListener() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Main.DisPlayInterface = Main.drawState;
            }
        });

        funtionCloseContorl.addFuntionControlEvent(new FuntionControlListener() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });

        funtionMinimumContorl.addFuntionControlEvent(new FuntionControlListener() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                frame.setExtendedState(JFrame.ICONIFIED);
            }
        });
    }

}
