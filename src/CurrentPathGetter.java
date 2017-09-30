import java.io.File;

public class CurrentPathGetter {
  
  String currentPath = "";
  
  public CurrentPathGetter(){
    autoSetCurrentPath();
  }
  
  public void autoSetCurrentPath(){
    this.currentPath = new File("").getAbsolutePath();
  }
  
  public String getCurrentPath(){
    return this.currentPath;
  }
  
}
