import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;

/**
 * PasteExce.java
 *
 * @author YukiShimizu
 *
 */

public class PasteExcel {

  //private BufferedImage image;
  String outputPath = "";
  String fileName = "";
  File imageFile = null;
  FileInputStream jpeg = null;
  String excelFileName = "sample.xls";
  File excelFile = null;
  FileOutputStream out = null;
  FileOutputStream fos = null;
  String jpgName = "";
  private int row = 1;
  private int column = 0;
  private int numOfFile = 0;
  File[] files = null;
  File file = null;
  private String sheetName = "";
  private int numOfSheets;
  private boolean outputExcelFlg = false;
  private int space;
  private String modulePath = "";
  HSSFWorkbook workbook = null;

  public PasteExcel( ) {
  }

  @SuppressWarnings("unchecked")
  public PasteExcel( String outputPath, String fileName, String sheetName, int numOfSheets, int space, int startRow, int startColumn ) {
    //this.image = image;
    this.outputPath = outputPath;
    this.fileName = fileName;
    this.excelFileName = fileName + ".xls";
    this.sheetName = sheetName;
    this.numOfSheets = numOfSheets;
    this.space = space;
    //this.excelFileName = fileName;
    this.row = startRow;
    this.column = startColumn;
    this.modulePath = new CurrentPathGetter().getCurrentPath( );
    this.file = new File( modulePath + "\\images" );
    this.files = file.listFiles( getFileExtensionFilter( ".jpg" ) );
    Arrays.sort( this.files, new FileComparator( ) );
    numOfFile = files.length;

    execute( );
  }

  public static FilenameFilter getFileExtensionFilter(String extension) {
    final String _extension = extension;
    return new FilenameFilter() {
      public boolean accept(File file, String name) {
        boolean ret = name.endsWith(_extension);
        return ret;
      }
    };
  }

  public void execute( ) {

    try {
      //      imageFile = new File( outputPath, fileName );
      //      ImageIO.write( image, "jpg", imageFile );
      //
      //      jpeg = new FileInputStream( imageFile );
      FileInputStream in = null;
      //HSSFWorkbook workbook = null;
      HSSFSheet sheet = null;
      HSSFSheet createdSheet = null;
      boolean existFlg = false;
      String filePath = outputPath + "\\" + excelFileName;
      File tempFile = new File( filePath );

      if ( tempFile.exists( ) ) {
        existFlg = true;
        in = new FileInputStream( filePath );
        try {
          workbook = (HSSFWorkbook) WorkbookFactory.create( in );
          sheet = workbook.createSheet(sheetName);
          workbook.setSheetName( numOfSheets, sheetName );
          //System.out.println("IN!!!!!!!!" + numOfSheets);
          createdSheet = workbook.getSheetAt( numOfSheets );
        } catch (InvalidFormatException e) {
          e.printStackTrace();
        }

      } else {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        workbook.setSheetName( numOfSheets, sheetName );
        createdSheet = workbook.getSheetAt( numOfSheets );

      }

      for ( int i = 0; i < numOfFile; i++ ) {
        if ( i > 0 ){
          BufferedImage readImage =  ImageIO.read( files[ i - 1 ] );
          double height = readImage.getHeight();
          System.out.println("height: " + height);
          double tmpDouble = height / 17 ;
          System.out.println(tmpDouble);
          tmpDouble = Math.ceil(tmpDouble);
          int addRowNum = (int) tmpDouble;

          //int addRowNum = height / 17;
          this.row = row + addRowNum + space;
        }

        jpeg = new FileInputStream( files[ i ] );

        byte[] bytes = IOUtils.toByteArray(jpeg);
        int pictureIndex = workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);

        jpeg.close( );

        HSSFCreationHelper helper = ( HSSFCreationHelper ) workbook.getCreationHelper( );
        HSSFPatriarch patriarch = createdSheet.createDrawingPatriarch();
        HSSFClientAnchor clientAnchor = helper.createClientAnchor();

        // 開始位置（横）
        clientAnchor.setCol1( column );
        clientAnchor.setRow1( row );
        //イメージ（JPEG）を描画する
        HSSFPicture picture = patriarch.createPicture( clientAnchor, pictureIndex );
        picture.resize( );
      }

      //ファイルを保存する
      if ( !existFlg ) {
        excelFile = new File( outputPath + "\\" + excelFileName );
        out = new FileOutputStream( excelFile, true );
        workbook.write( out );
      } else {
        fos = new FileOutputStream( filePath );
        workbook.write(fos);
      }

      this.outputExcelFlg = true;

    } catch ( IOException e ) {
      e.printStackTrace( );
    } finally {
      if ( jpeg != null ){
        try {
          jpeg.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      for ( int i = 0; i < numOfFile; i++ ) {
        files[i].delete();
      }

      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException ioex) {
      }

      try {
        if (fos != null) {
          fos.close();
        }
      } catch (IOException ioex) {
      }

    }
  }

  public boolean getOutputExcelFlg( ) {
    return this.outputExcelFlg;
  }

}
