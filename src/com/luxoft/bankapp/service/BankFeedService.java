package com.luxoft.bankapp.service;

import com.luxoft.bankapp.expeption.FeedException;
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

    ;


//   public void loadFeeds(String folder) throws IOException, FeedException {
//
//           File file = new File(folder);
//           FileInputStream fileInputStream2;
//           FileInputStream fileInputStream1;
//           String s = "";
//
//       if(file.exists()){
//           if(file.isDirectory()) {
//               String str[] = file.list();
//              for (int i = 0; i< str.length; i++){
//                 File file2 = new File (str[i]);
//                 fileInputStream2 =  new FileInputStream(file);
//    DataInputStream  dataInputStream2  = new DataInputStream(new BufferedInputStream(fileInputStream2));
//                  while (dataInputStream2.available() > 0) {
//                      s =  dataInputStream2.readUTF();
//                      parseFeed(s);
//                  }
//                  dataInputStream2.close();
//              }
//           }
//       else {
//               fileInputStream1 =  new FileInputStream(file);
//               DataInputStream  dataInputStream1  = new DataInputStream(new BufferedInputStream(fileInputStream1));
//               while (dataInputStream1.available() > 0) {
//                   s =  dataInputStream1.readUTF();
//                   parseFeed(s);
//               }
//               dataInputStream1.close();
//           }
//   }}
//


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
