package socketModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestTuan {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner sc = new Scanner(System.in);
        Socket socket = new Socket("192.168.2.64", 2025);
        DataInputStream din = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Boolean stop = false;
        while (!stop){
            String respond = sc.nextLine();
            System.out.println("===========================================================");
            dos.writeUTF(respond);
            String server_result = din.readUTF();
            System.out.println(server_result);
            dos.flush();
        }
        socket.close();
    }
}