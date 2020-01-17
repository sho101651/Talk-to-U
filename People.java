package Netcen;

public class People {
	private String ID,IP,port;
	
	public People(String iD, String iP, String port) {
		ID = iD;
		IP = iP;
		this.port = port;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
