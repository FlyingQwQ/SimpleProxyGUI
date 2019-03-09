package win.mc10;

import java.util.Date;

public class Log {

    private static Date date = null;

    public static void INFO(String str) {
        date = new Date();
        System.out.println("[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " INFO]: " + str);
    }

    public static void WARNING(String str) {
        date = new Date();
        System.out.println("[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " WARNING]: " + str);
    }

    public static void ERROR(String str) {
        date = new Date();
        System.out.println("[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " ERROR]: " + str);
    }
}
