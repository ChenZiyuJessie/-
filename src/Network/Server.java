package Network;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class Server {  
    public static final int PORT = 13000;//port number
      
    public static void main(String[] args) {    
        System.out.println("Server start...\n");    
        Server server = new Server();    
        server.init();    
    }    
    
    public void init() {    
        try {    
            ServerSocket serverSocket = new ServerSocket(PORT);    
            while (true) {    
                // If there is a clog, the server and the client are connected   
                Socket client = serverSocket.accept();    
                // deal with the connect    
                new HandlerThread(client);    
            }    
        } catch (Exception e) {    
            System.out.println("Server exception: " + e.getMessage());    
        }    
    }    
    
    private class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }    
    
        public void run() {    
            try {    
                // read client data   
                DataInputStream input = new DataInputStream(socket.getInputStream());  
                String clientInputStr = input.readUTF();  
                // deal with client message    
                System.out.println("Client send message:" + clientInputStr);    
    
                // reply message to client   
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
                System.out.print("input:\t");    
                // send the first line of input   
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();    
                out.writeUTF(s);    
                  
                out.close();    
                input.close();    
            } catch (Exception e) {    
                System.out.println("Server run exception: " + e.getMessage());    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();    
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("Server finally exception:" + e.getMessage());    
                    }    
                }    
            }   
        }    
    }    
}    