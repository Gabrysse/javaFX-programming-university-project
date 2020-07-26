import java.io.*;
import java.time.*;
import java.util.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.application.*;
import javafx.scene.image.*;
import com.thoughtworks.xstream.*;

public class Meteo extends Application{ //00
    private InterfacciaMeteo gui;
    private ParametriConfigurazioneMeteoXML config;
    private final String PATH_FILECONFIG = new File("xml/config.xml").getAbsolutePath(), //01
                         PATH_FILEINPUT = new File("xml/input.xml").getAbsolutePath(),
                         PATH_XSDCONFIG = new File("xml/config.xsd").getAbsolutePath(),
                         PATH_XSDINPUT = new File("xml/input.xsd").getAbsolutePath(),
                         PATH_FILECACHE = "cache.bin";;
    
    private String dataSelezionata, fasciaSelezionata; //02
    private String periodoInizioSelezionato, periodoFineSelezionato, campioneSelezionato; //02

    @Override
    public void start(Stage stage){ //03
        if(ValidatoreXML.valida(PATH_FILECONFIG, PATH_XSDCONFIG, true)) //04
            config = new ParametriConfigurazioneMeteoXML(GestoreFileMeteo.leggiFile(PATH_FILECONFIG));
        else
            config = new ParametriConfigurazioneMeteoXML(null);
        
        CreaLogXMLAttivitaMeteo.crea("Meteo", "Apertura applicazione", config.getIPClient(), config.getIPServerLog(), config.getPortaServerLog()); //05

        gui = new InterfacciaMeteo(); //06
        gui.applicaStile(config); //07
        aggiungiEventiInterfaccia(); //08
        
        caricamentoDati(); //09
        
        aggiornaTabellaMeteo(); //10
        
        CacheMeteo c = SalvaCaricaCacheMeteo.carica(PATH_FILECACHE); //11
        if(c != null) gui.caricamentoCache(c); //12
          
        Group root = new Group(gui.getInterfacciaCompleta()); //13
        Scene scena = new Scene(root, 1550, 650); //14
        applicaStileFinestra(stage); //15
        stage.setOnCloseRequest(ev ->{ //16
                                        CreaLogXMLAttivitaMeteo.crea("Meteo", "Chiusura applicazione", config.getIPClient(), config.getIPServerLog(), config.getPortaServerLog());
                                        SalvaCaricaCacheMeteo.salva(gui, PATH_FILECACHE);
                                     });
        stage.setScene(scena); //17
        stage.show(); //18
    }
    
    public static void main(String[] args) { //19
        launch(args);
    }
    
    private void caricamentoDati(){ //20
        String contenutoFileInput = GestoreFileMeteo.leggiFile(PATH_FILEINPUT); //21
        if(contenutoFileInput.split("\r\n|\r|\n").length > 1){  //22
            if(ValidatoreXML.valida(PATH_FILEINPUT, PATH_XSDINPUT, true)){  //23
                if(GestoreDatabaseMeteo.inserisciVoci((ArrayList<DatiMeteo>)new XStream().fromXML(contenutoFileInput)) > 0) //24
                    GestoreFileMeteo.svuotaFileXML(PATH_FILEINPUT); //25
                else
                    System.err.println("Errore nel caricamento iniziale dei dati.");
            }
        }
        else
            System.err.println("Nessun dato presente nel file input.xml");
    }
    
    private void aggiungiEventiInterfaccia(){ //26
        evento_TabellaDati_selezioneRiga();
        evento_BATabellaDati_clickBottoneEliminaRiga();
        evento_BATabellaDati_selezioneData();
        evento_BATabellaDati_selezioneFascia();
        evento_BAAndamentoStorico_clickBottoneConferma();
        evento_BAAndamentoStorico_clickBottoneReset();
    }
    
    private void applicaStileFinestra(Stage stage){ //27
        stage.setTitle("Meteo"); //28
        stage.getIcons().add(new Image("file:icon.png")); //29
        stage.setResizable(false); //30
    }
    
    private void evento_TabellaDati_selezioneRiga(){ //31
        
        gui.getTabella_DatiMeteo().getTabellaDati().getSelectionModel().selectedItemProperty().
            addListener((obs, oldValue, newValue) -> { //32
                                                        if(newValue != null)
                                                            gui.getBarraAzioni_TabellaMeteo().disabilitaButtonEliminaRiga(false);
                                                      });
        
    }
    
    private void evento_BATabellaDati_clickBottoneEliminaRiga(){ //33
        
        gui.getBarraAzioni_TabellaMeteo().getButtonElimina_Riga().
            setOnAction((ActionEvent ev) -> {
                                                CreaLogXMLAttivitaMeteo.crea("Meteo", "Click pulsante Elimina riga selezionata", config.getIPClient(), config.getIPServerLog(), config.getPortaServerLog()); //34
                                                GestoreDatabaseMeteo.eliminaVoce(gui.getTabella_DatiMeteo().getIDRigaSelezionata()); //35
                                                aggiornaTabellaMeteo(); //36
                                                gui.getBarraAzioni_TabellaMeteo().disabilitaButtonEliminaRiga(true); //37
                                            });
        
    }
    
    private void evento_BATabellaDati_selezioneData(){ //38
        
        gui.getBarraAzioni_TabellaMeteo().getDatePicker_Data().
            setOnAction((ActionEvent ev) -> {
                                                dataSelezionata = gui.getBarraAzioni_TabellaMeteo().getDataSelezionata(); //39
                                                aggiornaTabellaMeteo(); //40
                                            });
    }
    
    private void evento_BATabellaDati_selezioneFascia(){ //41
        
        gui.getBarraAzioni_TabellaMeteo().getSelezione_FasciaOraria().
            setOnAction(ev -> {
                                    fasciaSelezionata = gui.getBarraAzioni_TabellaMeteo().getSelezione_FasciaOraria().getValue().toString();  //42
                                    aggiornaTabellaMeteo();  //43
                              });
        
    }
    
    private void evento_BAAndamentoStorico_clickBottoneConferma(){ //44
        
        gui.getBarraAzioni_AndamentoStorico().getButtonConferma().
            setOnAction((ActionEvent ev) ->{
                                                CreaLogXMLAttivitaMeteo.crea("Meteo", "Click bottone Conferma", config.getIPClient(), config.getIPServerLog(), config.getPortaServerLog()); //45
                                                periodoInizioSelezionato = gui.getBarraAzioni_AndamentoStorico().getPeriodoInizio(); //46
                                                periodoFineSelezionato = gui.getBarraAzioni_AndamentoStorico().getPeriodoFine(); //47
                                                campioneSelezionato = gui.getBarraAzioni_AndamentoStorico().getCampioneSelezionato(); //48
                                                aggiornaAndamentoStorico(); //49
                                                gui.getGrafico_AndamentoDatiMeteo().setPrecisione(config.getPrecisione_GraficoAndamentoStorico()); //50
                                            });
        
    }
    
    private void evento_BAAndamentoStorico_clickBottoneReset(){ //51
        
        gui.getBarraAzioni_AndamentoStorico().getButtonReset().
            setOnAction((ActionEvent ev) ->{
                                                CreaLogXMLAttivitaMeteo.crea("Meteo", "Click bottone Reset", config.getIPClient(), config.getIPServerLog(), config.getPortaServerLog()); //52
                                                gui.getBarraAzioni_AndamentoStorico().setPeriodoInizio(LocalDate.now().withMonth((LocalDate.now().getMonthValue() - 1))); //53
                                                gui.getBarraAzioni_AndamentoStorico().setPeriodoFine(LocalDate.now()); //54
                                                gui.getBarraAzioni_AndamentoStorico().setCampioneSelezionato(0); //55
                                                gui.getGrafico_AndamentoDatiMeteo().reset(); //56
                                            });
        
    }
    
    private void aggiornaTabellaMeteo(){ //57
        String fasciaInizio = null, fasciaFine = null;

        if(fasciaSelezionata != null){ //58
            switch(fasciaSelezionata){
                case "00 - 06":
                    fasciaInizio = "00:00:00";
                    fasciaFine = "06:00:00";
                    break;
                case "07 - 13":
                    fasciaInizio = "07:00:00";
                    fasciaFine = "13:00:00";
                    break;
                case "14 - 19":
                    fasciaInizio = "14:00:00";
                    fasciaFine = "19:00:00";
                    break;
                case "20 - 00":
                    fasciaInizio = "20:00:00";
                    fasciaFine = "23:59:59";
                    break;
            }
        }
        gui.getTabella_DatiMeteo().aggiorna(GestoreDatabaseMeteo.ottieniDatiTabella(dataSelezionata, fasciaInizio, fasciaFine, config.getNumeroRighe_TabellaMeteo())); //59
    }
    
    private void aggiornaAndamentoStorico(){
        gui.getGrafico_AndamentoDatiMeteo().aggiorna(GestoreDatabaseMeteo.ottieniAndamentoStorico_ASSEX(periodoInizioSelezionato, periodoFineSelezionato), GestoreDatabaseMeteo.ottieniAndamentoStorico_ASSEY(campioneSelezionato, periodoInizioSelezionato, periodoFineSelezionato), campioneSelezionato); //61
    }
}

/*NOTE

################################################################################

(00)Classe che controlla tutta l'applicazione. E' responsabile del caricamento del
    file di configurazione, del caricamento e del salvataggio della cache, della
    gestione degli eventi e del aggiornamento del grafico dell'andamento storico
    e della tabella dei dati meteo
    
################################################################################

(01)Path dei vari file utilizzati dall'applicazione. Viene utilizzato il metodo
    getAbsolutePath per ottenere il path assoluto visto che questi file sono
    contenuti all'interno della cartela xml che si trova nella radice della cartella
    del progetto

(02)Variabili in cui vengono salvati i valori dei vari campi quando avviene un
    determinato evento

--------------------------------------------------------------------------------

(03)Metodo start che viene chiamato automaticamente all'avvio e consente di 
    inizializzare l'applicazione
   
    04)Se la validazione del file di configurazione ha successo, allora viene
       caricato il file e utilizzato per istanziare un oggetto di tipo
       ParametriConfigurazioneMeteoXML altrimenti gli viene passato null e la classe
       userà i valori di default
    05)Viene creato il log relativo all'apertura dell'applicazione
    06)Viene creata l'interfaccia grafica
    07)Viene applicato lo stile all'interfaccia grafica
    08)Metodo che serve per aggiungere i vari eventi ai componenti che lo necessitano
    09)Metodo per caricare eventuali dati presenti nel file input.xml
    10)Metodo per aggiornare la tabella dei dati meteo
    11)Viene caricato il contenuto della cache locale degli input
    12)Se questa è diversa da null (quindi non vi sono stati errori ed è presente)
       viene usata per caricare questi valori nei componenti dell'interfaccia 
       grafica
    13)Un gruppo è un contenitore di oggetti osservabili dentro la scena.
    14)Una scena è il contenitore principale della finestra
    15)Metodo per impostare alcuni parametri stilistici alle scena
    16)Viene creato un evento che all'atto della chiusura dell'applicazione, crea
       un log relativo alla chisura e dopo salva il contenuto della cache locale
       degli input
    17)Inserisce la scena nello stage
    18)Mostra la finestra

--------------------------------------------------------------------------------

(19)Metodo main per poter lanciare l'applicazione

--------------------------------------------------------------------------------

(20)Metodo per caricare eventuali dati presenti nel file input.xml
    
    21)Viene letto il contenuto del file input.xml
    22)Se la sua lunghezza è maggiore di una riga, allora significa che non è
       vuoto e che vi sono dei dati da caricare
       Idea presa da: https://stackoverflow.com/questions/2850203/count-the-number-of-lines-in-a-java-string
    23)Viene effettuata la validazione del file input.xml con il relativo XML schema
    24)Se la validazione ha successo allora viene effettuata la chiamata al gestore
       del database che provvederà ad inserire le nuove voci. Viene usato il metodo
       fromXML della classe XStream visto che dobbiamo convertire il contenuto
       del file xml. Visto che nel file vi potrebbero essere più gruppo di DatiMeteo
       è necessario usare un ArrayList.
    25)Se il risultato di questa operazione è >1 (quindi almeno una riga è stata
       inserita, possiamo svuotare il file xml

--------------------------------------------------------------------------------

(26)Metodo che serve per aggiungere i vari eventi ai componenti che lo necessitano

--------------------------------------------------------------------------------

(27)Metodo per impostare alcuni parametri stilistici alle scena
    
    28)Viene impostato il titolo dell'applicazione
    29)Viene impostata l'icona dell'applicazione
    30)Viene disabilitato il ridimensionamento della finestra

--------------------------------------------------------------------------------

(31)Metodo per aggiungere l'evento di selezione di una riga all'interno della 
    tabella dei dati meteo per disabilitare il bottone Elimina riga selezionata
    
    32)Viene controllato il valore precedente e il nuovo valore. Se quest'ultimo
       è diverso da null, vuol dire che è stata selezionata una riga e che il
       bottone Elimina riga selezionata può essere abilitato

--------------------------------------------------------------------------------

(33)Metodo per aggiungere l'evento di click al bottone Elimina riga selezionata

    34)Viene creato il log relativo al click del bottone
    35)Visto che una riga è selezionata allora, possiamo procedere all'eliminazione
       invocando il metodo eliminaVoce della classe GestoreDatabaseMeteo
    36)Metodo per aggiornare il contenuto della tabella meteo e mostrare il nuovo
       contenuto
    37)Viene poi disabilitato il bottone Elimina riga selezionata

--------------------------------------------------------------------------------

(38)Metodo per aggiungere l'evento di aggiornamento della tabella a seguito della
    selezione di una data

    39)Viene memorizzata la data selezionata
    40)Viene effettuato un aggiornamento della tabella meteo che filtrerà i dati
       in base alla data selezionata (ed eventualmente alla fascia oraria selezionata)

--------------------------------------------------------------------------------

(41)Metodo per aggiungere l'evento di aggiornamento della tabella a seguito della
    selezione di una fascia oraria

    42)Viene memorizzata la fascia oraria selezionata
    43)Viene effettuato un aggiornamento della tabella meteo che filtrerà i dati
       in base alla fascia oraria selezionata (ed eventualmente alla data selezionata)

--------------------------------------------------------------------------------

(44)Metodo per aggiungere l'evento di click sul bottone conferma

    45)Viene creato il log relativo al click del bottone
    46)Viene salvato il periodo iniziale selezionato
    47)Viene salvato il periodo finale selezionato
    48)Viene salvato il campione selezionato
    49)Viene effettuato un aggiornamento del grafico dell'andamento storico che 
       mostrerà l'andamento del campione selezionato nel periodo selezionato
    50)Viene settata la precisione del grafico in base ai parametri di configurazione

--------------------------------------------------------------------------------

(51)Metodo per aggiungere l'evento di click sul bottone reset

    52)Viene creato il log relativo al click del bottone
    53)Viene impostato il valore di default del periodo iniziale cioè un mese fa
    54)Viene impostato il valore di default del periodo finale cioè oggi
    55)Viene impostato il campione di default cioè la temperatura
    56)Viene invocato il metodo reset della classe GraficoAndamentodìDatiMeteo
       che resetterà il grafico e lo riporterà alla stadio iniziale

--------------------------------------------------------------------------------

(57)Metodo per aggiornare la tabella dei dati meteo

    58)Se la fascia selezionato è presente, dobbiamo scomporla in due intervalli
       di tempo, iniziale e finale, per poi poter fare la query
    59)Viene invocato il metodo aggiorna della classe TabellaDatiMeteo a cui si
       passa il risultato dell'esecuzione del metodo ottieniDatiTabella della 
       classe che gestisce il database, che in base ai parametri passati, restituirà
       un ArrayList contenente i DatiMeteo filtrati

--------------------------------------------------------------------------------

(60)Metodo per agguornare il grafico dell'andamento storico dei dati meteo

    61)Viene invocato il metodo aggiorna della classe GraficoAndamentoDatiMeteo a 
       cui si passa il risultato dell'esecuzione dei metodi ottieniAndamentoStorico_ASSEX
       e ottieniAndamentoStorico_ASSEY della classe che gestisce il database, 
       che in base ai parametri passati, restituiranno due ArrayList contenente 
       i valori del campione selezionato nel periodo selezionato e il periodo 
       selezionato

################################################################################

*/
