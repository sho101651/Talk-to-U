package Netcen;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Main {
	private JFrame myframe;
	private JPanel mypanel,mypanel1,mypanel2,mypanel3;
	private JLabel usernamelabel,passlabel,portlabel;
	private String IP;
	private JTextField userTxt,portTxt;
	private JPasswordField pwdTxt;
	private JButton login;
	
	public Main() {
		
		usernamelabel = new JLabel("USERNAME : ");
		passlabel = new JLabel("PASSWORD : ");
		portlabel = new JLabel("PORT : ");
		userTxt = new JTextField(20);
		portTxt = new JTextField(20);
		pwdTxt = new JPasswordField(20);
		
		userTxt.setText("6009650117");
		portTxt.setText("12345");
		pwdTxt.setText("0117");
		
		login = new JButton("LOGIN");
		
		mypanel = new JPanel();
		mypanel1 = new JPanel();
		mypanel2 = new JPanel();
		mypanel3 = new JPanel();
		
		mypanel1.add(usernamelabel);
		mypanel1.add(userTxt);
		
		mypanel2.add(passlabel);
		mypanel2.add(pwdTxt);
		
		mypanel3.add(portlabel);
		mypanel3.add(portTxt);
		
		mypanel.setLayout(new GridLayout(3,1));
		mypanel.add(mypanel1);
		mypanel.add(mypanel2);
		mypanel.add(mypanel3);

		
		myframe= new JFrame();	
		myframe.setTitle("Talk to You"); 
		myframe.add(mypanel);
		myframe.add(login,BorderLayout.EAST);
		
		
		//myframe.setSize(800, 200);
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.pack();
		myframe.setVisible(true);
		
		try{
            InetAddress inet= InetAddress.getLocalHost(); 
            IP = inet.getHostAddress();
        }catch(UnknownHostException ex){ 
        	System.out.println(ex);
        	}
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = userTxt.getText();
				String pwd = pwdTxt.getText();
				String port = portTxt.getText();
				
				if(username.equals("")||pwd.equals("")||port.equals("")) {
					JOptionPane.showMessageDialog(myframe, "Fill all field");
				}
				else {
					JOptionPane.showMessageDialog(myframe, "Success to login");
					ConnectHeartBeat chat = new ConnectHeartBeat(IP, portTxt.getText(), userTxt.getText(), pwdTxt.getText());
					//chat.setVisible(true);
					myframe.dispose();
				}
			}
		});
		
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		
	}

}
