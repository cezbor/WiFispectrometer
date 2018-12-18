package cezbor.WiFispectrometer.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ImageHandler
{
	private BufferedImage image;
	private int[][] imageRGBArray;
	private float[] luminanceArray;

	public ImageHandler(File loadingFile) throws IOException
	{
		File file = loadingFile;
		if (file == null) throw new IOException();
		image = ImageIO.read(file);
	}
	
	public int[][] convertToRGBArray(int x0, int y0, int width, int height)
	{
		if (width + x0 > image.getWidth()) throw new ArrayIndexOutOfBoundsException();
		if (height + y0 > image.getHeight()) throw new ArrayIndexOutOfBoundsException();
		imageRGBArray = new int[height][width];

	    for (int row = 0; row < height; row++) {
	       for (int col = 0; col < width; col++) {
	    	   imageRGBArray[row][col] = image.getRGB(x0 + col, y0 + row);
	       }
	    }
	    return imageRGBArray;
	}
	
	public int[][] convertToRGBArray(int x0, int y0)
	{
		int width = image.getWidth() - x0;
		int height = image.getHeight() - y0;
		return convertToRGBArray(x0, y0, width, height);
	}
	
	public int[][] convertToRGBArray()
	{
		return convertToRGBArray(0, 0);
	}
	

	private float singleToLuminance(int rgb)
	{
		int red =   (rgb >> 16) & 0xFF;
		int green = (rgb >>  8) & 0xFF;
		int blue =  (rgb      ) & 0xFF;
		float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f);
		return luminance;
	}
	
	//TODO delete
	//Not used
	public BufferedImage convertToGrey(BufferedImage sourceImg)
	{
		BufferedImage newImage = new BufferedImage(sourceImg.getWidth(), sourceImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
		Graphics g = newImage.getGraphics();  
		g.drawImage( sourceImg, 0, 0, null );  
		g.dispose();
		return newImage;
	}
	
	//TODO delete
	//Not used
	private String singleToRGB(int rgb)
	{
		int alpha = (rgb >> 24) & 0xFF;
		int red =   (rgb >> 16) & 0xFF;
		int green = (rgb >>  8) & 0xFF;
		int blue =  (rgb      ) & 0xFF;
		String rgbString = "RGB: "+ rgb + " R:" + red + " G:" + green + " B:" + blue + " A:" + alpha;
		return rgbString;
	}
	
	
	public float[] convertRGBToLuminance()
	{
		int xLength = imageRGBArray[0].length;
		int yLength = imageRGBArray.length;
		luminanceArray = new float[xLength];
		for (int x = 0; x < xLength; x++)
		{
			luminanceArray[x] = 0;
			float sum = 0;
			for (int y = 0; y < yLength; y++)
			{
				sum += singleToLuminance(imageRGBArray[y][x]);
			}
			luminanceArray[x] = sum;
			System.out.println(luminanceArray[x]);
		}
		System.out.println("Image size: " + xLength + "x" + yLength);	//Image size
		return luminanceArray;
	}
	
	//TODO delete
	//Not used
	public float[] convertRGBToLuminanceWithAvg()
	{
		int xLength = imageRGBArray[0].length;
		int yLength = imageRGBArray.length;
		luminanceArray = new float[xLength];
		for (int x = 0; x < xLength; x++)
		{
			luminanceArray[x] = 0;
			float sum = 0;
			for (int y = 0; y < yLength; y++)
			{
				sum += singleToLuminance(imageRGBArray[y][x]);
			}
			luminanceArray[x] = sum / yLength;
			System.out.println(luminanceArray[x]);
		}
		System.out.println("Image size: " + xLength + "x" + yLength);	//Image size
		return luminanceArray;
	}
	
	//TODO delete
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600); 
		frame.setLocationByPlatform(true);
		//frame.setLayout(new FlowLayout());
		//frame.setVisible(true);
		
		//File file = new File("C:\\Users\\Czarek\\Desktop\\test.png");
		//File file = new File("C:\\Users\\Czarek\\Desktop\\220px-Spectrum.png");
		//File file = new File("C:\\Users\\Czarek\\Desktop\\220px-Linear_visible_spectrum.png");
		File file = new File("C:\\Users\\Czarek\\Desktop\\cam\\13.04.2018 zdjecia testowe 1048\\20180221_143141A_halogen.jpg");

		try
		{
			ImageHandler ih = new ImageHandler(file);
			ih.imageRGBArray = ih.convertToRGBArray(0, 1057, ih.image.getWidth(), 20);
			ih.convertRGBToLuminance();
			ImagePanel imagePanel = new ImagePanel(ih.image);
			frame.add(imagePanel);
			frame.setVisible(true);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		BufferedImage newImg = imageHandler.convertToGrey(imageHandler.image);
		result = imageHandler.convertToRGBArray(newImg);
		*/
		
		/*
		for (int i=0; i < result[0].length; i++)
		{
			//System.out.println(singleToRGB(result[0][i]));
			//System.out.println("Luminance: " + singleToLuminance(result[0][i]) );
			System.out.println(singleToLuminance(result[0][i]) + "  " + i);
		}*/
		//Old for
		/*
		float suma;
		for (int[] y : result)
		{
			suma = 0;
			for (int x : y)
			{
				suma += singleToLuminance(x);
				//System.out.println(singleToLuminance(x) );
			}
			System.out.println("suma: " + suma);
		}
		*/
		//old
		/*
		int newGreyRgb;
		for (int a : result[0])
		{
			newGreyRgb = (int) singleToLuminance(a);
			System.out.println(newGreyRgb);
		}
		BufferedImage newImg = new BufferedImage(result[0].length, result.length,BufferedImage.TYPE_BYTE_GRAY);
		byte [] newData = ((DataBufferByte) newImg.getRaster().getDataBuffer()).getData();
		for (int i=0; i < result[0].length; i++)
		{ 
			newImg.setRGB(0, 0, 2, 3, result[0], 0, 0);
		}*/
		
		
	}

	public int getWidth()
	{
		return image.getWidth();
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
}
