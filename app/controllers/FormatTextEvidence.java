package controllers;

import models.Evidence;

public class FormatTextEvidence {



    public static String formataTexto(Evidence evidence){

        String[] linha;
        String formatado="";

        linha=evidence.getDescription().split("\n");

        for(int i = 0;i<linha.length;i++){
                formatado = formatado+"<p>" +linha[i]+"</p>";


        }

        System.out.println("FormatTextEvidence result: "+ formatado);
        return formatado;
    }

}
