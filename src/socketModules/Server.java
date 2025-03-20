package socketModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import exercises.Exercise1;
import exercises.Exercise2;
import exercises.Exercise3;
import exercises.Exercise4;
import exercises.Exercise5;
import exercises.Exercise6;

public class Server {
	public static String PrintMenu() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nDANH SÁCH BÀI TẬP:\n");
		sb.append("1. Kiểm tra số nguyên tố, chính phương, hoàn hảo, amstrong.\n");
		sb.append("2. Tính tổng và tích các chữ số của số nguyên dương.\n");
		sb.append("3. Tìm ước chung lớn nhất và bội chung nhỏ nhất của hai số nguyên dương.\n");
		sb.append("4. Nhập và in chuỗi đảo ngược của một chuỗi ký tự.\n");
		sb.append("5. Xử lý chuỗi ký tự (đảo ngược, chữ hoa, chữ thường, đổi chữ, đếm từ, tìm nguyên âm).\n");
		sb.append("6. Xử lý chuỗi ký tự (in từng từ, đếm tần số ký tự).\n");
		sb.append("0. Thoát chương trình.\n");
		sb.append("--> Nhập lựa chọn của bạn: ");
		return sb.toString();
	}

	public static void inLenhBaiTap(int choice, DataOutputStream dos) {
		switch (choice) {
		case 1:
			new Exercise1(dos);
			break;
		case 2:
			new Exercise2(dos);
			break;
		case 3:
			new Exercise3(dos);
			break;
		case 4:
			new Exercise4(dos);
			break;
		case 5:
			new Exercise5(dos);
			break;
		case 6:
			new Exercise6(dos);
			break;
		case 0:
			System.out.println("Thoat chuong trinh...\n");
			break;
		default:
			System.out.println("Lựa chọn không hợp lệ!\n");
		}
	}

	public static String ThucHienBaiTap(int choice, String ... value) {
		String result = "If this one doesn't change, sth wrong";
		switch (choice) {
		case 1:
			result = new Exercise1().solve(Integer.parseInt(value[0]));
			break;
		case 2:
			result = new Exercise2().solve(Integer.parseInt(value[0]));
			break;
		case 3:
			result = new Exercise3().solve(Integer.parseInt(value[0]), Integer.parseInt(value[1]));
			break;
		case 4:
			result = new Exercise4().solve(value[0]);
			break;
		case 5:
			result = new Exercise5().solve(value[0]);
			break;
		case 6:
			result = new Exercise6().solve(value[0]);
			break;
		case 0:
			System.out.println("Thoat chuong trinh...\n");
			break;
		default:
			System.out.println(choice);
			System.out.println("Lựa chọn không hợp lệ!\n");
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			String menu = "";
			ServerSocket server = new ServerSocket(2025);
			System.out.println("Server has started!...");
			Socket client = server.accept();
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			int choice = -1;
			while (true) {
				menu = PrintMenu();
				// Send menu to client
				dos.writeUTF(menu);
				dos.flush();
				// Read choice from client
				choice = dis.readInt();
				inLenhBaiTap(choice, dos);
				// Read input here
				String input = dis.readUTF();
				String input2 = "";
				if (choice == 3) {
					input2 = dis.readUTF();
				}
				String result = ThucHienBaiTap(choice, input, input2);
				dos.writeUTF(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
