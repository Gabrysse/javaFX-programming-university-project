import com.thoughtworks.xstream.*;

public class ParametriConfigurazioneMeteoXML { //00
    private final String IPCLIENT_DEFAULT = "127.0.0.1", //01
                         IPSERVERLOG_DEFAULT = "127.0.0.1", 
                         FONT_DEFAULT = "Segoe UI", 
                         COLORE_BACKGROUND_DEFAULT = "WHITE";
    private final int PORTASERVERLOG_DEFAULT = 4242,  //01
                      NUMERORIGHE_TABELLAMETEO_DEFAULT = 15,
                      PRECISIONE_GRAFICOANDAMENTO_DEFAULT = 1;
    private final double DIMENSIONE_FONT_DEFAULT = 15.0; //01
    
    private String IPClient, IPServerLog, font, colore_background; //02
    private int portaServerLog, numeroRighe_TabellaMeteo, precisione_graficoAndamento;
    private double dimensione_font;

    public ParametriConfigurazioneMeteoXML(String xml) { //03
        if(xml == null || xml.equals("")){ //04
            IPClient = IPCLIENT_DEFAULT;
            IPServerLog = IPSERVERLOG_DEFAULT;
            portaServerLog = PORTASERVERLOG_DEFAULT;
            font = FONT_DEFAULT;
            dimensione_font = DIMENSIONE_FONT_DEFAULT;
            colore_background = COLORE_BACKGROUND_DEFAULT;
            numeroRighe_TabellaMeteo = NUMERORIGHE_TABELLAMETEO_DEFAULT;
            precisione_graficoAndamento = PRECISIONE_GRAFICOANDAMENTO_DEFAULT;
        }
        else{
            XStream xs = new XStream(); //05
            xs.useAttributeFor(ParametriConfigurazioneMeteoXML.class, "IPClient"); //06
            xs.useAttributeFor(ParametriConfigurazioneMeteoXML.class, "IPServerLog"); //06
            xs.useAttributeFor(ParametriConfigurazioneMeteoXML.class, "portaServerLog"); //06
            ParametriConfigurazioneMeteoXML p = (ParametriConfigurazioneMeteoXML)(xs.fromXML(xml)); //07
            IPClient = p.IPClient;
            IPServerLog = p.IPServerLog;
            portaServerLog = p.portaServerLog;
            font = p.font;
            dimensione_font = p.dimensione_font;
            colore_background = p.colore_background;
            numeroRighe_TabellaMeteo = p.numeroRighe_TabellaMeteo;
            precisione_graficoAndamento = p.precisione_graficoAndamento;
        }
    }
    
    //######################### METODI GET/SET #################################

    public String getIPClient() {return IPClient;} //08

    public String getIPServerLog() {return IPServerLog;} //09

    public String getFont() {return font;} //10

    public String getColore_background() {return colore_background;} //11

    public int getPortaServerLog() {return portaServerLog;} //12

    public int getNumeroRighe_TabellaMeteo() {return numeroRighe_TabellaMeteo;} //13

    public double getDimensione_font() {return dimensione_font;} //14
    
    public int getPrecisione_GraficoAndamentoStorico(){return precisione_graficoAndamento;} //15
}

/* NOTE

################################################################################

(00)Classe che contiene i parametri di configurazione dell'applicazione presi dal
    relativo file XML (config.xml)

    Parametri disponibili:
    -IP del client
    -IP del server di log
    -Porta del server di log
    -Famiglia del font
    -Dimensione del font
    -Colore del background dell'applicazione
    -Numero di righe visualizzate nella tabella dei dati meteo
    -Precisione della scala dei valori del grafico

################################################################################

(01)Variabili contenenti i valori di default dei vari parametri
(02)Variabili che conterranno i valori dei parametri ottenuti dal file XML

--------------------------------------------------------------------------------

(03)Costruttore della classe
    
    04)Se la variabile xml passata al costruttore è vuoto o è null, vuol dire che
       vi è stato un errore in fase di apertura/lettura del file XML e quindi
       si procede con il settaggio dei paramentri di default.
    05)Se la variabile xml quindi contiene qualcosa è necessario convertirla in 
       oggetto di tipo ParametriConfigurazioneMeteoXML. Per far questo creiamo l'
       oggetto XStream
    06)Di solito i dati vengono serializzati sottoforma di tag annidati. Con il
       metodo useAttributeFor si può specificare quali invece dovranno essere
       attributi
   07)Convertiamo la stringa xml in un oggetto mediante il metodo della classe
      XStream fromXML() che restituisce però un oggetto di tipo Object che quindi
      convertiamo in ParametriConfigurazioneMeteoXML.
      In seguito basta assegnare i valori contenenti in questo oggetti ai parametri
      attuali della classe.

--------------------------------------------------------------------------------

(08)Metodo get per restituire l'indirizzo IP del client

(09)Metodo get per restituire l'indirizzo IP del del server di log

(10)Metodo get per restituire la famiglia di font scelta

(11)Metodo get per restituire il colore di background

(12)Metodo get per restituire la porta del server di log

(13)Metodo get per restituire il numero di righe da visualizzare nalla tabella

(14)Metodo get per restituire la dimensione del font

(15)Metodo get per restituire la precisione del grafico

################################################################################

*/
