import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args){
        ServerSocket ss;
        try{
            ss = new ServerSocket(8090);

            Socket socket = ss.accept();

            InputStream i = socket.getInputStream();
            OutputStream o = socket.getOutputStream();

            int d = i.read();

            d+=3;
            o.write(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
