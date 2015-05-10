package ciselnik_dlouheho_drivi.ciselnik;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ciselnik_dlouheho_drivi.structures.DruhDreviny;

/**
 *
 * @author Michal
 */
@XmlType(propOrder={"druh_dreviny_id",
                    "pocet_oddenkovych_00","pocet_oddenkovych_0","pocet_oddenkovych_1",
                    "pocet_oddenkovych_2" ,"pocet_oddenkovych_3","pocet_oddenkovych_4","pocet_oddenkovych_5",
                    "pocet_ostatnich_00","pocet_ostatnich_0","pocet_ostatnich_1",
                    "pocet_ostatnich_2" ,"pocet_ostatnich_3","pocet_ostatnich_4","pocet_ostatnich_5"})
public class TeplickaZaznam {

    public TeplickaZaznam() {
    }

    public TeplickaZaznam(TeplickaZaznam zaznam) {
        set(zaznam);
    }

    public void set(TeplickaZaznam zaznam){
        if(zaznam!=null){
            this.druh_dreviny_id = zaznam.druh_dreviny_id;
            this.pocet_oddenkovych_00 = zaznam.pocet_oddenkovych_00;
            this.pocet_oddenkovych_0  = zaznam.pocet_oddenkovych_0;
            this.pocet_oddenkovych_1  = zaznam.pocet_oddenkovych_1;
            this.pocet_oddenkovych_2  = zaznam.pocet_oddenkovych_2;
            this.pocet_oddenkovych_3  = zaznam.pocet_oddenkovych_3;
            this.pocet_oddenkovych_4  = zaznam.pocet_oddenkovych_4;
            this.pocet_oddenkovych_5  = zaznam.pocet_oddenkovych_5;
            this.pocet_ostatnich_00 = zaznam.pocet_ostatnich_00;
            this.pocet_ostatnich_0  = zaznam.pocet_ostatnich_0;
            this.pocet_ostatnich_1  = zaznam.pocet_ostatnich_1;
            this.pocet_ostatnich_2  = zaznam.pocet_ostatnich_2;
            this.pocet_ostatnich_3  = zaznam.pocet_ostatnich_3;
            this.pocet_ostatnich_4  = zaznam.pocet_ostatnich_4;
            this.pocet_ostatnich_5  = zaznam.pocet_ostatnich_5;
        }        
    }    
    
    @XmlElement(name = "DRUH_DREVINY_ID")
    public Integer druh_dreviny_id = 0;

    @XmlElement(name = "POCET_ODDENKOVYCH_00")
    public Integer pocet_oddenkovych_00 = 0;

    @XmlElement(name = "POCET_ODDENKOVYCH_0")
    public Integer pocet_oddenkovych_0 = 0;
    
    @XmlElement(name = "POCET_ODDENKOVYCH_1")
    public Integer pocet_oddenkovych_1 = 0;
    
    @XmlElement(name = "POCET_ODDENKOVYCH_2")
    public Integer pocet_oddenkovych_2 = 0;
    
    @XmlElement(name = "POCET_ODDENKOVYCH_3")
    public Integer pocet_oddenkovych_3 = 0;
    
    @XmlElement(name = "POCET_ODDENKOVYCH_4")
    public Integer pocet_oddenkovych_4 = 0;

    @XmlElement(name = "POCET_ODDENKOVYCH_5")
    public Integer pocet_oddenkovych_5 = 0;
    
    @XmlElement(name = "POCET_OSTATNICH_00")
    public Integer pocet_ostatnich_00 = 0;

    @XmlElement(name = "POCET_OSTATNICH_0")
    public Integer pocet_ostatnich_0 = 0;
    
    @XmlElement(name = "POCET_OSTATNICH_1")
    public Integer pocet_ostatnich_1 = 0;
    
    @XmlElement(name = "POCET_OSTATNICH_2")
    public Integer pocet_ostatnich_2 = 0;
    
    @XmlElement(name = "POCET_OSTATNICH_3")
    public Integer pocet_ostatnich_3 = 0;
    
    @XmlElement(name = "POCET_OSTATNICH_4")
    public Integer pocet_ostatnich_4 = 0;

    @XmlElement(name = "POCET_OSTATNICH_5")
    public Integer pocet_ostatnich_5 = 0;
    
    public DruhDreviny getDruhDreviny(){
        return DruhDreviny.get(druh_dreviny_id);
    }    
    
    public Integer getPocet_00(){
        return pocet_oddenkovych_00 + pocet_ostatnich_00;
    }
    public Integer getPocet_0(){
        return pocet_oddenkovych_0 + pocet_ostatnich_0;
    }
    public Integer getPocet_1(){
        return pocet_oddenkovych_1 + pocet_ostatnich_1;
    }
    public Integer getPocet_2(){
        return pocet_oddenkovych_2 + pocet_ostatnich_2;
    }
    public Integer getPocet_3(){
        return pocet_oddenkovych_3 + pocet_ostatnich_3;
    }
    public Integer getPocet_4(){
        return pocet_oddenkovych_4 + pocet_ostatnich_4;
    }
    public Integer getPocet_5(){
        return pocet_oddenkovych_5 + pocet_ostatnich_5;
    }
    public Integer getPocet_Oddenkovych(){
        return  pocet_oddenkovych_00 + pocet_oddenkovych_0 
               + pocet_oddenkovych_1 + pocet_oddenkovych_2
               + pocet_oddenkovych_3 + pocet_oddenkovych_4 + pocet_oddenkovych_5;
    }
    public Integer getPocet_Ostatnich(){
        return  pocet_ostatnich_00 + pocet_ostatnich_0 
               + pocet_ostatnich_1 + pocet_ostatnich_2
               + pocet_ostatnich_3 + pocet_ostatnich_4 + pocet_ostatnich_5;
    }
    
    public Integer getPocet(){
        return  getPocet_Oddenkovych() + getPocet_Ostatnich();
    }
    
    public static BigDecimal coef_00 = new BigDecimal("0.025");
    public static BigDecimal coef_0  = new BigDecimal("0.05");
    public static BigDecimal coef_1  = new BigDecimal("0.1");
    public static BigDecimal coef_2  = new BigDecimal("0.2");
    public static BigDecimal coef_3  = new BigDecimal("0.3");
    public static BigDecimal coef_4  = new BigDecimal("0.4");
    public static BigDecimal coef_5  = new BigDecimal("0.5");
    
    public BigDecimal getObjem_00(){
        return coef_00.multiply(BigDecimal.valueOf(getPocet_00()));
    }
    public BigDecimal getObjem_0(){
        return coef_0.multiply(BigDecimal.valueOf(getPocet_0()));
    }
    public BigDecimal getObjem_1(){
        return coef_1.multiply(BigDecimal.valueOf(getPocet_1()));
    }
    public BigDecimal getObjem_2(){
        return coef_2.multiply(BigDecimal.valueOf(getPocet_2()));
    }
    public BigDecimal getObjem_3(){
        return coef_3.multiply(BigDecimal.valueOf(getPocet_3()));
    }
    public BigDecimal getObjem_4(){
        return coef_4.multiply(BigDecimal.valueOf(getPocet_4()));
    }
    public BigDecimal getObjem_5(){
        return coef_5.multiply(BigDecimal.valueOf(getPocet_5()));
    }
    public BigDecimal getObjem_Celkove(){
        return       getObjem_00().add(getObjem_0())
                .add(getObjem_1()).add(getObjem_2())
                .add(getObjem_3()).add(getObjem_4()).add(getObjem_5());
    }
 
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(druh_dreviny_id)
                .append(pocet_oddenkovych_00).append(pocet_oddenkovych_0)
                .append(pocet_oddenkovych_1 ).append(pocet_oddenkovych_2)
                .append(pocet_oddenkovych_3 ).append(pocet_oddenkovych_4).append(pocet_oddenkovych_5)
                .append(pocet_ostatnich_00).append(pocet_ostatnich_0)
                .append(pocet_ostatnich_1 ).append(pocet_ostatnich_2)
                .append(pocet_ostatnich_3 ).append(pocet_ostatnich_4).append(pocet_ostatnich_5)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }
    
}
