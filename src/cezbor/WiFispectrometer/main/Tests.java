package cezbor.WiFispectrometer.main;

import java.io.File;

public class Tests
{
	public Tests()
	{
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		testSQLiteJDBC();
		testLoadImage();
	}
	

    
    public static void testSQLiteJDBC() 
    {
    	SQLiteJDBC app = new SQLiteJDBC();
    	System.out.println(app.getLatest());
    }

    public static void testLoadImage()
	{
//		//Frame frame = new Frame();
//		//frame.setVisible(true);
//		File file = new File("C:\\Users\\Czarek\\Desktop\\test.png");
//		ImageHandler imageLoader = new ImageHandler(file);
//		int[][] result = imageLoader.convertToRGBArray();
//		
//		for (int i=0; i<6; i++)
//		{
//			rgbConvert(result[0][i]);
//		}
//		
	}
    
}
