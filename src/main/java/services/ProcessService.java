package services;

import models.bean.ProcessResultData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessService {

    /**
     * Gives us a subproces, i.e. we can run commands in terminal
     * @param command
     * @return
     */
    public static ProcessResultData executeCommand(String command) {
        try{
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            ArrayList<String> lines = new ArrayList<>();
            while ((line = stdInput.readLine()) != null) {
                lines.add(line);
            }

            process.waitFor();
            stdInput.close();
            return new ProcessResultData(lines, process.exitValue());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
