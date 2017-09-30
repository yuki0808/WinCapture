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
          
          String selectvalues[] = {"�͂�", "������", "�����I��"};
          //JOptionPane opane = new JOptionPane( "�N���b�N������ʂ��L���v�`���[���܂����H", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION );
          JOptionPane opane = new JOptionPane( "�N���b�N������ʂ��L���v�`���[���܂����H", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, selectvalues );
          JDialog dialog = opane.createDialog( "WinCapture" );
          dialog.setAlwaysOnTop( true );
          dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
          dialog.setVisible( true );

          String optionResult = ( String ) opane.getValue( );

          if ( optionResult == null ) {
            optionResult = "�����I��";
          }
          
          if ( optionResult.equals("�͂�") ){
            GetTime gt = new GetTime();
            this.imageFileName = gt.getTime( );
            String moduleDir = new CurrentPathGetter().getCurrentPath();
            
            @SuppressWarnings("unused")
            OutputImage oi = new OutputImage( this.image, imageFileName, moduleDir );
            //PasteExcel pe = new PasteExcel( this.image, outputPath, fileName, imageFileName );
            System.out.println( "output the image file!" );  
          } else if ( optionResult.equals("������") ) {
            i--;
            numOfCapRemain++;
          } else if ( optionResult.equals("�����I��") ) {
            int ans = JOptionPane.showConfirmDialog(null, "�����I��", "�{���ɃL���v�`���[�����������I�����܂����H", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
      JOptionPane opane = new JOptionPane( "�L���v�`���[������ʂ��G�N�Z���ɏo�͂��܂����H", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION );
      JDialog dialog = opane.createDialog( "WinCapture" );
      dialog.setAlwaysOnTop( true );
      dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
      dialog.setVisible( true );

      Integer optionResult = (Integer) opane.getValue( );

      if ( optionResult == 0 ){
        PasteExcel pe = new PasteExcel( outputPath, fileName, sheetName, numOfSheets, space, startRow, startColumn );
        
        while( !pe.getOutputExcelFlg( ) ) {  
        }
        JOptionPane.showMessageDialog(null, "Excel�ɏo�͂��������܂����B", "Excel�o�͊���", JOptionPane.INFORMATION_MESSAGE );
        
      }
    }
  }
}
