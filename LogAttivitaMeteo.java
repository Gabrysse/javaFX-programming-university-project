import java.io.*;
import java.text.*;
import java.util.*;

public class LogAttivitaMeteo implements Serializable{ //00
    private final String nomeApplicazione, IPClient, timestamp, nomeEvento;

    public LogAttivitaMeteo(String nome, String ip, String evento) { //01
        nomeApplicazione = nome;
        IPClient = ip;
        nomeEvento = evento;
        timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()); //02
    }
}

/* NOTE

################################################################################

(00)Classe per costruire il log da inviare al server

################################################################################

(01)Costruttore della classe

    02)Per ottenere il timestamp dell'istante attuale si istanzia un oggetto di
       tipo Date(), che però restituisce i millisecondi. Per formattarli si crea
       un oggetto di tipo SimpleDateFormat e si utilizza il suo metodo format per
       ottenere la formattazione desiderata

################################################################################

*/
