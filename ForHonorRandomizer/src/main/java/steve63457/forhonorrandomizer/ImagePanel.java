package steve63457.forhonorrandomizer;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(Image img) {
		setImage(img);
	}
	
	public void setImage(Image img) {
		this.img = img;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double sfWidth = new Double(this.getWidth()) / img.getWidth(null);
		double sfHeight = new Double(this.getHeight()) / img.getHeight(null);
		double sf = sfWidth > sfHeight ? sfHeight : sfWidth;
		
		int width = new Double(img.getWidth(null) * sf).intValue();
		int height = new Double(img.getHeight(null) * sf).intValue();
		int xOffset = new Double((1.0 * this.getWidth()-width) / 2).intValue();
		int yOffset = this.getHeight()-height;
		
//		//debug
//		System.out.println("*********");
//		System.out.println("Window width=" + this.getWidth() + ", height=" + this.getHeight());
//		System.out.println("Src image width=" + img.getWidth(null) + ", height=" + img.getHeight(null));
//		System.out.println("sfWidth=" + sfWidth + ", sfHeight=" + sfHeight + ", sf=" + sf);
//		System.out.println("Calculated width=" + width + ", height=" + height);
		
		g.drawImage(img, xOffset, yOffset, xOffset+width, yOffset+height, 
				0, 0, img.getWidth(null), img.getHeight(null), null, this);
	}

}