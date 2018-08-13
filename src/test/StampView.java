package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class StampView extends JComponent {
	
	@Override
	public void paint(Graphics g) {
		StampImage stampImage=new StampImage(300, 300);
		stampImage.setTopCircleText("中国艺术网文化传播委员会");
		stampImage.setCenterText("某某委员会");
		InputStream is=this.getClass().getResourceAsStream("/star.png");
		BufferedImage centerImg;
		try {
			centerImg = ImageIO.read(is);
			stampImage.setCenterImage(centerImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stampImage.setBottomCircleText("201404221234");
		g.drawImage(stampImage.create(), 0, 0, null);
		
	}
}
