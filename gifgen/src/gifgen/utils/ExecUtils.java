package gifgen.utils;

import gifgen.exception.CommandExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecUtils {
    public static String executeCommand(String command) throws CommandExecutionException {
        StringBuffer stdOut = new StringBuffer();
        StringBuffer stdErr = new StringBuffer();

        Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			 try {
				p.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				throw new CommandExecutionException(e);
			}
		        stdOut = getInputStream(p.getInputStream());
		        stdErr = getInputStream(p.getErrorStream());

		        if (stdErr.length() > 0){
		            String excMessage = "Executing command " + command +
		                                "failed. StdErr: " + stdErr;
		            throw new CommandExecutionException(excMessage);
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CommandExecutionException(e);
		}
       
        return stdOut.toString();
 
    }

    public static StringBuffer getInputStream(InputStream inputStream) throws CommandExecutionException{
        StringBuffer output = new StringBuffer();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream));
        String line = "";            
        try {
			while ((line = reader.readLine())!= null) {
			    output.append(line + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CommandExecutionException(e);
		}
        return output;
    }
}
