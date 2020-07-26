import java.time.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


public class InterfacciaMeteo { //00
    private final BarraAzioniTabellaMeteo barraAzioni_TabellaMeteo; //01
    private final BarraAzioniAndamentoStorico barraAzioni_AndamentoStorico; //01
    private final TabellaDatiMeteorologici tabella_DatiMeteo; //01
    private final GraficoAndamentoDatiMeteo grafico_AndamentoDatiMeteo; //01
    
    private final Label titoloApplicazione, labelTabellaMeteo, labelAndamentoStorico;
    private final GridPane interfacciaCompleta; //02
    private final VBox boxTabellaDati; //03

    public InterfacciaMeteo() { //04
        barraAzioni_TabellaMeteo = new BarraAzioniTabellaMeteo();
        barraAzioni_AndamentoStorico = new BarraAzioniAndamentoStorico();
        tabella_DatiMeteo = new TabellaDatiMeteorologici();
        grafico_AndamentoDatiMeteo = new GraficoAndamentoDatiMeteo();
        boxTabellaDati = new VBox(tabella_DatiMeteo.getTabellaDati()); //05
        titoloApplicazione = new Label("Meteo");
        labelTabellaMeteo = new Label("Dati meteorologici");
        labelAndamentoStorico = new Label("Andamento storico");

        interfacciaCompleta = new GridPane(); //06
        inserisciComponentiGridPane(); //07
    }
    
    private void inserisciComponentiGridPane(){ //08
        interfacciaCompleta.add(titoloApplicazione, 1, 0);
        interfacciaCompleta.add(barraAzioni_TabellaMeteo.getBarraAzioni(), 0, 1);
        interfacciaCompleta.add(boxTabellaDati, 0, 2);
        interfacciaCompleta.add(barraAzioni_AndamentoStorico.getBarraAzioni(), 2, 1);
        interfacciaCompleta.add(grafico_AndamentoDatiMeteo.getGraficoAndamentoDatiMeteo(), 2, 2);
        interfacciaCompleta.add(labelTabellaMeteo, 0, 3);
        interfacciaCompleta.add(labelAndamentoStorico, 2, 3);
    }
    
    public void applicaStile(ParametriConfigurazioneMeteoXML config){ //09
        titoloApplicazione.setPadding(new Insets(5, 0, 30, 0)); //10
        labelTabellaMeteo.setPadding(new Insets(10, 0, 10, 0)); //10
        labelAndamentoStorico.setPadding(new Insets(10, 0, 10, 0)); //10
        interfacciaCompleta.setPadding(new Insets(10, 10, 0, 10)); //10
        GridPane.setHalignment(labelTabellaMeteo, HPos.CENTER); //11
        GridPane.setHalignment(labelAndamentoStorico, HPos.CENTER); //11
        titoloApplicazione.setStyle("-fx-text-fill: red; -fx-font-size: 30px; -fx-font-weight: bold;"); //12
        labelTabellaMeteo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold"); //12
        labelAndamentoStorico.setStyle("-fx-font-size: 18px; -fx-font-weight: bold"); //12
        interfacciaCompleta.setStyle("-fx-font-size: " + config.getDimensione_font() + "; -fx-font-family: " + config.getFont() + "; -fx-background-color: " + config.getColore_background()); //13
        
        interfacciaCompleta.getColumnConstraints().addAll(new ColumnConstraints(650), new ColumnConstraints(100), new ColumnConstraints(800)); //14
    }    
    
    public void caricamentoCache(CacheMeteo c){ //15
        barraAzioni_TabellaMeteo.setFasciaOraria(c.getFasciaOraria());
        barraAzioni_TabellaMeteo.setData(c.getData());
        barraAzioni_AndamentoStorico.setPeriodoInizio(LocalDate.parse(c.getPeriodoInizio()));
        barraAzioni_AndamentoStorico.setPeriodoFine(LocalDate.parse(c.getPeriodoFine()));
        String campione = c.getCampione();
        switch (campione) { //16
            case "temperatura":
                barraAzioni_AndamentoStorico.setCampioneSelezionato(0);
                break;
            case "pressione":
                barraAzioni_AndamentoStorico.setCampioneSelezionato(1);
                break;
            case "umidita":
                barraAzioni_AndamentoStorico.setCampioneSelezionato(2);
                break;
        }
        tabella_DatiMeteo.setRigaSelezionata(c.getRigaSelezionata());
    }
    
    
    //######################### METODI GET/SET #################################
    
    public GridPane getInterfacciaCompleta() {return interfacciaCompleta;} //17

    public BarraAzioniTabellaMeteo getBarraAzioni_TabellaMeteo() {return barraAzioni_TabellaMeteo;} //18

    public BarraAzioniAndamentoStorico getBarraAzioni_AndamentoStorico() {return barraAzioni_AndamentoStorico;} //19

    public TabellaDatiMeteorologici getTabella_DatiMeteo() {return tabella_DatiMeteo;} //20

    public GraficoAndamentoDatiMeteo getGrafico_AndamentoDatiMeteo() {return grafico_AndamentoDatiMeteo;} //21
  
}


/*NOTE

################################################################################

(00)Classe responsabile della creazione della costruzione dell'interfaccia grafica
    completa dell'applicazione.
    
################################################################################

(01)Componenti grafici da inserire nell'interfaccia completa
(02)GridPane è un componente utile per sistemare secondo un layout a griglia i vari
    componenti grafici
(03)VBox è un componente utile per sistemare in un box verticale i vari componenti
    grafici. Si rende necessario perchè la TableView non può essere inserita
    direttamente nel GridPane

--------------------------------------------------------------------------------

(04)Costruttore della classe
    
    05)Creazione del VBox e inserimento della tabella al suo interno.
    06)Viene creato il GridPane
    07)Metodo per inserire i vari componenti nel GridPane

--------------------------------------------------------------------------------

(08)Metodo di utilità usato per segmentare il codice. Inserisce le varie componenti
    all'interno del GridPane

    |             0             |            1           |                2              |        
    --------------------------------------------------------------------------------------        
 0  |                           | titoloApplicazione     |                               |
 1  | barraAzioni_TabellaMeteo  |                        | barraAzioni_AndamentoStorico  |
 2  | boxTabellaDati            |                        | grafico_AndamentoDatiMeteo    |
 3  | labelTabellaMeteo         |                        | labelAndamentoStorico         |
    --------------------------------------------------------------------------------------

--------------------------------------------------------------------------------

(09)Metodo usato per applicare i parametri di stile all'interfaccia grafica
    -config, è un oggetto di tipo ParametriConfigurazioneMeteoXML che serve
             per ottenere i vari parametri letti dal file di configurazione

    10)Viene settato il padding, cioè lo spazio fra il contenuto interno e il bordo.
       E' necessario usare la classe Insets che ha un costruttore che permette di
       impostare nell'ordine il padding: top, right, bottom, left
    11)Vengono centrate le due label all'interno della loro posizione nel griglia
    12)Vengono impostati alcuni parametri di stile, come il colore o la dimensione,
       di alcuni componenti che non verranno modificati dall'applicazione delle
       configurazione utente
    13)In base alla configurazioni presenti nel file di configurazione vengono 
       impostati i valori dei vari parametri di stile
    14)Viene impostata una larghezza fissa delle colonne usando la classe ColumnConstraints
       che nel suo costruttore accetta prorpio un valore numerico che indica la 
       larghezza

--------------------------------------------------------------------------------

(15)Metodo invocato dalla classe Meteo quando, all'avvio, legge e carica la cache
    locale degli input. Questo metodo non fa altro che settare i vari componenti
    ai valori presenti nella cache
    -c, è un oggetto di tipo CacheMeteo e contiene i vari valori da assegnare ai
        vari campi che fanno parte della cache locale degli input

    16)In base al valore "testuale" del campione salvato, è necessario scegliere
       l'indice giusto per rendere selezionato il giusto ComboBox: indice 0 per
       la temperatura, 1 per la pressione e 2 per l'umidità

--------------------------------------------------------------------------------

(17)Metodo get per restituire il GridPane dell'interfaccia completa (usato per 
    inserire l'interfaccia nell'applicazione nella classe Meteo)

(18)Metodo get per restituire la barra azioni della tabella meteo (usato per creare
    i vari eventi nella classe Meteo)

(19)Metodo get per restituire la barra azioni della andamento storico (usato per 
    creare i vari eventi nella classe Meteo)

(20)Metodo get per restituire la tabella meteo (usato per creare i vari eventi 
    nella classe Meteo)

(21)Metodo get per restituire il grafico dell'andamento storico (usato per creare
    i vari eventi nella classe Meteo)

################################################################################

*/
