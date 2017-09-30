import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.eclipse.swt.internal.win32.OS;

/**
 * ClickThread.java
 * 
 * @author YukiShimizu
 *
 */

public class ClickThread extends Thread{

  private BufferedImage image;
  private String outputPath = "";
  private String fileName = "";
  private String imageFileName = "";
  private String sheetName = "";
  private int numOfCap;
  private int numOfSheets;
  private boolean forceShutDown = false;
  private int space;
  private int startRow;
  private int startColumn;
  Thread t;

  public ClickThread( ) {

  }

  public ClickThread( String outputPath, String fileName, int numOfCap, String sheetName, int numOfSheets, int space, int startRow, int startColumn ) {
    this.outputPath = outputPath;
    this.fileName = fileName;
    this.numOfCap = numOfCap;
    this.sheetName = sheetName;
    this.numOfSheets = numOfSheets;
    this.space = space;
    this.startRow = startRow;
    this.startColumn = startColumn;
  }

  public void run( ) {
    
    CaptureGui cg = new CaptureGui( numOfCap );
    cg.setLocationRelativeTo( null );
    int numOfCapRemain = numOfCap + 1;
    
    for ( int i = 0; i < this.numOfCap; i++ ) {
      numOfCapRemain--;
      cg.setNumOfCapL( numOfCapRemain );
      
      while( true ) {
 
        int event = OS.GetAsyncKeyState( OS.VK_LBUTTON );

        if ( event < 0 ){
          try {
            Thread.sleep(100);
          } catch (InterruptedException e){
          }
          ActiveWinShot aws = new ActiveWinShot( );
          this.image = aws.getImage( );
          
          String selectvalues[] = {"はい", "いいえ", "強制終了"};
          //JOptionPane opane = new JOptionPane( "クリックした画面をキャプチャーしますか？", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION );
          JOptionPane opane = new JOptionPane( "クリックした画面をキャプチャーしますか？", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, selectvalues );
          JDialog dialog = opane.createDialog( "WinCapture" );
          dialog.setAlwaysOnTop( true );
          dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
          dialog.setVisible( true );

          String optionResult = ( String ) opane.getValue( );

          if ( optionResult == null ) {
            optionResult = "強制終了";
          }
          
          if ( optionResult.equals("はい") ){
            GetTime gt = new GetTime();
            this.imageFileName = gt.getTime( );
            String moduleDir = new CurrentPathGetter().getCurrentPath();
            
            @SuppressWarnings("unused")
            OutputImage oi = new OutputImage( this.image, imageFileName, moduleDir );
            //PasteExcel pe = new PasteExcel( this.image, outputPath, fileName, imageFileName );
            System.out.println( "output the image file!" );  
          } else if ( optionResult.equals("いいえ") ) {
            i--;
            numOfCapRemain++;
          } else if ( optionResult.equals("強制終了") ) {
            int ans = JOptionPane.showConfirmDialog(null, "強制終了", "本当にキャプチャー処理を強制終了しますか？", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if ( ans == 0 ) {
              this.forceShutDown = true;
              i = this.numOfCap;
            } else {
              i--;
              numOfCapRemain++;
            }
          }
          try {
            Thread.sleep(100);
          } catch (InterruptedException e){
          }
          break;
        }
      }
      
      if ( numOfCapRemain == 1 || i == numOfCap ) {
        cg.dispose();
      }
    }

    if ( !forceShutDown ) {
      JOptionPane opane = new JOptionPane( "キャプチャーした画面をエクセルに出力しますか？", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION );
      JDialog dialog = opane.createDialog( "WinCapture" );
      dialog.setAlwaysOnTop( true );
      dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
      dialog.setVisible( true );

      Integer optionResult = (Integer) opane.getValue( );

      if ( optionResult == 0 ){
        PasteExcel pe = new PasteExcel( outputPath, fileName, sheetName, numOfSheets, space, startRow, startColumn );
        
        while( !pe.getOutputExcelFlg( ) ) {  
        }
        JOptionPane.showMessageDialog(null, "Excelに出力が完了しました。", "Excel出力完了", JOptionPane.INFORMATION_MESSAGE );
        
      }
    }
  }
}
