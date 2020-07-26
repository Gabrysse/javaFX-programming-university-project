import java.io.*;
import java.nio.file.*;

public class GestoreFileMeteo { //00
    public static String leggiFile(String filePath){ //01
        try {
            byte[] contenutoFile = Files.readAllBytes(Paths.get(filePath)); //02
            return new String(contenutoFile); //03
        } catch (IOException ex) {System.err.println(ex.getMessage());}
        return null; //03
    }
    
    public static void scriviFile(String filePath, String contenuto, boolean append){ //04
        try {
            Files.write(Paths.get(filePath), contenuto.getBytes(), ((append == true)? StandardOpenOption.APPEND: StandardOpenOption.TRUNCATE_EXISTING)); //05
        } catch (IOException ex) {System.err.println(ex.getMessage());}
    }
    
    public static void svuotaFileXML(String filePath){ //06
        scriviFile(filePath, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", false); //07
    }
}

/* NOTE

################################################################################

(00)Classe per la lettura/scrittura di un file

################################################################################

(01)Metodo per la lettura del file
    - filePath, indica il path del file

    02)Vengono letti tutti i byte presenti nel file mediante l'utilizzo della 
       libreria nio.file
    03)Si restituisce sottoforma di stringa il contenuto letto oppure null se vi 
       sono stati degli errori.

--------------------------------------------------------------------------------

(04)Metodo per la scrittura sul file
    - filePath, indica il path del file
    - contenuto, è il contenuto da scrivere sul file
    - append, indica la modalità di apertura del file: se true la modalità sarà 
              "append" altrimenti "truncate"

    05)Si scrive sul file il contenuto scelto con la modalità indicata dalla
       variabile append mediante l'utilizzo della libreria nio.file

--------------------------------------------------------------------------------

(06)Metodo per svuotare un file XML.
    - filePath, indica il path del file

    07)Mediante l'uso del metodo scriviFile, viene scritto, in modalità truncate, 
       l'header del file xml

################################################################################
*/