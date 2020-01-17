package Netcen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GossipClient {

	private Socket sock;
	private BufferedReader keyRead;
	private OutputStream ostream;
	private PrintWriter pwrite;
	private String receiveMessage, sendMessage;
	private InputStream istream;
	private BufferedReader receiveRead;

	private JFrame frame;
	private JPanel Panel, Panel1;
	private JTextArea top, down;
	private JButton sendBtn;

	public GossipClient(String ip,String port) throws UnknownHostException, IOException {

		frame = new JFrame();
		Panel = new JPanel();
		Panel1 = new JPanel();
		sendBtn = new JButton("SEND");
		top = new JTextArea(30, 50);
		down = new JTextArea(10, 50);
		Panel.add(top);
		Panel1.add(down);
		Panel1.add(sendBtn);
		frame.setLayout(new BorderLayout());
		frame.add(Panel, BorderLayout.PAGE_START);
		frame.add(Panel1, BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		//sock = new Socket("172.20.10.52", 3000);
		sock = new Socket(ip, Integer.valueOf(port));
		// reading from keyboard (keyRead object)
		keyRead = new BufferedReader(new InputStreamReader(System.in));
		// sending to client (pwrite object)
		ostream = sock.getOutputStream();
		pwrite = new PrintWriter(ostream, true);

		// receiving from server ( receiveRead object)
		istream = sock.getInputStream();
		receiveRead = new BufferedReader(new InputStreamReader(istream));

		top.setText(top.getText() + "Start the chitchat\n");

//		Thread send = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				send();
//			}
//		});
//		send.start();

		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendMessage = down.getText();
				top.setText(top.getText() + sendMessage + "\n");
				down.setText("");
				pwrite.println(sendMessage); // sending to server
				pwrite.flush();
			}
		});

		Thread receive = new Thread(new Runnable() {
			@Override
			public void run() {
				receive();
			}
		});
		receive.start();

	}

	public void receive() {
		while (true) {
			try {
				if ((receiveMessage = receiveRead.readLine()) != null) // receive from server
				{
					top.setText(top.getText() + receiveMessage + "\n"); // displaying at DOS prompt
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	public void send() {
//		while (true) {
//			try {
//				
//				sendMessage = keyRead.readLine();
//				pwrite.println(sendMessage); // sending to server
//				pwrite.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} // keyboard reading
//		}
//	}

//	public static void main(String[] args) throws Exception {
//		GossipClient gc = new GossipClient("192.168.1.39","3000");
//	}
}