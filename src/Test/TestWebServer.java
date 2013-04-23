package Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
import org.mockito.Mockito;

import webserver.WebServer;

import static org.mockito.Mockito.*;

public class TestWebServer {

	WebServer wb =new WebServer();
	
	@Test
	public void testSetBacklog() {
		wb.setBacklog(10);
	}
	
	@Test
	public void testSetServerAdd() {
		wb.setServerAddr("127.0.0.1");
	}
	
	@Test
	public void testGetServerAddrd() {
		assertEquals("127.0.0.1",wb.getServerAddr());
	}
	
	@Test
	public void testSetMaintenanced() {
		wb.setMaintenanced(true);
	}
	
	@Test
	public void testSetPort() {
		wb.setPort("8080");
	}
	
	@Test
	public void testGetPort() {
		assertEquals("8080", wb.getPort()); 
	}
	
	@Test
	public void testSetRoot() {
		wb.setRoot("C:\\MyServer");
	}
	
	@Test
	public void testGetRoot() {
		assertEquals("C:\\MyServer", wb.getRoot());
	}
	
	@Test
	public void testSetMaintenance() {
		wb.setMaintenance("C:\\MyServer\\Maintenance");
	}
	
	@Test
	public void testGetMaintenance() {
		assertEquals("C:\\MyServer\\Maintenance", wb.getMaintenance());
	}
	
	@Test
	(expected=NullPointerException.class)
	public void testRun() {
		wb.run();
		wb.interrupt();
	}
	
	@Test
	//(expected=NullPointerException.class)
	public void testRun2() {
		wb.setClientSocket(new Socket());
		wb.run();
		wb.interrupt();
	}
	
	@Test
	public void testToStart()
	{
		wb.toStart();
		//assertEquals("RUNNABLE", wb.getState().toString());
		//wb.interrupt();
	}

	@Test
	public void testMain() throws IOException
	{
		//WebServer wb2=mock(WebServer.class);
		//verify(wb2,times(1)).main(null);
		//WebServer.main(null);
		//WebServer.interrupt();
		//assertEquals("RUNNABLE", wb.getState().toString());
	}
	
	
	 
	

}
