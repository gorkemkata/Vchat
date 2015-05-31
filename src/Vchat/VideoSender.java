package Vchat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

public class VideoSender extends Thread{
	// Bu class karsi tarafa webcam den okudugu frame leri yollar. Saniyede 30 frame kadar yollar.
	private String HOST_IP;
	private WebcamHandler webcamHandler;
	private DataOutputStream dos;
	public VideoSender(String HOST_IP)
	{
		this.HOST_IP=HOST_IP; // baglanilacak host adresi
		webcamHandler = new WebcamHandler(); // webcam den frame okumak icin
		this.start(); // thread baslatildi.
	}
	
	public void run()
	{
	   while(true)
		{
			try {
				Socket clientSocket = new Socket(HOST_IP,815);
				if(clientSocket.isConnected())
				{
				dos = new DataOutputStream(clientSocket.getOutputStream()); // socket in output stream ine data yazmak icin.
				

				try {
					Thread.sleep(30); // 30 ms de bir frame gonderilir. Bu da yaklasik 30 fps gibi bir kaliteye denk geliyor.
					this.sendFrame(clientSocket); // okunan frame i gonder
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
				clientSocket.close(); // baglanti kapatilir her frame yollandiktan sonra.
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		
	}
	
	public void sendFrame(Socket clientSocket) throws IOException{
		
		BufferedImage image = webcamHandler.getCurrentFrame(); // webcam den o siradaki frame okunur.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream); // yeni olusan resim byte array output stream e yazdirilir.
	    int contentLength = byteArrayOutputStream.size(); // dosya boyutu.
	    byte [] imageAsByteArray = new byte[contentLength]; // resmi koymak icin bir byte array olusturulur
	    imageAsByteArray = byteArrayOutputStream.toByteArray(); // resim byte array e kopyalanir.
	    dos.writeInt(contentLength); // output stream e ilk once resmin boyutu yazdirilir. boylece karsi taraf ne kadar byte okumasi gerektigini bilecek.
	    dos.flush();
	    dos.write(imageAsByteArray,0,imageAsByteArray.length); // resim byte byte yazdirilir.
	    dos.flush();
	    
	}
	
	
	
}
