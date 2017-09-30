import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * SheetChecker.java
 * 
 * @author YukiShimizu
 *
 */

public class SheetChecker {

  private String excelFile = "";
  private String sheetName = "";
  private boolean checkFlg = true;
  private int numOfSheets;

  public SheetChecker( ) {

  }

  public SheetChecker( String excelFile, String sheetName ) {
    this.excelFile = excelFile;
    this.sheetName = sheetName;

    execute( );    
  }

  public void execute( ) {
    InputStream in = null;
    Workbook wb = null;

    try {
      in = new FileInputStream( this.excelFile );
      wb = WorkbookFactory.create( in );

      this.numOfSheets = wb.getNumberOfSheets();    

      Sheet sheet1 = wb.getSheet( sheetName );

      if ( sheet1 != null ){
        this.checkFlg = false;
      } 

    } catch (FileNotFoundException e) {
      this.checkFlg = true;
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }

  public boolean getCheckFlg( ) {
    return checkFlg;
  }

  public int getNumOfSheets( ) {
    return this.numOfSheets;
  }

}
