package com.andruy.assistant.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.andruy.assistant.models.Email;
import com.andruy.assistant.services.EmailService;

public final class BashHandler extends Thread {
    private String line;
    private String report;
    private String[] input;
    private Process process;
    private List<String> output;
    private BufferedReader reader;
    private StringBuffer emailReport;

    /**
     * Executes a bash command straight away
     * @param input the command to execute
     */
    public BashHandler(String[] input) {
        this.input = input;
        output = new ArrayList<>();
    }

    /**
     * Executes bash script test.sh (without returning output)
     */
    public BashHandler(String report) {
        this.report = report;
        emailReport = new StringBuffer();
        input = new String[] { "./script.sh" };
        output = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void run() {
        execute();
        emailReport.append("Running script...\n");

        if (output.size() == 0) {
            emailReport.append("No output\n");
            System.out.println("Something went wrong");
            emailOutput();
        } else {
            for (int i = 0; i < output.size() - 1; i++) {
                if (output.get(i).startsWith("[d") && output.get(i).contains("ETA")) {
                    continue;
                }
                emailReport.append(output.get(i) + "\n");
            }
            emailReport.append("Completed: " + output.get(output.size() - 1));
            System.out.println("Task complete");
            emailOutput();
        }
    }

    public List<String> startAndReturnOutput() {
        execute();
        return output;
    }

    private void emailOutput() {
        new EmailService().sendEmail(
            new Email(
                System.getProperty("receiver"),
                "Script report for " + LocalDateTime.now().toString().substring(0, 16),
                report + "\n" + emailReport.toString()
            )
        );
    }

    private void execute() {
        try {
            process = Runtime.getRuntime().exec(input);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
