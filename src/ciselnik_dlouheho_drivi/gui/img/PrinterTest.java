package ciselnik_dlouheho_drivi.gui.img;

import unidataz.util.swing.printUtilities.PrintUtilities;

/**
 *
 * @author Michal
 */
public class PrinterTest {
    
    public static void main(String[] args) {
        PrintUtilities.printHTML(getHTML(), "Stranka");
    }

    private static String getHTML() {
        String s = "";
        s += "<html>";
        s += "Nejaky text";
        s += "</html>";
        return s;
    }
    
}
