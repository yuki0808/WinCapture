import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * OutputImage.java
 * 
 * @author YukiShimizu
 *
 */

public class OutputImage {

  private BufferedImage image;
  File imageFile = null;
  private String fileName = "";
  private String outputPath = "";
  final String EXTENSION_JPG = ".jpg";

  /**
   * Default Constructor
   */
  public OutputImage( ) {
  }
  
  public OutputImage( BufferedImage image, String fileName, String outputPath ) {
    this.image = image;
    this.fileName = fileName + EXTENSION_JPG;
    this.outputPath = outputPath + "\\images";
    
    File dir = new File( this.outputPath );
    if ( !dir.exists( ) ) {
      dir.mkdir( );
    }
    output( );  
  }
  
  /**
   * <p>イメージファイル出力メソッド</p>
   */
  public void output( ) {
    imageFile = new File( outputPath, fileName );
    try {
      ImageIO.write( image, "jpg", imageFile );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
