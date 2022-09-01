package utilityClasses;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static core.ui.ParallelDriver.getDriver;
import static java.lang.Thread.sleep;

public class GenericUtilities {

	public final static String TEST_ACRONYM = "T-";

	public enum OS {

		WINDOWS, LINUX

	};

	private static OS os = null;

	public static OS getOS() {
		if (os == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				os = OS.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux")
					|| operSys.contains("aix")) {
				os = OS.LINUX;
			}
		}
		return os;
	}

	public static Boolean isFileDownloaded(String fileName) {
		boolean flag = false;

		String dirPath = System.getProperty("user.dir") + "/target/downloads"; 
		File dir = new File(dirPath);

		File[] files = dir.listFiles();
		if (files.length == 0 || files == null) {
			flag = false;
		} else {
			for (File listFile : files) {
				if (listFile.getName().contains(fileName)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	
	/**
	 * @param length of the string needed
	 * @return a string of the length required
	 */
	public static String generateRandomString(int length){
		String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) { // length of the random string.
			int index = (int) (rnd.nextFloat() * saltChars.length());
			salt.append(saltChars.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static void handleAlert(boolean accept) {
		int i=0;
		while(i++<5)
		{
		if (accept){
			try{
				getDriver().switchTo().alert().accept();
			}
			catch(NoAlertPresentException ex){
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				continue;
			}
		}
		else{
			try{
				getDriver().switchTo().alert().dismiss();
			}
			catch (NoAlertPresentException ex){
				//logger.trace(ex.getMessage());
			}
		}
		}

	}

	public static String getCurrentUrl(){ return getDriver().getCurrentUrl(); }

	public static String getWindowTitle(){ return getDriver().getTitle(); }

	public static String getWindowHandle() { return getDriver().getWindowHandle(); }

	public static String getAlertMsg(){ return getDriver().switchTo().alert().getText().trim(); }

	public static void refreshPage() { getDriver().navigate().refresh(); }

	public static int generateRandomNumber(int ceiling){
		Random random = new Random();
		int number = random.nextInt(ceiling);
		if (number == 0)
			number = number + 1;
		return number;
	}

	public static void uploadFile(String filePath, WebElement element){
		File fileToUpload = new File(filePath);
		element.sendKeys(fileToUpload.getAbsolutePath());
	}

	//Method for converting json to objects
	public static <T> T readJsonFile(String filePath, Class<T> classOfT) {
		try {
			Reader file = Files.newBufferedReader(Paths.get(filePath));
			Gson gson = new Gson();
			T object = gson.fromJson(file, classOfT);
			file.close();
			return object;
		} catch (IOException e) {
			throw new RuntimeException(String.format("Error when reading json file, %s", e.getMessage()));
		}
	}

	public static String readFile(String fileLocation){
		try{
			Reader file = Files.newBufferedReader(Paths.get(fileLocation));
			return CharStreams.toString(file);
		}
		catch (IOException ex){
			throw new RuntimeException(String.format("Unable to read file. Error: %s", ex.getMessage()));
		}
	}

	public enum ReportTabs{
		MyReports,
		MyPOC,
		MyRemote,
		AllReports,
		Unprocessed,
		FollowUp,
		Wearables
	}
	
	
	/**
	 * The date generated is between the start and end years passed as parameters
	 *
	 * @param startYear to create random date from
	 * @param endYear to create random date up to
	 * @return random date string in the format yyyy-dd-mm
	 */
	public static String generateRandomDate(int startYear, int endYear) {
		 GregorianCalendar gc = new GregorianCalendar();

	        int year = randBetween(1900, 2020);

	        gc.set(Calendar.YEAR, year);

	        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

	        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

	        String dob = gc.get(Calendar.YEAR) + "-";
	        
	        if((gc.get(Calendar.MONTH) + 1) < 10) {
	        	dob = dob + "0" + (gc.get(Calendar.MONTH) + 1)  + "-";
	        } else {
	        	dob = dob + (gc.get(Calendar.MONTH) + 1)  + "-";
	        }
	        
	        if(gc.get(Calendar.DAY_OF_MONTH) < 10) {
	        	dob = dob + "0" + gc.get(Calendar.DAY_OF_MONTH);
	        } else {
	        	dob = dob + gc.get(Calendar.DAY_OF_MONTH);
	        }

	        return dob;

	}

	 public static int randBetween(int start, int end) {
	        return start + (int)Math.round(Math.random() * (end - start));
	    }
}


