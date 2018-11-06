package utils;

import java.awt.*;
import java.io.File;

public class LoadFile {


    public String openFile(String url){
        try{
            File htmlFile = new File(url);
            Desktop.getDesktop().browse(htmlFile.toURI());
            return "ok";

        }catch(Exception e){
            System.out.println("ERROR! "+e.toString());
            return "error";
        }
    }
}
