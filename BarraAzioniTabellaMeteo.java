import java.time.*;
import java.time.format.*;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class BarraAzioniTabellaMeteo { //00
    private final Label label_Data, label_FasciaOraria;
    private final DatePicker data; //01

    private final ObservableList<String> listaFascieOrarie; //02
    private final ComboBox selezione_FasciaOraria; //02
    private final Button elimina_Riga;

    private final GridPane barraAzioni; //03

    public BarraAzioniTabellaMeteo() { //04
        label_Data = new Label("Data"); //05
        label_FasciaOraria = new Label("Fascia oraria"); //05
        data = new DatePicker(); //05
        listaFascieOrarie = FXCollections.observableArrayList("","00 - 06", "07 - 13", "14 - 19", "20 - 00"); //05
        selezione_FasciaOraria = new ComboBox(listaFascieOrarie); //05
        elimina_Riga = new Button("Elimina riga selezionata"); //05
        
        label_Data.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;"); //06
        label_FasciaOraria.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;"); //06
        selezione_FasciaOraria.setMinWidth(218); //07
        elimina_Riga.setDisable(true); //08
        
        barraAzioni = new GridPane(); //09
        barraAzioni.setHgap(10); //10
        barraAzioni.setVgap(20); //10
        barraAzioni.add(label_Data, 0, 0); //11
        barraAzioni.add(data, 1, 0); //11
        barraAzioni.add(label_FasciaOraria, 0, 1); //11
        barraAzioni.add(selezione_FasciaOraria, 1, 1); //11
        barraAzioni.add(elimina_Riga, 2, 1); //11
    }

    //######################### METODI GET/SET #################################
    public GridPane getBarraAzioni() {return barraAzioni;} //12
    
    public DatePicker getDatePicker_Data() {return data;} //13
    
    public String getDataSelezionata() { //14
        if(data.getValue() == null) return null;
        return data.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));}

    public ComboBox getSelezione_FasciaOraria() {return selezione_FasciaOraria;} //15
    
    public String getFasciaOrariaSelezionata() {return selezione_FasciaOraria.getValue().toString();} //16

    public Button getButtonElimina_Riga() {return elimina_Riga;} //17
    
    public void disabilitaButtonEliminaRiga(boolean v) {elimina_Riga.setDisable(v);} //18
    
    public void setData(String d) { //19
        if(d != null) data.setValue(LocalDate.parse(d));}
    
    public void setFasciaOraria(String f) {selezione_FasciaOraria.setValue(f);} //20
    
}

/*NOTE

################################################################################

(00)Classe responsabile della creazione della barra di navigazione che si trova
    sopra la tabella dei dati meteo. Essa contiene la selezione della data, quella
    della fascia oraria e il bottone per eliminare la riga selezionata
    
################################################################################

(01)DatePicker è un un campo dati speciale che permette di selezionare una data
    da un calendario a scomparsa che appare quando vi si clicca sopra. Comunque
    è possibile anche l'inserimento manuale.
(02)Per creare una selezione a scomparsa mediante un combobox è necessario utilizzare
    una ObservableList
(03)GridPane è un componente utile per sistemare secondo un layout a griglia i vari
    componenti grafici

--------------------------------------------------------------------------------

(04)Costruttore della classe
    
    05)Vengono creati tutti gli oggetti dei vari componenti grafici che compongono
       la barra di navigazione
    06)Vengono settati alcuni parametri di stile
    07)Viene settata una dimensione minima di larghezza del combobox in modo che
       sia uguale a quella del DatePicker appena sopra
    08)Viene disabilitato il bottone usato per eliminare la riga selezionata
    09)Viene creato il GridPane
    10)Viene settato il lo spazio orizzontale e verticale fra le varie componenti
       che compongono la griglia
    11)Inseriamo le varie componenti grafiche nel GridPane, indicando nel metodo
       add (nell'ordine) il componente, l'indice della colonna, l'indice della riga
        
           |          0          |            1           |        2      |
           ----------------------------------------------------------------        
        0  | label_Data          | data                   |               |
        1  | label_FasciaOraria  | selezione_FasciaOraria | elimina_Riga  |
           ----------------------------------------------------------------

--------------------------------------------------------------------------------

(12)Metodo get per restituire il GridPane (usato per costruire l'interfaccia
   grafica completa nella classe InterfacciaMeteo)

(13)Metodo get per restituire il DatePicker (usato per aggiungere l'evento di
    variazione del contenuto nella classe Meteo)

(14)Metodo get per restituire la data selezionata. Vi è la possibilità che la data
    sia lasciata vuota per mostrare tutti i dati presenti nel database, quindi se
    il valore del campo data è null ritorniamo null

(15)Metodo get per restituire il ComboBox della fascia oraria (usato per aggiungere 
    l'evento di variazione del contenuto nella classe Meteo)

(16)Metodo get per restituire la fascia oraria selezionata

(17)Metodo get per restituire il bottone per eliminare la riga selezionata (usato
    per aggiungere l'evento del click nella classe Meteo)

--------------------------------------------------------------------------------

(18)Metodo set per disabilitare il bottone per eliminare la riga selezionata

(19)Metodo set per settare la il DatePicker relativo alla data. Se la stringa d
    è vuota vuol dire che nella cache il campo Data è vuoto e quindi non deve 
    essere settato nessun valore

(20)Metodo set per settare la fascia oraria

################################################################################

*/