package ciselnik_dlouheho_drivi.gui;

import ciselnik_dlouheho_drivi.ciselnik.Ciselnik;
import ciselnik_dlouheho_drivi.ciselnik.KlasickaZaznam;
import ciselnik_dlouheho_drivi.gui.img.Resource;
import ciselnik_dlouheho_drivi.structures.DruhDreviny;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.log4j.Level;
import unidataz.components.edit.UniEditComboBox;
import unidataz.unipos.client.components.window.UniDialog;
import unidataz.util.GLog;

/**
 *
 * @author Michal
 */
public class KlasickaEditDialog extends UniDialog {

    public boolean approved = false;
    private final KlasickaZaznam zaznam;
    private final KlasickaZaznam editovanyZaznam;
    private final Ciselnik ciselnik;
    /**
     * Creates new form KalkulatorKlasickeMetodyDialog
     */
    public KlasickaEditDialog(java.awt.Window parent, Ciselnik ciselnik, KlasickaZaznam zaznam,EditMode mode) {
        super(parent);
        initComponents();
        
        this.ciselnik = ciselnik;
        this.zaznam = zaznam;
        this.editovanyZaznam = new KlasickaZaznam(zaznam);
        
        
        switch(mode){
            case New : setTitle("Nový záznam"); break;
            case Edit: setTitle("Editace záznamu"); break;
        }
        
        setIconImage(Resource.getImg(Resource.ImgKey.tree));
        setResizable(false);
        setVisibleHelpButton(false);
        
        switch(mode){
            case New  : setDefaultFocusedComponent(field_DruhDreviny); break;
            case Edit  : setDefaultFocusedComponent(field_Cislo.getEditor()); break;
        }         
        
        field_Cislo.setValue(editovanyZaznam.poradove_cislo);
        field_Cislo.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                editovanyZaznam.poradove_cislo = (Integer) field_Cislo.getValue();
            }
        });
        
        field_Poznamka.setText(editovanyZaznam.poznamka);
        field_Poznamka.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { onChange(); }
            @Override
            public void removeUpdate(DocumentEvent e) { onChange(); }
            @Override
            public void changedUpdate(DocumentEvent e) { onChange(); }
            private void onChange(){
                editovanyZaznam.poznamka = field_Poznamka.getText();
            }
        });
        
        field_DruhDreviny.setViewer(new UniEditComboBox.Viewer<DruhDreviny>() {
            @Override
            public String view(DruhDreviny item) {
                return item.name() + " - " + item.popis;
            }
        });
        field_DruhDreviny.setData(DruhDreviny.values());
        field_DruhDreviny.setSelectedItem(editovanyZaznam.getDruhDreviny());
        if(editovanyZaznam.getDruhDreviny().equals(DruhDreviny.BOx) || editovanyZaznam.getDruhDreviny().equals(DruhDreviny.MDx)){
            field_Oddenkovy.setSelected(true);
            field_Oddenkovy.setEnabled(false);
        }
        field_DruhDreviny.addChangeListener(new UniEditComboBox.ChangeListener<DruhDreviny>() {
            @Override
            public void onChange(UniEditComboBox uniEditComboBox, DruhDreviny selectedItem) {
                if(selectedItem!=null){
                    editovanyZaznam.druh_dreviny_id = selectedItem.id;
                    if(selectedItem.equals(DruhDreviny.BOx) || selectedItem.equals(DruhDreviny.MDx)){
                        field_Oddenkovy.setSelected(true);
                        field_Oddenkovy.setEnabled(false);
                    } else {
                        field_Oddenkovy.setEnabled(true);
                    }
                } else {
                    editovanyZaznam.druh_dreviny_id = null;
                }
                updateValue();
            }
        });
                
        field_Oddenkovy.setSelected(editovanyZaznam.oddenkovy_kus);
        field_Oddenkovy.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    editovanyZaznam.oddenkovy_kus = true;
                } else {
                    editovanyZaznam.oddenkovy_kus = false;
                }
            }
        });
        
        field_Delka.setValue(editovanyZaznam.delka);
        field_Delka.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                editovanyZaznam.delka = (Integer) field_Delka.getValue();
                updateValue();
            }
        });
        
        field_Prumer.setValue(editovanyZaznam.prumer);
        field_Prumer.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                editovanyZaznam.prumer = (Integer) field_Prumer.getValue();
                updateValue();
            }
        });
        
        updateValue();

        setListeners(field_Cislo);
        setListeners(field_Delka);
        setListeners(field_Prumer);
        field_hmota.overwritableTextField.setForeground(Color.BLUE);
        field_hmota.overwritableTextField.setHorizontalAlignment(SwingConstants.CENTER);
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
    }
            
    private void updateValue(){
        field_hmota.setValue(editovanyZaznam.getObjem());
    }
    
    public JPopupMenu generatePopup(){
        JPopupMenu jpm = new JPopupMenu();
        final HashMap<DruhDreviny, Integer> klasicka_dd_HM = ciselnik.getKlasicka_DruhyDrevin_HM();
        if(klasicka_dd_HM.isEmpty()) return null;
        ArrayList<DruhDreviny> klasicka_dd = new ArrayList<DruhDreviny>(klasicka_dd_HM.keySet());
        Collections.sort(klasicka_dd, new Comparator<DruhDreviny>() {
            @Override
            public int compare(DruhDreviny o1, DruhDreviny o2) {
                return klasicka_dd_HM.get(o2).compareTo(klasicka_dd_HM.get(o1));
            }
        });
        JLabel jl = new JLabel(" Rychlá volba");
        jl.setBackground(Color.WHITE);
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jl.setOpaque(true);
        jpm.add(jl);
        for (final DruhDreviny dd : klasicka_dd) {
            JMenuItem jmi = 
            new JMenuItem(new AbstractAction(dd.toString()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    field_DruhDreviny.setSelectedItem(dd);
                }
            });
            ImageUtil.setIcon(jmi, klasicka_dd_HM.get(dd));
            jpm.add(jmi);
        }
        return jpm;
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
        field_Delka = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        field_Prumer = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        field_hmota = new unidataz.components.edit.UniEditBigDecimal();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        label_Cislo = new javax.swing.JLabel();
        field_Oddenkovy = new javax.swing.JCheckBox();
        field_Cislo = new javax.swing.JSpinner();
        btn_ShowPopup = new javax.swing.JButton();
        label_Poznamka = new javax.swing.JLabel();
        field_Poznamka = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_OK = new unidataz.unipos.client.components.form.button.ButtonOK();
        btn_Cancel = new unidataz.unipos.client.components.form.button.ButtonCancel();

        jPanel4.setMinimumSize(new java.awt.Dimension(250, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_START);

        field_DruhDreviny.setCanBeUnselected(false);

        label_DruhDreviny.setText("Druh dřeviny");

        field_Delka.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel1.setText("Délka v m");

        field_Prumer.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(19), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel3.setText("Průměr v cm");

        field_hmota.setEditable(false);
        field_hmota.setPrecision(2);

        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Objem");

        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("m³");

        label_Cislo.setText("Pořadové číslo");

        field_Oddenkovy.setText("Oddenkovy");

        field_Cislo.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));

        btn_ShowPopup.setText("..");
        btn_ShowPopup.setToolTipText("Rychlý výběr");
        btn_ShowPopup.setMargin(new java.awt.Insets(0, 2, 0, 2));
        btn_ShowPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ShowPopupActionPerformed(evt);
            }
        });

        label_Poznamka.setText("Poznámka");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_Delka))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_Prumer)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(field_hmota, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_Cislo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_Cislo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_DruhDreviny)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(field_Oddenkovy)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(field_DruhDreviny, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                .addGap(1, 1, 1)
                                .addComponent(btn_ShowPopup))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_Poznamka)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_Poznamka)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel4, label_Cislo, label_DruhDreviny, label_Poznamka});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Cislo)
                    .addComponent(field_Cislo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Poznamka)
                    .addComponent(field_Poznamka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(field_DruhDreviny, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_DruhDreviny)
                    .addComponent(btn_ShowPopup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(field_Oddenkovy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_Delka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_Prumer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(field_hmota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        zaznam.set(editovanyZaznam);
        approved = true;
        setVisible(false);
        
    }//GEN-LAST:event_btn_OKActionPerformed

    private void btn_ShowPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ShowPopupActionPerformed
        
        JPopupMenu popupMenu = generatePopup();
        if(popupMenu!=null){
            popupMenu.show(btn_ShowPopup, 0, btn_ShowPopup.getHeight());
        }
        
    }//GEN-LAST:event_btn_ShowPopupActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private unidataz.unipos.client.components.form.button.ButtonCancel btn_Cancel;
    private unidataz.unipos.client.components.form.button.ButtonOK btn_OK;
    private javax.swing.JButton btn_ShowPopup;
    private javax.swing.JSpinner field_Cislo;
    private javax.swing.JSpinner field_Delka;
    private unidataz.components.edit.UniEditComboBox field_DruhDreviny;
    private javax.swing.JCheckBox field_Oddenkovy;
    private javax.swing.JTextField field_Poznamka;
    private javax.swing.JSpinner field_Prumer;
    private unidataz.components.edit.UniEditBigDecimal field_hmota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel label_Cislo;
    private javax.swing.JLabel label_DruhDreviny;
    private javax.swing.JLabel label_Poznamka;
    // End of variables declaration//GEN-END:variables
}
