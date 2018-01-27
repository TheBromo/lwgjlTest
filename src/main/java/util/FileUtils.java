package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileUtils {
    private FileUtils(){}

    public static String loadAsString(String file){
        String result="";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer ="";
            while ((buffer =reader.readLine())!=null){
                result += buffer +"\n";
            }
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result;


    }
}
