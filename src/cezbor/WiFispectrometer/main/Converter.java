package cezbor.WiFispectrometer.main;

public class Converter
{
	private static double px1 = 2932;	//2932(1), 2962(2)
	private static double px2 = 990;
	private static double w1 = 663.5;
	private static double w2 = 532.5;
	
	public static double convertPxToNanometers(int px)
	{
		double a = (w2-w1) / (px2-px1);
		double b = w1 - a * px1;
		double y = a * px + b;
		//System.out.println("a=" + a + "\nb=" + b);
		return y;
	}
	
	public static void setCalibrationParameters(double wavelenght1, 
			double pixels1, double wavelenght2, double pixels2)
	{
		Converter.w1 = wavelenght1;
		Converter.w2 = wavelenght2;
		Converter.px1 = pixels1;
		Converter.px2 = pixels2;
	}

	public static double getPixels1()
	{
		return px1;
	}

	public static double getPixels2()
	{
		return px2;
	}

	public static double getWavelenght1()
	{
		return w1;
	}

	public static double getWavelenght2()
	{
		return w2;
	}
	
}
