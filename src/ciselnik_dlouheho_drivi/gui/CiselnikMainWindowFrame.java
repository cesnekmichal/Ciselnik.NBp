package ciselnik_dlouheho_drivi.gui;

import ciselnik_dlouheho_drivi.ciselnik.Ciselnik;
import ciselnik_dlouheho_drivi.gui.img.Resource;
import ciselnik_dlouheho_drivi.gui.laborator.LaboratorDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import unidataz.components.UniMessageDialog;
import unidataz.components.UniMessageDialog.Option;
import unidataz.laf.UniLookAndFeel;
import unidataz.util.UniSystem;
import unidataz.util.file.UniFileChooser;
import unidataz.util.swing.WindowUtils;
import unidataz.util.system.Settings;

/**
 *
 * @author Michal
 */
public class CiselnikMainWindowFrame extends JFrame {

    /** Ciselnik */
    Ciselnik ciselnik = new Ciselnik();
    /** Hash otevreneho ciselniku */
    Integer  openedHash = null;
    File     openedProjectFile = null;
    Integer  emptyHash = null;

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
    
    private String title = "Číselník dlouhého dříví";
    
    /**
     * Creates new form CiselnikMainWindowFrame
     */
    public CiselnikMainWindowFrame() {
        super();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        initComponents();
        
        setTitle(title);
        setIconImage(Resource.getImg(Resource.ImgKey.tree).getImage());
        
        WindowUtils.setAutomaticMinimumSetter(CiselnikMainWindowFrame.this);
        setLocationRelativeTo(null);
        WindowUtils.setAutomaticSizePositionSettings(CiselnikMainWindowFrame.this, "Main.Window2.");
        
//        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    //Pokud jde o neulozeny projekt a nejde o prazdny projekt
                if(   openedHash==null && ciselnik.hashCode()!=emptyHash 
                    //Pokud jde o uložený projekt a jde o upraveny projekt
                   || openedHash!=null && ciselnik.hashCode()!=openedHash){
                    Option o = UniMessageDialog.showYesNoCancelDialog(CiselnikMainWindowFrame.this,
                        "Chcete uložit změny provedené v číselníku?", 
                        "Uložit číselník?",UniMessageDialog.YES);
                    if(o.eq(UniMessageDialog.CANCEL)){
                        return;
                    } else 
                    if(o.eq(UniMessageDialog.YES)){
                        if(!onSave()) {
                            return;
                        }
                    } else
                    if(o.eq(UniMessageDialog.NO)){
                        //nic
                    }
                }
                CiselnikMainWindowFrame.this.setVisible(false);
                CiselnikMainWindowFrame.this.dispose();
            }
        });
        
        btn_Novy.setIcon(Resource.getImg(Resource.ImgKey.novy));
        btn_Otevrit.setIcon(Resource.getImg(Resource.ImgKey.otevrit));
        btn_Ulozit.setIcon(Resource.getImg(Resource.ImgKey.ulozit));
        btn_UlozitJako.setIcon(Resource.getImg(Resource.ImgKey.ulozit_jako));
        
        btn_Laborator.setIcon(Resource.getImg(Resource.ImgKey.laboratory));
        btn_KlasickaKalkulacka.setIcon(Resource.getImg(Resource.ImgKey.calc));
        btn_TeplickaKalkulacka.setIcon(Resource.getImg(Resource.ImgKey.calc));
        
        ciselnik = new Ciselnik();
        emptyHash = ciselnik.hashCode();
        openedHash = null;
        CiselnikMainWindowPanel panel = new CiselnikMainWindowPanel(this,ciselnik);
        panelCenter.add(panel,BorderLayout.CENTER);
        
        panelCenter.setMinimumSize(new Dimension(660,480));
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
        jToolBar1 = new javax.swing.JToolBar();
        btn_Novy = new javax.swing.JButton();
        btn_Otevrit = new javax.swing.JButton();
        btn_Ulozit = new javax.swing.JButton();
        btn_UlozitJako = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btn_Laborator = new javax.swing.JButton();
        btn_KlasickaKalkulacka = new javax.swing.JButton();
        btn_TeplickaKalkulacka = new javax.swing.JButton();
        panelCenter = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        panelNahore.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        panelNahore.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_Novy.setText("Nový");
        btn_Novy.setToolTipText("Nový číselník");
        btn_Novy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Novy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Novy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NovyActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_Novy);

        btn_Otevrit.setText("Otevřít");
        btn_Otevrit.setToolTipText("Otevřít číselník (*.cdd)");
        btn_Otevrit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Otevrit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Otevrit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OtevritActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_Otevrit);

        btn_Ulozit.setText("Uložit");
        btn_Ulozit.setToolTipText("Uložit");
        btn_Ulozit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Ulozit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Ulozit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UlozitActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_Ulozit);

        btn_UlozitJako.setText("Uložit jako");
        btn_UlozitJako.setToolTipText("Uložit jako");
        btn_UlozitJako.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_UlozitJako.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_UlozitJako.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UlozitJakoActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_UlozitJako);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        jToolBar1.add(jPanel1);

        btn_Laborator.setText("Laboratoř");
        btn_Laborator.setFocusable(false);
        btn_Laborator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Laborator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Laborator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LaboratorActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_Laborator);

        btn_KlasickaKalkulacka.setText("Klasická");
        btn_KlasickaKalkulacka.setFocusable(false);
        btn_KlasickaKalkulacka.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_KlasickaKalkulacka.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_KlasickaKalkulacka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KlasickaKalkulackaActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_KlasickaKalkulacka);

        btn_TeplickaKalkulacka.setText("Teplická");
        btn_TeplickaKalkulacka.setFocusable(false);
        btn_TeplickaKalkulacka.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_TeplickaKalkulacka.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_TeplickaKalkulacka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TeplickaKalkulackaActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_TeplickaKalkulacka);

        panelNahore.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelNahore, java.awt.BorderLayout.PAGE_START);

        panelCenter.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_KlasickaKalkulackaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KlasickaKalkulackaActionPerformed
        
        KalkulatorKlasickeMetodyDialog dlg = new KalkulatorKlasickeMetodyDialog(this);
        dlg.setVisible(true);
        
    }//GEN-LAST:event_btn_KlasickaKalkulackaActionPerformed

    private void btn_NovyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NovyActionPerformed
        
        onNew();
        
    }//GEN-LAST:event_btn_NovyActionPerformed

    private void btn_OtevritActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OtevritActionPerformed
        
        onOpen();
        
    }//GEN-LAST:event_btn_OtevritActionPerformed

    private void btn_UlozitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UlozitActionPerformed
        
        onSave();
        
    }//GEN-LAST:event_btn_UlozitActionPerformed

    private void btn_UlozitJakoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UlozitJakoActionPerformed
        
        onSaveAs();
        
    }//GEN-LAST:event_btn_UlozitJakoActionPerformed

    private void btn_LaboratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LaboratorActionPerformed
        
        LaboratorDialog dlg = new LaboratorDialog(this, ciselnik);
        dlg.setVisible(true);
        
    }//GEN-LAST:event_btn_LaboratorActionPerformed

    private void btn_TeplickaKalkulackaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TeplickaKalkulackaActionPerformed
        
        KalkulatorTeplickeMetodyDialog dlg = new KalkulatorTeplickeMetodyDialog(this);
        dlg.setVisible(true);
        
    }//GEN-LAST:event_btn_TeplickaKalkulackaActionPerformed

    private void onNew(){
            //Pokud jde o neulozeny projekt a nejde o prazdny projekt
        if(   openedHash==null && ciselnik.hashCode()!=emptyHash 
            //Pokud jde o uložený projekt a jde o upraveny projekt
           || openedHash!=null && ciselnik.hashCode()!=openedHash){
            Option o = UniMessageDialog.showYesNoCancelDialog(this,
                "Chcete uložit změny provedené v číselníku?", 
                "Uložit číselník?",UniMessageDialog.YES);
            if(o.eq(UniMessageDialog.CANCEL)){
                return;
            } else 
            if(o.eq(UniMessageDialog.YES)){
                if(!onSave()) {
                    return;
                }
            } else
            if(o.eq(UniMessageDialog.NO)){
                //nic
            }
        }
        ciselnik = new Ciselnik();
        emptyHash = ciselnik.hashCode();
        openedHash = null;
        setTitle(title);
        CiselnikMainWindowPanel panel = new CiselnikMainWindowPanel(this,ciselnik);
        panelCenter.removeAll();
        panelCenter.add(panel,BorderLayout.CENTER);
    }
    
    private void onOpen(){
            //Pokud jde o neulozeny projekt a nejde o prazdny projekt
        if(   openedHash==null && ciselnik.hashCode()!=emptyHash 
            //Pokud jde o uložený projekt a jde o upraveny projekt
           || openedHash!=null && ciselnik.hashCode()!=openedHash){
            Option o = UniMessageDialog.showYesNoCancelDialog(this,
                "Chcete uložit změny provedené v číselníku?", 
                "Uložit číselník?",UniMessageDialog.YES);
            if(o.eq(UniMessageDialog.CANCEL)){
                return;
            } else 
            if(o.eq(UniMessageDialog.YES)){
                if(!onSave()) {
                    return;
                }
            } else
            if(o.eq(UniMessageDialog.NO)){
                //nic
            }
        }
        //Zde jiz muzeme vesele provezt otevreni ulozeneho ciselniku
        UniFileChooser ufc = new UniFileChooser();
        ufc.setDialogTitle("Otevřít číselník");
        ufc.setFileSelectionMode(UniFileChooser.SelectionMode.FILES_ONLY);
        ufc.addChoosableFileFilter(new FileNameExtensionFilter("Číselník (*.cdd)", "cdd"));
        ufc.setAcceptAllFileFilterUsed(false);
        ufc.setDefaultCurrentDirectory(new File(".\\ciselniky").getAbsolutePath());
        ufc.setKey4lastUsedDirectory("ciselnik.lastDir");
        if(ufc.showOpenDialog(this)==UniFileChooser.Option.APPROVE){
            File f = ufc.getSelectedFile();
            ciselnik = Ciselnik.load(f);
            openedHash = ciselnik.hashCode();
            openedProjectFile = f;
            setTitle(title + " - '"+f.getName()+"'");
            CiselnikMainWindowPanel panel = new CiselnikMainWindowPanel(this,ciselnik);
            panelCenter.removeAll();
            panelCenter.add(panel,BorderLayout.CENTER);            
        }
    }
    
    private boolean onSave(){
        //Pokud jde o neulozeny projekt
        if(openedHash==null){
            UniFileChooser ufc = new UniFileChooser();
            ufc.setDialogTitle("Uložit číselník");
            ufc.setFileSelectionMode(UniFileChooser.SelectionMode.FILES_ONLY);
            ufc.addChoosableFileFilter(new FileNameExtensionFilter("Číselník (*.cdd)", "cdd"));
            ufc.setAcceptAllFileFilterUsed(false);
            ufc.setDefaultCurrentDirectory(new File(".\\ciselniky").getAbsolutePath());
            String datum = new SimpleDateFormat("yyyy-MM-dd").format(ciselnik.datum);
            String fileName = "Číselník " + datum + ".cdd";
            ufc.setPreSelectedFileName(fileName);
            ufc.setKey4lastUsedDirectory("ciselnik.lastDir");
            if(ufc.showSaveDialog(this)==UniFileChooser.Option.APPROVE){
                File f = ufc.getSelectedFile();
                ciselnik.save(f, "UTF-8");
                openedHash = ciselnik.hashCode();
                openedProjectFile = f;
                setTitle(title + " - '"+f.getName()+"'");
                return true;
            } else {
                return false;
            }
        } else {//Pokud jde o uložený projekt
            ciselnik.save(openedProjectFile, "UTF-8");
            openedHash = ciselnik.hashCode();
            return true;
        }
    }

    private void onSaveAs(){
        UniFileChooser ufc = new UniFileChooser();
        ufc.setDialogTitle("Uložit číselník");
        ufc.setFileSelectionMode(UniFileChooser.SelectionMode.FILES_ONLY);
        ufc.addChoosableFileFilter(new FileNameExtensionFilter("Číselník (*.cdd)", "cdd"));
        ufc.setAcceptAllFileFilterUsed(false);
        if(openedProjectFile==null){
            ufc.setDefaultCurrentDirectory(new File(".\\ciselniky").getAbsolutePath());
            String datum = new SimpleDateFormat("yyyy-MM-dd").format(ciselnik.datum);
            String fileName = "Číselník - " + datum + ".cdd";
            ufc.setPreSelectedFileName(fileName);
            ufc.setKey4lastUsedDirectory("ciselnik.lastDir");            
        } else {
            ufc.setDefaultCurrentDirectory(openedProjectFile.getParentFile().getAbsolutePath());
            ufc.setPreSelectedFileName(openedProjectFile.getName());
        }
        if(ufc.showSaveDialog(this)==UniFileChooser.Option.APPROVE){
            File f = ufc.getSelectedFile();
            ciselnik.save(f, "UTF-8");
            openedHash = ciselnik.hashCode();
            openedProjectFile = f;
            setTitle(title + " - '"+f.getName()+"'");
        }
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        UniSystem.mainApp = "Ciselnik";
        Settings.setImpl(new Settings.SettingsImplXML("conf/Ciselnik.xml"));
        Settings.load();        
        
        /* Set the Nimbus look and feel */
        UniLookAndFeel.setUniLaF();

        CiselnikMainWindowFrame dialog = new CiselnikMainWindowFrame();
        dialog.setVisible(true);
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_KlasickaKalkulacka;
    private javax.swing.JButton btn_Laborator;
    private javax.swing.JButton btn_Novy;
    private javax.swing.JButton btn_Otevrit;
    private javax.swing.JButton btn_TeplickaKalkulacka;
    private javax.swing.JButton btn_Ulozit;
    private javax.swing.JButton btn_UlozitJako;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelNahore;
    // End of variables declaration//GEN-END:variables
}