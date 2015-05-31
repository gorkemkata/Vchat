package Vchat;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class VideoReceiver extends Thread {
	// Bu class karsi taraftan gelen video frame lerini dinler.
	private ServerSocket serverSocket;
	private DataInputStream dis;
	public VideoReceiver()
	{
		this.start();
	}

	public void  run()
	{
	try {
		serverSocket = new ServerSocket(815); // video icin PORT 815 dinleniyor.
	} catch (IOException e) {
		e.printStackTrace();
	}	
	while(true)
	  {
		
		 try {
			 Socket clientSocket = serverSocket.accept(); // baglanti bekleniyor
			 InputStream inputStream = clientSocket.getInputStream();
			 dis = new DataInputStream(inputStream); 
			 int imageSize = dis.readInt(); // * baglantiya yazilmis dosya boyutu okunuyor. Dosya gonderilirken 
			                                // * bir Integer in kaplayacagi alan kadar alana dosya boyutu yazdirildi.
			                                // * O kadarlik alani okuyup o bilgiyi integer a ceviriyoruz. Bu da dosya boyutudur.
			 byte [] imageByte = new byte[imageSize]; // dosya boyutu buyuklugunde bir byte array olusturulur.
			 dis.readFully(imageByte); // input stream den bu byte array i dolduracak kadar byte okunur.
		     BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByte)); // bu byte array den byte lar okunup image e cevirilir.
				// Bu kisim resmin cozunurlugunu artirmak icin. Kalitesi dusuyor.
			    AffineTransform trans = new AffineTransform();
			    trans.scale(2, 2); // cozunurluk 2 katina cikiyor
			    int resizedWidth = (int) (image.getWidth() * 2);
			    int resizedHeight = (int) (image.getHeight() * 2);
			    BufferedImage resizedImage = new BufferedImage(resizedWidth ,resizedHeight , BufferedImage.TYPE_3BYTE_BGR);
			    Graphics2D g = resizedImage.createGraphics();
			    g.drawImage(image, trans, null);
			    g.dispose();
		     MainLoop.getVideoDisplayer().showFrame(resizedImage); // bu image ana pencerede gosterilir.
		     
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	  }
	}
}
