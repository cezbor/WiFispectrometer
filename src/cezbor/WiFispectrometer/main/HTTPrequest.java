package cezbor.WiFispectrometer.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HTTPrequest
{
	
	public static File get(String pathStr)
	{
		Path path = Paths.get(pathStr);
		Path fileNamePath = path.getFileName();
		URI uri = URI.create("http://" + Camera.IP + pathStr);
	    try (InputStream in = uri.toURL().openStream()) 
	    {
	    	Files.copy(in, fileNamePath, StandardCopyOption.REPLACE_EXISTING);
	    }
		catch (IOException e)
		{
			e.printStackTrace();
		}
	    Camera.debugPrint(pathStr);
	    return fileNamePath.toFile();
	}
	
}
