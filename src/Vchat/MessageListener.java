package Vchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class MessageListener extends Thread {
	
	private ServerSocket serverSocket;
	private static JTextArea chatBox = new JTextArea(40,500); 
	private static JScrollPane chatBoxScroll;
	private DefaultCaret caret;
	// Bu class gelen text mesajlarini dinliyor ve bunlari ana penceredeki JTextArea nesnesine (chatBox) yazdiriyor.
	
	public MessageListener(){
		// Ana penceredeki konusma gecmisi penceresi olusturuluyor
	    caret = (DefaultCaret)chatBox.getCaret();              // Focus surekli son yazilan girdide oldu. 
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);    
		chatBox.setBounds(350, 16, 285, 292);                  // textarea parametleri ayarlaniyor.
		chatBox.setEditable(false); // editlenemez
		chatBox.setLineWrap(true);  // mesaj satir genisligini asarsa otomatik alt satira geciliyor.
		chatBox.setWrapStyleWord(true);
		chatBoxScroll = new JScrollPane(); // scroll eklendi.
		chatBoxScroll.setBounds(350, 16, 285, 292);
		chatBoxScroll.setViewportView(chatBox);  // scroll ile textarea baglandi.
		MainLoop.getVideoDisplayer().addComponent(chatBoxScroll); // pencereye eklendiler.
		
		
		this.start();  // Thread baslangici. Otomatik run() metodu cagiriliyor.
	}
	
	public void run(){
		try {
			serverSocket = new ServerSocket(816); // PORT 816 ya baglanildi.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true)
		  {
			
			 try {
				 Socket clientSocket = serverSocket.accept();  // PORT 816 dinleniyor.
				 InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream(),"UTF-8"); // * gelen String leri okumak icin UTF-8 charseti ile 
				                                                                                                     // * bir InputStreamReader nesnesi olusturuldu.
				 Reader reader = new BufferedReader(inputStreamReader); // String okumak icin inputStreamReader BufferedReader ile wrap edilir.
				 int readChar;
				 StringBuffer stringBuffer = new StringBuffer(); // okunacak char larin yazilacagi string buffer i.
				 while( (readChar = reader.read()) != -1){  // Reader.read() metodu sira sira charlari okur. Bufferin sonuna gelindiginde -1 dondurur.
					 stringBuffer.append((char)readChar);  // Okunan char lar string buffer a koyuluyor.
				 }
				 reader.close();
				 if(stringBuffer.toString().length() > 0){ // Sadece enter a basilmissa ekranda gosterilmiyor bu.
				 chatBox.append("\n"+ clientSocket.getInetAddress().getHostName() + " : " + stringBuffer.toString()); // mesaji gonderen makinenin adi yazilir mesajin basina.
				 chatBox.setCaretPosition(chatBox.getText().length()); // chatBox in imleci en alta aliniyor. Yoksa yazi gelse bile altta kaldigi icin gozukmuyor.
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		  }
		
	}
	
	public static JTextArea getChatBox(){ 
		return chatBox;
	}

}
