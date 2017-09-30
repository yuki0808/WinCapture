import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * WinCapGui.java
 * 
 * @author YukiShimizu
 *
 */
@SuppressWarnings("serial")
public class WinCapGui extends JFrame implements MouseListener, WindowListener, ActionListener {

  JPanel northP = new JPanel( new GridLayout( 4, 1 ) );
  private JPanel northPaneRow1_1 = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
  private JPanel northPaneRow1_2 = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
  private JPanel northPaneRow1_3 = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
  private JPanel northPaneRow1_4 = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
  JPanel centerP = new JPanel();
  JPanel southP = new JPanel( new FlowLayout( FlowLayout.CENTER ) );

  JLabel outputL = new JLabel( "�o�͐�F�@�@" );
  JTextField outputTF = new JTextField( 20 );

  JLabel fileNameL = new JLabel( "�t�@�C�����F" );
  JTextField fileNameTF = new JTextField( 20 );
  JLabel xlsL = new JLabel( ".xls" );

  JLabel sheetL = new JLabel( "�V�[�g���F�@" );
  JTextField sheetNameTF = new JTextField( "Sheet1", 20 );

  JLabel numCapL = new JLabel( "�L���v�`���[�񐔁F" );
  JTextField numCapTF = new JTextField( "1", 2 );

  JLabel spaceL = new JLabel( "�@�@�@   �摜�Ԋu(�s)�F" );
  JTextField spaceTF = new JTextField( "1", 1 );


  JButton captureB = new JButton( "�L���v�`���[" );

  JButton selectFileB = new JButton( "�Q��" );

  JButton outputExcelB = new JButton( "�G�N�Z���o��" );

  private String imagePath = "";
  private String systemTime = "" ;
  private String currentPath = "";
  private String excelFileName = "";
  private String sheetName = "";
  private int numOfCap = 1;
  private int numOfSheets;
  private XMLReader xmlr;
  private int space;
  private int startRow = 1;
  private int startColumn = 0;

  /**
   * Default Constructor
   */
  public WinCapGui( ) {

    GetTime gt = new GetTime( );
    this.systemTime = gt.getTime( );
    CurrentPathGetter cpg = new CurrentPathGetter( );
    this.imagePath = cpg.getCurrentPath();
    numCapTF.setHorizontalAlignment( JTextField.RIGHT );
    spaceTF.setHorizontalAlignment( JTextField.RIGHT );
    xmlr = new XMLReader( cpg.getCurrentPath() + "\\wincap.xml" );

    if ( xmlr.getOutputdir( ).equals("")) {
      this.currentPath = cpg.getCurrentPath( );    
    } else {
      this.currentPath = xmlr.getOutputdir( );
    }

    if ( !xmlr.getExcelFilName( ).equals("") ) {
      this.excelFileName = xmlr.getExcelFilName( );
    } else {
      this.excelFileName = this.systemTime;
    }
    
    if ( !xmlr.getNumcap( ).equals("") ) {
      numCapTF.setText( xmlr.getNumcap( ) );
    }

    if ( !xmlr.getSheetname( ).equals("") ) {
      sheetNameTF.setText( xmlr.getSheetname( ) );
    }

    if ( !xmlr.getSpace( ).equals("") ) {
      spaceTF.setText( xmlr.getSpace( ) );
    }

    if ( !xmlr.getStartRow().equals( "" ) ) {
      this.startRow = Integer.parseInt(xmlr.getStartRow( ) );
    }
    
    if ( !xmlr.getStartColumn().equals( "" ) ) {
      this.startColumn = Integer.parseInt( xmlr.getStartColumn( ) );
    }
    
    
    addWindowListener( this );
    displayGui();
  }

  /**
   * displayGui method
   */
  public void displayGui( ) {

    setTitle( "WinCapture" );
    captureB.addMouseListener( this );

    selectFileB.addActionListener(this);
    outputTF.setText( this.currentPath );
    outputTF.setToolTipText( this.currentPath );
    northPaneRow1_1.add( outputL );
    northPaneRow1_1.add( outputTF );
    northPaneRow1_1.add( selectFileB );

    fileNameTF.setText( this.excelFileName );
    fileNameTF.setToolTipText( this.excelFileName );
    northPaneRow1_2.add( fileNameL );
    northPaneRow1_2.add( fileNameTF );
    northPaneRow1_2.add( xlsL );

    northPaneRow1_3.add( sheetL );
    northPaneRow1_3.add( sheetNameTF );

    northPaneRow1_4.add( numCapL );
    northPaneRow1_4.add( numCapTF );
    northPaneRow1_4.add( spaceL );
    northPaneRow1_4.add( spaceTF );

    northP.add( northPaneRow1_1 );
    northP.add( northPaneRow1_2 );
    northP.add( northPaneRow1_3 );
    northP.add( northPaneRow1_4 );

    outputExcelB.addActionListener(this);
    southP.add( captureB );
    southP.add( outputExcelB );

    add( northP, BorderLayout.NORTH );
    add( southP, BorderLayout.SOUTH );

    pack( );
    setResizable( false );
    setLocationRelativeTo( null );
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
  }

  @Override
  public void mouseExited(MouseEvent arg0) {
  }

  @Override
  public void mousePressed(MouseEvent arg0) {
  }

  @Override
  public void mouseReleased( MouseEvent arg0 ) {

    this.currentPath = outputTF.getText( );
    this.excelFileName = fileNameTF.getText( );
    this.sheetName = sheetNameTF.getText( );

    String excelFile = currentPath + "\\" + excelFileName + ".xls";
    FileChecker dirFC = new FileChecker( currentPath );
    FileChecker excelFC = new FileChecker( excelFile );

    SheetChecker sc = new SheetChecker( excelFile, this.sheetName );
    //    if ( sc.getCheckFlg( ) ){
    //
    //      try {
    //        this.numOfCap = Integer.parseInt( numCapTF.getText( ) );
    //        this.numOfSheets = sc.getNumOfSheets( );
    //        ClickThread ct = new ClickThread( currentPath, excelFileName, numOfCap, sheetName, numOfSheets );
    //        ct.start();
    //      } catch ( NumberFormatException nfe ) {
    //        JOptionPane.showMessageDialog(null, "���l����͂��ĉ������B\n" + nfe.toString( ), "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE );
    //      }
    //    } else {
    //      JOptionPane.showMessageDialog(null, "���̃V�[�g���͂��łɑ��݂��܂��B" + this.sheetName, "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE );  
    //    }

    if ( spaceTF.getText( ).matches("[0-9]") ) {

      if ( numCapTF.getText().matches( "[0-9][0-9]|[0-9]" ) ) {

        if ( dirFC.getFileExistFlg( ) ) {

         // if ( !excelFC.getFileExistFlg( ) ) {
            try {
              this.numOfCap = Integer.parseInt( numCapTF.getText( ) );
              this.numOfSheets = sc.getNumOfSheets( );
              this.space = Integer.parseInt( spaceTF.getText( ) );

              ClickThread ct = new ClickThread( currentPath, excelFileName, numOfCap, sheetName, numOfSheets, space, startRow, startColumn );
              ct.start();
            } catch ( NumberFormatException nfe ) {
              JOptionPane.showMessageDialog(null, "���l����͂��ĉ������B\n" + nfe.toString( ), "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE );
            }
//          } else {
//            JOptionPane.showMessageDialog(null, "���̃t�@�C�����͂��łɑ��݂��܂��B" + this.excelFileName + ".xls", "ERROR", JOptionPane.ERROR_MESSAGE );
//          }
        } else {
          JOptionPane.showMessageDialog(null, "�o�͐�̃p�X���s���ł��B���݂��܂���B\n" +  this.currentPath, "ERROR", JOptionPane.ERROR_MESSAGE );
        }

      } else {
        JOptionPane.showMessageDialog(null, "�L���v�`���[�񐔂ɂ͐��l�����Ă��������B�ő�99��܂ŉ\�ł��B", "ERROR", JOptionPane.ERROR_MESSAGE );
      }

    } else {
      JOptionPane.showMessageDialog(null, "�摜�Ԋu(��)�ɂ͐��l�����Ă��������B�ő�l��9�ł��B", "ERROR", JOptionPane.ERROR_MESSAGE );
    }
  }

  @Override
  public void windowActivated(WindowEvent arg0) {
  }

  @Override
  public void windowClosed(WindowEvent arg0) {   
  }

  @Override
  public void windowClosing(WindowEvent arg0) {



    //    System.out.println("closing");
    //    int ans = JOptionPane.showConfirmDialog(null, 
    //        "�ݒ��ۑ����܂����H",
    //        "�ۑ��m�F",
    //        JOptionPane.YES_NO_OPTION,
    //        JOptionPane.INFORMATION_MESSAGE);
    //    if ( ans == 0 ) {
    //      
    //    }

  }

  @Override
  public void windowDeactivated(WindowEvent arg0) {
  }

  @Override
  public void windowDeiconified(WindowEvent arg0) {
  }

  @Override
  public void windowIconified(WindowEvent arg0) {
  }

  @Override
  public void windowOpened(WindowEvent arg0) {
  }

  @Override
  public void actionPerformed(ActionEvent ae) {

    String cmd = ae.getActionCommand( );

    if ( cmd.equals( "�Q��" ) ) {
      File dir = new File( outputTF.getText( ) );

      JFileChooser filechooser = new JFileChooser( dir );
      filechooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

      //     if ( dir.exists( ) ) {
      int selected = filechooser.showOpenDialog(this);

      if (selected == JFileChooser.APPROVE_OPTION){
        File file = filechooser.getSelectedFile();
        outputTF.setText( file.getAbsolutePath( ) );
      }     
      //      } else {
      //        JOptionPane.showMessageDialog(null, "�o�͐�̃p�X���s���ł��B���݂��܂���B\n" +  this.currentPath, "ERROR", JOptionPane.ERROR_MESSAGE );
      //      }


    } else if ( cmd.equals( "�G�N�Z���o��" ) ){

      this.currentPath = outputTF.getText( );
      this.excelFileName = fileNameTF.getText( );
      this.sheetName = sheetNameTF.getText( );
      this.space = Integer.parseInt( spaceTF.getText( ) );

      //ClickThread ct = new ClickThread( currentPath, excelFileName, numOfCap, sheetName, numOfSheets, space );

      File file = new File( imagePath + "\\images" );    
      File[] files = file.listFiles( );

      if ( files.length > 0 ) {
        JOptionPane opane = new JOptionPane( "images�t�H���_�ɂ���t�@�C�����G�N�Z���ɏo�͂��܂����H", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION );
        JDialog dialog = opane.createDialog( "WinCapture" );

        dialog.setAlwaysOnTop( true );
        dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        dialog.setVisible( true );

        Integer optionResult = (Integer) opane.getValue( );

        if ( optionResult == 0 ){
          PasteExcel pe = new PasteExcel( currentPath, excelFileName, sheetName, numOfSheets, space, startRow, startColumn );

          while( !pe.getOutputExcelFlg( ) ) {  
          }
          JOptionPane.showMessageDialog(null, "Excel�ɏo�͂��������܂����B", "Excel�o�͊���", JOptionPane.INFORMATION_MESSAGE ); 
        }
      } else {
        JOptionPane.showMessageDialog(null, 
            "images�t�H���_�ɑΏۃt�@�C�������݂��܂���B", "No File", JOptionPane.WARNING_MESSAGE );
      }
    }
  }
}
