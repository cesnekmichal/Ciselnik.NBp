package ciselnik_dlouheho_drivi.gui.img;

import javax.swing.ImageIcon;
import static unidataz.util.ResourceUtil.*;

/**
 * Image Resource Bundle
 * @author Michal
 */
public class Resource {

    public static enum ImgKey {
        calc       ("calc.png"),
        laboratory ("laboratory.png"),
        novy       ("novy.png"),
        otevrit    ("otevrit.png"),
        ulozit     ("ulozit.png"),
        ulozit_jako("ulozit_jako.png"),
        tree       ("tree.png");
        public final String relPath;
        private ImgKey(String relPath) {
            this.relPath = relPath;
        }
    }

    public static ImageIcon getImg(ImgKey key) {
        return getImageIcon(key.relPath, Resource.class);
    }

    public static ImageIcon getImg(ImgKey key,int size) {
        return getScaledImageIcon(key.relPath, Resource.class, size);
    }

}
