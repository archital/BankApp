package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exception.FeedException;
import com.luxoft.bankapp.model.Bank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SCJP on 19.01.2015.
 */
public class BankFeedService {

    private Bank activeBank;

    public Bank getCurrentBank() {
        return activeBank;
    }

    public void setActiveBank(Bank activeBank) {
        this.activeBank = activeBank;
    }


    public void parseFeed(String feed) throws FeedException {

        Map<String, String> result = new HashMap();
        String[] lines = feed.split(";");
        for (String line : lines) {
            String title = line.split("=")[0];
            String value = line.split("=")[1];
            result.put(title, value);
        }
        activeBank.parseFeed(result);
    }




    public void loadFeeds(String folder) throws FeedException, IOException {
        File dir = new File(folder);

        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    parseFeed(line);
                }
            }
        }
    }
}
