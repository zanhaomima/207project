import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

/**
 * finding sub-png in a big-png.
 * @author Jifeng Zheng
 *
 */
public class subPNGfinding {
	private int x;
	private int y;
	private int width;
	private int height;
	private int dx;
	private int dy;
	private int marky=180;
	private File currentSubImage;
	private File currentBigImage;
	private BufferedImage subbufferedImage;
	private BufferedImage BigbufferedImage;
	private MarvinImage window;
	private String newdis;
	private PixelRGBlist list;
	private double timefix=2.9f;
	
	public subPNGfinding(String dis,String pngbig, String pngsub,int ii) throws IOException {
		this.currentSubImage = new File(dis, pngsub);//access screenshot
		this.currentBigImage = new File(dis, pngbig);//access the chess piece picture cut out by ps increase the accuracy of find the chess piece position
		if(!this.currentBigImage.exists())System.out.println("找不到大图");
		if(!this.currentSubImage.exists())System.out.println("找不到小图");//if not find print out not find
		this.subbufferedImage=ImageIO.read(currentSubImage);
		this.BigbufferedImage=ImageIO.read(currentBigImage);
		System.out.println("Scene is："+this.subbufferedImage.getWidth()+" "+this.BigbufferedImage.getWidth());
		this.window=MarvinImageIO.loadImage(dis+pngbig);
		this.newdis=dis+"/solution/js"+String.format("%02d", ii)+".png";
		list=new PixelRGBlist();
	}
	
	public boolean findingsubNew() {
		
//		System.out.println("Get it x: "+f+" y: "+i+" z:"+(z*15));
//		drawline(window, f , i, subbufferedImage.getWidth(), subbufferedImage.getHeight());
//					MarvinImageIO.saveImage(window, this.newdis);
		return true;
	}
	
	public boolean findingsub() throws IOException {
		for(int z=1;z<8;z++) {
			for(int i=this.marky;i<BigbufferedImage.getHeight()*0.75;i++) {
				for(int f=0;f<BigbufferedImage.getWidth();f++) {
					if(checkfromthispiexl(BigbufferedImage,subbufferedImage,f,i,z*15)) {
						System.out.println("Get it x: "+f+" y: "+i+" z:"+(z*15));
						drawline(window, f , i, subbufferedImage.getWidth(), subbufferedImage.getHeight());
	//					MarvinImageIO.saveImage(window, this.newdis);
						return true;
					}
				}
			}
			System.out.println("finding Small man with " +(z*15));
		}
		return false;
	}
	
	public boolean checkfromthispiexl(BufferedImage BigbufferedImage, BufferedImage subbufferedImage, int x, int y,int Xlong) {
		PixelRGB p=new PixelRGB();
		PixelRGB b=new PixelRGB();
		if(BigbufferedImage.getWidth()<x+subbufferedImage.getWidth())return false;
		if(BigbufferedImage.getHeight()<y+subbufferedImage.getHeight())return false;
		for(int i=0;i<subbufferedImage.getHeight();i++) {
			for(int f=0;f<subbufferedImage.getWidth();f++) {
				b.reset();
				b.setRGB(BigbufferedImage.getRGB(x+f, y+i));
				p.reset();
				p.setRGB(subbufferedImage.getRGB(f,i));
				if(!p.isBlank())if(!b.compara(p, Xlong))return false;
			}
		}
		return true;
	}
	
	private void drawline(MarvinImage image, int x, int y, int width, int height) {
		image.drawRect(x, y, width, height, Color.red);
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	private void drawPoint(int x, int y,Color c) {
		window.drawRect(x, y, 1, 1, c);
	}
	
	
	public void findingStepPosition() throws IOException {
		for(int i=this.marky;i<this.y+subbufferedImage.getHeight()/2;i++) {
			for(int f=0;f<BigbufferedImage.getWidth();f++) {
				PixelRGB n=new PixelRGB();
				n.setRGB(BigbufferedImage.getRGB(f,i));
				n.setPosition(f, i);
				list.add(n);
			}
		}
		LinkedList<PixelRGB> l=list.sort();
		if(l==null) {System.out.println("找不到台阶");return;}
		LinkedList<PixelRGB> nl=new LinkedList<PixelRGB>();
		PixelRGB before=l.get(0);
		for (PixelRGB j : l) {
			if(before.comparaPosition(j,5)) {
				drawPoint(j.getPositionX(),j.getPositionY(),Color.RED);
				nl.add(j);
			}
			else break;
			before=j;
		}
		findMaxAndMix(nl);
	}
	
	public int[] findMaxAndMix(LinkedList<PixelRGB> l) {
		int n[]= {100000,0,100000,0};
		for (PixelRGB j : l) {
			if(n[0]>j.getPositionX())n[0]=j.getPositionX();
			if(n[1]<j.getPositionX())n[1]=j.getPositionX();
			if(n[2]>j.getPositionY())n[2]=j.getPositionY();
			if(n[3]<j.getPositionY())n[3]=j.getPositionY();
		}
		this.dy=(n[3]-n[2])/2+n[2];
		this.dx=(n[1]- n[0])/2+n[0];
		drawPoint(dx, dy,Color.GREEN);
		return n;
	}
	
	public double CalaterDistance()
    {
		window.drawLine((this.x+this.subbufferedImage.getWidth()/2), (this.y+108), this.dx, this.dy, Color.GREEN);
		MarvinImageIO.saveImage(window, this.newdis);
        return this.timefix*Math.sqrt(Math.pow(Math.abs(this.dx - (this.x+this.subbufferedImage.getWidth()/2)), 2) + Math.pow(Math.abs(this.dy - (this.y+108)), 2));
    }
	
	
//	PixelRGB p=new PixelRGB();
//	for(int i=0;i<subbufferedImage.getHeight();i++) {
//		for(int f=0;f<subbufferedImage.getWidth();f++) {
//			p.reset();
//			p.setRGB(subbufferedImage.getRGB(f,i));
//			p.printRGB();
//			System.out.print(" ");
//		}
//		System.out.println(" ");
//	}
//	
	
}
