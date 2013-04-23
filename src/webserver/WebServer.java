package webserver;

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;

public class WebServer extends Thread {
	
	protected Socket clientSocket;
	private static String root="C:\\MyServer";
	private static String error="\\Error\\404.htm";
	private static String maintenance="C:\\MyServer\\Maintenance";
	private static String maintenance_file="\\503.htm";
	private static String serverAddr="127.0.0.1";
	private static int backlog=1000;
	private static int port=8080;
	private static boolean isMaintenanced=false;
	
	private enum DEFAULTS {
		   DEFAULT1("index.html"), DEFAULT2("default.html"),DEFAULT3("index.htm");
		   private final String stringValue;
		   private DEFAULTS(final String s) { stringValue = s; }
		   public String toString() { return stringValue; }
		   // further methods, attributes, etc.
		}

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			InetAddress bindAddr = InetAddress.getByName(serverAddr);
			serverSocket = new ServerSocket(port,backlog,bindAddr);
			System.out.println("Connection Socket Created");
			try {
				while (true) {
					//System.out.println("Waiting for Connection");
					new WebServer(serverSocket.accept());
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: "+port);
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: "+port);
				System.exit(1);
			}
		}
	}

	private WebServer(Socket clientSoc) {
		clientSocket = clientSoc;
		start();
	}
	
	public WebServer(){
		
	}
	
	public void toStart(){
		ServerSocket serverSocket = null;

		try {
			InetAddress bindAddr = InetAddress.getByName(serverAddr);
			serverSocket = new ServerSocket(port,backlog,bindAddr);
			System.out.println("Connection Socket Created");
			try {
				while (true) {
					//System.out.println("Waiting for Connection");
					new WebServer(serverSocket.accept());
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: "+port);
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: "+port);
				System.exit(1);
			}
		}
	}

	public void run() {
		
		String request = null;
		File fileRequest;
	
		//System.out.println("New Communication Thread Started");
		try {
			PrintStream out = new PrintStream(clientSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String inputLine;
			String inputLine2;
			inputLine = in.readLine();
			System.out.println("CE PRIMESC: "+inputLine);
			/*while((inputLine2=in.readLine())!=null)
			{
				System.out.println("CE PRIMESC: "+inputLine2);
			}*/
			
			try
			{
				if(inputLine==null) //daca requestul e null va returna 404
					throw new FileNotFoundException();
						
				//System.out.println("Server: " + inputLine);
				
				 //SOURCE:http://cs.fit.edu/~mmahoney/cse3103/java/Webserver.java
			    StringTokenizer st=new StringTokenizer(inputLine);
					
				// Parse the filename from the GET command  //SOURCE:http://cs.fit.edu/~mmahoney/cse3103/java/Webserver.java
		        if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements())
		        	request=st.nextToken();
		        else
		        	throw new FileNotFoundException();  // Bad request
	
				request=request.replace("/", "\\");      // inlocuiesc "/" cu "\" (pt sisteme windows)
				request=request.replace("%20"," ");
				//request=request.replace(" ","");
				
				// Check for illegal characters to prevent access to superdirectories SOURCE:http://cs.fit.edu/~mmahoney/cse3103/java/Webserver.java
		        if (request.indexOf("..")>=0 || request.indexOf(':')>=0 || request.indexOf('|')>=0)
		        	throw new FileNotFoundException();
		        
		        if(request.endsWith("404.jpg")) // that's just for fun :)
		        	request="\\Error\\404.jpg";
		          
				if(request.endsWith("\\")) //se cere pagina default
				{
					System.out.println("se cere default");
					String intialRequest=request;
					for(DEFAULTS d:DEFAULTS.values()) //iterez prin enum pt a cauta dupa pagini default
					{
						request=request+d.toString();
						fileRequest=new File(root+request);
						//System.out.println("CE CONSTRUESC: "+ root+request);
						if(fileRequest.exists())
						{
							break;
						}
						request=intialRequest;
					}
				}
				
				
				InputStream f;
				if(isMaintenanced)
				{
					if(request.endsWith("503.jpg")) // that's just for fun :)
						f=new FileInputStream(maintenance+"\\503.jpg");
					else
						f=new FileInputStream(maintenance+maintenance_file);
				}
				else
					f=new FileInputStream(root+request);
							
				System.out.println("CE A AJUNS REQUESTU: "+request);
		        //InputStream f=new FileInputStream(root+request);
		        
		        // Send file contents to client, then close the connection SOURCE:http://cs.fit.edu/~mmahoney/cse3103/java/Webserver.java
		        byte[] a=new byte[4096];
		        int n;
		        while ((n=f.read(a))>0) 
		          out.write(a, 0, n);
		        System.out.println("AM TRIMIS: "+root+request);
				out.close();
				in.close();
				f.close();
				clientSocket.close();
			}
			
			catch (FileNotFoundException x)  //trimit pagina 404
			{
				try
				{
					System.out.println("NU S-A GASIT PAGINA: "+request);
					InputStream f=new FileInputStream(root+error);
					request=error;
					byte[] a=new byte[4096];
			        int n;
			        while ((n=f.read(a))>0)  
			        	out.write(a, 0, n);
			        out.close();
			        f.close();
			        in.close();
			        return;
				}
				catch(FileNotFoundException ex)
				{
					System.out.println("ERROR AT ERROR");
				}
				catch(IOException ex2)
				{
					System.out.println("ERROR AT IO ERROR");
				}
		    }
		}
		catch (IOException e)
		{
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public static String getMaintenance() {
		return maintenance;
	}

	public static void setMaintenance(String maintenance) {
		WebServer.maintenance = maintenance;
	}

	public static String getRoot() {
		return root;
	}

	public static void setRoot(String root) {
		WebServer.root = root;
	}
	
	public static String getPort() {
		return String.valueOf(port);
	}


	public static void setPort(String port) {
		WebServer.port = Integer.parseInt(port);
	}

	public static void setMaintenanced(boolean isMaintenanced) {
		WebServer.isMaintenanced = isMaintenanced;
	}

	public static String getServerAddr() {
		return serverAddr;
	}

	public static void setServerAddr(String serverAddr) {
		WebServer.serverAddr = serverAddr;
	}

	public static void setBacklog(int backlog) {
		WebServer.backlog = backlog;
	}

	
}