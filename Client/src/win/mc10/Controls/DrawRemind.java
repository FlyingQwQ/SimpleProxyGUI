package win.mc10.Controls;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import win.mc10.Controls.Controls.RemindControl;
import win.mc10.Controls.Enum.Remind;

public class DrawRemind {

    private static Frame frame = null;
    public static List<RemindControl> remindControlList = new ArrayList<>();

    public DrawRemind() {

    }

    public static void addRemind(String Title, String Content, Remind remind) {
        RemindControl remindControl = new RemindControl(Title, Content, remind);
        remindControl.setSuperFrame(frame);
        remindControlList.add(remindControl);
        ControlEvent(frame);
    }

    public void paint(Graphics g) {
        for(int i = 0; i < remindControlList.size(); i++) {
            if(i < 1) {
                remindControlList.get(i).setLocation(this.frame.getWidth() - 200 - 2, this.frame.getHeight() - 100 - 2);
            }else {
                remindControlList.get(i).setLocation(this.frame.getWidth() - 200 - 2, remindControlList.get(i - 1).y - remindControlList.get(i).height - 4);
            }
            remindControlList.get(i).setSize(200, 100);
            remindControlList.get(i).paint(g);
        }
    }

    public static void ControlEvent(Frame framen) {
        frame = framen;
        for(RemindControl remindControl : remindControlList) {
            framen.removeMouseListener(remindControl);
        }
        for(RemindControl remindControl : remindControlList) {
            framen.addMouseListener(remindControl);
        }
    }
}
