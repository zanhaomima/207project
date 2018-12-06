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
	
	private String filePreStr; // 默认前缀（选择存储路径例如： D：\\）
    static int serialNum = 0;  //截图名称后面的数字累加
    private static int time=0;
//    Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
    
    public JumpJump(String s) {
        filePreStr = s;
    }
 
    public void snapShot() {
        try {
            // *** 核心代码 *** 拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(10, 23, (int) 610, (int) 1111));
            File f = new File(filePreStr);
            System.out.print("Save File " + filePreStr);
            // 将screenshot对象写入图像文件
            ImageIO.write(screenshot, "png", f);
            System.out.print("..Finished!\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
	
	public static void main(String[] args) throws IOException {
		Scanner enter = new Scanner(System.in);
		SendToArduino arduinoSender=new SendToArduino();
        arduinoSender.printPorts();
        int port=enter.nextInt();
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
		int timepass=JumpPNGCalculateRun();
		if(timepass==-1) {
			System.out.println("找不到小人");
			runJumpJump(arduinoSender);
			return ;
		}
    	arduinoSender.sendToArduino(timepass);
    	try {Thread.sleep(3000); } catch(Exception e) {}
    	runJumpJump(arduinoSender);
    	try {Thread.sleep(3000); } catch(Exception e) {}
        
	}
	
	public static void screenShot() {
		JumpJump cam = new JumpJump("/Users/jifengzheng/Desktop/Jump.png");//
        cam.snapShot();
        try {Thread.sleep(500); } catch(Exception e) {}
	}
	
	public static int JumpPNGCalculateRun() throws IOException {
		long t=System.currentTimeMillis();
		subPNGfinding j=new subPNGfinding("/Users/jifengzheng/Desktop/","Jump.png","Jm.png",time++);
		if(j.findingsub()) {
			j.findingStepPosition();
			double dist=j.CalaterDistance();
			System.out.println("Using time: "+(System.currentTimeMillis()-t)+" ns");
			return (int)dist;
		}
		return -1;
	}
	


}
