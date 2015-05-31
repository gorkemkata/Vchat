package Vchat;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class UserInterface {
	private JPanel mainWindowPanel;
	private JPanel welcomeWindowPanel;
	private JFrame videoWindow;
	private JFrame welcomeWindow;
	private JLabel imageLabel;
	public UserInterface(){
		
		videoWindow  = new JFrame("Vchat-AYSU VIDEO CHAT");  // Baglanti kurulunca gelen arayuz
		welcomeWindow = new JFrame("WELCOME TO Vchat-AYSU VIDEO CHAT"); // Program ilk acildiginda gelen arayuz.
		mainWindowPanel = new JPanel(); // Paneller i de farkli oluyor.
		mainWindowPanel.setLayout(null);
		welcomeWindowPanel = new JPanel();
		welcomeWindowPanel.setLayout(null);
		imageLabel = new JLabel();  // Resim gostermek icin.
	}

	public void showFrame (BufferedImage image){
        ImageIcon imageIcon = new ImageIcon(image);  // videonun o siradaki frame i gosteriliyor.
        imageLabel.setIcon(imageIcon); 
        imageLabel.setSize(400,400);
        imageLabel.setBounds(10, 10, 300, 300); // gosterilecegi pencere boyutu
        mainWindowPanel.add(imageLabel);
	}
	
	public void addComponent(Component component){
		mainWindowPanel.add(component); // Ana pencereye component ekleme
	}
	
	public void addComponentToWelcomeWindow(Component component){
		welcomeWindowPanel.add(component); // Acilis penceresine component ekleme. Bunu sadece MainLoop yapiyor.
	}
	
	public void startProgramGUI(){
		videoWindow.add(mainWindowPanel); // Ana program penceresi baslatilir.
		videoWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        videoWindow.setSize(650, 430);
        videoWindow.setVisible(true);
        videoWindow.setResizable(false);
        videoWindow.setLocationRelativeTo(null);
	}
	
	public void startWelcomeGUI(){
		welcomeWindow.add(welcomeWindowPanel); // Acilis penceresi baslatilir.
		welcomeWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		welcomeWindow.setSize(300, 300);
		welcomeWindow.setVisible(true);
		welcomeWindow.setResizable(false);
		welcomeWindow.setLocationRelativeTo(null);
	}
	
	public void closeWelcomeGUI(){ // Acilis penceresini kapatir. Bunu da sadece MainLoop cagiriyor baslangicta.
		welcomeWindow.setVisible(false);
		welcomeWindow.dispose();	
	}
	
}
