import java.io.*;
import java.net.*;

public class ServerLogAttivitaMeteo { //00
    private static final String PATH_FILELOG = new File("xml/log.xml").getAbsolutePath(), 
                                PATH_XSDLOG = new File("xml/log.xsd").getAbsolutePath(); //01
    
    public static void main(String[] args) { //02
        System.out.println("Server log avviato! \n");
        try(ServerSocket server = new ServerSocket(4242)){ //03
            while(true){
                try(Socket s = server.accept(); //04
                    DataInputStream dis = new DataInputStream(s.getInputStream()); //05
                    ){
                    String log = dis.readUTF(); //06
                    System.out.println("LOG: " + log); //07
                    if(ValidatoreXML.valida(log, PATH_XSDLOG, false)){ //08
                        GestoreFileMeteo.scriviFile(PATH_FILELOG, log + "\n", true);
                    }
                }
            }
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
}

/*NOTE

################################################################################

(00)Classe che contiene il server di log che ciclicamente riceve log, li valida,
    e li aggiunge in coda al file log.xml

################################################################################

(01)Path del file log.xml e del relativo XML schema. Viene utilizzato il metodo
    getAbsolutePath per ottenere il path assoluto visto che questi file sono
    contenuti all'interno della cartela xml che si trova nella radice della cartella
    del progetto

--------------------------------------------------------------------------------

(02)Metodo main
    
    03)Creazione del socket sulla porta 4242 con coda di backlog di default (50)
    04)Il server si blocca in attesa di una connessione e appena arriva la accetta
    05)Flusso dati in input sul socket 
    06)Viene letto ciò che viene inviato sul socket e salvato nella stringa log
    07)Viene stampata a video la stringa log
    08)Viene validata la stringa log che contiene in formato XML il nuovo log da
       salvare. Se la valdazione va a buon fine, viene aggiunta in coda al file

################################################################################

*/