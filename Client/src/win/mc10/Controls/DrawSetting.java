package win.mc10.Controls;

import win.mc10.Controls.Listener.displayInterface;
import win.mc10.Main;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class DrawSetting implements displayInterface {

    private boolean inint = true;

    public DrawSetting() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(Main.DisPlayInterface == DrawSetting.this) {
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

    public void ControlEvent(Frame frame) {

    }

    public void removeEvent(Frame frame) {

    }

    @Override
    public void paint(Graphics g) {

    }
}
