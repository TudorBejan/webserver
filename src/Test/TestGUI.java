package Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import GUI.GUI;

import webserver.WebServer;

import static org.mockito.Mockito.*;

public class TestGUI {

	GUI gui= new GUI();
	
	@Test
	public void TestMain(){
		GUI.main(null);
	}
	
	/*
	@Test
	public void TestIsStopped(){
		assertEquals(false,);
	}
	*/
}
