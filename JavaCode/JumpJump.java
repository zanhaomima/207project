import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class JumpJump {
	
	private String filePreStr; // where you sace the screen shot
    static int serialNum = 0;  //use this number to name the screen shot save in pc
    private static int time=0;
//    Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
    
    public JumpJump(String s) {
        filePreStr = s;
    }
 
    public void snapShot() {
        try {
            // taking a screen shot 
            BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(10, 23, (int) 610, (int) 1111));
		//screen shot from your labtop screen X-axe 10-610 to Y-axe 23-1111 change accordingly to your screen size
            File f = new File(filePreStr);
            System.out.print("Save File " + filePreStr);
            // save file to directory
            ImageIO.write(screenshot, "png", f);
            System.out.print("..Finished!\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
	
	public static void main(String[] args) throws IOException {    //main function here 
		Scanner enter = new Scanner(System.in);     
		SendToArduino arduinoSender=new SendToArduino();
        arduinoSender.printPorts();//print list of port you connect
        int port=enter.nextInt();//chose port by enter a port option
        String s=arduinoSender.connect(port);
        System.out.println(s);
        if(s.equals("connect Seccued!")) {
        		runJumpJump(arduinoSender);
        }
        else System.out.println("bye");
		
//		FindSubimageWindow o=new FindSubimageWindow("/Users/jifengzheng/Desktop/Jump.png", "/Users/jifengzheng/Desktop/jh.png","/Users/jifengzheng/Desktop/jm.png","/Users/jifengzheng/Desktop/solution.png");
	}
	
	public static void runJumpJump(SendToArduino arduinoSender) throws IOException {
		screenShot();
		int timepass=JumpPNGCalculateRun();//getting presstime from JumpPNGCalculateRun function
		if(timepass==-1) {
			System.out.println("找不到小人");// print not find chess piece
			runJumpJump(arduinoSender);//if not find try it again
			return ;
		}
    	arduinoSender.sendToArduino(timepass);//send to arduino the press time
    	try {Thread.sleep(3000); } catch(Exception e) {}
    	runJumpJump(arduinoSender);//call this function every 6s to keep calculate the press time and send to arduino 
    	try {Thread.sleep(3000); } catch(Exception e) {}
        
	}
	
	public static void screenShot() {
		JumpJump cam = new JumpJump("/Users/jifengzheng/Desktop/Jump.png");
        cam.snapShot();//use snapShot to take a screenshot and save in same place overide the lat picture
        try {Thread.sleep(500); } catch(Exception e) {}
	}
	
	public static int JumpPNGCalculateRun() throws IOException {
		long t=System.currentTimeMillis();
		subPNGfinding j=new subPNGfinding("/Users/jifengzheng/Desktop/","Jump.png","Jm.png",time++);//function to scan the piece base on image we got out from screen shot by PS
		if(j.findingsub()) { 
			j.findingStepPosition();
			double dist=j.CalaterDistance();
			System.out.println("Using time: "+(System.currentTimeMillis()-t)+" ns");
			return (int)dist;
		}
		return -1;
	}
	


}
