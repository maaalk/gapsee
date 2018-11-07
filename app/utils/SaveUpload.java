package utils;

import models.Evidence;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveUpload {

    private File evidenceFile;
    private Evidence evidence;

    //endereco do local de salvamento
    private final String directoryAdress="C:\\Users\\Talita\\Desktop\\gapsee\\";

    public SaveUpload(File evidenceFile, Evidence evidence){
        this.evidenceFile = evidenceFile;
        this.evidence = evidence;
    }

    public String trasnformFile(){
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

}
