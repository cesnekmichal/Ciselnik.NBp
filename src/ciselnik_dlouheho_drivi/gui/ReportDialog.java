package ciselnik_dlouheho_drivi.gui;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Level;
import unidataz.unipos.client.components.window.UniDialog;
import unidataz.util.FileUtil;
import unidataz.util.GLog;
import unidataz.util.Localizer;
import unidataz.util.UniSystem;
import unidataz.util.file.Filters;
import unidataz.util.file.UniFileChooser;
import unidataz.util.file.UniFileChooser.Option;
import unidataz.util.swing.printUtilities.PrintUtilities;
import unidataz.util.uniicon.UniIcons.UniIcon;

/**
 * Dialog pro report
 * @author milicka
 */
public class ReportDialog extends UniDialog {

    public static void createAndShowGUI(Window parent,String html){
        new ReportDialog(parent, html).setVisible(true);
    }    
    
    private String HTML = "";
    
    /** Creates new form AboutDialog
     * @param window
     * @param modal
     * @param server
     */
    private ReportDialog(Window window,String html) {
        super(window);        
        initComponents();

        setIcon(UniIcon.FOR_ACTION__O_SYSTEMU);
        
                              //TRANS _UPS-FORM
        setTitle(Localizer.tr("About system"));
        HTML = html;
        textPanel.setText(HTML);
        
        textPanel.setCaretPosition(0);
        
        UniIcon.FOR_BUTTON__TISK.setToButton(btn_Print);
                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textPanel = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        standardButtonPanel1 = new unidataz.unipos.client.components.form.StandardButtonPanel();
        btn_Print = new javax.swing.JButton();
        btn_SaveToFile = new unidataz.unipos.client.components.form.button.ButtonSave();
        buttonClose1 = new unidataz.unipos.client.components.form.button.ButtonClose();

        setTitle(unidataz.util.Localizer.tr("About")); // NOI18N

        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 500));

        textPanel.setEditable(false);
        textPanel.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(textPanel);

        standardButtonPanel1.setLayout(new java.awt.GridLayout(1, 0, 6, 0));

        btn_Print.setText(Localizer.tr("Print",new Object[] {})); // NOI18N
        btn_Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrintActionPerformed(evt);
            }
        });
        standardButtonPanel1.add(btn_Print);

        btn_SaveToFile.setText(Localizer.tr("Save to file",new Object[] {})); // NOI18N
        btn_SaveToFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveToFileActionPerformed(evt);
            }
        });
        standardButtonPanel1.add(btn_SaveToFile);

        jPanel1.add(standardButtonPanel1);
        jPanel1.add(buttonClose1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btn_PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrintActionPerformed

                                                              //TRANS _UPC-FORM
    PrintUtilities.printHTML(HTML, "UniPOS - "+Localizer.tr("About system"));
    
}//GEN-LAST:event_btn_PrintActionPerformed

    private void btn_SaveToFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveToFileActionPerformed
        
        
        UniFileChooser ufc = new UniFileChooser();
        //TRANS _UPC-FORM
        ufc.setDialogTitle(Localizer.tr("Save to file"));
        ufc.setFileSelectionMode(UniFileChooser.SelectionMode.FILES_ONLY);
        ufc.addChoosableFileFilter(Filters.GetHTML());
        ufc.setAcceptAllFileFilterUsed(false);
        ufc.setDefaultCurrentDirectory(UniSystem.getUserdataDirectory().getPath());
                                        //TRANS _UPC-FORM
        String fileName = "UniPOS - "+ Localizer.tr("About system")+".html";
        ufc.setPreSelectedFileName(fileName);
        ufc.setKey4lastUsedDirectory("client.abaut.system.lastDir");
        if(ufc.showSaveDialog(ReportDialog.this)==Option.APPROVE){
            File f = ufc.getSelectedFile();
            try {
                FileUtil.writeText2File(f, HTML, "utf-8");
            } catch (IOException ex) {
                GLog.elog(Level.ERROR, ex, ex);
            }
        }    
        
    }//GEN-LAST:event_btn_SaveToFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Print;
    private unidataz.unipos.client.components.form.button.ButtonSave btn_SaveToFile;
    private unidataz.unipos.client.components.form.button.ButtonClose buttonClose1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private unidataz.unipos.client.components.form.StandardButtonPanel standardButtonPanel1;
    private javax.swing.JTextPane textPanel;
    // End of variables declaration//GEN-END:variables

}