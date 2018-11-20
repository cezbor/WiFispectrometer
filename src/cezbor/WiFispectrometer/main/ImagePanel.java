package cezbor.WiFispectrometer.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	private BufferedImage image;
	private static final int ORIGINAL_IMG_WIDTH = 3264;
	private static final int ORIGINAL_IMG_HEIGHT = 2448;
	private static final int SCALING = 5;

	public ImagePanel()
	{
		setEmptyImage();
    	setPreferredSize(new Dimension(ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING));
	}
	
    public ImagePanel(File file) 
    {
    	update(file);
    }
    
    public ImagePanel(BufferedImage newImage) 
    {
    	image = newImage;
    	//setPreferredSize(new Dimension(ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING));
    	setPreferredSize(new Dimension(newImage.getWidth()*10, newImage.getHeight()*10));
    }

	private void setEmptyImage()
	{
		image = new BufferedImage(ORIGINAL_IMG_WIDTH / SCALING, 
				ORIGINAL_IMG_HEIGHT / SCALING, BufferedImage.TYPE_INT_RGB);
	}
    
    public void update(File file)
    {
    	//if (file == null) { System.err.println("no file"); return; }	//TODO delete - old error handling
    	try
		{
			image = ImageIO.read(file);
		}
		catch (IOException | IllegalArgumentException e)
		{
			System.err.println("no file - initializing empty");
			setEmptyImage();
			//e.printStackTrace();
		}
    	//setPreferredSize(new Dimension(image.getWidth() / SCALING, image.getHeight() / SCALING));
    	setPreferredSize(new Dimension(ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING));
    	repaint();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, this);
        //g.drawImage(image, 0, 0, image.getWidth() / SCALING, image.getHeight() / SCALING, this);
        //g.drawImage(image, 0, 0, ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING, this);
        g.drawImage(image, 0, 0, ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING, this);
    }

}
