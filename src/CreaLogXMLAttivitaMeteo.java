import java.io.*;
import java.net.*;
import com.thoughtworks.xstream.*;

public class CreaLogXMLAttivitaMeteo { //00
    public static void crea(String nomeApp, String evento, String ipClient, String ipServer, int portaServer){ //01
        try(Socket s = new Socket(ipServer, portaServer); //02
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //03
            ){
            dos.writeUTF(creaStream().toXML(new LogAttivitaMeteo(nomeApp, ipClient, evento))); //04
        } catch (Exception e) {System.out.println(e.getMessage());}
    }
    
    private static XStream creaStream(){ //05
        XStream xs = new XStream(); //06
        xs.useAttributeFor(LogAttivitaMeteo.class, "nomeApplicazione"); //07
        xs.useAttributeFor(LogAttivitaMeteo.class, "IPClient"); //07
        xs.useAttributeFor(LogAttivitaMeteo.class, "timestamp"); //07
        xs.useAttributeFor(LogAttivitaMeteo.class, "nomeEvento"); //07
        return xs; //08
    }
}

/* NOTE

################################################################################

(00)Classe per la creazione del log e l'invio al server log

################################################################################

(01)Metodo che crea e invia il log al server sotto forma di xml
    - nomeApp, indica il nome dell'applicazione
    - evento, indica il nome dell'evento
    - ipClient, indica l'indirizzo IP del client presente nel file di configurazione
    - ipServer, indica l'indirizzo IP del server di log presente nel file di 
                configurazione
    - portaServer, indica la porta del server di log presente nel file di 
                   configurazione
    
    02)Viene creato un nuovo socket verso il server di log
    03)Flusso dati in uscita sul socket
    04)Creazione e trasformazione dell'oggetto LogAttivitaMeteo in XML e invio
       sul socket

--------------------------------------------------------------------------------

(05)Metodo di utilità per creare lo stream per la serializzazione

    06)Creazione dell'oggetto XStream
    07)Di solito i dati vengono serializzati sottoforma di tag annidati. Con il
       metodo useAttributeFor si può specificare quali invece dovranno essere
       attributi

################################################################################
*/
