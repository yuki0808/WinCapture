import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class ActiveWinShot {

  private BufferedImage image;
  private boolean result = true;

  public ActiveWinShot( ) {
    execute( );
  }
  
  public void execute() {
    
    RECT rect = new RECT();
    
    // �őO�ʂ̃E�C���h�E�n���h���𓾂�B
    int hwnd = OS.GetForegroundWindow( );
    System.out.println(" HWND�\���F " + hwnd);
    // �A�N�e�B�u�E�B���h�E�̏��擾
    OS.GetWindowRect(hwnd, rect);

    System.out.println( "Left:" + rect.left );
    System.out.println( "Top:" + rect.top );
    System.out.println( "Right:" + rect.right );
    System.out.println( "Bottom:" +  rect.bottom );

    int top_x = rect.left;  //������W x
    int top_y = rect.top;    //������W y
    int bottom_x = rect.right; //�E�����Wx
    int bottom_y = rect.bottom; //�E�����Wy

    int length_x = bottom_x - top_x;
    int length_y = bottom_y - top_y;

    try {
      // �L���v�`���͈̔�
      Rectangle bounds = new Rectangle( top_x, top_y, length_x, length_y );
      // ��ʃL���v�`��
      Robot robot = new Robot( );
      this.image = robot.createScreenCapture( bounds );
    } catch ( AWTException e ) {
      e.printStackTrace();
    } catch (IllegalArgumentException iae) { 
    }
  }

  public boolean getResult( ) {
    return result;
  }
  
  public BufferedImage getImage( ) {
    return image;
  }

  public void setImage( BufferedImage image ) {
    this.image = image;
  }
}
