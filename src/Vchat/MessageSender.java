package Vchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JTextField;

public class MessageSender extends Thread {
	
	private String HOST_IP;
	private OutputStreamWriter osw;
	private JTextField inputBox;
	private JButton sendButton;
	private JTextField connectedTo;
	// Bu class bir host a baglanip o host a mesaj gonderir.
	public MessageSender(String HOST_IP){
		
		this.HOST_IP=HOST_IP; // baglanilacak ip adresi.
		connectedTo = new JTextField("Connected to: " + HOST_IP);  // baglanilan ip adresini gosteren yer.
		connectedTo.setBounds(10, 360, 190, 25);
		connectedTo.setEditable(false);
		inputBox = new JTextField();  // mesajin yazilacagi yer
		inputBox.setBounds(10, 312, 500, 40);
		sendButton = new JButton("SEND"); // gonderme tusu
		sendButton.setBounds(520, 312,120,40);
		sendButton.setFocusable(false); // tus focusable degil. yoksa tusa basildiginda focus input kutusundan cikiyor.
		MainLoop.getVideoDisplayer().addComponent(sendButton); // ana pencereye ekleniyor componentlar.
		MainLoop.getVideoDisplayer().addComponent(inputBox);
		MainLoop.getVideoDisplayer().addComponent(connectedTo);
		this.start(); // thread baslatildi.
	}
	public void run(){
		
		inputBox.addActionListener(new ActionListener(){ // input box tayken enter a basilirsa bu kisim calisir.
			public void actionPerformed(ActionEvent e) {
				  if(inputBox.getText().length() > 0){  // sadece enter a basilmissa gonderilmez
				  MessageListener.getChatBox().append("\nYou : " + inputBox.getText());  // yazilan mesaj kendi chatbox imiza ekleniyor.
				  sendMessageToHost(inputBox.getText()); // mesaj host a yollandi.
				  inputBox.setText(""); // input box i tekrar sifirladik.
				  }
			  }
			});
		
		sendButton.addActionListener(new ActionListener(){ // ust kisimla tamamen aynidir. SEND tusuna basilirsa input boxtaki mesaji host a gonderir.
			public void actionPerformed(ActionEvent e) {
				  if(inputBox.getText().length() > 0){
				  MessageListener.getChatBox().append("\nYou : " + inputBox.getText());
				  sendMessageToHost(inputBox.getText());
				  inputBox.setText("");
				  }
			  }
			});


		while(true)
		{

		}	
	}
	
	public void sendMessageToHost(String message){
		try {
			Socket clientSocket = new Socket(HOST_IP,816); // host a baglan.
			if(clientSocket.isConnected())
			{
			osw = new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8");  // sockete UTF-8 charset i ile string yazdirilir.
			osw.write(message,0,message.length()); // mesaj yazildi.
			osw.flush(); // stream bosaltildi. karsi taraf bu komuttan sonra stream i okur.
			}
			clientSocket.close(); // baglanti kapatildi.
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	public JTextField getInputBox() {
		return inputBox;
	}
	public void setInputBox(JTextField inputBox) {
		this.inputBox = inputBox;
	}

}
