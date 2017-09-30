import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XMLReader.java
 * 
 * @author YukiShimizu
 *
 */

public class XMLReader {

  String xmlFile = "";
  String outputdir = "";
  String excelFileName = "";
  String numcap = "";
  String sheetname = "";
  private DocumentBuilderFactory dbfactory;
  private DocumentBuilder builder;
  private Document doc;
  private String space = "";
  private String startRow = "";
  private String startColumn =  "";

  public XMLReader( String xmlFile ){
    this.xmlFile = xmlFile;
    // ドキュメントビルダーファクトリを生成
    dbfactory = DocumentBuilderFactory.newInstance();
    // ドキュメントビルダーを生成
    try {
      builder = dbfactory.newDocumentBuilder();
      // パースを実行してDocumentオブジェクトを取得
      doc = builder.parse(new File( xmlFile ));
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    readXML();
  }

  public void readXML( ){

    try {
      //      // ドキュメントビルダーファクトリを生成
      //      DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
      //      // ドキュメントビルダーを生成
      //      DocumentBuilder builder = dbfactory.newDocumentBuilder();
      //      // パースを実行してDocumentオブジェクトを取得
      //      Document doc = builder.parse(new File( xmlFile ));
      // ルート要素を取得（タグ名：server）
      NodeList childs = doc.getChildNodes();

      for ( int i= 0; i < childs.getLength(); i++ ) {
        Node n = childs.item( i );

        NodeList paramNode = n.getChildNodes();

        for ( int j = 0; j < paramNode.getLength(); j++ ) {
          Node n2 = (Node)paramNode.item(j);

          if ( "outputdir".equals( n2.getNodeName( ) ) ) {

            try {
              this.outputdir = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.outputdir = "";
            }

          } else if ( "filename".equals( n2.getNodeName( ) ) ) {
            try {
              this.excelFileName = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.excelFileName = "";
            }

          } else if ( "numcap".equals( n2.getNodeName( ) ) ) {
            try {
              this.numcap = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.numcap = "";
            }

          } else if ( "sheetname".equals( n2.getNodeName( ) ) ) {

            try {
              this.sheetname = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.sheetname = "";
            }

          } else if ( "space".equals( n2.getNodeName( ) ) ) {
            try {
              this.space = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.space = "";
            }

          } else if ( "startrow".equals( n2.getNodeName( ) ) ) {
            try {
              this.startRow = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.startRow = "";
            }

          } else if ( "startcolumn".equals( n2.getNodeName( ) ) ) {
            try {
              this.startColumn = n2.getFirstChild().getNodeValue();
            } catch (NullPointerException npe ){
              this.startColumn = "";
            }

          }
        }
      }      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void writeXML( ){

    NodeList childs = doc.getChildNodes();

    for ( int i= 0; i < childs.getLength(); i++ ) {
      Node n = childs.item( i );
      NodeList paramNode = n.getChildNodes();

      for ( int j = 0; j < paramNode.getLength(); j++ ) {
        Node n2 = (Node)paramNode.item(j);
        if ( "outputdir".equals( n2.getNodeName( ) ) ) {

          try {
            //this.outputdir = n2.getFirstChild().getNodeValue();
            n2.setNodeValue(this.outputdir);
          } catch (NullPointerException npe ){
            this.outputdir = "";
          }

        } else if ( "filename".equals( n2.getNodeName( ) ) ) {
          try {
            //this.numcap = n2.getFirstChild().getNodeValue();
            n2.setNodeValue(this.excelFileName);
          } catch (NullPointerException npe ){
            this.excelFileName = "";
          }

        } else if ( "numcap".equals( n2.getNodeName( ) ) ) {
          try {
            //this.numcap = n2.getFirstChild().getNodeValue();
            n2.setNodeValue(this.numcap);
          } catch (NullPointerException npe ){
            this.numcap = "";
          }

        } else if ( "sheetname".equals( n2.getNodeName( ) ) ) {

          try {
            //this.sheetname = n2.getFirstChild().getNodeValue();
            n2.setNodeValue(sheetname);
          } catch (NullPointerException npe ){
            this.sheetname = "";
          }
        } else if ( "space".equals( n2.getNodeName( ) ) ){
          try {
            n2.setNodeValue(space);
          } catch (NullPointerException npe ){
            this.space = "";
          }

        } else if ( "startrow".equals( n2.getNodeName( ) ) ){
          try {
            n2.setNodeValue(startRow);
          } catch (NullPointerException npe ){
            this.startRow = "";
          }

        } else if ( "startcolumn".equals( n2.getNodeName( ) ) ){
          try {
            n2.setNodeValue(startColumn);
          } catch (NullPointerException npe ){
            this.startColumn = "";
          }
        }
      }
    }
  }

  public String getOutputdir() {
    return outputdir;
  }

  public void setOutputdir(String outputdir) {
    this.outputdir = outputdir;
  }

  public String getExcelFilName() {
    return excelFileName;
  }

  public void setExcelFileName(String excelFileName) {
    this.excelFileName = excelFileName;
  }
  
  public String getNumcap() {
    return numcap;
  }

  public void setNumcap(String numcap) {
    this.numcap = numcap;
  }

  public String getSheetname() {
    return sheetname;
  }

  public void setSheetname(String sheetname) {
    this.sheetname = sheetname;
  }

  public String getSpace() {
    return space;
  }

  public void setSpace(String space) {
    this.space = space;
  }
  
  public String getStartRow() {
    return startRow;
  }

  public void setStartRow(String startRow) {
    this.startRow = startRow;
  }
  
  public String getStartColumn() {
    return startColumn;
  }

  public void setStartColumn(String startColumn) {
    this.startColumn = startColumn;
  }
  
}
