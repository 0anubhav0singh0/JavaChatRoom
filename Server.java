
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {
	//  A server socket waits forrequests to come in over the network. It performs some operationbased on that request, and then possibly returns a result to the requester.
	ServerSocket server;
	
	//  A socket is an endpoint for communicationbetween two machines. 
	Socket socket;
	// to read
	BufferedReader br;

	// to write
	PrintWriter out;
	
	public Server() {
		try {
			server = new ServerSocket(7777);
			System.out.println("server is ready to accept connection");
			System.out.println("waiting...");
			socket = server.accept();
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			startReading();
			startWriting();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startReading() {
		// thread - read data (read karke deta rahega)
		
		Runnable r1 = ()->{
			System.out.println("reader started...");
			
			while(true) {
				try {
					String msg = br.readLine();
					
					if(msg.equals("exit")) {
						System.out.println("Client terminated the chat");
						break;
					}
					
					System.out.println("Client : " + msg);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		new Thread(r1).start();
	}
	
	public void startWriting() {
		// thread - take data from the user and send it to the client
		
		Runnable r2 = ()->{
			System.out.println("writer started...");
			while(true) {
				try {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();
					
					out.println(content);
					out.flush();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r2).start();		
	}
	
	public static void main(String[] args) {
		System.out.println();
		new Server();
	}
}
