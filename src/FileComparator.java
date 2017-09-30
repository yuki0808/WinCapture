import java.io.File;
import java.util.Comparator;

/**
 * FileComparator.java
 * 
 * @author YukiShimizu
 *
 */

@SuppressWarnings("unchecked")
public class FileComparator implements Comparator{

  @Override
  public int compare(Object o1, Object o2) {
   
    return (int)( ( File )o1 ).lastModified( ) - (int)( ( File ) o2 ).lastModified( );
  }

}
