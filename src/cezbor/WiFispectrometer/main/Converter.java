package cezbor.WiFispectrometer.main;

public class Converter
{
	private static final double x1 = 2932;
	private static final double x2 = 990;
	private static final double y1 = 663.5;
	private static final double y2 = 632.5;
	
	
	public static double convertPxToNanometers(double x)
	{
		double y;
		double a = (y2-y1) / (x2-x1);
		double b = y1 - a * x1;
		y = a * x + b;
		return y;
	}
	
	//TODO write function
	public void setCalibrationParameters(double wavelenght1, 
			double pixels1, double wavelenght2, double pixels2)
	{
		
	}
	
}
