package services;

import models.bean.ProcessResultData;

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

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
            System.out.println(process.exitValue());
            return new ProcessResultData(lines, process.exitValue());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            JTextArea ta = new JTextArea(10, 10);
            ta.setText(command + " " + e.getMessage() + " " + e.getCause().toString());
            ta.setWrapStyleWord(true);
            ta.setLineWrap(true);
            ta.setCaretPosition(0);
            ta.setEditable(false);

            JOptionPane.showMessageDialog(null, new JScrollPane(ta), "RESULT", JOptionPane.INFORMATION_MESSAGE);

            return null;
        }
    }
}
