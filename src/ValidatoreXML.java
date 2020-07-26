import java.io.*;
import javax.xml.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.validation.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


public class ValidatoreXML { //00
    public static boolean valida(String filePath, String schemaPath, boolean path){ //01
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); //02
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); //03
            Document d; //04
            if(path)
                d = db.parse(new File(filePath)); //05
            else
                d = db.parse(new InputSource(new StringReader(filePath))); //05
            Schema s = sf.newSchema(new StreamSource(new File(schemaPath))); //06
            s.newValidator().validate(new DOMSource(d)); //07
        } catch (Exception e) {
            if(e instanceof SAXException){
                System.err.println("Errore validazione " + e.getMessage());
                return false; //08
            }
            else
                System.out.println(e.getMessage());
        }
        return true; //08
    }
}

/* NOTE

################################################################################

(00)Classe responsabile della validazione degli XML dato un XML Schema

################################################################################

(01)Metodo statico usato per la validazione
    -filePath, indica il path del file XML (può contenere anche XML sottoforma di
               stringa)
    -schemaPath, indica il path del file Schema XML
    -path, variabile boolean per indicare se quello che è stato messo in filePath
           è un path o un stringa contenente XML.

    02)DocumentBuilderFactory istanzia dei parser che producono oggetti DOM da 
       documenti XML
    03)SchemaFactory legge rappresentazioni esterne di schemi, per la validazione
    04)Document è l'oggetto documento DOM vero e proprio caricato dal file XML
    05)Se path è true allora filePath contiene il path del file XML. 
       Se false filePath contiene una stringa XML ed è necessario effettuare il 
       parse di quest'ultima. DocumentBuiler permette il parsing non solo da file, 
       ma anche da un InputSource:
       https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html#parse-org.xml.sax.InputSource-
       che rappresenta una sorgente di input XML ottenibile da un flusso di byte
       o di caratteri. E' possibile associare ad un InputSource un flusso di
       caratteri con il seguente costruttore:
       https://docs.oracle.com/javase/8/docs/api/org/xml/sax/InputSource.html#InputSource-java.io.Reader-
       che richiede un oggetto Reader. In questo caso viene utilizzata una sua
       sottoclasse, StringReader, il cui costruttore richiede come parametro la
       stringa XML in questione:
       https://docs.oracle.com/javase/8/docs/api/java/io/StringReader.html#StringReader-java.lang.String-
       Idea presa da:
       http://stackoverflow.com/questions/7607327/how-to-create-a-xml-object-from-string-in-java
    06)Schema è l'oggetto schema vero e proprio caricato da file XSD
    07)Crea un oggetto validatore che valida un documento xml sullo schema caricato
       In caso di errore vi validazione viene generata una SAXException.
    08)In caso di errore si ritorna false, altrimenti true

################################################################################

*/
