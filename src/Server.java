import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
	BufferedReader br = null;		
	PrintWriter pw = null;
	
	HashMap<String , String> map = new HashMap<String , String>();	
	
	public static void main(String[] args)
	{
		Server s = new Server();
		s.go();
	}
	
	public void go()
	{
		regiesterUserInfo();
		try {
			ServerSocket serverSocket = new ServerSocket(5555);				
			
			while(true) {
				Socket socket = serverSocket.accept();
				MyRunnable threadJob = new MyRunnable(socket);
				Thread myThread = new Thread(threadJob);				
				myThread.start();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
	private void regiesterUserInfo()
	{
		map.put("test1", "1234");
		map.put("test2", "5678");
		map.put("test3", "9012");
		map.put("test4", "3456");
		map.put("test5", "7890");
	}
	
	class MyRunnable implements Runnable {
		BufferedReader br = null;		
		PrintWriter pw = null;
		Socket socket;
		GridSpace g = new GridSpace();
		
		public MyRunnable(Socket s) {
			socket = s;
			g.set();
		}
		
		public void run() {			
			go();			
		}
		
		public void go() {
			
			try {
				System.out.println(socket.getInetAddress().getHostAddress() + "/" + socket.getPort()  + " 님이 접속했습니다.");
				pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);				
								
				boolean c = stream();				
				
				while(c) {
					c = stream();			
				}				
				
				br.close();
				pw.close();				
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean stream()
		{
			boolean c = true;
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String receiveMsg = br.readLine();
				c = processData(receiveMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return c;
		}
		
		public boolean processData(String data)
		{
			System.out.println(data);
			String[] r = data.split(":");
			switch(r[0]) {
			case "LOGIN_REQ":
				System.out.println("??");
				if(map.containsKey(r[1]) && map.get(r[1]).equals(r[2])) {
					pw.println("LOGIN_RESP:OK:" + g.set() + "\r\n");
					pw.flush();
					g.print();
				} else {
					pw.println("LOGIN_RESP:NO\r\n");
					pw.flush();
				}
				return true;
			case "MOVE_REQ":
				String[] n = r[1].split(",");			
				int x = Integer.parseInt(n[0]);
				int y = Integer.parseInt(n[1]);
				pw.println("MOVE_REQ:"+g.DFS(x, y) + "\r\n");
				pw.flush();
				System.out.println("9: 탑색경로, 7:최단경로");
				g.print();
				g.reset();
				System.out.println("이동 후 위치");
				g.print();
				
				return true;
			case "LOGOUT_REQ":
				pw.println("LOGOUT_REQ\r\n");
				pw.flush();
				return false;
				
			}
			return true;
		}
	}
}
