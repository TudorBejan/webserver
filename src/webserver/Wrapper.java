package webserver;

public class Wrapper implements Runnable{

	private WebServer webserver;
	
	public Wrapper(WebServer wb)
	{
		this.webserver=wb;
	}
	
	public void run(){
		webserver.toStart();
	}
	
}
