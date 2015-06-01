package gifgen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecUtils {
	public static String executeCommand(String command) {
		System.out.println("Executing command: " + command);
		StringBuffer stdOut = new StringBuffer();
		StringBuffer stdErr = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			stdOut = getInputStream(p.getInputStream());
			stdErr = getInputStream(p.getErrorStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Got stdout: " + stdOut.toString());
		System.out.println("Got stderr: " + stdErr.toString());
		return stdOut.toString();
 
	}

	public static StringBuffer getInputStream(InputStream inputStream) throws IOException{
		StringBuffer output = new StringBuffer();
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(inputStream));
		String line = "";			
		while ((line = reader.readLine())!= null) {
			output.append(line + "\n");
		}
		return output;
	}
}
