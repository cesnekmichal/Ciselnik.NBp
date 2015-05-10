package ciselnik_dlouheho_drivi.gui.laborator;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Level;
import unidataz.util.GLog;

/**
 *
 * @author Michal
 */
public class LaboratPanel extends javax.swing.JPanel {

    private final BigDecimal objem;
    private final int oddenkovych;
    private final int ostatnich;
    private final int celkemKusu;
    
    /**
     * Creates new form LaboratPanel
     */
    public LaboratPanel(String name, BigDecimal objem, int odd, int ost) {
        initComponents();
        
        setBorder(javax.swing.BorderFactory.createTitledBorder(name));
        
        this.objem = objem;
        this.oddenkovych = odd;
        this.ostatnich   = ost;
        this.celkemKusu = odd + ost;
        
        //setBackground(Color.lightGray);
        
        field_Objem.setValue(objem);
        field_OddenkovychKusu.setModel(new javax.swing.SpinnerNumberModel(oddenkovych, 0, celkemKusu, 1));
        field_OstatnichKusu.setModel(new javax.swing.SpinnerNumberModel(ostatnich, 0, celkemKusu , 1));
        field_PrumernaHmotnatost.setValue(getHmotnatost());
        
        field_OddenkovychKusu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                field_OstatnichKusu.setValue(celkemKusu - getOddenkovych());
                int oddZmena = getOddenkovych()-oddenkovych;
                if(oddZmena>0){
                    label_OddZmena.setText("+"+oddZmena);
                    label_OddZmena.setForeground(Color.RED);
                } else 
                if(oddZmena<0){
                    label_OddZmena.setText(""+oddZmena);
                    label_OddZmena.setForeground(Color.RED);
                } else {
                    label_OddZmena.setText("+0");
                    label_OddZmena.setForeground(Color.BLACK);
                }
                int ostZmena = getOstatnich()-ostatnich;
                if(ostZmena>0){
                    label_OstZmena.setText("+"+ostZmena);
                    label_OstZmena.setForeground(Color.RED);
                } else 
                if(ostZmena<0){
                    label_OstZmena.setText(""+ostZmena);
                    label_OstZmena.setForeground(Color.RED);
                } else {
                    label_OstZmena.setText("+0");
                    label_OstZmena.setForeground(Color.BLACK);
                }
                field_PrumernaHmotnatost.setValue(getHmotnatost());
            }
        });
        
        setFocusListener();
        setMouseWhellIncrementation();
        
    }

    private Integer getOddenkovych(){
        return (Integer) field_OddenkovychKusu.getValue();
    }

    private Integer getOstatnich(){
        return (Integer) field_OstatnichKusu.getValue();
    }
    
    private BigDecimal getHmotnatost(){
        Integer oddenkovychKusu = (Integer) field_OddenkovychKusu.getValue();
        return oddenkovychKusu!=0 ?  objem.divide(new BigDecimal(oddenkovychKusu), 3, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }
    
    private void setFocusListener(){
        final JFormattedTextField ftf = getTextField();
        ftf.addFocusListener(new FocusListener() {
            Color bgColor_orig = ftf.getBackground();
            @Override
            public void focusGained(FocusEvent e) {
                                  //pastelově zelená
                ftf.setBackground(new Color(240, 251, 242));
            }
            @Override
            public void focusLost(FocusEvent e) {
                ftf.setBackground(bgColor_orig);
            }
        });        
    }    
    
    private void setMouseWhellIncrementation(){
        getTextField().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Robot r;
                try {
                    r = new Robot();
                } catch (AWTException ex) {
                    GLog.elog(Level.ERROR,GLog.EXCEPTION, ex, ex);
                    return;
                }
                if (e.getWheelRotation() < 0) {//Nahoru
                    r.keyPress(KeyEvent.VK_UP);
                } else {                       //Dolu
                    r.keyPress(KeyEvent.VK_DOWN);
                }
            }
        });        
    }    
    
    private JFormattedTextField getTextField(){
        JSpinner.DefaultEditor jse = (JSpinner.DefaultEditor) field_OddenkovychKusu.getEditor();
        return jse.getTextField();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        field_Objem = new unidataz.components.edit.UniEditBigDecimal();
        jLabel7 = new javax.swing.JLabel();
        label_OddZmena = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        field_PrumernaHmotnatost = new unidataz.components.edit.UniEditBigDecimal();
        field_OstatnichKusu = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        field_OddenkovychKusu = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        label_OstZmena = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Titulek"));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Objem");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Oddenkových kusů");

        field_Objem.setEditable(false);
        field_Objem.setPrecision(3);

        jLabel7.setText("m³/strom");

        label_OddZmena.setFont(label_OddZmena.getFont().deriveFont(label_OddZmena.getFont().getStyle() & ~java.awt.Font.BOLD));
        label_OddZmena.setText("+0");

        jLabel3.setText("m³");

        field_PrumernaHmotnatost.setEnabled(false);
        field_PrumernaHmotnatost.setPrecision(3);

        field_OstatnichKusu.setEnabled(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Průměrná hmotnatost");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Ostatních kusů");

        label_OstZmena.setFont(label_OstZmena.getFont().deriveFont(label_OstZmena.getFont().getStyle() & ~java.awt.Font.BOLD));
        label_OstZmena.setText("+0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_PrumernaHmotnatost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_Objem, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_OstatnichKusu)
                            .addComponent(field_OddenkovychKusu, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(label_OddZmena)
                    .addComponent(label_OstZmena))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel4, jLabel5, jLabel6});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {field_Objem, field_OddenkovychKusu, field_OstatnichKusu, field_PrumernaHmotnatost});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(field_Objem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(field_OddenkovychKusu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_OddZmena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(field_OstatnichKusu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_OstZmena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(field_PrumernaHmotnatost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private unidataz.components.edit.UniEditBigDecimal field_Objem;
    private javax.swing.JSpinner field_OddenkovychKusu;
    private javax.swing.JSpinner field_OstatnichKusu;
    private unidataz.components.edit.UniEditBigDecimal field_PrumernaHmotnatost;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel label_OddZmena;
    private javax.swing.JLabel label_OstZmena;
    // End of variables declaration//GEN-END:variables
}
