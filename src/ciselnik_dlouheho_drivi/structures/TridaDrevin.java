package ciselnik_dlouheho_drivi.structures;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 
 * Koeficienty dle ČSN 480009 
 *  I. 	  smrk(SM), jedle(JD)
 *  II.A  borovice - kůra(BO), vejmutovka(VJ), douglaska(DG)
 *  II.B  borovice - borka (oddenkové výřezy), modřín
 *  III.  buk, javor, habr, jeřáb, lípa, osika, platan, švestka, třešeň, hrušeň, jabloň
 *  IV.   dub, dub cer, jilm, jasan, akát, bříza, jírovec, olše, topol, ořešák, vrby
 * Zroj: http://www.optimalanskroun.com/kubirovani.html
 */
public enum TridaDrevin {
    I  (TypStromu.JEHLICNAN, 0.577230, 0.0068968, 1.312300),
    IIa(TypStromu.JEHLICNAN, 0.250170, 0.0019147, 1.786600),
    IIb(TypStromu.JEHLICNAN, 1.701500, 0.0087620, 1.456800),
    III(TypStromu.LISTNAC  ,-0.040877, 0.1663400, 0.560760),
    IV (TypStromu.LISTNAC  , 1.247400, 0.0426230, 1.062300);

    public  final TypStromu typ;
    private final double P0,P1,P2;
    
    private TridaDrevin(TypStromu typ,double P0,double P1,double P2) {
        this.typ = typ;
        this.P0 = P0;
        this.P1 = P1;
        this.P2 = P2;        
    }
    
    public BigDecimal objem(int delka, int prumer){
        //V = {[d - (P0 + P1 × dP2)]2 × π × L}/40 000
        return ROUND((POWER(prumer-(P0+P1*POWER(prumer,P2)),2)*PI()*delka)/40000,2);
    }

    //<editor-fold defaultstate="collapsed" desc="POWER, ROUND, PI">
    private static double POWER(double zaklad, double exponent){
        return Math.pow(zaklad, exponent);
    }
    private static BigDecimal ROUND(double v,int i){
        return BigDecimal.valueOf(v).setScale(i, RoundingMode.HALF_UP);
    }
    private static double PI() {
        return Math.PI;
    }
    //</editor-fold>
    
    
}
