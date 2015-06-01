package gifgen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecUtils {
    public static String executeCommand(String command) throws Exception {
        StringBuffer stdOut = new StringBuffer();
        StringBuffer stdErr = new StringBuffer();

        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        stdOut = getInputStream(p.getInputStream());
        stdErr = getInputStream(p.getErrorStream());

        if (stdErr.length() > 0){
            String excMessage = "Executing command " + command +
                                "failed. StdErr: " + stdErr;
            throw new Exception(excMessage);
        }
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
