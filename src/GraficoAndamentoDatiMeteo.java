import java.util.*;
import javafx.scene.chart.*;


public class GraficoAndamentoDatiMeteo { //00
    private final NumberAxis asseY; //01
    private final LineChart<String, Number> grafico; //02
    private final XYChart.Series serie; //03
    
    private double minYvalue, maxYvalue; //04

    public GraficoAndamentoDatiMeteo() { //05
        asseY = new NumberAxis();
        grafico = new LineChart<String, Number>(new CategoryAxis(), asseY); //06
        serie = new XYChart.Series(); //07
        grafico.getData().add(serie); //08
        grafico.setLegendVisible(false); //09
        grafico.setAnimated(false); //10
        grafico.setMaxWidth(750); //11
    }
    
    public void aggiorna(ArrayList<String> x, ArrayList<Double> y, String label){ //12
        serie.getData().clear(); //13
        for (int i = 0; i < x.size(); i++) { //14
            serie.getData().add(new XYChart.Data(x.get(i), y.get(i))); //15
            if(i == 0) //16
                minYvalue = maxYvalue = y.get(i);
            else{ //17
                if(y.get(i) <= minYvalue) minYvalue = y.get(i);
                else if(y.get(i) >= maxYvalue) maxYvalue = y.get(i);
            }
        }
        
        asseY.setLabel(label); //18
        grafico.getXAxis().setLabel("periodo"); //19
        asseY.setAutoRanging(false); //20
        asseY.setLowerBound((int)(minYvalue) - 3); //21
        asseY.setUpperBound((int)(maxYvalue) + 3); //21
    }
    
    public void reset(){ //22
        serie.getData().clear(); //23
        asseY.setAutoRanging(true); //24
        asseY.setLowerBound(0); //25
        asseY.setUpperBound(100); //25
        asseY.setLabel(null); //26
        grafico.getXAxis().setLabel(null); //26
    }
    
    //######################### METODI GET/SET #################################
    public LineChart getGraficoAndamentoDatiMeteo() {return grafico;} //27
    
    public void setPrecisione(int p){ //28
        asseY.setTickUnit(p); //29
        asseY.setTickLabelGap(p); //30
    }
}

/* NOTE

################################################################################

(00)Classe responsabile della creazione e della gestione del grafico dell'
    andamento storico dei dati

################################################################################

(01)Oggetto di tipo NumberAxis per la creazione di un asse "numerico" sul grafico
(02)LineChart è un tipo di grafico contenuto nella libreria javafx.scene.chart e
    permette di ottenere un linea spezzata che congiunge vari punti disegnati sul
    grafico. Molto utile per ottenere una visione di temporale di un insieme di 
    dati.
(03)Serie dei dati che verranno poi visualizzati nel grafico
(04)Variabili usate per memorizzare il valore massimo e minimi (vedi più sotto)

--------------------------------------------------------------------------------

(05)Costruttore della classe
    
    06)Viene creato il grafico destinato ad avere sull'asse delle ascisse delle
       stringhe (le date) e sulle ordinate numeri corrispodenti al campione
       selezionato.
    07)Viene inizializzata una serie vuota al momento
    08)Viene creata l'associazione fra la serie e il grafico
    09)Viene disabilitata la legenda, inutile in quando vi sarà un'unica linea
    10)Vengono disabilitate le animazioni che portavano a malfunzionamenti
    11)Viene impostata una larghezza limite

--------------------------------------------------------------------------------

(12)Metodo per aggiornare il grafico a seguito di cambiamenti
    -x, ArrayList che contiene i valori da inserire sull'asse delle X (String)
    -y, ArrayList che contiene i valori da inserire sull'asse delle Y (double)
    -label, variabile string usata per stampare il nome corrente del campione
            a sinistra dell'asse Y
    
    13)Viene suotata la serie da eventuali dati inseriti in precedenza
    14)Viene eseguito un for per inserire tutti i dati presenti nei due ArrayList
       nella serie
    15)Inserimento all'interno della serie dei dati
    16)if per inizializzare il minimo e il massimo valori al primo elemento della
       lista di elementi di tipo double. Queste operazioni di seugito ci
       permetteranno di regolare il valore minimo e massimo da visualizzare sull'
       asse delle Y
    17)Se non siamo di fronte al primo valore della lista allora dobbiamo vedere
       se questo valore è un candidato valore massimo o minimo.
    18)Impostiamo la label dell'asse Y
    19)Impostiamo la label dell'asse X
    20)Disabilitiamo l'autoranging cioè la scelta automatica del valore massimo
       e minimo da visualizzare sull'asse Y
    21)Impostiamo il valore massimo e minimo da visualizzare sull'asse Y

--------------------------------------------------------------------------------

(22)Metodo per resettare il grafico e riportarlo allo stato iniziale

    23)Viene suotata la serie
    24)Riabilitiamo l'autoranging
    25)Impostiamo un valore massimo e minimo dell'asse Y
    26)Cancelliamo il contenuto dele label degli assi

--------------------------------------------------------------------------------

(27)Metodo get per ottenere il grafico (usato per costruire l'interfaccia grafica)

--------------------------------------------------------------------------------

(28)Metodo per settare la precisione del grafico
    
    29)Viene settata l'unità di misura dell'asse Y
    30)Viene settata la distanza fra un'etichetta e l'altra

################################################################################

*/
