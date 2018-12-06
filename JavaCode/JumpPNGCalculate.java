import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

 
import javax.imageio.ImageIO;
 


public class JumpPNGCalculate {

 
    private final PixelRGB       rgbInfo                 = new PixelRGB();
 
    //截屏中游戏分数显示区域最下方的Y坐标，300是 1920x1080的值，根据实际情况修改
    private final int           gameScoreBottomY        = 160;
 
    //按压的时间系数，可根据具体情况适当调节
    private final double        pressTimeCoefficient    = 3.4;
 
    //按压的起始点坐标，也是再来一局的起始点坐标
    private final int           swipeX                  = 550;
 
    private final int           swipeY                  = 1580;
 
    //二分之一的棋子底座高度
    private final int           halfBaseBoardHeight     = 50;
 
    //棋子的宽度，从截屏中量取，自行调节
    private final int           halmaBodyWidth          = 36;
 
    private int maxY=0;//哪一行不一样到颜色最多
    
    
    public int[] getHalmaAndBoardXYValue(File currentImage) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(currentImage);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        System.out.println("宽度：" + width + "，高度：" + height);
        int halmaXSum = 0;
        int halmaXCount = 0;
        int halmaYMax = 0;
        int boardX = 0;
        int boardY = 0;
        //从截屏从上往下逐行遍历像素点，以棋子颜色作为位置识别的依据，最终取出棋子颜色最低行所有像素点的平均值，即计算出棋子所在的坐标
        for (int y = gameScoreBottomY; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                processRGBInfo(bufferedImage, x, y);
                int rValue = this.rgbInfo.getRValue();
                int gValue = this.rgbInfo.getGValue();
                int bValue = this.rgbInfo.getBValue();
                //根据RGB的颜色来识别棋子的位置，
//                if (rValue > 50 && rValue < 60 && gValue > 53 && gValue < 63 && bValue > 95 && bValue < 110)
                if (rValue > 35 && rValue < 60 && gValue > 40 && gValue < 55 && bValue > 60 && bValue < 90)
                {
                    halmaXSum += x;
                    halmaXCount++;
                    //棋子底行的Y坐标值
                    halmaYMax = y > halmaYMax ? y : halmaYMax;
                }
            }
        }
 
        if (halmaXSum != 0 && halmaXCount != 0)
        {
        		int maxXhelp=0;
        		int maxXStart=0;
            //棋子底行的X坐标值
            int halmaX = halmaXSum / halmaXCount;
            //上移棋子底盘高度的一半
            int halmaY = halmaYMax ;
            System.out.println("棋子低行位置："+halmaX+" "+halmaY);
            //从gameScoreBottomY开始
            for (int y = gameScoreBottomY; y < halmaY-halfBaseBoardHeight; y++)
            {
                processRGBInfo(bufferedImage, 11, 155);
                int lastPixelR = this.rgbInfo.getRValue();
                int lastPixelG = this.rgbInfo.getGValue();
                int lastPixelB = this.rgbInfo.getBValue();
                
//                findRGBinMan(bufferedImage,halmaX,halmaY);
//                int ManPixelR = this.rgbInfo.getRValue();
//                int ManPixelG = this.rgbInfo.getGValue();
//                int ManPixelB = this.rgbInfo.getBValue();
                
                
//                System.out.println("R:"+lastPixelR+"G:"+lastPixelG+"B:"+lastPixelB);
                //只要计算出来的boardX的值大于0，就表示下个跳板的中心坐标X值取到了。
//                if (boardX > 0)
//                {
//                    break;
//                }
                int boardXSum = 0;
                int boardXCount = 0;
                int startx=0;
                for (int x = 0; x < 500; x++)
                {
//                		System.out.println("checkingx:"+x+" y:"+y);
                    processRGBInfo(bufferedImage, x, y);
                    int pixelR = this.rgbInfo.getRValue();
                    int pixelG = this.rgbInfo.getGValue();
                    int pixelB = this.rgbInfo.getBValue();
                    //处理棋子头部比下一个跳板还高的情况
                    if (Math.abs(x - halmaX) < halmaBodyWidth)
                    {
                        continue;
                    }
 
                    //从上往下逐行扫描至下一个跳板的顶点位置，下个跳板可能为圆形，也可能为方框，取多个点，求平均值
                    if (Math.abs(pixelR - lastPixelR) > 20 || Math.abs(pixelG - lastPixelG) >20 || Math.abs(pixelB - lastPixelB) > 20)
                    {
//                    		System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGETingx:"+x+" y:"+y+" R:"+pixelR+"G:"+pixelG+"B:"+pixelB);
//                        if((Math.abs(ManPixelR-pixelR)>20)||(Math.abs(ManPixelG-pixelG)>20)||(Math.abs(ManPixelB-pixelB)>20)) {
	                    		boardXSum += x;
	                        boardXCount++;
	                        if(startx==0)startx=x;
//                        }
                    }
                }
 
                if (boardXSum > 0)
                {
                    boardX = boardXSum / boardXCount;
                    if(boardXCount>maxXhelp) {
                    		maxXhelp=boardXCount;
                    		maxY=y;
                    		maxXStart=startx;
                    }
                }
                
            }
            System.out.println("MaxY:"+maxY+" "+maxXhelp+" x:"+maxXStart);
            //按实际的角度来算，找到接近下一个 board 中心的坐标
//            boardY = (int) (halmaY - Math.abs(boardX - halmaX) * Math.abs(boardY1 - boardY2)
//                    / Math.abs(boardX1 - boardX2));
            boardY=maxY;
            boardX=(maxXhelp)/2+maxXStart;
            if (boardX > 0 && boardY > 0)
            {
                int[] result = new int[4];
                //棋子的X坐标
                result[0] = halmaX;
                //棋子的Y坐标
                result[1] = halmaY;
                //下一块跳板的X坐标
                result[2] = boardX;
                //下一块跳板的Y坐标
                result[3] = boardY;
                return result;
            }
        }
 
        return null;
    }
 
    /**
     * 无用
     * @param command
     */
    private void executeCommand(String command)
    {
        Process process = null;
        try
        {
            process = Runtime.getRuntime().exec(command);
            System.out.println("exec command start: " + command);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = bufferedReader.readLine();
            if (line != null)
            {
                System.out.println(line);
            }
            System.out.println("exec command end: " + command);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (process != null)
            {
                process.destroy();
            }
        }
    }
 
//    /**
//     * ADB获取安卓截屏
//     * 
//     * @author LeeHo
//     * @update 2017年12月31日 下午12:11:42
//     */
//    public void executeADBCaptureCommands()
//    {
//        for (String command : ADB_SCREEN_CAPTURE_CMDS)
//        {
//            executeCommand(command);
//        }
//    }
 
    /**
     * 跳一下
     *
     * @param distance
     * @author LeeHo
     * @update 2017年12月31日 下午12:23:19
     */
    public int doJump(double distance)
    {
        System.out.println("distance: " + distance);
        //计算按压时间，最小200毫秒
        int pressTime = (int) Math.max(distance * pressTimeCoefficient, 200);
        System.out.println("pressTime: " + pressTime);
        //执行按压操作
        String command = String.format("adb shell input swipe %s %s %s %s %s", swipeX, swipeY, swipeX, swipeY,
                pressTime);
        System.out.println(command);
        return pressTime;
//        executeCommand(command);
    }
 
//    /**
//     * 再来一局
//     * 
//     * @author LeeHo
//     * @update 2017年12月31日 下午12:47:06
//     */
//    private void replayGame()
//    {
//        String command = String.format("adb shell input tap %s %s", swipeX, swipeY);
//        executeCommand(command);
//    }
 
    /**
     * 计算点的距离
     * @param halmaX
     * @param halmaY
     * @param boardX
     * @param boardY
     * @return
     */
    public double computeJumpDistance(int halmaX, int halmaY, int boardX, int boardY)
    {
        return Math.sqrt(Math.pow(Math.abs(boardX - halmaX), 2) + Math.pow(Math.abs(boardY - halmaY), 2));
    }

 
//    /**
//     * 检查是否需要重新开局
//     * 
//     * @author LeeHo
//     * @update 2017年12月31日 下午1:39:18
//     */
//    public void checkDoReplay()
//    {
//        if (imageLength[0] > 0 && imageLength[0] == imageLength[1] && imageLength[1] == imageLength[2]
//                && imageLength[2] == imageLength[3] && imageLength[3] == imageLength[4])
//        {
//            //此时表示已经连续5次图片大小一样了，可知当前屏幕处于再来一局
//            Arrays.fill(imageLength, 0);
//            //模拟点击再来一局按钮重新开局
//            replayGame();
//        }
//    }
 
    /**
     * 获取指定坐标的RGB值
     *
     * @param bufferedImage
     * @param x
     * @param y
     * @author LeeHo
     * @update 2017年12月31日 下午12:12:43
     */
    private void processRGBInfo(BufferedImage bufferedImage, int x, int y)
    {
        this.rgbInfo.reset();
        int pixel = bufferedImage.getRGB(x, y);
        //转换为RGB数字  
        this.rgbInfo.setRValue((pixel & 0xff0000) >> 16);
        this.rgbInfo.setGValue((pixel & 0xff00) >> 8);
        this.rgbInfo.setBValue((pixel & 0xff));
    }
    
    /**
     * 寻找棋子脚下的颜色
     * @param bufferedImage
     * @param humanx
     * @param humany
     */
    private void findRGBinMan(BufferedImage bufferedImage,int humanx,int humany)
    {
    		setRBG(bufferedImage,this.rgbInfo,humanx,humany);
    		PixelRGB	rgbInfohuman= new PixelRGB();
    		setRBG(bufferedImage,rgbInfohuman,humanx,humany);
    		int ManPixelR = rgbInfohuman.getRValue();
        int ManPixelG = rgbInfohuman.getGValue();
        int ManPixelB = rgbInfohuman.getBValue();
        PixelRGB	rgbInfo1= new PixelRGB();
        PixelRGB	rgbInfo2= new PixelRGB();
        for(int i=10;i<35;i++) {
        		setRBG(bufferedImage,rgbInfo1,humanx,humany+i);
        		setRBG(bufferedImage,rgbInfo2,humanx+i,humany);
        		 int ManPixelR1 = rgbInfo1.getRValue();
             int ManPixelG1 = rgbInfo1.getGValue();
             int ManPixelB1 = rgbInfo1.getBValue();
             int ManPixelR2 = rgbInfo2.getRValue();
             int ManPixelG2 = rgbInfo2.getGValue();
             int ManPixelB2 = rgbInfo2.getBValue();
             if( (ManPixelR1==ManPixelR2)&&(ManPixelG1==ManPixelG2)&&(ManPixelB2==ManPixelB1)&&
            		 (ManPixelR!=ManPixelR2)&&(ManPixelG!=ManPixelG2)&&(ManPixelB!=ManPixelB1)
            		 ) {
            	 	this.rgbInfo.reset();
                 int pixel = bufferedImage.getRGB(humanx+i, humany);
                 //转换为RGB数字  
                 this.rgbInfo.setRValue((pixel & 0xff0000) >> 16);
                 this.rgbInfo.setGValue((pixel & 0xff00) >> 8);
                 this.rgbInfo.setBValue((pixel & 0xff));
                 return;
             }
        }
    }
    
    private void setRBG(BufferedImage bufferedImage,PixelRGB rgbInfox,int x,int y) {
    		rgbInfox.reset();
        int pixel = bufferedImage.getRGB(x, y);
        //转换为RGB数字  
        rgbInfox.setRValue((pixel & 0xff0000) >> 16);
        rgbInfox.setGValue((pixel & 0xff00) >> 8);
        rgbInfox.setBValue((pixel & 0xff));
    		
    }
    
}