package application.io;

import java.io.File;

public class DirectoryImpl implements DirectoryInterface {

	final static String ROOT_PATH = "C:/";
	@Override
	public String createDirectory(String path) {
		File theDir = new File(path);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    }
		    catch(SecurityException se){
		        //handle it
		    }
		    if(result) {
		        System.out.println("DIR created");
		    }
		}
		return theDir.getAbsolutePath();
	}

}
