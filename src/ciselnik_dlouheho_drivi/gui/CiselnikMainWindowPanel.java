package ciselnik_dlouheho_drivi.gui;

import ciselnik_dlouheho_drivi.ciselnik.Ciselnik;
import ciselnik_dlouheho_drivi.ciselnik.KlasickaZaznam;
import ciselnik_dlouheho_drivi.ciselnik.TeplickaZaznam;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import unidataz.components.UniMessageDialog;
import unidataz.components.UniMessageDialog.Option;
import unidataz.components.table.Presenter;
import unidataz.components.table.cell_renderers.AbstractRenderer;
import unidataz.components.table.cell_renderers.CompoundRenderer;
import unidataz.components.table.cell_renderers.DecimalRenderer;
import unidataz.components.table.cell_renderers.IntegerRenderer;
import unidataz.components.table.cell_renderers.StringRenderer;
import unidataz.unipos.database.tables.DBItemCurrency_V3;
import unidataz.util.ExecuteUtil;
import unidataz.util.FileUtil;
import unidataz.util.Handler;
import unidataz.util.swing.WindowUtils;
import unidataz.util.text.StringUtil;

/**
 *
 * @author Michal
 */
public class CiselnikMainWindowPanel extends javax.swing.JPanel {

    public final Ciselnik ciselnik;
    public final CiselnikMainWindowFrame mainFrame;
            
    /**
     * Creates new form CiselnikMainWindowPanel
     */
    public CiselnikMainWindowPanel(CiselnikMainWindowFrame mainFrame, final Ciselnik ciselnik) {
        initComponents();
        this.mainFrame = mainFrame;
        this.ciselnik = ciselnik;
        
        field_Vystaveno.setDate(ciselnik.datum);
        field_Vystaveno.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ciselnik.datum = field_Vystaveno.getDate();
            }
        });
        
        field_Popis.setText(ciselnik.popis);
        field_Popis.addChangeListener(new Handler.HandlerChangeListener<String>() {
            @Override
            public void afterChange(String oldValue, String newValue) {
                ciselnik.popis = newValue;
            }
        });
        
        fieldKlasic_Celkem.setValue(ciselnik.getKlasicka_Objem());
        fieldTeplic_Celkem.setValue(ciselnik.getTeplicka_Objem());
        
        //<editor-fold defaultstate="collapsed" desc="tablePanel_Klasicka">
        tablePanel_Klasicka.setOwnerOfInstance(CiselnikMainWindowPanel.this);
        tablePanel_Klasicka.setPresenter(new Presenter<KlasickaZaznam>() {
            @Override
            public String[] getColumnNames() {
                return strArr("Pořadové číslo","Pozn.",
                        "Druh dřeviny",
                        "Oddenkový kus",
                        "Délka (m)",
                        "Průměr (cm)",
                        "Objem (m³)");
            }
            
            @Override
            public Object[] getColumnValues(KlasickaZaznam item) {
                return objArr(item.poradove_cislo,
                        new StringRenderer(item.poznamka).setCellRenderAlign(AbstractRenderer.Align.CENTER),
                        item.getDruhDreviny().toString(),
                        item.oddenkovy_kus,
                        item.delka,item.prumer,getCOLUMN_Objem(item));
            }
            private DecimalRenderer getCOLUMN_Objem(KlasickaZaznam item){
                return new DecimalRenderer(item.getObjem())
                        .setCellRenderAlign(AbstractRenderer.Align.CENTER);
            }
        });
        
        tablePanel_Klasicka.setData(ciselnik.klasicka);
        tablePanel_Klasicka.setDoubleClickOrEnterAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnKlas_Edit.doClick();
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="tablePanel_Teplicka">
        tablePanel_Teplicka.setOwnerOfInstance(CiselnikMainWindowPanel.this);
        tablePanel_Teplicka.setPresenter(new Presenter<TeplickaZaznam>() {
            @Override
            public String[] getColumnNames() {
                return strArr(
                        "Druh dřeviny",
                        "<html><font color=red>00</font></html>",
                        "<html><font color=red>0</font></html>",
                        "<html><font color=red>1</font></html>",
                        "<html><font color=red>2</font></html>",
                        "<html><font color=red>3</font></html>",
                        "<html><font color=red>4</font></html>",
                        "<html><font color=red>5</font></html>",
                        "<html><font color=red>Odden. kusů</font></html>",
                        "<html><font color=blue>00</font></html>",
                        "<html><font color=blue>0</font></html>",
                        "<html><font color=blue>1</font></html>",
                        "<html><font color=blue>2</font></html>",
                        "<html><font color=blue>3</font></html>",
                        "<html><font color=blue>4</font></html>",
                        "<html><font color=blue>5</font></html>",
                        "<html><font color=blue>Ostat. kusů</font></html>",
                        "Objem (m³)");
            }
            
            @Override
            public Object[] getColumnValues(TeplickaZaznam item) {
                return objArr(
                        item.getDruhDreviny(),
                        getCOL(item.pocet_oddenkovych_00, Color.RED),
                        getCOL(item.pocet_oddenkovych_0 , Color.RED),
                        getCOL(item.pocet_oddenkovych_1 , Color.RED),
                        getCOL(item.pocet_oddenkovych_2 , Color.RED),
                        getCOL(item.pocet_oddenkovych_3 , Color.RED),
                        getCOL(item.pocet_oddenkovych_4 , Color.RED),
                        getCOL(item.pocet_oddenkovych_5 , Color.RED),
                        getCOLUMN_pocetOddenkovych(item),
                        getCOL(item.pocet_ostatnich_00, Color.BLUE),
                        getCOL(item.pocet_ostatnich_0 , Color.BLUE),
                        getCOL(item.pocet_ostatnich_1 , Color.BLUE),
                        getCOL(item.pocet_ostatnich_2 , Color.BLUE),
                        getCOL(item.pocet_ostatnich_3 , Color.BLUE),
                        getCOL(item.pocet_ostatnich_4 , Color.BLUE),
                        getCOL(item.pocet_ostatnich_5 , Color.BLUE),
                        getCOLUMN_pocetOstatnich(item),
                        getCOLUMN_objem(item));
            }
            private CompoundRenderer getCOL(int pocet,Color color){
                String view = pocet!=0 ? pocet+"" : "";
                StringRenderer viewer = new StringRenderer(view);
                viewer.setForegroundColor(color);
                viewer.setCellRenderAlign(AbstractRenderer.Align.CENTER);
                IntegerRenderer sorter = new IntegerRenderer(pocet);
                return new CompoundRenderer(viewer, sorter);
            }
            private CompoundRenderer getCOLUMN_pocetOddenkovych(TeplickaZaznam item){
                StringRenderer viewer = new StringRenderer("Σ "+item.getPocet_Oddenkovych());
                viewer.setForegroundColor(Color.RED);
                viewer.setCellRenderAlign(AbstractRenderer.Align.CENTER);
                IntegerRenderer sorter = new IntegerRenderer(item.getPocet_Oddenkovych());
                return new CompoundRenderer(viewer, sorter);
            }
            private CompoundRenderer getCOLUMN_pocetOstatnich(TeplickaZaznam item){
                StringRenderer viewer = new StringRenderer("Σ "+item.getPocet_Ostatnich());
                viewer.setForegroundColor(Color.BLUE);
                viewer.setCellRenderAlign(AbstractRenderer.Align.CENTER);
                IntegerRenderer sorter = new IntegerRenderer(item.getPocet_Ostatnich());
                return new CompoundRenderer(viewer, sorter);
            }
            private DecimalRenderer getCOLUMN_objem(TeplickaZaznam item){
                DecimalRenderer dr = new DecimalRenderer(item.getObjem_Celkove());
                dr.setCellRenderAlign(AbstractRenderer.Align.CENTER);
                dr.setCellRenderPattern(StringUtil.getDecimalNumberMask(3, DBItemCurrency_V3.ThousandSeparator.Space));
                dr.setCellRenderPrecision(3);
                return dr;
            }
        });
        
        tablePanel_Teplicka.setData(ciselnik.teplicka);
        tablePanel_Teplicka.setDoubleClickOrEnterAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTepl_Edit.doClick();
            }
        });
        //</editor-fold>
        
        tablePanel_Klasicka.autoResizeColumnWidth();
        tablePanel_Teplicka.autoResizeColumnWidth();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNahore = new javax.swing.JPanel();
        field_Vystaveno = new unidataz.components.edit.UniEditDateChooser();
        label_Vystaveno = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        field_Popis = new unidataz.components.edit.UniEditString();
        btn_Print_Sum = new unidataz.unipos.client.components.form.button.ButtonPrint();
        btn_Print_KlasickaTeplickaSum = new unidataz.unipos.client.components.form.button.ButtonPrint();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelKlasickaMetoda = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnKlas_New = new unidataz.unipos.client.components.form.button.ButtonNew();
        btnKlas_Edit = new unidataz.unipos.client.components.form.button.ButtonEdit();
        btnKlas_Delete = new unidataz.unipos.client.components.form.button.ButtonDelete();
        jPanel7 = new javax.swing.JPanel();
        tablePanel_Klasicka = new unidataz.components.table.UniTablePanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldKlasic_Celkem = new unidataz.components.edit.UniEditBigDecimal();
        btnKlas_Tisk = new unidataz.unipos.client.components.form.button.ButtonPrint();
        panelTeplickaMetoda = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        tablePanel_Teplicka = new unidataz.components.table.UniTablePanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fieldTeplic_Celkem = new unidataz.components.edit.UniEditBigDecimal();
        btnTepl_Tisk = new unidataz.unipos.client.components.form.button.ButtonPrint();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnTepl_New = new unidataz.unipos.client.components.form.button.ButtonNew();
        btnTepl_Edit = new unidataz.unipos.client.components.form.button.ButtonEdit();
        btnTepl_Delete = new unidataz.unipos.client.components.form.button.ButtonDelete();

        setLayout(new java.awt.BorderLayout());

        label_Vystaveno.setText("Vystaveno");

        jLabel1.setText("Porost");

        btn_Print_Sum.setText("Σ");
        btn_Print_Sum.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_Print_Sum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Print_SumActionPerformed(evt);
            }
        });

        btn_Print_KlasickaTeplickaSum.setText("K+T+Σ");
        btn_Print_KlasickaTeplickaSum.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn_Print_KlasickaTeplickaSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Print_KlasickaTeplickaSumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNahoreLayout = new javax.swing.GroupLayout(panelNahore);
        panelNahore.setLayout(panelNahoreLayout);
        panelNahoreLayout.setHorizontalGroup(
            panelNahoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNahoreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_Vystaveno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field_Vystaveno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field_Popis, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Print_Sum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Print_KlasickaTeplickaSum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelNahoreLayout.setVerticalGroup(
            panelNahoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNahoreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNahoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(field_Vystaveno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_Vystaveno)
                    .addComponent(jLabel1)
                    .addComponent(field_Popis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Print_Sum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Print_KlasickaTeplickaSum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(panelNahore, java.awt.BorderLayout.PAGE_START);

        panelKlasickaMetoda.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 6, 0));

        btnKlas_New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKlas_NewActionPerformed(evt);
            }
        });
        jPanel2.add(btnKlas_New);

        btnKlas_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKlas_EditActionPerformed(evt);
            }
        });
        jPanel2.add(btnKlas_Edit);

        btnKlas_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKlas_DeleteActionPerformed(evt);
            }
        });
        jPanel2.add(btnKlas_Delete);

        jPanel1.add(jPanel2);

        panelKlasickaMetoda.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel7.setLayout(new java.awt.BorderLayout());
        jPanel7.add(tablePanel_Klasicka, java.awt.BorderLayout.CENTER);

        jLabel2.setText("Celkem:");

        jLabel3.setText("m³");

        fieldKlasic_Celkem.setEditable(false);
        fieldKlasic_Celkem.setPrecision(2);

        btnKlas_Tisk.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnKlas_Tisk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKlas_TiskActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(395, Short.MAX_VALUE)
                .addComponent(btnKlas_Tisk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldKlasic_Celkem, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(fieldKlasic_Celkem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnKlas_Tisk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPanel7.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        panelKlasickaMetoda.add(jPanel7, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Klasická metoda", panelKlasickaMetoda);

        panelTeplickaMetoda.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());
        jPanel8.add(tablePanel_Teplicka, java.awt.BorderLayout.CENTER);

        jLabel4.setText("Celkem:");

        jLabel5.setText("m³");

        fieldTeplic_Celkem.setEditable(false);
        fieldTeplic_Celkem.setPrecision(3);

        btnTepl_Tisk.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnTepl_Tisk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTepl_TiskActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(395, Short.MAX_VALUE)
                .addComponent(btnTepl_Tisk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldTeplic_Celkem, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(fieldTeplic_Celkem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnTepl_Tisk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPanel8.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        panelTeplickaMetoda.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 6, 0));

        btnTepl_New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTepl_NewActionPerformed(evt);
            }
        });
        jPanel4.add(btnTepl_New);

        btnTepl_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTepl_EditActionPerformed(evt);
            }
        });
        jPanel4.add(btnTepl_Edit);

        btnTepl_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTepl_DeleteActionPerformed(evt);
            }
        });
        jPanel4.add(btnTepl_Delete);

        jPanel3.add(jPanel4);

        panelTeplickaMetoda.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Teplická metoda", panelTeplickaMetoda);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnKlas_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlas_NewActionPerformed
        
        KlasickaZaznam posledniZaznam = null;
        if(ciselnik.klasicka.size()>0){
            posledniZaznam = ciselnik.klasicka.get(ciselnik.klasicka.size()-1);
        }
        KlasickaZaznam novyZaznam = new KlasickaZaznam(posledniZaznam);
        novyZaznam.poradove_cislo++;
        
        KlasickaEditDialog dlg = new KlasickaEditDialog(WindowUtils.getWindow(this), ciselnik, novyZaznam, EditMode.New);
        dlg.setVisible(true);
        if(dlg.approved){
            tablePanel_Klasicka.addData(novyZaznam);
            tablePanel_Klasicka.setSelectedItem(novyZaznam);
            tablePanel_Klasicka.setScrollBarToView(novyZaznam);
            tablePanel_Klasicka.autoResizeColumnWidth();
            fieldKlasic_Celkem.setValue(ciselnik.getKlasicka_Objem());
        }
        
    }//GEN-LAST:event_btnKlas_NewActionPerformed

    private void btnKlas_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlas_EditActionPerformed
        
        Object selectedItem = tablePanel_Klasicka.getSelectedItem();
        if(selectedItem instanceof KlasickaZaznam){
            KlasickaZaznam zaznam = (KlasickaZaznam) selectedItem;
            KlasickaEditDialog dlg = new KlasickaEditDialog(WindowUtils.getWindow(this), ciselnik, zaznam, EditMode.Edit);
            dlg.setVisible(true);
            if(dlg.approved){
                tablePanel_Klasicka.updateData(zaznam);
                tablePanel_Klasicka.setSelectedItem(zaznam);
                tablePanel_Klasicka.setScrollBarToView(zaznam);
                tablePanel_Klasicka.autoResizeColumnWidth();
                fieldKlasic_Celkem.setValue(ciselnik.getKlasicka_Objem());
            }
        }
        
    }//GEN-LAST:event_btnKlas_EditActionPerformed

    private void btnKlas_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlas_DeleteActionPerformed

        Object selectedItem = tablePanel_Klasicka.getSelectedItem();
        if(selectedItem instanceof KlasickaZaznam){
            KlasickaZaznam zaznam = (KlasickaZaznam) selectedItem;
            Option o = UniMessageDialog.showYesNoDialog(this,
                    "Opravdu chcete smazat vybraný záznam "+zaznam.poradove_cislo+"?",
                    "Smazat");
            if(o.eq(UniMessageDialog.YES)){
                tablePanel_Klasicka.removeData(selectedItem);
                tablePanel_Klasicka.autoResizeColumnWidth();
                fieldKlasic_Celkem.setValue(ciselnik.getKlasicka_Objem());
            }            
        }
                
    }//GEN-LAST:event_btnKlas_DeleteActionPerformed

    private void btnTepl_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTepl_NewActionPerformed
        
        TeplickaZaznam novyZaznam = new TeplickaZaznam();
        novyZaznam.druh_dreviny_id = null;
        TeplickaEditDialog dlg = new TeplickaEditDialog(WindowUtils.getWindow(this),ciselnik, novyZaznam, EditMode.New);
        dlg.setVisible(true);
        if(dlg.approved){
            tablePanel_Teplicka.addData(novyZaznam);
            tablePanel_Teplicka.setSelectedItem(novyZaznam);
            tablePanel_Teplicka.setScrollBarToView(novyZaznam);
            tablePanel_Teplicka.autoResizeColumnWidth();
            fieldTeplic_Celkem.setValue(ciselnik.getTeplicka_Objem());
        }
        
    }//GEN-LAST:event_btnTepl_NewActionPerformed

    private void btnTepl_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTepl_EditActionPerformed
        
        Object selectedItem = tablePanel_Teplicka.getSelectedItem();
        if(selectedItem instanceof TeplickaZaznam){
            TeplickaZaznam zaznam = (TeplickaZaznam) selectedItem;
            TeplickaEditDialog dlg = new TeplickaEditDialog(WindowUtils.getWindow(this),ciselnik, zaznam, EditMode.Edit);
            dlg.setVisible(true);
            if(dlg.approved){
                tablePanel_Teplicka.updateData(zaznam);
                tablePanel_Teplicka.setSelectedItem(zaznam);
                tablePanel_Teplicka.setScrollBarToView(zaznam);
                tablePanel_Teplicka.autoResizeColumnWidth();
                fieldTeplic_Celkem.setValue(ciselnik.getTeplicka_Objem());
            }
        }        
        
    }//GEN-LAST:event_btnTepl_EditActionPerformed

    private void btnTepl_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTepl_DeleteActionPerformed
                
        Object selectedItem = tablePanel_Teplicka.getSelectedItem();
        if(selectedItem instanceof TeplickaZaznam){
            Option o = UniMessageDialog.showYesNoDialog(this,
                    "Opravdu chcete smazat vybraný záznam?",
                    "Smazat");
            if(o.eq(UniMessageDialog.YES)){
                tablePanel_Teplicka.removeData(selectedItem);
                tablePanel_Teplicka.autoResizeColumnWidth();
                fieldTeplic_Celkem.setValue(ciselnik.getTeplicka_Objem());
            }            
        }
       
    }//GEN-LAST:event_btnTepl_DeleteActionPerformed

    private void btnKlas_TiskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlas_TiskActionPerformed
        
        String html = HTMLReporter.generateHTML_Klasicka(ciselnik);
        String fileNamePrefix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(mainFrame.openedProjectFile!=null){
            fileNamePrefix = mainFrame.openedProjectFile.getName().replace(".cdd", "");
        }
        File f = FileUtil.createTemporary(fileNamePrefix+"-KlasickaMetoda.html", true);
        try {
            FileUtil.writeText2File(f, html, "UTF-8");
        } catch (IOException ex) { }
        ExecuteUtil.openFile(f);
        
    }//GEN-LAST:event_btnKlas_TiskActionPerformed

    private void btnTepl_TiskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTepl_TiskActionPerformed
        
        String html = HTMLReporter.generateHTML_Teplicka(ciselnik);
        String fileNamePrefix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(mainFrame.openedProjectFile!=null){
            fileNamePrefix = mainFrame.openedProjectFile.getName().replace(".cdd", "");
        }
        File f = FileUtil.createTemporary(fileNamePrefix+"-TeplickaMetoda.html", true);
        try {
            FileUtil.writeText2File(f, html, "UTF-8");
        } catch (IOException ex) { }
        ExecuteUtil.openFile(f);
        
        
    }//GEN-LAST:event_btnTepl_TiskActionPerformed

    private void btn_Print_SumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Print_SumActionPerformed
        
        String html = HTMLReporter.generateHTML_Souhrnna(ciselnik);
        String fileNamePrefix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(mainFrame.openedProjectFile!=null){
            fileNamePrefix = mainFrame.openedProjectFile.getName().replace(".cdd", "");
        }
        File f = FileUtil.createTemporary(fileNamePrefix+"-Souhrn.html", true);
        try {
            FileUtil.writeText2File(f, html, "UTF-8");
        } catch (IOException ex) { }
        ExecuteUtil.openFile(f);
        
    }//GEN-LAST:event_btn_Print_SumActionPerformed

    private void btn_Print_KlasickaTeplickaSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Print_KlasickaTeplickaSumActionPerformed
        
        int klasicka = ciselnik.klasicka.size();
        int teplicka = ciselnik.teplicka.size();
        if(klasicka==0 && teplicka==0){
            UniMessageDialog.showMessageDialog(this, 
                "Žádná data pro tisk!", 
                "Upozornění",UniMessageDialog.WARNING);
        } else if(klasicka!=0 && teplicka==0){
            UniMessageDialog.showMessageDialog(this, 
                "Teplická metoda neobsahuje žádé záznamy!", 
                "Upozornění",UniMessageDialog.WARNING);
            jTabbedPane1.setSelectedIndex(0);
            btnKlas_Tisk.doClick(350);
        } else if(klasicka==0 && teplicka!=0){
            UniMessageDialog.showMessageDialog(this, 
                "Klasická metoda neobsahuje žádé záznamy!", 
                "Upozornění",UniMessageDialog.WARNING);
            jTabbedPane1.setSelectedIndex(1);
            btnTepl_Tisk.doClick(350);
        } else if(klasicka!=0 && teplicka!=0){
            String html = HTMLReporter.generateHTML_KlasickaTeplikcaSouhrnna(ciselnik);
            String fileNamePrefix = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if(mainFrame.openedProjectFile!=null){
                fileNamePrefix = mainFrame.openedProjectFile.getName().replace(".cdd", "");
            }
            File f = FileUtil.createTemporary(fileNamePrefix+"-K+T+Σ.html", true);
            try {
                FileUtil.writeText2File(f, html, "UTF-8");
            } catch (IOException ex) { }
            ExecuteUtil.openFile(f);
        }
        
    }//GEN-LAST:event_btn_Print_KlasickaTeplickaSumActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private unidataz.unipos.client.components.form.button.ButtonDelete btnKlas_Delete;
    private unidataz.unipos.client.components.form.button.ButtonEdit btnKlas_Edit;
    private unidataz.unipos.client.components.form.button.ButtonNew btnKlas_New;
    private unidataz.unipos.client.components.form.button.ButtonPrint btnKlas_Tisk;
    private unidataz.unipos.client.components.form.button.ButtonDelete btnTepl_Delete;
    private unidataz.unipos.client.components.form.button.ButtonEdit btnTepl_Edit;
    private unidataz.unipos.client.components.form.button.ButtonNew btnTepl_New;
    private unidataz.unipos.client.components.form.button.ButtonPrint btnTepl_Tisk;
    private unidataz.unipos.client.components.form.button.ButtonPrint btn_Print_KlasickaTeplickaSum;
    private unidataz.unipos.client.components.form.button.ButtonPrint btn_Print_Sum;
    private unidataz.components.edit.UniEditBigDecimal fieldKlasic_Celkem;
    private unidataz.components.edit.UniEditBigDecimal fieldTeplic_Celkem;
    private unidataz.components.edit.UniEditString field_Popis;
    private unidataz.components.edit.UniEditDateChooser field_Vystaveno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel label_Vystaveno;
    private javax.swing.JPanel panelKlasickaMetoda;
    private javax.swing.JPanel panelNahore;
    private javax.swing.JPanel panelTeplickaMetoda;
    private unidataz.components.table.UniTablePanel tablePanel_Klasicka;
    private unidataz.components.table.UniTablePanel tablePanel_Teplicka;
    // End of variables declaration//GEN-END:variables
}
