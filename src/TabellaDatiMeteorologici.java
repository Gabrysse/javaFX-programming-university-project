import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class TabellaDatiMeteorologici { //00
    private final TableView<DatiMeteo> tabellaDati; //01
    private final TableColumn colData, colOrario, colTemp, colUmid, colPress, colPrece; //02
    
    public TabellaDatiMeteorologici() { //03     
        colData = new TableColumn("Data"); //04
        colData.setCellValueFactory(new PropertyValueFactory<>("data")); //05
        
        colOrario = new TableColumn("Orario"); //04
        colOrario.setCellValueFactory(new PropertyValueFactory<>("orario")); //05

        colTemp = new TableColumn("Temperatura"); //04
        colTemp.setCellValueFactory(new PropertyValueFactory<>("temperatura")); //05
        
        colUmid = new TableColumn("Umidità"); //04
        colUmid.setCellValueFactory(new PropertyValueFactory<>("umidita")); //05
        
        colPress = new TableColumn("Pressione Atmosferica"); //04
        colPress.setCellValueFactory(new PropertyValueFactory<>("pressione")); //05
        
        colPrece = new TableColumn("Precipitazioni"); //04
        colPrece.setCellValueFactory(new PropertyValueFactory<>("precipitazioni")); //05
        
        tabellaDati = new TableView<>();
        tabellaDati.getColumns().addAll(colData, colOrario, colTemp, colUmid, colPress, colPrece); //06
        
        tabellaDati.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //07
        colTemp.setMinWidth(20); //08
        colPrece.setMinWidth(28); //08
        colPress.setMinWidth(85); //08
    }

    public void aggiorna(ArrayList<DatiMeteo> lista){ //09
        tabellaDati.setItems(FXCollections.observableArrayList(lista)); //10
    }
    
    
    //######################### METODI GET/SET #################################
    
    public TableView<DatiMeteo> getTabellaDati() {return tabellaDati;} //11
    
    public int getIDRigaSelezionata() {return tabellaDati.getSelectionModel().getSelectedItem().getID();} //12
    
    public int getRigaSelezionata() {return tabellaDati.getSelectionModel().getSelectedIndex();} //13
    
    public void setRigaSelezionata(int id) {tabellaDati.getSelectionModel().select(id);} //14
}

/*NOTE

################################################################################

(00)Classe responsabile della creazione e della gestione della tabella dei dati
    meteorologici

################################################################################

(01)Tabella nella quale verranno mostrati i Dati Meteo filtrati
(02)Colonne della tabella: avremo una colonna per l'data, l'orario, la temperatura,
    la pressione atmosferica, l'umidità e per indicare se vi sono state o no 
    precipitazioni in quel determinato giorno

--------------------------------------------------------------------------------

(03)Costruttore della classe

    04)Una TableView è formata da un insieme di colonne TabelColumn, il cui
       costruttore prede come parametro il nome della colonna.
    05)Per ogni colonna andranno costruite le celle, e riempite con una specifica
       proprietà dei bean. Il metodo setCellValueFactory specifica una fabbrica 
       di celle per la colonna, implementata usando la classe PropertyValueFactory
       che prende come parametro il nome della proprietà del bean.
    06)Aggiungiamo le colonne create in precedenza alla TableView
    07)Settando la politica di ridimensionamento su costrained, facciamo in modo
       che la somma delle larghezze delle colonne riempia tutto lo spazio a
       disposizione della tabella
    08)Settiamo una minima larghezza per alcune colonne in modo da visualizzare
       correttamente il nome della colonna

--------------------------------------------------------------------------------

(09)Metodo usato per aggiornare il contenuto della tabella a seguito di un evento
    -lista, è un ArrayList che serve per popolare la tabella

    10)Per aggiungere i dati alla tabella dobbiamo associare una ObservableList
       alla TableView. Per far questo è necessario usare il metodo setItems, 
       passandogli l'ArrayList convertita in ObservableList mediante il metodo
       statico observableArrayList della classe FXCollections
       La ObservableList è una struttura dati a lista che aggrega i beans e permette
       il tracciamento automatico delle modifiche ai dati in essa contenuti. 

--------------------------------------------------------------------------------

(11)Metodo get per restituire la TableView (usato per costruire l'interfaccia
   grafica completa nella classe InterfacciaMeteo)

(12)Metodo get per restituire il campo ID della riga selezionata, ottenuto dal
   database ma non mostrato nella TableView perchè utile solo ai fini gestionali

(13)Metodo get per restituire l'indice della riga selezionata nella TableView

--------------------------------------------------------------------------------

(14)Metodo set per settare la riga selezionata nella TableView

################################################################################
*/
