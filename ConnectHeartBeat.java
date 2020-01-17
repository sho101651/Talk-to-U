package Netcen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConnectHeartBeat {
	private Socket socket = null;
	private InputStream input = null, is;
	private OutputStream out = null;
	private String address, port, username, password, sendMessage, message;
	private ArrayList<People> users;
	private OutputStream os;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	private InputStreamReader isr;
	private BufferedReader br;
	private ArrayList<String> list;
	private JFrame frame;
	private JPanel panel;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnClient,btnServer;
	// private InputStream is;

	public ConnectHeartBeat(String address, String port, String username, String password) {

		this.address = address;
		this.port = port;
		this.username = username;
		this.password = password;

		panel = new JPanel();
		textField = new JTextField(20);
		btnClient = new JButton("Connect");
		btnServer = new JButton("Server");
		
		btnServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String ipReceive = port;
				JOptionPane.showMessageDialog(frame, "You are client,Wait for client");
				try {
					GossipServer gs = new GossipServer(ipReceive);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String wantConnect = textField.getText();
				//JOptionPane.showMessageDialog(frame, users.size());
				for (int j2 = 0; j2 < users.size(); j2++) {
					if (wantConnect.equals(users.get(j2).getID())) {
			
						JOptionPane.showMessageDialog(frame, "You are client,Start Chat");
						String ipConnect = users.get(j2).getIP();
						String portConnect = users.get(j2).getPort();
						try {
							GossipClient gc = new GossipClient(ipConnect, portConnect);
							//GossipClient gc = new GossipClient("192.168.1.39","3000");
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		});
		
		panel.setLayout(new BorderLayout());
		panel.add(textField,BorderLayout.LINE_START);
		panel.add(btnClient,BorderLayout.LINE_END);
		panel.add(btnServer,BorderLayout.PAGE_END);

		frame = new JFrame("Users");
		textArea = new JTextArea(15, 25);
		textArea.setEditable(false);
		frame.setLayout(new BorderLayout());
		frame.add(textArea, BorderLayout.PAGE_START);
		frame.add(panel, BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);


		try {

			users = new ArrayList<People>();
			list = new ArrayList<String>();

			socket = new Socket("192.168.1.102", 62342);
			os = socket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);

			sendMessage = "USER:" + username + "\n" + "PASS:" + password + "\n" + "IP:" + address + "\n" + "PORT:"
					+ port + "\n";
			bw.write(sendMessage);
			bw.flush();
			System.out.println("Message sent to the server : " + sendMessage);

			// Get the return message from the server
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			message = br.readLine();

			System.out.println("Message received from the server : " + message);
			

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					heartbeat();
				}
			});
			thread.start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void heartbeat() {
		int i = 0, j = 0;
		boolean check = false;
		while (true) {
			// if (br.readLine() != null) {

			try {
				message = br.readLine();
				bw.write("Hello Server");
				bw.flush();
				// list.clear();

				if (!message.equals("END")) {
					if(check) {
						users.clear();
						check = false;
					}
					// list.add(i,String.valueOf(message));
					String str[] = message.split(":");
					if (!str[1].equals("-1") && !str[2].equals("-1")) {
						if (str[0].contains("Hello 6009650117")) {
							str[0] = str[0].replaceAll("Hello 6009650117", "");
							// System.out.println(str[0]);
							// System.out.println(str[0].replaceAll("Hello 6009650117", ""));
						}
						users.add(new People(str[0], str[1], str[2]));
						// list.add(String.valueOf(message));
						// textArea.setText(textArea.getText()+"\n"+users.get(j).getID());
						// System.out.println(users.get(j).getID());
						// j++;
					}
					// System.out.println("Message received from the server : " + message);
					// System.out.println(list.get(i));
					// System.out.println(users.get(j).getID());
					i++;
				} else {
					textArea.setText("");
					for (int j2 = 0; j2 < users.size(); j2++) {
						textArea.setText(textArea.getText() + "\n" + users.get(j2).getID());
						System.out.println("j2:" + users.get(j2).getID());
					}
					check = true;
					// j = 0;
					// i++;

					System.out.println("");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	public static void main(String[] args) {
//		ConnectHeartBeat conn = new ConnectHeartBeat("192.168.1.54", "3000", "6009650117", "0117");
//	}

}
