import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GetTime.java
 * 
 * @author YukiShimizu
 *
 */

public class GetTime {
  
  private Date date;
  private SimpleDateFormat sdf;
  private static final String TIMEFORMAT_YYYYMMDDHHMMSS = "yyyyMMdd_HHmmSS";
  
  public GetTime(){
    date = new Date();
  }
  
  /**
   * <p>フォーマット「yyyyMMdd_HHmmSS」の時間を返します。</p>
   * @return formatTime
   */
  public String  getTime( ) {
    
    String formatTime = "";
    sdf = new SimpleDateFormat( TIMEFORMAT_YYYYMMDDHHMMSS );
    formatTime = sdf.format( date );
    
    return formatTime; 
  }
  
}
