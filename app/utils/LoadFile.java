package utils;

import java.awt.*;
import java.io.File;

public class LoadFile {


    public File openFile(String url){
        try{
            File htmlFile = new File(url);
            //Desktop.getDesktop().browse(htmlFile.toURI());
            return htmlFile;

        }catch(Exception e){
            System.out.println("ERROR! "+e.toString());
            return null;
        }
    }
}
