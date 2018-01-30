package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileUtils {
    private FileUtils(){}

    public static String loadAsString(String file){
        StringBuilder result=new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer ="";
            while ((buffer =reader.readLine())!=null){
                result.append(buffer).append('\n');
            }
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result.toString();


    }
}
