package ciselnik_dlouheho_drivi.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import unidataz.util.UniSystem;
import static ciselnik_dlouheho_drivi.structures.TridaDrevin.*;

/**
 *
 * @author Michal
 */
public enum DruhDreviny {
    SM(0, I,  "Smrk"),
    JD(1, I,  "Jedle"),
    BO(2, IIa,"Borovice"),
    VJ(3, IIa,"Vejmutovka"),
    MD(4, IIa,"Modřín"),
   MDx(28,IIb,"Modřín oddenky"),
    DG(5, IIa,"Douglaska"),
   BOx(6, IIb,"Borové oddenky"),
    BK(7, III,"Buk"),
    KL(8, III,"Klen"),
    JV(9, III,"Javory"),
    BB(10,III,"Babyka"),
    HB(11,III,"Habr"),
    JR(12,III,"Jeřáb"),
    LP(13,III,"Lípa"),
    OS(14,III,"Osika"),
    TR(15,III,"Třešeň"),
    HR(16,III,"Hrušen"),
    JB(17,III,"Jabloň"),
    DB(18,IV, "Dub"),
    JL(19,IV, "Jilm"),
    JS(20,IV, "Jasan"),
    AK(21,IV, "Akát"),
    BR(22,IV, "Bříza"),
    KS(23,IV, "Kaštan"),
    OL(24,IV, "Olše"),
    TP(25,IV, "Topol"),
    OR(26,IV, "Ořešák"),
    VR(27,IV, "Vrby");

    public final int id;
    public final TridaDrevin tridaDrevin;
    public final String popis;
    
    private DruhDreviny(int id, TridaDrevin typTabulky,String popis) {
        this.id = id;
        this.tridaDrevin = typTabulky;
        this.popis = popis;
    }
    
    public static DruhDreviny get(Integer id){
        for (DruhDreviny dd : values()) {
            if(UniSystem.equals(dd.id, id)) return dd;
        }
        return null;
    }
    
    /** Druhy dřevit pro klasickou metodu */
    public static DruhDreviny[] proKlasickou(){
        List<DruhDreviny> proTeplickou = new ArrayList<DruhDreviny>(Arrays.asList(values()));
        return proTeplickou.toArray(new DruhDreviny[proTeplickou.size()]);
    }
    
    /** Druhy dřevit pro teplickou metodu */
    public static List<DruhDreviny> proTeplickou(){
        //V Teplické metodě se používají všechny druhy jako v klasicke, jen se nepoužívá BOx
        List<DruhDreviny> proTeplickou = new ArrayList<DruhDreviny>(Arrays.asList(values()));
        proTeplickou.remove(BOx);
        return proTeplickou;
    }
    
    @Override
    public String toString() {
        return name() + " - " + popis;
    }
    
}
