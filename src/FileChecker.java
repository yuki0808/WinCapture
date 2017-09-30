import java.io.File;


public class FileChecker {

  private String fullPath = "";
  private boolean fileExistFlg = false;
  
  public FileChecker( ) {
    
  }
  
  public FileChecker( String fullPath ) {

    this.fullPath = fullPath;
    execute( );
  }
  
  public void execute (  ) {
    File file = new File( this.fullPath );
    this.fileExistFlg = file.exists( );    
  }
  
  public boolean getFileExistFlg( ){
    return fileExistFlg;
  }
  
}
