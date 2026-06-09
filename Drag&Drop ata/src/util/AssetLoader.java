package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Resolves image paths for both IDE (src/) and compiled (bin/) runs.
 */
public final class AssetLoader {

    private AssetLoader() {
    }

    public static File resolveImageFile(String fileName) {
        String rel = "assets/images/" + fileName;
        String userDir = System.getProperty("user.dir");
        String[] candidates = {
                "src/" + rel,
                "bin/" + rel,
                rel,
        };
        for (String candidate : candidates) {
            File f = new File(userDir, candidate);
            if (f.isFile())
                return f;
        }
        return null;
    }

    /** Same loading path as CharacterSelectPanel (ImageIcon preserves PNG alpha). */
    public static BufferedImage loadCharacterSprite(String assetKey) {
        for (String ext : new String[] { ".png", ".gif" }) {
            File file = resolveImageFile("char_" + assetKey + ext);
            if (file == null)
                continue;
            try {
                Image img = new ImageIcon(file.getAbsolutePath()).getImage();
                BufferedImage buf = toArgb(img);
                if (buf != null)
                    return buf;
            } catch (Exception ignored) {
                // try next extension / classpath
            }
        }
        return loadCharacterFromClasspath(assetKey);
    }

    private static BufferedImage loadCharacterFromClasspath(String assetKey) {
        for (String ext : new String[] { ".png", ".gif" }) {
            String resource = "assets/images/char_" + assetKey + ext;
            try (InputStream in = AssetLoader.class.getClassLoader().getResourceAsStream(resource)) {
                if (in == null)
                    continue;
                BufferedImage img = ImageIO.read(in);
                if (img != null)
                    return toArgb(img);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private static BufferedImage toArgb(Image img) {
        if (img == null)
            return null;
        if (img instanceof BufferedImage) {
            BufferedImage bi = (BufferedImage) img;
            if (bi.getType() == BufferedImage.TYPE_INT_ARGB)
                return bi;
        }
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        if (w <= 0 || h <= 0)
            return null;
        BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buf.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return buf;
    }
}
