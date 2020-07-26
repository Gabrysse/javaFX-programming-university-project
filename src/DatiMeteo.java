import javafx.beans.property.*;

public class DatiMeteo { //00
    private final SimpleIntegerProperty id; //01
    private final SimpleStringProperty data, orario, precipitazioni; //01
    private final SimpleDoubleProperty temperatura, umidita, pressione; //01

    public DatiMeteo(int id, String d, String o, double t, double u, double p, String pre) { //02
        this.id = new SimpleIntegerProperty(id);
        data = new SimpleStringProperty(d);
        orario = new SimpleStringProperty(o);
        temperatura = new SimpleDoubleProperty(t);
        umidita = new SimpleDoubleProperty(u);
        pressione = new SimpleDoubleProperty(p);
        precipitazioni = new SimpleStringProperty(pre);
    }

    //######################### METODI GET/SET #################################
    
    public int getID() {return id.get();} //03
    
    public String getData() {return data.get();} //04
    
    public String getOrario() {return orario.get();} //05

    public double getTemperatura() {return temperatura.get();} //06

    public double getUmidita() {return umidita.get();} //07

    public double getPressione() {return pressione.get();} //08

    public String getPrecipitazioni() { return precipitazioni.get(); } //09
    
}

/*NOTE

################################################################################

(00)Classe bean

################################################################################

(01)Per dotare i bean di osservabilità e di binding è necessario usare i tipi
    definiti dalle classi presenti nel package javafx.beans.property

--------------------------------------------------------------------------------

(02)Costruttore della classe

--------------------------------------------------------------------------------

[In tutti i medoti get per restiture il valore sottoforma di variabile "classica"
è necessario usare il metodo get()]

(03)Metodo get per restituire il campo ID

(04)Metodo get per restituire il campo Data

(05)Metodo get per restituire il campo Orario

(06)Metodo get per restituire il campo Temperatura

(07)Metodo get per restituire il campo Umidita

(08)Metodo get per restituire il campo Pressione

(09)Metodo get per restituire il campo Precipitazioni

################################################################################

*/
