import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class allows image files to be loaded and stored.  The data within such
 * images is stored within an 2D array of Color object references.  The color
 * at each position with these images is both accessible and mutatable through
 * the methods defined in this class. 
 * 
 * @author dahl
 */
public class Image {
  private Color[][] colors;  // colors stored at each position within this image
                             // first index y-position (top:0 to bottom:height-1), 
                             // second index x-position (left:0 to right:width-1)

  /**8
   * Loads an image from the specified file, and stores its contents as Color objects.
   * @param imageFile is the image file to be loaded
   * @throws IOException is thrown when there is trouble reading data from the specified file
   */
  public Image(File imageFile) throws IOException {
    FileInputStream stream = null;
    try {
      stream = new FileInputStream(imageFile);
      BufferedImage image = ImageIO.read(stream);
      boolean hasAlpha = image.getColorModel().hasAlpha();
      colors = new Color[image.getHeight()][];
      for (int y = 0; y < colors.length; y++) {
        colors[y] = new Color[image.getWidth()];
        for (int x = 0; x < colors[y].length; x++) {
          colors[y][x] = new Color(image.getRGB(x, y));
          if(!hasAlpha)
            colors[y][x].setAlpha(255);
        }
      }
    } finally {
      if (stream != null)
        stream.close();
    }
  }

  /**
   * Changes the color at a specific position within this image.
   * @param x the horizontal position of the color to change - left:0 to right:width-1
   * @param y the vertical position of the color to change - top:0 to bottom:height-1
   * @param newColor is the new color object to store at this position within the image
   */
  public void setColor(int x, int y, Color newColor) {
    colors[y][x] = newColor;
  }

  /**
   * Retrieves the color stored at a specific position within this image.
   * @param x the horizontal position of the color to access - left:0 to right:width-1
   * @param y the vertical position of the color to access - top:0 to bottom:height-1
   * @return a reference to the color at this position
   */
  public Color getColor(int x, int y) {
    return colors[y][x];
  }

  /**
   * Retrieves the number of pixels/Colors wide that this image is.
   * @return image width (in pixels)
   */
  public int getWidth() {
    return colors[0].length;
  }

  /**
   * Retrieves the number of pixels/Colors tall that this image is.
   * @return image height (in pixels)
   */
  public int getHeight() {
    return colors.length;
  }

  /**
   * Saves the colors stored within this image into the specified png file.
   * @param pngFile is the file that this images contents should be saved to
   * @throws IOException is thrown when there is trouble writing data from the specified file
   */
  public void saveAs(File pngFile) throws IOException {
    BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < getHeight(); y++)
      for (int x = 0; x < getWidth(); x++)
        image.setRGB(x, y, colors[y][x].getARGB());
    FileOutputStream stream = null;
    try {
      stream = new FileOutputStream(pngFile);
      ImageIO.write(image, "png", stream);
    } finally {
      if (stream != null)
        stream.close();
    }
  }
  
}