package cezbor.WiFispectrometer.main;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageFrame extends JFrame
{
	private static final long serialVersionUID = -145912061623079422L;
	private JSpinner spinner;
	private JLabel spinnerLabel;
	private JSpinner spinner2;
	private JLabel spinnerLabel2;
	private JButton okButton;
	
	private ImagePanel imagePanel;

	public ImageFrame(ImagePanel imagePanel) throws HeadlessException
	{
		this.imagePanel = imagePanel;
		setSize(700, 620); 
		//fr.setLocationByPlatform(true);
		setLocationRelativeTo(null); 	//center
		setTitle("Opcje");
    	setLayout(new FlowLayout());
    	constructMembers();
    	
	}

	private void constructMembers()
	{
		SpinnerModel spinnerModel =
    	        new SpinnerNumberModel(imagePanel.getY0(), //initial value
    	                               0,    //min
    	                               imagePanel.ORIGINAL_IMG_HEIGHT - imagePanel.getNumOfPxToAnalize(), //max
    	                               1);   //step
    	spinner = new JSpinner(spinnerModel);
    	spinnerLabel = new JLabel("wiersz pikseli: ");
    	spinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				int y0 = (int)((JSpinner)e.getSource()).getValue();
				imagePanel.setY0(y0);
			}
		});
    	
    	
    	SpinnerModel spinnerModel2 =
    	        new SpinnerNumberModel(imagePanel.getNumOfPxToAnalize(), //initial value
    	                               1,    //min
    	                               20, //max
    	                               1);   //step
    	spinner2 = new JSpinner(spinnerModel2);
    	spinnerLabel2 = new JLabel("liczba pikseli do pomiaru: ");
    	spinner2.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				int value = (int)((JSpinner)e.getSource()).getValue();
				imagePanel.setNumOfPxToAnalize(value);
				imagePanel.repaint();
			}
		});
    	
    	
    	okButton = new JButton("     OK     ");
    	okButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ImageFrame.this.dispose();
			}
		});
    	dispose();
		add(imagePanel);
		add(spinnerLabel);
		add(spinner);
		add(spinnerLabel2);
		add(spinner2);
		add(okButton);
		
	}

	public void setSpinnerValue(int value)
	{
		spinner.setValue(value);
	}

}
