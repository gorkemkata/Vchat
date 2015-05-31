package Vchat;

import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;

public class WebcamHandler {
	
	private Webcam webcam;
	
	public WebcamHandler()
	{
	  webcam = Webcam.getDefault(); // Sisteme bagli varsayilan kamera aygiti secildi.
	}
	
	public BufferedImage getCurrentFrame(){
		if(!webcam.isOpen())
		webcam.open();   // Webcam Acilir. 
		return webcam.getImage(); // O siradaki frame bufferedimage olarak donduruluyor.
	}
}
