package cezbor.WiFispectrometer.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

public class Camera
{
	public static final String IP = "192.168.100.1";
	private static final int PORT = 6666;
	private static final int BUFFER = 256;
	
	private static final String AUTHENTICATION = "abcd00810000011061646d696e00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003132333435000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	private static final String TAKE_PICTURE = "abcd00040000a03801000000";
	
	private static final boolean DEBUG_MODE_ON = false;
	
	private void connect() throws IOException
	{
		//Socket socket = new Socket(Camera.IP, Camera.PORT);
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(Camera.IP, Camera.PORT), 1000);
		
		debugPrint("<I> got: ");
		receive(socket);
		
		send(socket, Camera.AUTHENTICATION);
		debugPrint("<I> authentication");
  	   	
  	   	debugPrint("<I> got: ");
		receive(socket);
		
		send(socket, Camera.TAKE_PICTURE);
		debugPrint("<I> take picture");
		
		debugPrint("<I> got: ");
		receive(socket);
		
		socket.close();
	}
	
	private void receive(Socket socket) throws IOException
	{
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[Camera.BUFFER];
		inputStream.read(bytes);
		if (Camera.DEBUG_MODE_ON) 
		{
			String line = DatatypeConverter.printHexBinary(bytes);
			System.out.println(line);
		}
	}
	
	private void send(Socket socket, String message) throws IOException
	{
		OutputStream outputStream = socket.getOutputStream();
	   	byte[] bytes = DatatypeConverter.parseHexBinary(message);
	   	outputStream.write(bytes);
	   	outputStream.flush();
	}
	
	public static void debugPrint(String string)
	{
		if (Camera.DEBUG_MODE_ON) 
			System.out.println(string);
	}
	
	public static File takeAndGetPhoto()
	{
		Camera camera = new Camera();
		SQLiteJDBC sqlite = new SQLiteJDBC();
		File imgFile = null;
		
		try
		{
			camera.connect();
			TimeUnit.MILLISECONDS.sleep(900);	//550 za ma³o //600 - przy szybkim zgubione
			HTTPrequest.get("/data/sunxi.db");
			String imgRemotePath = sqlite.getLatest();
			imgFile = HTTPrequest.get(imgRemotePath);
			return imgFile;
			
		}
		catch (InterruptedException | IOException e)
		{
			//e.printStackTrace();
			System.err.println("Connection error");
			return null;
		}
		
		
	}
}
