/**
 * WinCap.java
 * 
 * @author YukiShimizu
 *
 */

public class WinCap {

  @SuppressWarnings("static-access")
  public static void main( String []args  ){
    WinCapGui gui = new WinCapGui();
    gui.setDefaultCloseOperation(gui.EXIT_ON_CLOSE);
    gui.setVisible(true);
  }
}
