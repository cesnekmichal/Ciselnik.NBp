package ciselnik_dlouheho_drivi.ciselnik;

import ciselnik_dlouheho_drivi.structures.DruhDreviny;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Záznam klasické metody
 * @author Michal
 */
@XmlType(propOrder={"poradove_cislo","poznamka","druh_dreviny_id","oddenkovy_kus","delka","prumer"})
public class KlasickaZaznam {

    public KlasickaZaznam() {
    }
    
    public KlasickaZaznam(KlasickaZaznam zaznam) {
        set(zaznam);
    }
    
    public void set(KlasickaZaznam zaznam){
        if(zaznam!=null){
            this.poradove_cislo = zaznam.poradove_cislo;
            this.poznamka = zaznam.poznamka;
            this.druh_dreviny_id = zaznam.druh_dreviny_id;
            this.oddenkovy_kus = zaznam.oddenkovy_kus;
            this.delka = zaznam.delka;
            this.prumer = zaznam.prumer;           
        }        
    }
    
    @XmlAttribute(name = "PORADOVE_CISLO")
    public Integer poradove_cislo = 0;

    @XmlElement(name="POZNAMKA")
    public String poznamka = "";
    
    @XmlElement(name = "DRUH_DREVINY_ID")
    public Integer druh_dreviny_id = DruhDreviny.SM.id;
    
    @XmlElement(name="ODDENKOVY_KUS", defaultValue="flase")
    public Boolean oddenkovy_kus = Boolean.FALSE;    
    
    @XmlElement(name="DELKA")
    public Integer delka = 3;
    
    @XmlElement(name="PRUMER")
    public Integer prumer = 10;

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(poradove_cislo).append(poznamka).append(druh_dreviny_id)
                .append(oddenkovy_kus).append(delka).append(prumer)
            .toHashCode();
    }
    
    public DruhDreviny getDruhDreviny(){
        return DruhDreviny.get(druh_dreviny_id);
    }
    
    public BigDecimal getObjem(){
        return getDruhDreviny().tridaDrevin.objem(delka, prumer);
    }
    
}
