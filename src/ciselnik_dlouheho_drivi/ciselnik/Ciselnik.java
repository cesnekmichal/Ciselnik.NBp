package ciselnik_dlouheho_drivi.ciselnik;

import ciselnik_dlouheho_drivi.structures.DruhDreviny;
import ciselnik_dlouheho_drivi.structures.TypStromu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Level;
import unidataz.util.GLog;

/**
 *
 * @author Michal
 */
@XmlRootElement(name = "CISELNIK")
@XmlType(propOrder={"datum","popis","klasicka","teplicka"})
public class Ciselnik {
    
    @XmlElement(name="DATUM")
    public Date datum = new Date();

    @XmlElement(name="POPIS")
    public String popis = "";
    
    @XmlElementWrapper(name="KLASICKA", required=true)
    @XmlElement(name="ZAZNAM")
    public List<KlasickaZaznam> klasicka = new ArrayList<KlasickaZaznam>();

    @XmlElementWrapper(name="TEPLICKA", required=true)
    @XmlElement(name="ZAZNAM")
    public List<TeplickaZaznam> teplicka = new ArrayList<TeplickaZaznam>();
    
    //<editor-fold defaultstate="collapsed" desc="SAVING">
    /** Save to File. */
    public boolean save(File descriptorFile, String encoding){
        try {
            FileOutputStream fos = new FileOutputStream(descriptorFile);
            boolean saved = save(fos, encoding);
            fos.close();
            return saved;
        } catch (Exception ex) {
            GLog.elog(Level.ERROR, GLog.EXCEPTION, ex);
            return false;
        }
    }
    
    /** Save to OutputStream. */
    public boolean save(OutputStream outputStream, String encoding){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(outputStream,encoding);
            JAXBContext context = JAXBContext.newInstance(Ciselnik.class);
            Marshaller marshaller = context.createMarshaller();
            // output pretty printed
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.marshal(this, osw);
            osw.close();
            return true;
        } catch (Exception e) {
            GLog.elog(Level.ERROR, GLog.EXCEPTION, e);
            return false;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="LOADING">
    /** Load from File. */
    public static Ciselnik load(File xmlFile){
        Ciselnik result = null;
        try {
            String encoding = detectEncoding(xmlFile);
            FileInputStream fileInputStream = new FileInputStream(xmlFile);
            result = load(fileInputStream, encoding);
            fileInputStream.close();
        } catch (Exception ex) {
            GLog.elog(Level.ERROR, GLog.EXCEPTION, ex);
        }
        return result;
    }
    
    private static String detectEncoding(File xmlFile) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(xmlFile));
            String line = br.readLine();
            if(line.contains("encoding")){
                int idx = line.indexOf("encoding") + "encoding".length() + 1;
                int idxL = line.indexOf("\"",idx) + 1;
                int idxR = line.indexOf("\"",idxL);
                result = line.substring(idxL, idxR);
            }
            br.close();
        } catch (Exception ex) { }
        return !result.isEmpty() ? result : "UTF-8";
    }
    
    /** Load from InputStream. */
    public static Ciselnik load(InputStream inputStream, String encoding){
        Ciselnik result = null;
        try {
            InputStreamReader isr = new InputStreamReader(inputStream, encoding);
            JAXBContext jaxbContext = JAXBContext.newInstance(Ciselnik.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object o = unmarshaller.unmarshal(isr);
            if(o instanceof Ciselnik){
                result = ((Ciselnik)o);
            }
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //</editor-fold>
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(datum).append(popis)
                .append(klasicka).append(teplicka)
                .toHashCode();
    }

    public static void main(String[] args) {
        Ciselnik demo = getDemo();
        System.out.println(demo.hashCode());
        demo.save(new File("Ciselnik.cdd"), "UTF-8");
        Ciselnik c = Ciselnik.load(new File("Ciselnik.cdd"));
        System.out.println(c.hashCode());
        c.save(System.out, "UTF-8");
    }

    public static Ciselnik getDemo(){
        Ciselnik c = new Ciselnik();
        c.klasicka.add(new KlasickaZaznam());
        c.klasicka.add(new KlasickaZaznam());
        c.klasicka.add(new KlasickaZaznam());
        c.teplicka.add(new TeplickaZaznam());
        c.teplicka.add(new TeplickaZaznam());
        c.teplicka.add(new TeplickaZaznam());
        c.teplicka.add(new TeplickaZaznam());
        return c;
    }
    
    public BigDecimal getKlasicka_Objem(){
        BigDecimal sum = BigDecimal.ZERO;
        for (KlasickaZaznam zaznam : klasicka) {
            sum = sum.add(zaznam.getObjem());
        }
        return sum;
    }

    public BigDecimal getSouhrnna_Objem(){
        return getKlasicka_Objem().add(getTeplicka_Objem());
    }
    
    public HashMap<DruhDreviny,Integer> getKlasicka_DruhyDrevin_HM(){
        HashMap<DruhDreviny,Integer> dreviny = new HashMap<DruhDreviny,Integer>();
        for (KlasickaZaznam z : klasicka) {
            DruhDreviny dd = z.getDruhDreviny();
            if(!dreviny.containsKey(dd)) {
                dreviny.put(dd,1);
            } else {
                Integer count = dreviny.get(z.getDruhDreviny());
                dreviny.put(dd,count+1);
            }
        }
        return dreviny;
    }
    
    public List<DruhDreviny> getKlasicka_DruhyDrevin(){
        List<DruhDreviny> dreviny = new ArrayList<DruhDreviny>();
        for (KlasickaZaznam z : klasicka) {
            if(!dreviny.contains(z.getDruhDreviny())) {
                dreviny.add(z.getDruhDreviny());
            }
        }
        return dreviny;
    }

    public List<DruhDreviny> getTeplicka_DruhyDrevin(){
        List<DruhDreviny> dreviny = new ArrayList<DruhDreviny>();
        for (TeplickaZaznam z : teplicka) {
            if(!dreviny.contains(z.getDruhDreviny())) {
                dreviny.add(z.getDruhDreviny());
            }
        }
        return dreviny;
    }

    public List<DruhDreviny> getSouhrnna_DruhyDrevin(){
        List<DruhDreviny> dreviny = new ArrayList<DruhDreviny>();
        for (TeplickaZaznam z : teplicka) {
            if(!dreviny.contains(z.getDruhDreviny())) {
                dreviny.add(z.getDruhDreviny());
            }
        }
        for (KlasickaZaznam z : klasicka) {
            if(!dreviny.contains(z.getDruhDreviny())) {
                dreviny.add(z.getDruhDreviny());
            }
        }
        return dreviny;
    }
    
    public int getKlasicka_OddenkovychKusu(){
        int pocet = 0;
        for (KlasickaZaznam z : klasicka) {
            if(z.oddenkovy_kus)  pocet++;
        }
        return pocet;
    }

    public int getKlasicka_OstatnichKusu(){
        int pocet = 0;
        for (KlasickaZaznam z : klasicka) {
            if(!z.oddenkovy_kus)  pocet++;
        }
        return pocet;
    }
    
    public int getKlasicka_OddenkovychKusu(DruhDreviny dd){
        int pocet = 0;
        for (KlasickaZaznam z : klasicka) {
            if(z.oddenkovy_kus && z.getDruhDreviny().equals(dd))  pocet++;
        }
        return pocet;
    }

    public int getKlasicka_OstatnichKusu(DruhDreviny dd){
        int pocet = 0;
        for (KlasickaZaznam z : klasicka) {
            if(!z.oddenkovy_kus && z.getDruhDreviny().equals(dd))  pocet++;
        }
        return pocet;
    }
    
    public int getSouhrnna_OddenkovychKusu(DruhDreviny dd){
        return getKlasicka_OddenkovychKusu(dd) + getTeplicka_OddenkovychKusu(dd);
    }

    public int getSouhrnna_OstatnichKusu(DruhDreviny dd){
        return getKlasicka_OstatnichKusu(dd) + getTeplicka_OstatnichKusu(dd);
    }
    
    public int getTeplicka_OddenkovychKusu(DruhDreviny dd){
        int pocet = 0;
        for (TeplickaZaznam z : teplicka) {
            if(z.getDruhDreviny().equals(dd))  pocet += z.getPocet_Oddenkovych();
        }
        return pocet;
    }

    public int getTeplicka_OstatnichKusu(DruhDreviny dd){
        int pocet = 0;
        for (TeplickaZaznam z : teplicka) {
            if(z.getDruhDreviny().equals(dd))  pocet += z.getPocet_Ostatnich();
        }
        return pocet;
    }
    
    public int getTeplicka_OddenkovychKusu(){
        int pocet = 0;
        for (TeplickaZaznam z : teplicka) {
            pocet += z.getPocet_Oddenkovych();
        }
        return pocet;
    }

    public int getTeplicka_OstatnichKusu(){
        int pocet = 0;
        for (TeplickaZaznam z : teplicka) {
            pocet += z.getPocet_Ostatnich();
        }
        return pocet;
    }
    
    public int getSouhrnna_OddenkovychKusu(){
        return getKlasicka_OddenkovychKusu() + getTeplicka_OddenkovychKusu();
    }

    public int getSouhrnna_OstatnichKusu(){
        return getKlasicka_OstatnichKusu() + getTeplicka_OstatnichKusu();
    }
    
    public int getKlasicka_OddenkovychKusu(TypStromu ts){
        int pocet = 0;
        for (DruhDreviny dd : DruhDreviny.proKlasickou()) {
            if(dd.tridaDrevin.typ==ts) pocet += getKlasicka_OddenkovychKusu(dd);
        }
        return pocet;
    }

    public int getKlasicka_OstatnichKusu(TypStromu ts){
        int pocet = 0;
        for (DruhDreviny dd : DruhDreviny.proKlasickou()) {
            if(dd.tridaDrevin.typ==ts) pocet += getKlasicka_OstatnichKusu(dd);
        }
        return pocet;
    }
    
    public int getSouhrnna_OddenkovychKusu(TypStromu ts){
        return getKlasicka_OddenkovychKusu(ts) + getTeplicka_OddenkovychKusu(ts);
    }

    public int getSouhrnna_OstatnichKusu(TypStromu ts){
        return getKlasicka_OstatnichKusu(ts) + getTeplicka_OstatnichKusu(ts);
    }
    
    public int getTeplicka_OddenkovychKusu(TypStromu ts){
        int pocet = 0;
        for (DruhDreviny dd : DruhDreviny.proTeplickou()) {
            if(dd.tridaDrevin.typ==ts) pocet += getTeplicka_OddenkovychKusu(dd);
        }
        return pocet;
    }

    public int getTeplicka_OstatnichKusu(TypStromu ts){
        int pocet = 0;
        for (DruhDreviny dd : DruhDreviny.proTeplickou()) {
            if(dd.tridaDrevin.typ==ts) pocet += getTeplicka_OstatnichKusu(dd);
        }
        return pocet;
    }
    
    public BigDecimal getKlasicka_Objem(DruhDreviny dd){
        BigDecimal objem = BigDecimal.ZERO;
        for (KlasickaZaznam z : klasicka) {
            if(z.getDruhDreviny().equals(dd)) {
                objem = objem.add(z.getObjem());
            }
        }
        return objem;
    }

    public BigDecimal getTeplicka_Objem(DruhDreviny dd){
        BigDecimal objem = BigDecimal.ZERO;
        for (TeplickaZaznam z : teplicka) {
            if(z.getDruhDreviny().equals(dd)) {
                objem = objem.add(z.getObjem_Celkove());
            }
        }
        return objem;
    }

    public BigDecimal getSouhrnna_Objem(DruhDreviny dd){
        return getKlasicka_Objem(dd).add(getTeplicka_Objem(dd));
    }
    
    public BigDecimal getKlasicka_Objem(TypStromu ts){
        BigDecimal objem = BigDecimal.ZERO;
        for (DruhDreviny dd : DruhDreviny.proKlasickou()) {
            if(dd.tridaDrevin.typ==ts) {
                objem = objem.add(getKlasicka_Objem(dd));
            }
        }
        return objem;
    }

    public BigDecimal getSouhrnna_Objem(TypStromu ts){
        return getKlasicka_Objem(ts).add(getTeplicka_Objem(ts));
    }
    
    public BigDecimal getTeplicka_Objem(TypStromu ts){
        BigDecimal objem = BigDecimal.ZERO;
        for (DruhDreviny dd : DruhDreviny.proTeplickou()) {
            if(dd.tridaDrevin.typ==ts) {
                objem = objem.add(getTeplicka_Objem(dd));
            }
        }
        return objem;
    }
    
    public BigDecimal getTeplicka_Objem(){
        BigDecimal sum = BigDecimal.ZERO;
        for (TeplickaZaznam zaznam : teplicka) {
            sum = sum.add(zaznam.getObjem_Celkove());
        }
        return sum;
    }
    
}
