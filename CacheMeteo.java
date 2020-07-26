import java.io.*;

public class CacheMeteo implements Serializable{ //00
    private final String fasciaOraria, data, campione, periodoInizio, periodoFine;  //01
    private final int rigaSelezionata; //01

    public CacheMeteo(InterfacciaMeteo gui){ //02
        fasciaOraria = gui.getBarraAzioni_TabellaMeteo().getFasciaOrariaSelezionata(); //03
        data = gui.getBarraAzioni_TabellaMeteo().getDataSelezionata(); //03
        periodoInizio = gui.getBarraAzioni_AndamentoStorico().getPeriodoInizio(); //03
        periodoFine = gui.getBarraAzioni_AndamentoStorico().getPeriodoFine(); //03
        campione = gui.getBarraAzioni_AndamentoStorico().getCampioneSelezionato(); //03
        rigaSelezionata = gui.getTabella_DatiMeteo().getRigaSelezionata(); //03
    }
    
    //######################### METODI GET/SET #################################

    public String getFasciaOraria() {return fasciaOraria;} //04

    public String getCampione() {return campione;} //05

    public String getData() {return data;} //06

    public String getPeriodoInizio() {return periodoInizio;} //07

    public String getPeriodoFine() {return periodoFine;} //08

    public int getRigaSelezionata() {return rigaSelezionata;} //09
    
}

/*NOTE

################################################################################

(00)Classe che contiene i dati per salvare la cache locale degli input. Implementa
    l'interfaccia Serializable in modo da essere facilmente serializzata

################################################################################

(01)Variabili per salvare i vari valori dei vari input. A questo proposito vengono
    memorizzati i seguenti parametri:
    -fascia oraria
    -campione
    -data
    -periodo (inizio)
    -periodo (fine)
    -riga selezionata nella tabella

--------------------------------------------------------------------------------

(02)Costruttore della classe
    -gui, oggetto di tipo InterfacciaGrafica utilizzato per ottenere tutti i
          i valori attualmente selezionati dei parametri sopra citati

    03)Mediante i metodi get dei vari componenti dell'interfaccia grafica otteniamo
       i valori attualmente selezionati dei parametri sopra citati

--------------------------------------------------------------------------------

(04)Metodo get per restituire la fascia oraria

(05)Metodo get per restituire il campione

(06)Metodo get per restituire la data

(07)Metodo get per restituire il periodo (inizio)

(08)Metodo get per restituire il periodo (fine)

(09)Metodo get per restituire la riga selezionata

################################################################################

*/
