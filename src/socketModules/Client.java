package socketModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Socket client;
		String menu = "";
		String ex_prompt = "";
		String input, input2;
		try {
//			 client = new Socket("localhost", 2025);
			// String host = "pthserverclientmath-production.up.railway.app";
			// int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
			// client = new Socket(host, port);
			client = new Socket("hopper.proxy.rlwy.net", 32418);
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			Scanner sc = new Scanner(System.in);
			int chonBaiTap = -1;
			while (true) {
				try {
					// Read menu from server
					menu = dis.readUTF();
					System.out.println(menu);
					// Input choice
					chonBaiTap = sc.nextInt();
					// Prevent "" error
					sc.nextLine();
					dos.writeInt(chonBaiTap);
					// Get ex prompt
					ex_prompt = dis.readUTF();
					System.out.println(ex_prompt);
					// Insert answer accordingly
					input = sc.nextLine();
					dos.writeUTF(input);
					// Since ex3 requires 2 args
					if (chonBaiTap == 3) {
						input2 = sc.nextLine();
						dos.writeUTF(input2);
					}
					if (chonBaiTap == 0) {
					    System.out.println("Exiting client...");
					    client.close();
					    System.exit(0);
					    break;
					}
					// get server response
					String response = dis.readUTF();
					System.out.println(response);

				} catch (Exception e) {
					System.out.println("End program client!");
//					e.printStackTrace();
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
