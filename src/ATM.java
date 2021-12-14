import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class ATM implements ActionListener{
	static String user, Password;
	
	private static JLabel userlabel , userpassword, success;
	private static JPanel panel;
	private static JFrame frame;
	private static JTextField userText;
	private static JPasswordField userPass;
	private static JButton button;
	
	//construct virtual keyboard
	public void virtualKeyBoard() {
		String row1 = "123";
		String row2 = "456";
		String row3 = "789";
		String[] rows = { row1, row2,row3 };
		for (int i = 0; i < rows.length; i++) {
		    char[] keys = rows[i].toCharArray();
		    for (int j = 0; i < keys.length && j<keys.length; j++) {
		        JButton button = new JButton(Character.toString(keys[j]));
		        button.setBounds(10*(j*10),80+(i*25),80,25);
				button.addActionListener(this);
				panel.add(button);
		        // add button
		    }
		}
		
		JButton zero = new JButton("0");
		zero.setBounds(100,155,80,25);
		zero.addActionListener(this);
		panel.add(zero);
		
		JButton clear = new JButton("Clear");
		clear.setBounds(0,155,80,25);
		clear.addActionListener(this);
		panel.add(clear);
		
		JButton enter = new JButton ("Enter");
		enter.setBounds(200,155,80,25);
		enter.addActionListener(this);
		panel.add(enter);
	}
	
	//create main interface (logging in screen)
	public ATM() {
		panel = new JPanel();
		frame = new JFrame();
		frame.setTitle("ATM");
		frame.setSize(450,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
		userlabel = new JLabel("User");
		userlabel.setBounds(10,20,80,25);
		panel.add(userlabel);
		
		userText = new JTextField(20);
		userText.setBounds(100,20,165,25);
		panel.add(userText);
		
		userpassword = new JLabel("PIN");
		userpassword.setBounds(10,50,80,25);
		panel.add(userpassword);
		
		userPass = new JPasswordField(20);
		userPass.setBounds(100,50,65,25);
		panel.add(userPass);
		
		success = new JLabel("");
		success.setBounds(20,180,300,25);
		panel.add(success);
		
		virtualKeyBoard();
		playMusic();
		frame.setVisible(true);
	}
	
	//Reads the csv file
	static boolean ReadFile() {
		boolean continueLoop = true;
		do {
			try {
				File myObj = new File("ATM.csv");
				Scanner myReader = new Scanner (myObj);
				myReader.next();
				myReader.useDelimiter(",");
				String name = "",pass= "";
				while(myReader.hasNext()) {
					String data = myReader.next();
					if(user.equals(data) ) {
						name = data;
					}
					
					if(Password.equals(data) ) {
						pass = data;
					}
				}
				continueLoop = false;
//				System.out.println(name);
//				System.out.println(pass);
				if(name!= "" && pass != "") {
					return true;
				}else {
					return false;
				}
			}catch(FileNotFoundException e) {
				System.out.println("Open a good File.");
				e.printStackTrace();
			}
		}while(continueLoop);
		return false;
	}	
	
//function to upload music
	void playMusic()
	    {
	       try
	        {
	            File musicpath = new File("Music.wav");
	            
	            if (musicpath.exists())
	            {
	                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicpath);
	                Clip c = AudioSystem.getClip();
	                c.open(audioInput);
	                c.start();
	            }
	            else
	            {
	                JOptionPane.showMessageDialog (null,"ERROR! MUSIC FILE DOES NOT EXIST", "ERROR", JOptionPane.ERROR_MESSAGE);

	            }
	        }
	        catch (Exception ex)
	        {
	        ex.printStackTrace();        
	        }
	        
	    }   
	
	//handles logging in functionalities
	int count =0;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		//userPass.setText(userPass.getText() + actionCommand);
		if(actionCommand.equals("Clear")) {
			actionCommand = userPass.getText().substring(0, userPass.getText().length()-1);
			userPass.setText(""+actionCommand);
		}else if(actionCommand.equals("Enter")) {
			
			 user = userText.getText();
			 Password = userPass.getText();
			 if(ReadFile()) {
				// success.setText("Login successful!!");
				 LoginSuccessful Successful = new LoginSuccessful();
				 Successful.ReadFile(user,false);
			 }
			 else {
				 success.setText("Try Again. You have only 3 attempts!!");
				 count =count+ 1;
			 }
			 if(count == 3) {
				 System.exit(-1);
			 }
		}else {
			userPass.setText(userPass.getText() + actionCommand);}
	}
}

