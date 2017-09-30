import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * CaptureGui.java
 * 
 * @author YukiShimizu
 *
 */

@SuppressWarnings("serial")
public class CaptureGui extends JFrame {

  //private int time = 0;
  JLabel howToCapL;
  JLabel numOfCapL;
  Timer timer;
//  private BufferedImage image;
//  private String outputPath = "";
//  private String fileName = "";
  
  private int numOfCap = 0;
  
  public CaptureGui( ) {
  }
  
  public CaptureGui( int numOfCap ){
    this.numOfCap = numOfCap;
    howToCapL = new JLabel( "キャプチャー残り回数" );
//    this.outputPath = outputPath;
//    this.fileName = fileName;
    showGui();

  }
  
  public void showGui(){
    //startTime();
    //setTitle( "キャプチャー回数" );
    numOfCapL = new JLabel( String.valueOf( this.numOfCap ) );
    numOfCapL.setFont( new Font("Arial", Font.BOLD, 28) );
    JPanel northP = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    JPanel panel = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    northP.add( howToCapL );
    panel.add( numOfCapL );
    add( northP, BorderLayout.NORTH );
    add( panel, BorderLayout.CENTER );
    
    pack( );
    setVisible( true );
  }

  public void  setNumOfCapL( int numOfCap ){
    this.numOfCapL.setText( String.valueOf( numOfCap ) );
  }
  
//  public void startTime(){
//    timer = new Timer( 1000, this );
//    timer.start();
//  }

//  public void actionPerformed(ActionEvent ae) {
//    timeL.setText( String.valueOf( this.time ) );
//    
//    if ( this.time == -1 ) {
//      timer.stop();
//      dispose();
//    } else {
//      this.time--;
//    }
//    
//  }
  
}
