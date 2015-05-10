package ciselnik_dlouheho_drivi.gui;

import ciselnik_dlouheho_drivi.ciselnik.Ciselnik;
import ciselnik_dlouheho_drivi.ciselnik.KlasickaZaznam;
import ciselnik_dlouheho_drivi.ciselnik.TeplickaZaznam;
import static ciselnik_dlouheho_drivi.gui.HTMLConstants.*;
import ciselnik_dlouheho_drivi.gui.HTMLConstants.Align;
import ciselnik_dlouheho_drivi.structures.DruhDreviny;
import ciselnik_dlouheho_drivi.structures.TypStromu;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import unidataz.util.ExecuteUtil;
import unidataz.util.FileUtil;

/**
 *
 * @author Michal
 */
public class HTMLReporter {

    public static String generateHTML_KlasickaTeplikcaSouhrnna(Ciselnik ciselnik){
        String html = "";
        html += HTMLReporter.generateHTML_Klasicka0(ciselnik);
        html += "<hr/>";
        html += HTMLReporter.generateHTML_Teplicka0(ciselnik);
        html += "<hr/>";
        html += HTMLReporter.generateHTML_Souhrnna0(ciselnik);
        html = HTMLReporter.generateHTML_addHeadAndFoot(ciselnik,html);
        return html;
    }
    
    public static String generateHTML_addHeadAndFoot(Ciselnik ciselnik, String HTMLcontent){
        String s = "";
        s+= "<HTML face=\"arial\" size=\"15\">";
        s+= "<head>";
        s+= "<meta http-equiv=\"Content-Language\" content=\"cs\">";
        s+= "<meta http-equiv=\"Content-type\" content=\"text/html; charset=UTF-8\">";
        s+= "</head>";
        s+= "<body>";
        s+= "<center>";
        s+= "<table cellpadding=\"5\" cellspacing=\"0\" border=\"1\" width=\"90%\" style=\"margin-left:auto;margin-right:auto\">";
         s+= "<tr>";
            s+= td("Polesí:");
            s+= td("Lesní úsek:");
            s+= td("Datum: "+b(new SimpleDateFormat("yyyy-MM-dd").format(ciselnik.datum)));
         s+= "</tr>";
         s+= "<tr>";
            s+= td("");
            s+= td("Porost: "+b(ciselnik.popis));
            s+= td("Dřevorubec(těž. sk.):");
         s+= "</tr>";
        s+= "</table>";
        s+= "</center>";
        s+= HTMLcontent;
        s+= "</body>";
        s+= "</HTML>";        
        return s;
    }
    
    public static String generateHTML_Klasicka(Ciselnik ciselnik){
        String html = generateHTML_Klasicka0(ciselnik);
        html = generateHTML_addHeadAndFoot(ciselnik, html);
        return html;
    }
    
    private static String generateHTML_Klasicka0(Ciselnik ciselnik){
        String s = "";
        s+= "<center>";
        s+= h(1,"Číselník dlouhého dříví", Align.Center);
        s+= h(2,"Klasická metoda", Align.Center);
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"600\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr><th>Pořadové číslo</th>"
                + "<th>Pozn.</th>"
                + "<th>Druh dřeviny</th>"
                + "<th>Oddenkový kus</th>"
                + "<th>Délka v m</th>"
                + "<th>Průměr v cm</th>"
                + "<th>m³</th>"
                + "</tr>";
        for (KlasickaZaznam z : ciselnik.klasicka) {
            s+= "<tr>";
            s+= td(z.poradove_cislo, Align.Center);
            s+= td(z.poznamka+"&nbsp;", Align.Center);
            s+= td(z.getDruhDreviny().name(), Align.Center);
            s+= td(z.oddenkovy_kus ? "X" : "&nbsp;", Align.Center);
            s+= td(z.delka, Align.Center);
            s+= td(z.prumer, Align.Center);
            s+= td(z.getObjem().setScale(2), Align.Center);
            s+= "</tr>";            
        }
        s+= "</table>";
        
        s+= h(2, "Sumář dle druhu", Align.Center);
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"400\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr>  <th>Druh dřeviny</th>"
                + "<th>Počet oddenkových kusů</th>"
                + "<th>m³</th>"
                + "</tr>";        
        for (DruhDreviny dd : ciselnik.getKlasicka_DruhyDrevin()) {
            int        oddenku = ciselnik.getKlasicka_OddenkovychKusu(dd);
            BigDecimal objem   = ciselnik.getKlasicka_Objem(dd);
            s+= "<tr>";
            s+= td(dd.name(), Align.Center);
            s+= td(oddenku, Align.Center);
            s+= td(objem.setScale(2)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
            s+= "</tr>";            
        }
        s+= "<tr>";
        s+= td("Celkem", Align.Center);
        s+= td(ciselnik.getKlasicka_OddenkovychKusu()+" ks", Align.Center);
        s+= td(ciselnik.getKlasicka_Objem().setScale(2)+" m³&nbsp;", Align.Right);
        s+= "</tr>";
        s+= "</table>";            
        
        s+= h(2, "Sumář dle typu", Align.Center);
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"400\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr>  <th>Typ dřeviny</th>"
                + "<th>Počet oddenkových kusů</th>"
                + "<th>m³</th>"
                + "</tr>";        
        int        jehlic_oddenku = ciselnik.getKlasicka_OddenkovychKusu(TypStromu.JEHLICNAN);
        BigDecimal jehlic_objem = ciselnik.getKlasicka_Objem(TypStromu.JEHLICNAN);
        int list_oddenku = ciselnik.getKlasicka_OddenkovychKusu(TypStromu.LISTNAC);
        BigDecimal list_objem = ciselnik.getKlasicka_Objem(TypStromu.LISTNAC);
        s+= "<tr>";
            s+= td("Jehličnany", Align.Center);
            s+= td(jehlic_oddenku+" ks", Align.Center);
            s+= td(jehlic_objem.setScale(2) +" m³&nbsp;", Align.Right);
        s+= "</tr>";
        s+= "<tr>";
            s+= td("Listnáče", Align.Center);
            s+= td(list_oddenku+" ks", Align.Center);
            s+= td(list_objem.setScale(2) +" m³&nbsp;", Align.Right);
        s+= "</tr>";
        s+= "</table>";
        s+= "</center>";
        return s;
    }
    
    public static String generateHTML_Teplicka(Ciselnik ciselnik){
        String html = generateHTML_Teplicka0(ciselnik);
        html = generateHTML_addHeadAndFoot(ciselnik, html);
        return html;
    }
    
    private static String generateHTML_Teplicka0(Ciselnik ciselnik){
        String s = "";
        s+= "<center>";
        s+= h(1,"Číselník dlouhého dříví", Align.Center);
        s+= h(2,"Teplická metoda", Align.Center);
        for (TeplickaZaznam zaznam : ciselnik.teplicka) {
            s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"600\" style=\"margin-left:auto;margin-right:auto\" >";
                s+= "<tr>";
                    s+= "<th>"+zaznam.getDruhDreviny().name() + "</th>";                    
                    s+= "<th>Oddenkové kusy</th>";                    
                    s+= "<th>Ostatní kusy</th>";                    
                    s+= "<th>Počet ks celkem</th>";                    
                    s+= "<th>m³</th>";                    
                s+= "</tr>";
                s+= "<tr>";
                    s+= td("00", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_00!=0 ? zaznam.pocet_oddenkovych_00 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_00!=0 ? zaznam.pocet_ostatnich_00 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_00()!=0 ? zaznam.getPocet_00() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_00().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";                
                s+= "<tr>";
                    s+= td("0", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_0!=0 ? zaznam.pocet_oddenkovych_0 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_0!=0 ? zaznam.pocet_ostatnich_0 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_0()!=0 ? zaznam.getPocet_0() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_0().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";                
                s+= "<tr>";
                    s+= td("1", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_1!=0 ? zaznam.pocet_oddenkovych_1 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_1!=0 ? zaznam.pocet_ostatnich_1 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_1()!=0 ? zaznam.getPocet_1() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_1().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";
                s+= "<tr>";
                    s+= td("2", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_2!=0 ? zaznam.pocet_oddenkovych_2 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_2!=0 ? zaznam.pocet_ostatnich_2 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_2()!=0 ? zaznam.getPocet_2() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_2().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";                
                s+= "<tr>";
                    s+= td("3", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_3!=0 ? zaznam.pocet_oddenkovych_3 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_3!=0 ? zaznam.pocet_ostatnich_3 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_3()!=0 ? zaznam.getPocet_3() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_3().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";                
                s+= "<tr>";
                    s+= td("4", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_4!=0 ? zaznam.pocet_oddenkovych_4 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_4!=0 ? zaznam.pocet_ostatnich_4 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_4()!=0 ? zaznam.getPocet_4() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_4().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";                
                s+= "<tr>";
                    s+= td("5", Align.Center);
                    s+= td(zaznam.pocet_oddenkovych_5!=0 ? zaznam.pocet_oddenkovych_5 : "&nbsp;", Align.Center);
                    s+= td(zaznam.pocet_ostatnich_5!=0 ? zaznam.pocet_ostatnich_5 : "&nbsp;", Align.Center);
                    s+= td(zaznam.getPocet_5()!=0 ? zaznam.getPocet_5() : "&nbsp;", Align.Center);
                    s+= td(zaznam.getObjem_5().setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
                s+= "</tr>";                
                s+= "<tr>";
                    s+= td("Celkem", Align.Center);
                    s+= td(zaznam.getPocet_Oddenkovych()+" ks", Align.Center);
                    s+= td(zaznam.getPocet_Ostatnich()+" ks", Align.Center);
                    s+= td(zaznam.getPocet()+" ks", Align.Center);
                    s+= td(zaznam.getObjem_Celkove()+" m³&nbsp;", Align.Right);
                s+= "</tr>";
            s+= "</table>";
        s+= "</center>";
            
        s+= "<br/>";
        }
        
        s+= h(2, "Sumář dle druhu", Align.Center);
        s+= "<center>";
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"400\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr>  <th>Druh dřeviny</th>"
                + "<th>Počet oddenkových kusů</th>"
                + "<th>m³</th>"
                + "</tr>";        
        for (DruhDreviny dd : ciselnik.getTeplicka_DruhyDrevin()) {
            int        oddenku = ciselnik.getTeplicka_OddenkovychKusu(dd);
            BigDecimal objem   = ciselnik.getTeplicka_Objem(dd);
            s+= "<tr>";
            s+= td(dd.name(), Align.Center);
            s+= td(oddenku, Align.Center);
            s+= td(objem.setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
            s+= "</tr>";            
        }
        s+= "<tr>";
        s+= td("Celkem", Align.Center);
        s+= td(ciselnik.getTeplicka_OddenkovychKusu()+" ks", Align.Center);
        s+= td(ciselnik.getTeplicka_Objem() + " m³&nbsp;", Align.Right);
        s+= "</tr>";
        s+= "</table>";            
        s+= "</center>";
        
        s+= h(2, "Sumář dle typu", Align.Center);
        s+= "<center>";
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"400\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr>  <th>Typ dřeviny</th>"
                + "<th>Počet oddenkových kusů</th>"
                + "<th>m³</th>"
                + "</tr>";        
        int        jehlic_oddenku = ciselnik.getTeplicka_OddenkovychKusu(TypStromu.JEHLICNAN);
        BigDecimal jehlic_objem = ciselnik.getTeplicka_Objem(TypStromu.JEHLICNAN);
        int list_oddenku = ciselnik.getTeplicka_OddenkovychKusu(TypStromu.LISTNAC);
        BigDecimal list_objem = ciselnik.getTeplicka_Objem(TypStromu.LISTNAC);
        s+= "<tr>";
            s+= td("Jehličnany", Align.Center);
            s+= td(jehlic_oddenku+" ks", Align.Center);
            s+= td(jehlic_objem.setScale(3)+" m³&nbsp;", Align.Right);
        s+= "</tr>";
        s+= "<tr>";
            s+= td("Listnáče", Align.Center);
            s+= td(list_oddenku+" ks", Align.Center);
            s+= td(list_objem.setScale(3)+" m³&nbsp;", Align.Right);
        s+= "</tr>";
        
            s+= "</table>";
        s+= "</center>";
        return s;    
    }
    
    public static String generateHTML_Souhrnna(Ciselnik ciselnik){
        String html = generateHTML_Souhrnna0(ciselnik);
        html = generateHTML_addHeadAndFoot(ciselnik, html);
        return html;
    }
    
    private static String generateHTML_Souhrnna0(Ciselnik ciselnik){
        String s = "";        
        s+= h(1, "Souhrn", Align.Center);
        s+= h(2, "Sumář dle druhu : Klasická + Teplická", Align.Center);
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"400\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr>  <th>Druh dřeviny</th>"
                + "<th>Počet oddenkových kusů</th>"
                + "<th>m³</th>"
//                + "<th>Průměrná hmotnatost</th>"
                + "</tr>";        
        for (DruhDreviny dd : ciselnik.getSouhrnna_DruhyDrevin()) {
            int        oddenku = ciselnik.getSouhrnna_OddenkovychKusu(dd);
            BigDecimal objem   = ciselnik.getSouhrnna_Objem(dd);
            s+= "<tr>";
            s+= td(dd.name(), Align.Center);
            s+= td(oddenku, Align.Center);
            s+= td(objem.setScale(3)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", Align.Right);
//            BigDecimal prumernaHmotnatost = oddenku!=0 ? objem.divide(new BigDecimal(oddenku), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;
//            s+= td(prumernaHmotnatost.setScale(3)+" m³/strom", Align.Center);
            s+= "</tr>";            
        }
        s+= "<tr>";
        s+= td("Celkem", Align.Center);
        int oddenku = ciselnik.getSouhrnna_OddenkovychKusu();
        s+= td(oddenku+" ks", Align.Center);
        BigDecimal objem = ciselnik.getSouhrnna_Objem();
        s+= td(objem + " m³&nbsp;", Align.Right);
//        BigDecimal prumernaHmotnatost = oddenku!=0 ? objem.divide(new BigDecimal(oddenku), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;       
//        s+= td(prumernaHmotnatost.setScale(3)+" m³/strom", Align.Center);
        s+= "</tr>";
        s+= "</table>";            
        
        s+= h(2, "Sumář dle typu : Klasická + Teplická", Align.Center);
        s+= "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"600\" style=\"margin-left:auto;margin-right:auto\" >";
        s+= "<tr>  <th>Typ dřeviny</th>"
                + "<th>Počet oddenkových kusů</th>"
                + "<th>m³</th>"
                + "<th>Průměrná hmotnatost</th>"
                + "</tr>";        
        int        jehlic_oddenku = ciselnik.getSouhrnna_OddenkovychKusu(TypStromu.JEHLICNAN);
        BigDecimal jehlic_objem = ciselnik.getSouhrnna_Objem(TypStromu.JEHLICNAN);
        int list_oddenku = ciselnik.getSouhrnna_OddenkovychKusu(TypStromu.LISTNAC);
        BigDecimal list_objem = ciselnik.getSouhrnna_Objem(TypStromu.LISTNAC);
        s+= "<tr>";
            s+= td("Jehličnany", Align.Center);
            s+= td(jehlic_oddenku+" ks", Align.Center);
            s+= td(jehlic_objem.setScale(3)+" m³&nbsp;&nbsp;&nbsp;", Align.Right);
            BigDecimal prumernaHmotnatostJehlicnanu = jehlic_oddenku!=0 ? jehlic_objem.divide(new BigDecimal(jehlic_oddenku), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            s+= td("<b>"+prumernaHmotnatostJehlicnanu.setScale(3) +" m³/strom</b>", Align.Center);
        s+= "</tr>";
        s+= "<tr>";
            s+= td("Listnáče", Align.Center);
            s+= td(list_oddenku+" ks", Align.Center);
            s+= td(list_objem.setScale(3)+" m³&nbsp;&nbsp;&nbsp;", Align.Right);
            BigDecimal prumernaHmotnatostListnacu = list_oddenku!=0 ? list_objem.divide(new BigDecimal(list_oddenku), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            s+= td("<b>"+prumernaHmotnatostListnacu.setScale(3) +" m³/strom</b>", Align.Center);
        s+= "</tr>";
        s+= "</table>";
        s+= "</center>";
        return s;
    }
    
    public static void main(String[] args) {
        //
        //Ciselnik c = Ciselnik.load(new File("c:\\Users\\Michal\\Documents\\NetBeansProjects\\UniPOS.NBp\\run\\ciselniky\\Číselník 2012-11-25.cdd"));
        Ciselnik c = Ciselnik.load(new File("c:\\Users\\Michal\\Documents\\NetBeansProjects\\Ciselnik.NBp\\run\\ciselniky\\Číselník Dešov listopad.cdd"));
        String html = generateHTML_Souhrnna(c);
        File f = new File("out.html");
        try {
            FileUtil.writeText2File(f, html, "UTF-8");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
        ExecuteUtil.openFile(f);
    }
    
}
