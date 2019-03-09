package win.mc10.Proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class DataExchangeThread extends Thread {

    private Socket InSocket = null;
    private Socket OutSocket = null;

    public DataExchangeThread(Socket InSocket, Socket OutSocket) {
        this.InSocket = InSocket;
        this.OutSocket = OutSocket;
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1024];
        int len = 0;

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = this.InSocket.getInputStream();
            outputStream = this.OutSocket.getOutputStream();

            while(true) {
                try {
                    if(len == -1) {
                        break;
                    }
                    len = inputStream.read(bytes, 0, bytes.length);
                    if(len > 0) {
                        outputStream.write(bytes, 0, len);
                    }
                } catch(SocketTimeoutException e) {
                    break;
                } catch (SocketException e) {
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.InSocket.close();
            outputStream.flush();
            this.OutSocket.close();

        } catch (SocketException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
