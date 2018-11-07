package utils;

import models.Evidence;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {


    public static String savaFile(File evidenceFile, Evidence evidence){
        //endereco do local de salvamento
        final String directoryAdress="C:\\Users\\Talita\\Desktop\\gapsee\\";

        try {
            FileInputStream fileInputStream = new FileInputStream(evidenceFile);
            long byteLength = evidenceFile.length();
            byte[] fileContent = new byte[(int) byteLength];
            fileInputStream.read(fileContent, 0, (int) byteLength);
            Files.write(Paths.get(directoryAdress+evidence.getFileName()), fileContent );

            return directoryAdress+evidence.getFileName();
        }catch(Exception e){
            System.out.println("Error! "+e.toString());
            return "error";
        }
    }

    public static File openFile(String url){
        try{
            File htmlFile = new File(url);
            return htmlFile;

        }catch(Exception e){
            System.out.println("ERROR! "+e.toString());
            return null;
        }

    }


}
