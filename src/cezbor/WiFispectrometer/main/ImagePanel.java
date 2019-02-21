package cezbor.WiFispectrometer.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = -6677138348675347141L;
	//configurable
	private static final int ORIGINAL_IMG_WIDTH = 3264;
	public static final int ORIGINAL_IMG_HEIGHT = 2448;
	private static final int SCALING = 5;
	private static final int SPACER = 3;
	//
	private static final Dimension PANEL_SIZE = new Dimension(new Dimension(ORIGINAL_IMG_WIDTH / SCALING + 2, 
			ORIGINAL_IMG_HEIGHT / SCALING + 20 + 2 + SPACER));
	
	private BufferedImage image;
	private int y0 = 948;	//1057, 948, 1048

	public ImagePanel()
	{
		setEmptyImage();
    	setPreferredSize(PANEL_SIZE);
    	mouseListener();
	}
	
    public ImagePanel(File file) 
    {
    	update(file);
    	mouseListener();
    }
    
    public ImagePanel(BufferedImage newImage) 
    {
    	image = newImage;
    	setPreferredSize(PANEL_SIZE);
    	mouseListener();
    }

	private void setEmptyImage()
	{
		image = new BufferedImage(ORIGINAL_IMG_WIDTH, ORIGINAL_IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
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
    	setPreferredSize(PANEL_SIZE);
    	repaint();
    }

    public BufferedImage getSubimage(BufferedImage image)
    {
    	BufferedImage newimg = image.getSubimage(0, y0, ORIGINAL_IMG_WIDTH, 20);
    	return newimg;
    }
    
    private void mouseListener()
    {
    	addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e)
            {
            	try
            	{
            		if (getMousePosition().y * SCALING + 20 < ORIGINAL_IMG_HEIGHT)
            		{
                    	y0 = getMousePosition().y * SCALING;
                        repaint();
                        System.out.println("drag " + getMousePosition());
            		}
            	}
            	catch (NullPointerException exception) 
            	{
					//System.err.println("outside raster " + y0);
				}
            	super.mouseDragged(e);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, this);
        //g.drawImage(image, 0, 0, image.getWidth() / SCALING, image.getHeight() / SCALING, this);
        //g.drawImage(image, 0, 0, ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING, this);
        g.drawImage(image, 1, 0, ORIGINAL_IMG_WIDTH / SCALING, ORIGINAL_IMG_HEIGHT / SCALING, this);
        g.setColor(new Color(255, 0, 0));
        g.drawRect(0, y0 / SCALING, ORIGINAL_IMG_WIDTH / SCALING + 1, 20 / SCALING);
        
        g.drawImage(getSubimage(image), 
        		1, 
        		ORIGINAL_IMG_HEIGHT / SCALING + SPACER, 
        		ORIGINAL_IMG_WIDTH / SCALING + 1, 
        		20, //TODO read this from other class
        		this);
        g.drawRect(0, ORIGINAL_IMG_HEIGHT / SCALING + SPACER - 1, ORIGINAL_IMG_WIDTH / SCALING + 1, 20 + 1);
    }

	public void setY0(int y0)
	{
		this.y0 = y0;
		repaint();
	}

	public int getY0()
	{
		return y0;
	}

}
