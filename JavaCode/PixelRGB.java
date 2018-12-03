

/**
 * Object for each Pixel of RGB
 * @author Jifeng Zheng
 */
public class PixelRGB
{
    private int RValue;

    private int GValue;

    private int BValue;
    
    private int pixel;
    
    private int positionX;
    
    private int positionY;

    
    /**
     * @return the value of Red
     */
    public int getRValue()
    {
        return RValue;
    }
    
    /**
     * Set the Red value
     * @param bValue
     */
    public void setRValue(int rValue)
    {
        RValue = rValue;
    }
    
    /**
     * @return the value of Green
     */
    public int getGValue()
    {
        return GValue;
    }

    /**
     * Set the Green value
     * @param bValue
     */
    public void setGValue(int gValue)
    {
        GValue = gValue;
    }
    
    /**
     * @return the value of Blue
     */
    public int getBValue()
    {
        return BValue;
    }
    
    /**
     * Set the Blue value
     * @param bValue
     */
    public void setBValue(int bValue)
    {
        BValue = bValue;
    }
    
    /**
     * @return the Pixel in Integer form
     */
    public int getPixel() {
    	return this.pixel;
    }
    
    /**
     * return the position of X
     * @return
     */
    public int getPositionX() {
		return positionX;
	}
    
    /**
     * @return the position of Y
     */
	public int getPositionY() {
		return positionY;
	}
	
	/**
	 * Set the position of the pixel x and y
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
    	this.positionX=x;
    	this.positionY=y;
    }
    
	/**
	 * reset all the RGB private value to 0
	 */
    public void reset()
    {
        this.RValue = 0;
        this.GValue = 0;
        this.BValue = 0;
        this.pixel=0;
    }
    
    /**
     * Set the RGB with a integer value
     * @param pixel int
     */
    public void setRGB(int pixel) {
    	this.pixel=pixel;
    	this.setRValue((pixel & 0xff0000) >> 16);
        this.setGValue((pixel & 0xff00) >> 8);
        this.setBValue((pixel & 0xff));
    }
    
    /**
     * if the Pixel is same as other return true,
     * otherwise return false 
     * @param other
     * @param x
     * @return
     */
    public boolean compara(PixelRGB other, int x) {
    	if(Math.abs(this.getRValue()-other.getRValue())>x) return false;
    	else if(Math.abs(this.getBValue()-other.getBValue())>x) return false;
    	else if(Math.abs(this.getGValue()-other.getGValue())>x) return false;
    	return true;
    }
    
    /**
     * if the distant between the pixel and pixel is not > x 
     * will return true，otherwise return false
     * @param other
     * @param x
     * @return
     */
    public boolean comparaPosition(PixelRGB other, int x) {
    	if((Math.abs(this.getPositionX()-other.getPositionX())>x)&&(Math.abs(this.getPositionY()-other.getPositionY())>x)) return false;
    	return true;
    }
    
    /**
     * if it is 000 or 255 for all RGB will return true
     * @return
     */
    public boolean isBlank() {
    	if(this.getRValue()==0&&this.getGValue()==0&&this.getBValue()==0)return true;
    	else if(this.getRValue()==255&&this.getGValue()==255&&this.getBValue()==255)return true;
    	return false;
    }
    
    /**
     * print out the Red Blue Green for this pixel
     */
    public void printRGB() {
//    	System.out.print(this.getRValue()+" "+this.getGValue()+" "+this.getBValue()+" \t");
    	System.out.printf("%3s", this.getRValue());
    	System.out.printf("%4s", this.getGValue());
    	System.out.printf("%4s", this.getBValue());
    }
}
