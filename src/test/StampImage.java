package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class StampImage  {
	private final int width,height;
	private final int circleRadius;
	private Font topCircleTextFont= new Font("宋体",Font.PLAIN,30);
	private Font bottomCircleTextFont=new Font("宋体",Font.PLAIN,30);
	private Font centerTextFont=new Font("宋体",Font.PLAIN,30);
	private String topCircleText,bottomCircleText,centerText;
	private BufferedImage centerImage;
	private final BufferedImage result;
	public StampImage(int width, int height) {
		result=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width=width;
		this.height=height;
		this.circleRadius=Math.min(width-5,height-5)/2;
	}
	
	public void setTopCircleText(String txt) {
		this.topCircleText=txt;
	}
	public void setBottomCircleText(String txt) {
		this.bottomCircleText=txt;
	}
	public void setCenterText(String txt) {
		this.centerText=txt;
	}
	public void setCenterImage(BufferedImage img) {
		this.centerImage=img;
	}
	/**
	 * 有限责任公司印章一律为圆形，直径为4.0cm，专用章和公司所属部门印章直径为3.8cm，圆边宽为0.1cm，中央刊五角星，五角星外刊企业名称，自左而右环行，或者名称前段自左而右环行，后段自左而右横行，印文使用简化的宋体字。
	 * @return
	 */
	public BufferedImage create() {
		drawStamp();
		return result;
	}
	private void drawStamp() {
	 Graphics2D g=result.createGraphics();
	 //设置画笔
     g.setPaint(Color.WHITE);
     g.fillRect(0, 0, width,height);
     //01draw circle
     drawCircle(g);
     //02draw top circle text 
     drawTopCircleText(g);
     //draw star
     drawStar(g);
     //draw centerText
     drawCenterText(g);
    //draw bottom circle text
     drawBottomCircleText(g);
	}
	
	private void drawCircle(Graphics2D g) {
		 g.setPaint(Color.RED);
	     g.setStroke(new BasicStroke(5));//设置画笔的粗度
	     Shape circle = new Arc2D.Double(0,0,circleRadius*2,circleRadius*2,0,360,Arc2D.OPEN);
	     g.draw(circle);
	}
	private void drawTopCircleText(Graphics2D g) {
		 g.setFont(topCircleTextFont);
	     FontRenderContext context = g.getFontRenderContext();
	     Rectangle2D bounds = topCircleTextFont.getStringBounds(topCircleText,context);
	     double msgWidth = bounds.getWidth();
	     int circleTxtAmount = topCircleText.length();
	    //计算间距
	     double interval = msgWidth/(circleTxtAmount-1);


	     double newRadius = circleRadius + bounds.getY()-5;//bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
	     double radianPerInterval = 2 * Math.asin(interval / (2 * newRadius));//每个间距对应的角度

	     //第一个元素的角度
	     double firstAngle;
	     if(circleTxtAmount % 2 == 1){//奇数
	         firstAngle = (circleTxtAmount-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
	     }else{//偶数
	         firstAngle = (circleTxtAmount/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
	     }

	     for(int i = 0;i<circleTxtAmount;i++){
	         double aa = firstAngle - i*radianPerInterval;
	         double ax = newRadius * Math.sin(Math.PI/2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
	         double ay = newRadius * Math.cos(aa-Math.PI/2);//同上类似，这样处理就不必再考虑正负的问题了
	         AffineTransform transform = AffineTransform .getRotateInstance(Math.PI/2 - aa);// ,x0 + ax, y0 + ay);
	         g.setFont(topCircleTextFont.deriveFont(transform));
	         g.drawString(topCircleText.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius - ay));
	     }
	}
	private void drawStar(Graphics2D g) {
		//01计算圆心
		int w=100;
		int h=100;
		int x=(width-w)/2;
		int y=(height-h)/2;
		g.drawImage(centerImage, x,y,w,h,null);
	}
	private void drawCenterText(Graphics2D g) {
		 g.setFont(centerTextFont);
	     Rectangle2D  centerTextBounds = centerTextFont.getStringBounds(centerText,g.getFontRenderContext());
	     g.drawString(centerText, (float) (circleRadius - centerTextBounds.getCenterX()), (float) (circleRadius*1.5 - centerTextBounds.getCenterY()));
	     
	}
	
	private void drawBottomCircleText(Graphics2D g) {
		
	}
	
	

}
