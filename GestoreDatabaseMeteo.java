import java.sql.*;
import java.util.*;

public class GestoreDatabaseMeteo { //00
    public static ArrayList<DatiMeteo> ottieniDatiTabella(String data, String fasciaInizio, String fasciaFine, int numRighe){ //01
        int tipo = 0;   //02
        String sql = "SELECT * FROM datimeteo ORDER BY data ASC, orario ASC LIMIT ? "; //03
        ArrayList<DatiMeteo> lista = new ArrayList<>(); //04
        
        if(data == null && fasciaInizio != null){ //05
            sql = "SELECT * FROM datimeteo WHERE orario BETWEEN ? AND ? ORDER BY data ASC, orario ASC LIMIT ?";
            tipo = 1;
        }
        else if(data != null && fasciaInizio == null){ //06
            sql = "SELECT * FROM datimeteo WHERE data = ? ORDER BY data LIMIT ?";
            tipo = 2;
        }
        else if(data != null && fasciaInizio != null){ //07
            sql = "SELECT * FROM datimeteo WHERE data = ? AND orario BETWEEN ? AND ? ORDER BY data ASC, orario ASC LIMIT ?";
            tipo = 3;
        }
        

        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/meteo", "root", ""); //08
            PreparedStatement ps = co.prepareStatement(sql); //09
            ){
            impostaParametriQueryTabella(ps, tipo, data, fasciaInizio, fasciaFine, numRighe); //10
            ResultSet rs = ps.executeQuery(); //11
            
            while(rs.next()) //12
                lista.add(new DatiMeteo(rs.getInt("id"), rs.getString("data"), rs.getString("orario"), rs.getDouble("temperatura"), 
                                        rs.getDouble("umidita"), rs.getDouble("pressione"), ((rs.getBoolean("precipitazioni")==true)?"si":"no")));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return lista;
    }
    
    private static void impostaParametriQueryTabella(PreparedStatement ps, int tipo, String data, String fasciaInizio, String fasciaFine, int numRighe) throws SQLException{ //13
        if(tipo == 0){
            ps.setInt(1, numRighe);
        }
        else if(tipo == 1){
            ps.setString(1, fasciaInizio);
            ps.setString(2, fasciaFine);
            ps.setInt(3, numRighe);
        }
        else if(tipo == 2){
            ps.setString(1, data);
            ps.setInt(2, numRighe);
        }
        else if(tipo == 3){
            ps.setString(1, data);
            ps.setString(2, fasciaInizio);
            ps.setString(3, fasciaFine);
            ps.setInt(4, numRighe);
        }
    }
    
    public static ArrayList<String> ottieniAndamentoStorico_ASSEX(String periodoInizio, String periodoFine){ //14
        ArrayList<String> asseX = new ArrayList<>(); //15
        
        try (Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/meteo", "root", ""); //16
             PreparedStatement ps = co.prepareCall("SELECT * FROM datimeteo WHERE data >= ? AND data <= ? ORDER BY data ASC, orario ASC") //17
            ){
            ps.setString(1, periodoInizio); //18
            ps.setString(2, periodoFine); //18
            ResultSet rs = ps.executeQuery(); //19
            
            while(rs.next()){ //20
                String s = rs.getString("data") + "\n" + rs.getString("orario");
                asseX.add(s);
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return asseX;
    }
    
    public static ArrayList<Double> ottieniAndamentoStorico_ASSEY(String campione, String periodoInizio, String periodoFine){ //21
        ArrayList<Double> asseY = new ArrayList<>(); //22
        
        try (Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/meteo", "root", ""); //23
             PreparedStatement ps = co.prepareCall("SELECT * FROM datimeteo WHERE data >= ? AND data <= ? ORDER BY data ASC, orario ASC") //24
            ){
            ps.setString(1, periodoInizio); //25
            ps.setString(2, periodoFine); //25
            ResultSet rs = ps.executeQuery(); //26
            
            while(rs.next()) //27
                asseY.add(rs.getDouble(campione));
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return asseY;
    }
    
    public static void eliminaVoce(int idDaEliminare){ //28
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/meteo", "root", ""); //29
            PreparedStatement ps = co.prepareStatement("DELETE FROM datimeteo WHERE id = ?") //30
            ){
            ps.setInt(1, idDaEliminare); //31
            System.out.println("Selezionato: " + idDaEliminare + " rows affected: " + ps.executeUpdate()); //32
        } catch (SQLException e) {System.err.println(e.getMessage());}
    }
    
    public static int inserisciVoci(ArrayList<DatiMeteo> dati){ //33
        int row = 0;
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/meteo", "root", ""); //34
            PreparedStatement ps = co.prepareStatement("INSERT INTO datimeteo (data, orario, temperatura, umidita, pressione, precipitazioni) VALUES (?, ?, ?, ?, ?, ?)"); //35       
            ){
            
            for (DatiMeteo d : dati) { //36
                ps.setString(1, d.getData());
                ps.setString(2, d.getOrario());
                ps.setDouble(3, d.getTemperatura());
                ps.setDouble(4, d.getUmidita());
                ps.setDouble(5, d.getPressione());
                ps.setInt(6, (d.getPrecipitazioni().equals("si")) ? 1 : 0);
                
                row += ps.executeUpdate();
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return row;
    }
}

/*NOTE

################################################################################

(00)Classe che si di effettuare le query nel database

################################################################################

(01)Metodo utilizzato per ottenere i dati della Tabella dei dati meteo. Restituisce
    un ArrayList che verrà usato poi dalla tabella visualizzare i dati ottenuti.
    -data, è la data selezionata
    -fasciaInizio, è la fascia iniziale ricavata dall'intervallo selezionato
    -fasciaFine, è la fascia finale ricavata dall'intervallo selezionato
    -numRighe, indica il numero di righe da ottenere dal database

    02)In base ai parametri che sono != null viene individuato un tipo ed effettuata
       una query diversa
    03)Di default la query ottiene tutti i dati
    04)Viene creato l'ArrayList
    05)Se la data == null e la fasciaInizio è != null (di conseguenza anche 
       fasciaInizio), allora verrà effettuata una query discriminando i dati 
       solo per fascia oraria (tipo 1)
    06)Se la data != null e la fasciaInizio è == null (di conseguenza anche 
       fasciaInizio), allora verrà effettuata una query discriminando i dati 
       solo per data (tipo 2)
    07)Se sia la data che la fasciaInizio sono != null allora sono entrambi 
       presenti e viene effettuata una query discriminando i dati in base alla
       data e alla fascia oraria (tipo 3)
    08)Viene creata la connessione con il database
    09)Viene creato un PreparedStatement che ci è utile per fare query parametriche
    10)Chiamata al metodo impostaParametriQuery per impostare i vari parametri delle
       query in base al tipo.
    11)Viene eseguita la query mediante il metodo executeQuery che restituisce il
       risultato di questa sottoforma di ResultSet
    12)Fin tanto che il ResultSet non è vuoto, lo scorriamo, e di volta in volta
       creiamo un oggetto di tipo DatiMeteo che viene poi aggiunto all'ArrayList
       che verrà restituito alla fine

--------------------------------------------------------------------------------

(13)Metodo che in base al tipo (vedi sopra per maggiori dettagli) setta i vari
    parametri della query

--------------------------------------------------------------------------------

(14)Metodo utilizzato per ottenere la data e l'orario da inserire sull'asse X
    del grafico dell'andamento storico. Restituisce un ArrayList che sarà usato
    dalla classe GraficoAndamentoDatiMeteo per visualizzare la "scala" dell'asse
    -periodoInizio, indica il periodo inziale selezionato
    -periodoFine, indica il periodo finale selezionato

    15)Viene creato l'ArrayList
    16)Viene creata la connessione con il database
    17)Viene creato un PreparedStatement che ci è utile per fare query parametriche.
       In questo caso viene effettuata una query discriminando in base al periodo
       selezionato.
    18)Vengono settati i due parametri della query parametrica
    19)Viene eseguita la query mediante il metodo executeQuery che restituisce il
       risultato di questa sottoforma di ResultSet
    20)Fin tanto che il ResultSet non è vuoto, lo scorriamo, e di volta in volta
       aggiungiamo il valore del campione all'ArrayList che verrà restituito alla 
       fine

--------------------------------------------------------------------------------

(21)Metodo utilizzato per ottenere i valori del campione selezionato da inserire 
    sull'asse Y del grafico dell'andamento storico. Restituisce un ArrayList che 
    sarà usato dalla classe GraficoAndamentoDatiMeteo per visualizzare la "scala" 
    dell'asse
    -campione, indica il campione selezionato
    -periodoInizio, indica il periodo inziale selezionato
    -periodoFine, indica il periodo finale selezionato

    22)Viene creato l'ArrayList
    23)Viene creata la connessione con il database
    24)Viene creato un PreparedStatement che ci è utile per fare query parametriche.
       In questo caso viene effettuata una query discriminando in base al periodo
       selezionato.
    25)Vengono settati i due parametri della query parametrica
    26)Viene eseguita la query mediante il metodo executeQuery che restituisce il
       risultato di questa sottoforma di ResultSet
    27)Fin tanto che il ResultSet non è vuoto, lo scorriamo, e di volta in volta
       aggiungiamo la stringa data + \n + orario all'ArrayList che verrà restituito
       alla fine

--------------------------------------------------------------------------------

(28)Metodo usato per eliminare una voce dal database
    -idDaEliminare, contiene l'id della record da eliminare

    29)Viene creata la connessione con il database
    30)Viene creato un PreparedStatement che ci è utile per fare query parametriche.
       In questo caso viene effettuata una query di eliminazione basata sull'id
    31)Viene settato il parametro della query parametrica
    32)Viene eseguita la query di aggiornamento mediante il metodo executeUpdate()
       che esegue l'aggiornamento e restituisce il numero di righe coinvolte che
       viene stampato nella console

--------------------------------------------------------------------------------

(33)Metodo usato per inserire nuove voci all'interno del database
    -dati, è un ArrayList di DatiMeteo che contiene le voci da inserire

    34)Viene creata la connessione con il database
    35)Viene creato un PreparedStatement che ci è utile per fare query parametriche.
       In questo caso viene effettuata una query di inserimento di tutti le colonne
       tranne id che è un campo auto incrementale del database
    36)Viene scorso tutto l'ArrayList e ogni campo di quell'oggetto DatiMeteo
       serve per settare i parametri della query parametrica.Ogni volta che questa
       viene eseguito incrementa un contatore di righe, che viene poi restituito
       per indicare quante righe sono state inserite effettivamente.

################################################################################

*/