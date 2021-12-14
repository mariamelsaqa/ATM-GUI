import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

public class LoginSuccessful implements ActionListener{
	private static String Balance, index, Pin , BalanceTransfer, indexTransfer, SaveHeadings, AccNo, AccNoTransfer;
	JTextField userText , TransferToWho;
	private static BigDecimal bal, Newbal, balTransfer, New_bal_saved;
	private static ArrayList<String> fileReader = new ArrayList<String>();
	public static StringBuilder StoreData = new StringBuilder();
	
	//function to handle when the user press logout
	public void Logout(JPanel panel) {
		JButton Logout = new JButton("Logout");
		Logout.setBounds(270,160,150,50);
		Logout.addActionListener(this);
		panel.add(Logout);
	}
	
	//create new screen when the user log in successfully
	public LoginSuccessful() {
		JFrame frameSuccess = new JFrame();
		JPanel panelSuccess = new JPanel();
		frameSuccess.setTitle("ATM");
		frameSuccess.setSize(450,300);
		frameSuccess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSuccess.add(panelSuccess);
		
		panelSuccess.setLayout(null);
		
		JLabel AskUser = new JLabel("What kind of service do you want?");
		AskUser.setBounds(120,10,250,25);
		panelSuccess.add(AskUser);
		
		JButton checkBalance = new JButton("Check Balance");
		checkBalance.setBounds(20,40,150,50);
		checkBalance.addActionListener(this);
		panelSuccess.add(checkBalance);
		
		JButton withdraw = new JButton("Withdraw Money");
		withdraw.setBounds(20,100,150,50);
		withdraw.addActionListener(this);
		panelSuccess.add(withdraw);
		
		JButton Deposit = new JButton("Deposit Money");
		Deposit.setBounds(20,160,150,50);
		Deposit.addActionListener(this);
		panelSuccess.add(Deposit);
		
		JButton changePin = new JButton("Change Pin");
		changePin.setBounds(270,40,150,50);
		changePin.addActionListener(this);
		panelSuccess.add(changePin);
		
		JButton Transfer = new JButton("Transfer Money");
		Transfer.setBounds(270,100,150,50);
		Transfer.addActionListener(this);
		panelSuccess.add(Transfer);
		
		Logout(panelSuccess);
		
		frameSuccess.setVisible(true);
	}
	
	//Reading file and get necessary data
	static void ReadFile(String userName, boolean flag) {
		boolean continueLoop = true;
		do {
			try {
				File myObj = new File("ATM.csv");
				Scanner myReader = new Scanner (myObj);
				SaveHeadings = myReader.next();
				//myReader.useDelimiter(",");
				while(myReader.hasNext()) {
					String data = myReader.next();
					fileReader.add(data);
					if(!flag) {
						if(data.contains(userName)) {
							String[] splitData = data.split(",");
						
							Balance = splitData[4];
							index = splitData[0];
							Pin = splitData[3];
							AccNo = splitData[2];
							bal = new BigDecimal (Balance);
							//System.out.println(Balance);
							//System.out.println(bal);
						}
					}
					else {
						if(data.contains(userName)) {
							//System.out.println("HII");
							String[] splitData = data.split(",");
						
							BalanceTransfer = splitData[4];
							indexTransfer = splitData[0];
							AccNoTransfer = splitData[2];
							balTransfer = new BigDecimal (BalanceTransfer);
							//System.out.println(Balance);
							//System.out.println(balTransfer);
							}
						}
				
				}
				continueLoop = false;
//				System.out.println(name);
//				System.out.println(pass);
			}catch(FileNotFoundException e) {
				System.out.println("Open a good File.");
				e.printStackTrace();
			}
		}while(continueLoop);
	}
	
	//function to check if the user wants a receipt or not in check balance
	public void Receipt(JPanel panelReceipt) {		
		JLabel receipt = new JLabel("Do you want to print a receipt?");
		receipt.setBounds(120,50,250,25);
		panelReceipt.add(receipt);
		
		JButton PrintReceipt = new JButton("Yes");
		PrintReceipt.setBounds(20,100,150,50);
		PrintReceipt.addActionListener(this);
		panelReceipt.add(PrintReceipt);
		
		JButton DontPrintReceipt = new JButton("No");
		DontPrintReceipt.setBounds(270,100,150,50);
		DontPrintReceipt.addActionListener(this);
		panelReceipt.add(DontPrintReceipt);
	}
	
	//function to check if the user wants to print a receipt in a new page for the rest of the transactions
	public void ReceiptinNewPage() {
		JFrame frameReceipt = new JFrame();
		JPanel panelReceipt = new JPanel();
		
		frameReceipt.setTitle("ATM");
		frameReceipt.setSize(450,300);
		frameReceipt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameReceipt.add(panelReceipt);
		
		panelReceipt.setLayout(null);
		
		Receipt(panelReceipt);
		
		frameReceipt.setVisible(true);
	}
	
	//print a rciept if the user chooses to OR print thank you otherwise
	public void DecideReceipt(boolean printReceipt) {
		JFrame frameReceipt = new JFrame();
		JPanel panelReceipt = new JPanel();
		
		frameReceipt.setTitle("ATM");
		frameReceipt.setSize(450,300);
		frameReceipt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameReceipt.add(panelReceipt);
		
		panelReceipt.setLayout(null);
		
		if(printReceipt) {
		JLabel receipt = new JLabel("Printing Receipt");
		receipt.setBounds(135,70,250,25);
		panelReceipt.add(receipt);}
		
		JLabel Thanks = new JLabel("Thank You for using our services! :)");
		Thanks.setBounds(90,120,250,25);
		panelReceipt.add(Thanks);
		
		frameReceipt.setVisible(true);
		
		Timer t = new Timer(3000,this);
		t.setRepeats(false);
		t.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	AnotherService();  
		        }
		    });
		t.start();
	}
	
	//handle the screen of check balance transactions
	public void CheckBalance() {
		JFrame frameBalance = new JFrame();
		JPanel panelBalance = new JPanel();
		
		frameBalance.setTitle("ATM");
		frameBalance.setSize(450,300);
		frameBalance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBalance.add(panelBalance);
		
		panelBalance.setLayout(null);
		
		JLabel bal = new JLabel("Your Balance is: " + Balance);
		bal.setBounds(130,10,250,25);
		panelBalance.add(bal);
		
		Receipt(panelBalance);
		
		frameBalance.setVisible(true);
		
	}
	
	//construct a virual keyboard 
	public void virtualKeyBoard(JPanel panel) {
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
		
		JButton back = new JButton ("Back");
		back.setBounds(300,130,80,25);
		back.addActionListener(this);
		panel.add(back);
	}
	
	//handle different transactions interface
	public void Transactions(String str, boolean transfer) {
		JFrame frameBalance = new JFrame();
		JPanel panelBalance = new JPanel();
		
		frameBalance.setTitle("ATM");
		frameBalance.setSize(450,300);
		frameBalance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBalance.add(panelBalance);
		
		panelBalance.setLayout(null);
		
		JLabel withdraw = new JLabel(str);
		withdraw.setBounds(90,20,250,25);
		panelBalance.add(withdraw);
		
		userText = new JTextField(20);
		userText.setBounds(110,50,165,25);
		panelBalance.add(userText);
		virtualKeyBoard(panelBalance);
		
		if(transfer) {
			JLabel who = new JLabel("To who");
			who.setBounds(170,190,250,25);
			panelBalance.add(who);
			
			TransferToWho = new JTextField(20);
			TransferToWho.setBounds(110,210,165,25);
			panelBalance.add(TransferToWho);
		}
		
		frameBalance.setVisible(true);	
	}
	
	//handle the interface which check if the user wants another service or not
	public void AnotherService() {
		JFrame frameService = new JFrame();
		JPanel panelService = new JPanel();
		
		frameService.setTitle("ATM");
		frameService.setSize(450,300);
		frameService.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameService.add(panelService);
		
		panelService.setLayout(null);
		
		JButton PrintReceipt = new JButton("Another Service?");
		PrintReceipt.setBounds(270,100,150,50);
		PrintReceipt.addActionListener(this);
		panelService.add(PrintReceipt);
		
		Logout(panelService);
		
		frameService.setVisible(true);
	}
	
	//overwrite the csv file with new values
	void UpdateFile(String index, String bal, String Newbal,int fileSize) {
		String replacer;
		int i = Integer.parseInt(index);
		replacer = fileReader.get(i).replace(bal, Newbal);
		fileReader.set(i,replacer);
		
//		for(int j=0;j<fileSize;j++) {
//			System.out.println(fileReader.get(j));
//			}
//		System.out.println("DONE");
		
		try {
			FileWriter writer = new FileWriter("ATM.csv", false);
			writer.write(SaveHeadings + System.lineSeparator());
			for(int j=0;j<fileSize;j++) {
				writer.write(fileReader.get(j)+ System.lineSeparator());
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//write transaction log file for Deposit, withdraw
	void TransactionLog(String Type, String AccNo, BigDecimal Amount) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd, HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			FileWriter writer = new FileWriter("Log.txt", true);
			
			writer.write(Type + ", "+ "*****"+AccNo+", "+Amount+ ", "+dtf.format(now)+System.lineSeparator());
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//write transaction log file for change pin
	void TransactionLog(String AccNo, String NewPin) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd, HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now(); 
			FileWriter writer = new FileWriter("Log.txt", true);
			
			writer.write("Change Pin, " + "*****"+AccNo + ", "+ NewPin+", "+dtf.format(now)+System.lineSeparator());
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//write transaction log file for transfer
	void TransactionLog_Transfer(BigDecimal Amount, String AccNoFrom, String AccNoTo) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd, HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now(); 
			FileWriter writer = new FileWriter("Log.txt", true);
			
			writer.write("Transfer Money, fROM "+"*****"+AccNoFrom +" TO "+"*****"+AccNoTo+", "+ Amount +", "+dtf.format(now)+System.lineSeparator());
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//handle what happens when user presses a button and update values
	boolean displayReceipt=true;
	boolean transfer = false;
	String Operation = "";
	String WhoTransfer = null;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Check Balance")) {
			CheckBalance();
		}
		else if(actionCommand.equals("Withdraw Money")) {
			Transactions("How much do you want to withdraw?",transfer);
			Operation = "Withdraw";
		}
		else if(actionCommand.equals("Deposit Money")) {
			Transactions("How much do you want to deposit?",transfer);
			Operation = "Deposit";
		}
		else if(actionCommand.equals("Change Pin")) {
			displayReceipt = false;
			Transactions("What is you new Pin",transfer);
			Operation = "Change";
		}
		else if(actionCommand.equals("Transfer Money")) {
			transfer = true;
			Transactions("How much do you want to transfer?",transfer);
			Operation = "Transfer";
		}
		else if(actionCommand.equals("Logout")) {
			new ATM();
		}
		else if(actionCommand.equals("Yes")) {
			DecideReceipt(true);
		}
		else if(actionCommand.equals("No")) {
			DecideReceipt(false);
		}
		else if(actionCommand.equals("Enter")) {
			String NewAmount = userText.getText();
			Newbal = new BigDecimal(NewAmount);
			boolean DecideEnter = true;
			if(transfer) {
				WhoTransfer = TransferToWho.getText();
				if(WhoTransfer.equals("")) 
					DecideEnter = false;
			}else {
				if(NewAmount.equals(""))
					DecideEnter = false;
			}
			
			int FileSize= fileReader.size();
			String AccNo_sub = AccNo.substring(5, 8);
			
			if(Operation.equals("Withdraw")) {
				TransactionLog("Withdrawl", AccNo_sub, Newbal);
				Newbal = bal.subtract(Newbal);
				String balance = bal.toString();
				String Newbalance = Newbal.toString();
				UpdateFile(index, balance, Newbalance,FileSize);
				bal = Newbal;
			}
			else if(Operation.equals("Deposit")) {
				TransactionLog("Deposit", AccNo_sub, Newbal);
				Newbal = bal.add(Newbal);
				String balance = bal.toString();
				String Newbalance = Newbal.toString();
				UpdateFile(index, balance, Newbalance, FileSize);
				bal = Newbal;
			}
			else if(Operation.equals("Change")) {
				String Newbalance = Newbal.toString();
				TransactionLog(AccNo_sub, Newbalance);
				UpdateFile(index, Pin, Newbalance, FileSize);
			}
			else if(Operation.equals("Transfer")) {
				New_bal_saved = new BigDecimal(NewAmount);
				Newbal = bal.subtract(Newbal);
				String balance = bal.toString();
				String Newbalance = Newbal.toString();
				UpdateFile(index, balance, Newbalance, FileSize);
				bal = Newbal;
				
				//System.out.println(WhoTransfer);
				ReadFile(WhoTransfer,true);
				
				String AccNoTransfer_sub = AccNoTransfer.substring(5, 8);
				
				TransactionLog_Transfer(New_bal_saved,AccNo_sub,AccNoTransfer_sub);
				//System.out.println(indexTransfer);
				//System.out.println(BalanceTransfer);
				New_bal_saved = balTransfer.add(New_bal_saved);
				//System.out.println(New_bal_saved);
				String balanceTransfer = balTransfer.toString();
				String NewbalanceTransfer = New_bal_saved.toString();
				UpdateFile(indexTransfer,balanceTransfer, NewbalanceTransfer, FileSize);
				balTransfer = New_bal_saved;
			}
			
			
			if(!DecideEnter) {
				JLabel attention = new JLabel("Try Again");
			}else {
				if(!displayReceipt)
					AnotherService();
				else
					ReceiptinNewPage();}

		}
		else if(actionCommand.equals("Clear")) {
			actionCommand = userText.getText().substring(0, userText.getText().length()-1);
			userText.setText(""+actionCommand);
		}
		else if(actionCommand.equals("Another Service?")) {
			new LoginSuccessful();
		}
		else if(actionCommand.equals("Back")) {
			new LoginSuccessful();
		}
		else {
			userText.setText(userText.getText() + actionCommand);}
		
		
	}
}
