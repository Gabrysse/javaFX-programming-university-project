import java.io.*;

public class SalvaCaricaCacheMeteo { //00
    public static void salva(InterfacciaMeteo gui, String filePath){ //01
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))){ //02
            oos.writeObject(new CacheMeteo(gui)); //03
        } catch (IOException e) {System.err.println(e.getMessage());}
    }
    
    public static CacheMeteo carica(String filePath){ //04
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(filePath))){ //05
            return (CacheMeteo)oin.readObject(); //06
        } catch (IOException | ClassNotFoundException e) {System.err.println(e.getMessage());}
        return null; //06
    }
}

/* NOTE

################################################################################

(00)Classe per gestire il salvataggio e il caricamento la cache locale degli input

################################################################################

(01)Metodo per il salvataggio della cache
    - gui, oggetto della classe InterfacciaMeteo per ottenere i valori dei campi
           da salvare
    - filePath, è il path del file di cache

    02)Flusso oggetto agganciato ad un flusso file per salvare la cache
    03)Scrittura dell'oggetto CacheMeteo sul file

--------------------------------------------------------------------------------

(04)Metodo per il caricamento della cache
    - filePath, è il path del file di cache
    
    05)Flusso oggetto agganciato ad un flusso file per leggere la cache
    06)Restituzione dell'oggetto letto dal file, previa conversione in oggetto di 
       tipo CacheMeteo oppure null in caso di errore

################################################################################
*/
