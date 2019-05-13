package cezbor.WiFispectrometer.main;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 3245153192412275385L;
	private static final String serializationFilename = "lastImg.ser";
	private File imgFile;
	
	private ImageFrame imageFrame;
	private ImagePanel imagePanel;
	private JButton takePhotoButton;
	private JButton getLastPhotoButton;
	//private JLabel analyzeImageSizeLabel; 	//TODO delete
	private JButton getChartButton;
	private JButton getChartButton2;
	private JButton optionsButton;
	private Chart chart;
	
	//private int y0 = 948;	//1057, 948, 1048		//TODO delete - moved
	//private int numOfPxToAnalize = 10;
	
	public Frame() throws HeadlessException
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 620); 
		//setLocationByPlatform(true);
		setLocationRelativeTo(null); 	//center
		setTitle("Spektrometr");
		setSystemLookAndFeel();
		imgFile = deserialize();
    	setLayout(new FlowLayout());
	}
	
	private void constructMembers()
	{
		imagePanel = new ImagePanel(imgFile, this);
		
    	takePhotoButton = new JButton("Zrob zdjecie");
    	takePhotoButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				File imgFileTemp = Camera.takeAndGetPhoto();
				if (imgFileTemp != null)
				{
					imgFile = imgFileTemp;
					imagePanel.update(imgFile);
					serialize(imgFile);
				}
			}
		});
    	
    	getLastPhotoButton = new JButton("Otworz zdjecie");
    	getLastPhotoButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(imgFile);
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					imgFile = fc.getSelectedFile();
					imagePanel.update(imgFile);
				}
				
				
			}
		});
    	
    	//String analyzeImageSizeLabelText = "Rozmiar obrazu: ";	//TODO delete
    	//analyzeImageSizeLabel = new JLabel(analyzeImageSizeLabelText);
    	
    	getChartButton = new JButton("Rysuj wykres A");
    	getChartButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				makeChart("A");
			}
		});
    	
    	getChartButton2 = new JButton("Rysuj wykres B");
    	getChartButton2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				makeChart("B");
			}
		});
    	
    	
    	
    	optionsButton = new JButton("Dostosuj obszar pomiarowy");
    	optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				imageFrame = new ImageFrame(imagePanel);
				imageFrame.setVisible(true);

			}
		});
    	
    	
    	add(takePhotoButton);
		add(getLastPhotoButton);
    	add(getChartButton);
    	add(getChartButton2);
		//add(imagePanel);
		//add(spinnerLabel);
		//add(spinner);
		//add(analyzeImageSizeLabel);	//TODO delete
		add(optionsButton);
		
		chart = new Chart();
    	add(chart.getChartPanel());
		
	}

	public void setSpinnerValue(int value)
	{
		imageFrame.setSpinnerValue(value);
	}

	private void makeChart(Comparable<String> seriesKey)
	{
		try
		{
			ImageHandler ih = new ImageHandler(imgFile);
			ih.convertToRGBArray(0, imagePanel.getY0(), ih.getWidth(), imagePanel.getNumOfPxToAnalize());
			float[] luminanceArray = ih.convertRGBToLuminance();
			
	    	chart.generateSeries(luminanceArray, seriesKey);
	    	
			//String sizeText = ih.getWidth() + "x" + ih.getHeight();	//TODO delete
			//analyzeImageSizeLabel.setText(analyzeImageSizeLabelText + sizeText);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Drawing chart error - no image loaded?");
		}
	}
	
	public static void main(String[] args)
	{
		Frame frame = new Frame();
		frame.constructMembers();
		frame.setVisible(true);
		//frame.imgFile = new File("C:\\Users\\Czarek\\Desktop\\cam\\13.04.2018\\20180221_143141A_halogen.jpg");
		//frame.serialize(frame.imgFile);
	}
	
	private void setSystemLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1)
		{
			e1.printStackTrace();
		}
	}
	
	private void serialize(Object obj)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(serializationFilename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
			System.out.println("serialized to: " + serializationFilename);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private File deserialize()
	{
		File deserializedFile;
		try
		{
			FileInputStream fileIn = new FileInputStream(serializationFilename);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        deserializedFile = (File) in.readObject();
	        in.close();
	        fileIn.close();
			System.out.println("deserialized from: " + serializationFilename);
			return deserializedFile;
		}
		catch (IOException | ClassNotFoundException e)
		{
			//e.printStackTrace();
			System.err.println("deserialization failed - no file: " + serializationFilename);
			return null;
		}
	}

}
