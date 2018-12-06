
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.fazecast.jSerialComm.SerialPort;

/**
 * Send message to Arduino
 * @author jifeng Zheng 
 *
 */
public class SendToArduino {
	
	static SerialPort chosenPort;
	private PrintWriter output;
	
	public void sendToArduino(int timepass) {
		if(!chosenPort.openPort()) {
			System.out.println("Arduino is disconnect");
			return;
		}
		long t=System.currentTimeMillis();
		output.print(timepass);
		output.flush();
		System.out.println("Sent passtime:"+timepass+" "+(System.currentTimeMillis()-t));
		t=System.currentTimeMillis();
	}
	
	public void printPorts() {
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++) System.out.println(i+" "+portNames[i].getSystemPortName());
	}
	
	public String connect(int i) {
		SerialPort[] portNames = SerialPort.getCommPorts();
		chosenPort = SerialPort.getCommPort(portNames[i].getSystemPortName());
		chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		if(chosenPort.openPort()) {
			output = new PrintWriter(chosenPort.getOutputStream());
			return "connect Seccued!";
		}
		else return "failed connect";
	}

}