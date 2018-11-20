package cezbor.WiFispectrometer.main;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 3245153192412275385L;
	private static final String serializationFilename = "lastImg.ser";
	private File imgFile;

	private int y0 = 1057;
	private int numOfPxToAvg = 20;
	
	public Frame() throws HeadlessException
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 600); 
		setLocationByPlatform(true);
		setTitle("Spektrometr");
		
		imgFile = deserialize();
    	ImagePanel imagePanel = new ImagePanel(imgFile);
		
    	JButton takePhotoButton = new JButton("Zrob zdjecie");
    	takePhotoButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				imgFile = Camera.takeAndGetPhoto();
				if (imgFile != null)
				{
					imagePanel.update(imgFile);
					serialize(imgFile);
				}
			}
		});
    	
    	JButton getLastPhotoButton = new JButton("Otworz zdjecie");
    	getLastPhotoButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(imgFile);
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					imgFile = fc.getSelectedFile();
				imagePanel.update(imgFile);
			}
		});
    	
    	String analyzeImageSizeLabelText = "Rozmiar obrazu: ";
    	JLabel analyzeImageSizeLabel = new JLabel(analyzeImageSizeLabelText);
    	
    	JButton getChartButton = new JButton("Rysuj wykres");
    	getChartButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				//File file = new File("C:\\Users\\Czarek\\Desktop\\cam\\13.04.2018\\20180221_143141A_halogen.jpg");
				//ImageHandler ih = new ImageHandler(file);
				ImageHandler ih = new ImageHandler(imgFile);
				//ih.convertToRGBArray(0, 1057, ih.getWidth(), 20);
				//ih.convertToRGBArray(0, 948, ih.getWidth(), 20);
				ih.convertToRGBArray(0, y0, ih.getWidth(), numOfPxToAvg);
				float[] luminanceArray = ih.convertRGBToLuminance();
				
		    	Chart chartPanel = new Chart(luminanceArray);
				chartPanel.drawChart(luminanceArray);
		    	
				String sizeText = ih.getWidth() + "x" + ih.getHeight();
				analyzeImageSizeLabel.setText(analyzeImageSizeLabelText + sizeText);;
			}
		});
    	
    	SpinnerModel spinnerModel =
    	        new SpinnerNumberModel(y0, //initial value
    	                               0,    //min
    	                               2448 - numOfPxToAvg, //max
    	                               1);   //step
    	JSpinner spinner = new JSpinner(spinnerModel);
    	JLabel spinnerLabel = new JLabel("wiersz pikseli: ");
    	spinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				y0 = (int)((JSpinner)e.getSource()).getValue();
			}
		});
    	
    	
    	setLayout(new FlowLayout());
    	add(takePhotoButton);
		add(getLastPhotoButton);
    	add(getChartButton);
		add(imagePanel);
		add(spinnerLabel);
		add(spinner);
		add(analyzeImageSizeLabel);
		//add(panel);
	    //setVisible(true);
	}
	
	public static void main(String[] args)
	{
		Frame frame = new Frame();
		frame.setVisible(true);
		//frame.imgFile = new File("C:\\Users\\Czarek\\Desktop\\cam\\13.04.2018\\20180221_143141A_halogen.jpg");
		//frame.serialize(frame.imgFile);
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
