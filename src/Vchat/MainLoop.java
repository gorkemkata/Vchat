package Vchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class MainLoop {
	private static UserInterface player = new UserInterface();
	private static JButton submitButton = new JButton("CONNECT");
	private static JTextField hostIpBox = new JTextField();;
	private static JTextField label = new JTextField("        ENTER HOST IP");;
	private static String HOST_IP;
	@SuppressWarnings("unused")
	
	public static void main(String[] args){
		
		
		VideoReceiver listener = new VideoReceiver();                    // *  Program baslangicinda port 815 (video icin) 
		MessageListener messageListener = new MessageListener();         // *  ve 816(chat icin) dinlenmeye baslar.
		
		submitButton.setBounds(90,150,120,40);  // Acilis penceresi olusturuluyor.
		submitButton.setFocusable(false);
		hostIpBox.setBounds(75, 110, 150, 30);
		label.setBounds(75, 67, 150, 30);
		label.setEditable(false);
		player.addComponentToWelcomeWindow(hostIpBox);
		player.addComponentToWelcomeWindow(label);
		player.addComponentToWelcomeWindow(submitButton);
		player.startWelcomeGUI();  // Acilis penceresi baslatildi.
		hostIpBox.requestFocusInWindow();
		
		hostIpBox.addActionListener(new ActionListener(){ // kullanici ip numarasi girip ENTER tusuna basarsa girilen IP ye baglaniliyor.
			public void actionPerformed(ActionEvent e) {
				  HOST_IP = hostIpBox.getText(); // kutuya yazilan ip numarasi alinir.
				  hostIpBox.setText("");
				  player.closeWelcomeGUI();
			      MessageSender messageSender = new MessageSender(HOST_IP); // Girilen Ip ye baglanildi.
				  VideoSender sender = new VideoSender(HOST_IP);            //
				  player.startProgramGUI();
				  messageSender.getInputBox().requestFocusInWindow();
			  }
			});
		
		submitButton.addActionListener(new ActionListener(){ // Kullanici CONNECT tusuna basarsa da girilen IP ye baglaniyoruz.
			public void actionPerformed(ActionEvent e) {
				  HOST_IP = hostIpBox.getText();
				  hostIpBox.setText("");
				  player.closeWelcomeGUI();
			      MessageSender messageSender = new MessageSender(HOST_IP);
				  VideoSender sender = new VideoSender(HOST_IP);
				  player.startProgramGUI();
				  messageSender.getInputBox().requestFocusInWindow();
			  }
			});


	}

	public static UserInterface getVideoDisplayer(){
		return player;
	}
	

}
