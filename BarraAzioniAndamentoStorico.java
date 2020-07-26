import java.time.*;
import java.time.format.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class BarraAzioniAndamentoStorico { //00
    private Label label_Periodo, label_Campione, label_Dal, label_Al;
    private DatePicker periodoInizio, periodoFine; //01
    private RadioButton campioneTemperatura, campionePressione, campioneUmidita; //02
    private ToggleGroup selezione_Campione; //02
    private final Button conferma, reset;
    
    private final GridPane barraAzioni; //03
    
    public BarraAzioniAndamentoStorico() { //04
        creaLabel(); //05
        creaSelezionePeriodo(); //06
        creaSelezioneCampione(); //07       
        conferma = new Button("Conferma");
        reset = new Button("Reset");

        barraAzioni = new GridPane(); //08
        barraAzioni.setHgap(10); //09
        barraAzioni.setVgap(22); //09
        barraAzioni.add(label_Periodo, 0, 0); //10
        barraAzioni.add(label_Dal, 1, 0); //10
        barraAzioni.add(periodoInizio, 2, 0); //10
        barraAzioni.add(label_Al, 3, 0); //10
        barraAzioni.add(periodoFine, 4, 0); //10
        
        barraAzioni.add(label_Campione, 0, 1); //10
        barraAzioni.add(campioneTemperatura, 1, 1); //10
        barraAzioni.add(campionePressione, 2, 1); //10
        barraAzioni.add(campioneUmidita, 3, 1, 2, 1); //10
        
        barraAzioni.add(conferma, 2, 2); //10
        barraAzioni.add(reset, 3, 2); //10
    }

    private void creaLabel(){ //11
        label_Periodo = new Label("Periodo:");
        label_Campione = new Label("Campione:");
        label_Dal = new Label("Dal");
        label_Al = new Label("al");
        label_Periodo.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
        label_Campione.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
    }
    private void creaSelezionePeriodo(){ //12
        periodoInizio = new DatePicker();
        periodoFine = new DatePicker();
        periodoInizio.setValue(LocalDate.now().withMonth((LocalDate.now().getMonthValue() - 1)));
        periodoFine.setValue(LocalDate.now());
    }
    private void creaSelezioneCampione(){ //13
        campioneTemperatura = new RadioButton("Temperatura");
        campioneTemperatura.setUserData("temperatura"); //14
        campionePressione = new RadioButton("Pressione atmosferica");
        campionePressione.setUserData("pressione");
        campioneUmidita = new RadioButton("Umidità");
        campioneUmidita.setUserData("umidita");
        selezione_Campione = new ToggleGroup();
        campioneTemperatura.setToggleGroup(selezione_Campione);
        campioneTemperatura.setSelected(true);
        campionePressione.setToggleGroup(selezione_Campione);
        campioneUmidita.setToggleGroup(selezione_Campione);
    }
    
    
    //######################### METODI GET/SET #################################
    public GridPane getBarraAzioni() {return barraAzioni;} //15

    public String getPeriodoInizio() {return periodoInizio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));} //16

    public String getPeriodoFine() {return periodoFine.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));} //17
    
    public String getCampioneSelezionato() {return selezione_Campione.getSelectedToggle().getUserData().toString();} //18

    public Button getButtonConferma() {return conferma;} //19

    public Button getButtonReset() {return reset;} //20
    
    public void setPeriodoInizio(LocalDate d) {periodoInizio.setValue(d);} //21
    
    public void setPeriodoFine(LocalDate d) {periodoFine.setValue(d);} //22
    
    public void setCampioneSelezionato(int index) {selezione_Campione.getToggles().get(index).setSelected(true);} //23
    
}

/*NOTE

################################################################################

(00)Classe responsabile della creazione della barra di navigazione che si trova
    sopra il grafico dell'andamento storico. Essa contiene la selezione del periodo
    (inziale e finale), la selezione del campione da graaficare, il bottone Conferma
    e quello di Reset
    
################################################################################

(01)DatePicker è un un campo dati speciale che permette di selezionare una data
    da un calendario a scomparsa che appare quando vi si clicca sopra. Comunque
    è possibile anche l'inserimento manuale.
(02)RadioButton è un componente grafico che fornisce una scelta multipla in cui
    una sola scelta può essere attiva contemporaneamente. Per far si che questo
    sia possibile è necessario aggiungere i vari RadioButton ad un ToggleGroup
(03)GridPane è un componente utile per sistemare secondo un layout a griglia i vari
    componenti grafici

--------------------------------------------------------------------------------

(04)Costruttore della classe
    
    05)Metodo usato per costruire le varie label che comporranno la barra
    06)Metodo usato per costruire la selezione del periodo
    07)Metodo usato per costruire la selezione del campione
    08)Viene creato il GridPane
    09)Viene settato il lo spazio orizzontale e verticale fra le varie componenti
       che compongono la griglia
    10)Inseriamo le varie componenti grafiche nel GridPane, indicando nel metodo
       add (nell'ordine) il componente, l'indice della colonna, l'indice della riga
        
           |          0          |            1           |          2         |        3      |        4      |
           -----------------------------------------------------------------------------------------------------        
        0  | label_Periodo       | label_Dal              | periodoInizio      | label_Al      | periodoFine   |
        1  | label_Campione      | campioneTemperatura    | campionePressione  | campioneUmidita               |
        2  |                     |                        | conferma           | reset         |               |
           -----------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------

(11)Metodo di utilità usato per segmentare il codice. Crea le varie label ed
    imposta per alcune, alcuni parametri stilistici

--------------------------------------------------------------------------------

(12)Metodo di utilità usato per segmentare il codice. Crea la selezione del periodo
    cioè vengono creati i due DatePicker relativi al Periodo. Viene impostato anche
    valore di default che corrisponde all'intervallo di un mese.

--------------------------------------------------------------------------------

(13)Metodo di utilità usato per segmentare il codice. Crea la selezione del campione
    cioè vengono creati i 3 ComboBox (temperatura, pressione e umidità) e vengono
    inseriti all'interno del ToggleGroup selezionando di default il campione della
    temperatura
    
    14)setUserData permette di impostare un nome personalizzato in modo da riferici
       in seguito a quel ComboBox usando questa proprietà. Viene usato quando all'
       avvio viene caricata la cache ed è necessario ripristinare il ComboBox
       selezionato in precedenza

--------------------------------------------------------------------------------

(15)Metodo get per restituire il GridPane (usato per costruire l'interfaccia
   grafica completa nella classe InterfacciaMeteo)

(16)Metodo get per restituire il periodo iniziale selezionato

(17)Metodo get per restituire il periodo finale selezionato

(18)Metodo get per restituire il campione selezionato

(19)Metodo get per restituire il bottone Conferma (usato per aggiungere l'evento 
    del click nella classe Meteo)

(20)Metodo get per restituire il bottone Reset (usato per aggiungere l'evento del
    click nella classe Meteo)

--------------------------------------------------------------------------------

(21)Metodo set per settare il periodo iniziale

(22)Metodo set per settare il periodo finale

(23)Metodo set per settare il campione

################################################################################

*/
