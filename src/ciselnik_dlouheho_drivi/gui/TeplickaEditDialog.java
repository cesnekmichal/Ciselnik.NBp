package ciselnik_dlouheho_drivi.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Level;
import unidataz.components.edit.RestrictionComboBox;
import unidataz.components.edit.RestrictionContainer;
import unidataz.components.edit.UniEditComboBox;
import unidataz.unipos.client.components.window.UniDialog;
import unidataz.util.GLog;
import ciselnik_dlouheho_drivi.ciselnik.Ciselnik;
import ciselnik_dlouheho_drivi.ciselnik.TeplickaZaznam;
import ciselnik_dlouheho_drivi.gui.img.Resource;
import ciselnik_dlouheho_drivi.structures.DruhDreviny;

/**
 *
 * @author Michal
 */
public class TeplickaEditDialog extends UniDialog {

    public boolean approved = false;
    private final TeplickaZaznam zaznam;
    private final TeplickaZaznam editovanyZaznam;
    private RestrictionContainer rc = new RestrictionContainer();
    
    /**
     * Creates new form KalkulatorKlasickeMetodyDialog
     */
    public TeplickaEditDialog(java.awt.Window parent,Ciselnik ciselnik, TeplickaZaznam zaznam,EditMode mode) {
        super(parent);
        initComponents();
        
        this.zaznam = zaznam;
        this.editovanyZaznam = new TeplickaZaznam(zaznam);
        
        
        switch(mode){
            case New : setTitle("Nový záznam"); break;
            case Edit: setTitle("Editace záznamu"); break;
        }
        
        setIconImage(Resource.getImg(Resource.ImgKey.tree));
        setResizable(false);
        setVisibleHelpButton(false);
        
        setDefaultFocusedComponent(field_DruhDreviny);
                        
        field_DruhDreviny.setViewer(new UniEditComboBox.Viewer<DruhDreviny>() {
            @Override
            public String view(DruhDreviny item) {
                return item.name() + " - " + item.popis;
            }
        });
        
        field_DruhDreviny.setData(DruhDreviny.proTeplickou().toArray());
        field_DruhDreviny.setSelectedItem(editovanyZaznam.getDruhDreviny());
        field_DruhDreviny.addChangeListener(new UniEditComboBox.ChangeListener<DruhDreviny>() {
            @Override
            public void onChange(UniEditComboBox uniEditComboBox, DruhDreviny selectedItem) {
                if(selectedItem!=null){
                    editovanyZaznam.druh_dreviny_id = selectedItem.id;
                } else {
                    editovanyZaznam.druh_dreviny_id = null;
                }
            }
        });
        
        rc.add(new RestrictionComboBox(field_DruhDreviny, label_DruhDreviny, true));
        
        updateValue();
        
        field_Oddenkove00.setValue(editovanyZaznam.pocet_oddenkovych_00);
        field_Oddenkove0 .setValue(editovanyZaznam.pocet_oddenkovych_0 );
        field_Oddenkove1 .setValue(editovanyZaznam.pocet_oddenkovych_1 );
        field_Oddenkove2 .setValue(editovanyZaznam.pocet_oddenkovych_2 );
        field_Oddenkove3 .setValue(editovanyZaznam.pocet_oddenkovych_3 );
        field_Oddenkove4 .setValue(editovanyZaznam.pocet_oddenkovych_4 );
        field_Oddenkove5 .setValue(editovanyZaznam.pocet_oddenkovych_5 );

        field_Ostatni00.setValue(editovanyZaznam.pocet_ostatnich_00);
        field_Ostatni0 .setValue(editovanyZaznam.pocet_ostatnich_0 );
        field_Ostatni1 .setValue(editovanyZaznam.pocet_ostatnich_1 );
        field_Ostatni2 .setValue(editovanyZaznam.pocet_ostatnich_2 );
        field_Ostatni3 .setValue(editovanyZaznam.pocet_ostatnich_3 );
        field_Ostatni4 .setValue(editovanyZaznam.pocet_ostatnich_4 );
        field_Ostatni5 .setValue(editovanyZaznam.pocet_ostatnich_5 );
        
        setListeners(field_Oddenkove00,field_Oddenkove0,field_Oddenkove1,
                     field_Oddenkove2,field_Oddenkove3,field_Oddenkove4,field_Oddenkove5,
                     field_Ostatni00,field_Ostatni0,field_Ostatni1,
                     field_Ostatni2,field_Ostatni3,field_Ostatni4,field_Ostatni5);
        
        field_OddenkoveSoucet.overwritableTextField.setHorizontalAlignment(SwingConstants.CENTER);
        field_OstatniSoucet  .overwritableTextField.setHorizontalAlignment(SwingConstants.CENTER);
        
        field_Objem.overwritableTextField.setForeground(Color.BLUE);
        field_Objem.overwritableTextField.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setListeners(JSpinner... jss){
        for (JSpinner js : jss) {
            setListeners(js);
        }
    }
    private void setListeners(JSpinner js){
        js.addMouseWheelListener(new MouseWheelListener() {
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
        JSpinner.DefaultEditor jse = (JSpinner.DefaultEditor) js.getEditor();
        final JFormattedTextField ftf = jse.getTextField();
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
        js.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner src = (JSpinner) e.getSource();
                Integer value = (Integer) src.getValue();
                if(src==field_Oddenkove00) editovanyZaznam.pocet_oddenkovych_00 = value;
                if(src==field_Oddenkove0)  editovanyZaznam.pocet_oddenkovych_0 = value;
                if(src==field_Oddenkove1)  editovanyZaznam.pocet_oddenkovych_1 = value;
                if(src==field_Oddenkove2)  editovanyZaznam.pocet_oddenkovych_2 = value;
                if(src==field_Oddenkove3)  editovanyZaznam.pocet_oddenkovych_3 = value;
                if(src==field_Oddenkove4)  editovanyZaznam.pocet_oddenkovych_4 = value;
                if(src==field_Oddenkove5)  editovanyZaznam.pocet_oddenkovych_5 = value;
                if(src==field_Ostatni00)   editovanyZaznam.pocet_ostatnich_00 = value;
                if(src==field_Ostatni0)    editovanyZaznam.pocet_ostatnich_0 = value;
                if(src==field_Ostatni1)    editovanyZaznam.pocet_ostatnich_1 = value;
                if(src==field_Ostatni2)    editovanyZaznam.pocet_ostatnich_2 = value;
                if(src==field_Ostatni3)    editovanyZaznam.pocet_ostatnich_3 = value;
                if(src==field_Ostatni4)    editovanyZaznam.pocet_ostatnich_4 = value;
                if(src==field_Ostatni5)    editovanyZaznam.pocet_ostatnich_5 = value;
                updateValue();
            }
        });
        
    }
        
    private void updateValue(){
        field_OddenkoveSoucet.setValue(editovanyZaznam.getPocet_Oddenkovych());
        field_OstatniSoucet  .setValue(editovanyZaznam.getPocet_Ostatnich());
        field_Objem.setValue(editovanyZaznam.getObjem_Celkove());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        field_DruhDreviny = new unidataz.components.edit.UniEditComboBox();
        label_DruhDreviny = new javax.swing.JLabel();
        field_Objem = new unidataz.components.edit.UniEditBigDecimal();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        field_Oddenkove00 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        field_Oddenkove0 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        field_Oddenkove1 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        field_Oddenkove2 = new javax.swing.JSpinner();
        field_Oddenkove3 = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        field_Oddenkove4 = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        field_Ostatni0 = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        field_Ostatni2 = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        field_Ostatni1 = new javax.swing.JSpinner();
        field_Ostatni4 = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        field_Ostatni00 = new javax.swing.JSpinner();
        field_Ostatni3 = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        field_OddenkoveSoucet = new unidataz.components.edit.UniEditInteger();
        field_OstatniSoucet = new unidataz.components.edit.UniEditInteger();
        jLabel19 = new javax.swing.JLabel();
        field_Oddenkove5 = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        field_Ostatni5 = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_OK = new unidataz.unipos.client.components.form.button.ButtonOK();
        btn_Cancel = new unidataz.unipos.client.components.form.button.ButtonCancel();

        jPanel4.setMinimumSize(new java.awt.Dimension(240, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_START);

        label_DruhDreviny.setText("Druh dřeviny");

        field_Objem.setEditable(false);
        field_Objem.setPrecision(3);

        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Objem");

        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("m³");

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("00");

        field_Oddenkove00.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Oddenkové");

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("0");

        field_Oddenkove0.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("1");

        field_Oddenkove1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("2");

        field_Oddenkove2.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        field_Oddenkove3.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("3");

        field_Oddenkove4.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("4");

        field_Ostatni0.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("0");

        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("2");

        field_Ostatni2.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("1");

        field_Ostatni1.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        field_Ostatni4.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel13.setForeground(new java.awt.Color(0, 0, 255));
        jLabel13.setText("00");

        jLabel14.setForeground(new java.awt.Color(0, 0, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("4");

        field_Ostatni00.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        field_Ostatni3.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("3");

        jLabel16.setForeground(new java.awt.Color(0, 0, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Ostatní");

        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Σ");

        jLabel18.setForeground(new java.awt.Color(0, 0, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Σ");

        field_OddenkoveSoucet.setEditable(false);

        field_OstatniSoucet.setEditable(false);

        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("5");

        field_Oddenkove5.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel20.setForeground(new java.awt.Color(0, 0, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("5");

        field_Ostatni5.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_DruhDreviny)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_DruhDreviny, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_Objem, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(field_Oddenkove00, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(field_Oddenkove0, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(field_Oddenkove1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(field_Oddenkove2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(field_Oddenkove3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel17)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel19))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(field_Oddenkove4, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                                .addComponent(field_OddenkoveSoucet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(field_Oddenkove5)))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni00, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni0, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_OstatniSoucet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(field_Ostatni5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, label_DruhDreviny});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel6, jLabel7, jLabel8, jLabel9});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel15, jLabel18});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {field_Oddenkove0, field_Oddenkove00, field_Oddenkove1, field_Oddenkove2, field_Oddenkove3, field_Oddenkove4, field_OddenkoveSoucet});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {field_Ostatni0, field_Ostatni00, field_Ostatni1, field_Ostatni2, field_Ostatni3, field_Ostatni4, field_OstatniSoucet});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(field_DruhDreviny, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_DruhDreviny))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(field_Ostatni00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(field_Ostatni0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(field_Ostatni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(field_Ostatni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(field_Ostatni3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(field_Ostatni4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(field_OstatniSoucet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(field_Oddenkove00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(field_Oddenkove0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(field_Oddenkove1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(field_Oddenkove2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(field_Oddenkove3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(field_Oddenkove4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(field_Oddenkove5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(field_Ostatni5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(field_OddenkoveSoucet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(field_Objem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 6, 0));

        btn_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OKActionPerformed(evt);
            }
        });
        jPanel3.add(btn_OK);
        jPanel3.add(btn_Cancel);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OKActionPerformed
        
        if(rc.runCheckFields(TeplickaEditDialog.this)){
            zaznam.set(editovanyZaznam);
            approved = true;
            setVisible(false);            
        }
        
    }//GEN-LAST:event_btn_OKActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private unidataz.unipos.client.components.form.button.ButtonCancel btn_Cancel;
    private unidataz.unipos.client.components.form.button.ButtonOK btn_OK;
    private unidataz.components.edit.UniEditComboBox field_DruhDreviny;
    private unidataz.components.edit.UniEditBigDecimal field_Objem;
    private javax.swing.JSpinner field_Oddenkove0;
    private javax.swing.JSpinner field_Oddenkove00;
    private javax.swing.JSpinner field_Oddenkove1;
    private javax.swing.JSpinner field_Oddenkove2;
    private javax.swing.JSpinner field_Oddenkove3;
    private javax.swing.JSpinner field_Oddenkove4;
    private javax.swing.JSpinner field_Oddenkove5;
    private unidataz.components.edit.UniEditInteger field_OddenkoveSoucet;
    private javax.swing.JSpinner field_Ostatni0;
    private javax.swing.JSpinner field_Ostatni00;
    private javax.swing.JSpinner field_Ostatni1;
    private javax.swing.JSpinner field_Ostatni2;
    private javax.swing.JSpinner field_Ostatni3;
    private javax.swing.JSpinner field_Ostatni4;
    private javax.swing.JSpinner field_Ostatni5;
    private unidataz.components.edit.UniEditInteger field_OstatniSoucet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel label_DruhDreviny;
    // End of variables declaration//GEN-END:variables
}
