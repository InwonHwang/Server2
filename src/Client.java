import java.io.*;
import java.net.*;

public class Client {
	
	BufferedReader input = null;	
	BufferedReader br = null;
	PrintWriter pw = null;

	public static void main(String[] args) {
		Client c = new Client();
		c.go();	
	}
	
	public void go() {
		try {

			Socket socket = new Socket("127.0.0.1", 5555);
			input = new BufferedReader(new InputStreamReader(System.in)); //Ű����� �Է¹���.
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true); // �Է��� ���� ����
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			
			System.out.print("ID:PASSWORD �Է� [ex)test:1111] ");
			String sendMsg = input.readLine();
			pw.println("LOGIN_REQ:" + sendMsg + "\r\n");
			pw.flush();
			System.out.println(br.readLine());
			
			String recvMsg = "dummy";
			if(sendMsg.contains("NO")) recvMsg = ""; 
			
			while(recvMsg.length() != 0) {
				System.out.print("��ǥ �Է� [ex)x,y] (-1 �Է½� �α׾ƿ��մϴ�.): ");
				sendMsg = input.readLine();
				if(sendMsg.contains("-1"))
					pw.println("LOGOUT_REQ\r\n");	
				else 
					pw.println("MOVE_REQ:" +sendMsg + "\r\n");
				pw.flush();
				
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));				
				recvMsg = br.readLine();
				System.out.println(recvMsg);
				if(recvMsg.contains("LOGOUT_REQ") == true)
					break;				
			}

			pw.close();
			br.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
