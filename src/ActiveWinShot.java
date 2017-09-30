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
    
    // 最前面のウインドウハンドルを得る。
    int hwnd = OS.GetForegroundWindow( );
    System.out.println(" HWND表示： " + hwnd);
    // アクティブウィンドウの情報取得
    OS.GetWindowRect(hwnd, rect);

    System.out.println( "Left:" + rect.left );
    System.out.println( "Top:" + rect.top );
    System.out.println( "Right:" + rect.right );
    System.out.println( "Bottom:" +  rect.bottom );

    int top_x = rect.left;  //左上座標 x
    int top_y = rect.top;    //左上座標 y
    int bottom_x = rect.right; //右下座標x
    int bottom_y = rect.bottom; //右下座標y

    int length_x = bottom_x - top_x;
    int length_y = bottom_y - top_y;

    try {
      // キャプチャの範囲
      Rectangle bounds = new Rectangle( top_x, top_y, length_x, length_y );
      // 画面キャプチャ
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
