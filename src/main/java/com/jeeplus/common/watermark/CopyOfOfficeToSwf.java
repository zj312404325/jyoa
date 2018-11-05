package com.jeeplus.common.watermark;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.jeeplus.common.config.Global;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfCopy.PageStamp;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
  
/**  
 * office转换
 */  
public class CopyOfOfficeToSwf {   
  
    /**  
     * @param args  
     */  
    public static void main(String[] args) {   
    	try {
			officeToPdf("d:/gtzy/1.txt","d:/gtzy/8.pdf");
    		//officeToHtml("d:/gtzy/5.pptx","d:/gtzy/5.html");
			addFooterAndWater("d:/gtzy/7.pdf", "d:/gtzy/777.pdf", "WordToPdf水印严禁复制", "WordToPdf页眉", "WordToPdf页脚");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }   
    
 // 将word格式的文件转换为pdf格式
    public static void officeToPdf(String srcPath, String desPath) throws IOException {
        // 源文件目录
        File inputFile = new File(srcPath);
        if (!inputFile.exists()) {
            System.out.println("源文件不存在！");
            return;
        }
        // 输出文件目录
        File outputFile = new File(desPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }
        // 调用openoffice服务线程
        OpenOfficeConnection connection=null;
        Process p =null;
        String command = "";
        if (!isWinOs()) {
        	//172.19.10.121
     	  //command = Global.LINUX+"soffice.exe -headless -accept=\"socket,host=172.19.3.225,port=8100;urp;\"";
  		  command = Global.LINUX+"soffice --headless --accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
  		//p = Runtime.getRuntime().exec(command);
        // 连接openoffice服务
        //connection = new SocketOpenOfficeConnection("172.19.10.121", 8100);
  		connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
  		//connection = new SocketOpenOfficeConnection(8100);
        connection.connect();
  	  } else{
  	  	  command = Global.WIN+"soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
  	  	p = Runtime.getRuntime().exec(command);
        // 连接openoffice服务
        connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();
  	  }

        // 转换word到pdf
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        // 关闭连接
        connection.disconnect();

        // 关闭进程
        p.destroy();
        System.out.println("转换完成！");
    }
    
    /**
     * word转html
     * @param srcPath
     * @param desPath
     * @throws IOException 
     */
    public static void officeToHtml(String srcPath, String desPath) throws IOException{
    	   File inputFile = new File(srcPath); 
    	   File outputFile = new File(desPath); 
    	   
    	   // 调用openoffice服务线程
           String command = "";
           if (!isWinOs()) {
        	  //command = Global.LINUX+"soffice.exe -headless -accept=\"socket,host=172.19.3.225,port=8100;urp;\"";
     		  command = Global.LINUX+"soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
     	  } else{
     	  	  command = Global.WIN+"soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
     	  }
           Process p = Runtime.getRuntime().exec(command);
    	   
           // 连接openoffice服务
           OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
    	   try{
    	   connection.connect();
    	   }catch(Exception e){
    	    e.printStackTrace();
    	   }
    	   DocumentConverter converter = new OpenOfficeDocumentConverter(connection); 
    	   converter.convert(inputFile, outputFile); 
    	   connection.disconnect(); 

           // 关闭进程
           p.destroy();
           System.out.println("转换完成！");
    }
    
    /**
     * 判断是否是windows系统
     * @return
     */
    public static boolean isWinOs(){
    	String os=System.getProperty ("os.name");
    	if(os.toLowerCase().startsWith("win")){  
    		  return true;
    	}else{
    		return false;
    	}
    }
    
    /** 
     *  添加水印、页眉、页脚 
     * @param fileName 源文件路径 
     * @param savepath 目标文件路径 
     * @param waterMarkName 文字水印 
     * @param pageHeade 页眉 
     * @param foot 页脚 
     * @return 
     */  
    public static int addFooterAndWater(String fileName, String savepath,  
            String waterMarkName, String pageHeade, String foot)  
    {  
        // 文档总页数  
        int num = 0;  
  
        Document document = new Document();  
        try  
        {  
            PdfReader reader = new PdfReader(fileName);   
            BaseFont base = BaseFont.createFont("C:/Windows/Fonts/simfang.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            num = reader.getNumberOfPages();  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));  
            document.open();  
            for (int i = 0; i < num;)  
            {  
                PdfImportedPage page = copy.getImportedPage(reader, ++i);  
                PageStamp stamp = copy.createPageStamp(page);  
                Font f = new Font(base);  
  
                // 添加页脚，左侧文字，右侧页码  
                ColumnText.showTextAligned(stamp.getUnderContent(),  
                        Element.ALIGN_RIGHT,  
                        new Phrase(String.format("第 %d 页/共 %d 页", i, num), f),  
                        550f, 28, 0);  
                ColumnText.showTextAligned(stamp.getUnderContent(),  
                        Element.ALIGN_LEFT, new Phrase(foot, f), 50f, 28, 0);  
  
                // 添加页眉 (文字页眉，居中)  
                ColumnText.showTextAligned(stamp.getUnderContent(),  
                        Element.ALIGN_CENTER, new Phrase(pageHeade, f), 150f,  
                        800, 0);  
                  
                // 页眉添加logo （图片页眉，居右）  
                /*Image img = Image.getInstance("template/logo.png");// 选择图片 
                img.setAlignment(1); 
                img.scaleAbsolute(436 / 5, 96 / 5);// 控制图片大小 
                img.setAbsolutePosition(450f, 800);// 控制图片位置 
                stamp.getUnderContent().addImage(img);*/  
  
                // 添加水印  
                PdfContentByte under = stamp.getUnderContent();  
                under.beginText();  
                under.setColorFill(Color.LIGHT_GRAY);  
                  
                // 字符越长，字体越小，设置字体  
                int fontSize = getFontSize(waterMarkName);  
                under.setFontAndSize(base, fontSize);  
  
                // 设置水印文字字体倾斜 开始  
                float pageWidth = reader.getPageSize(i).getWidth();  
                float pageHeight = reader.getPageSize(i).getHeight();  
  
                under.showTextAligned(Element.ALIGN_CENTER, waterMarkName,  
                        pageWidth / 2, pageHeight / 2, 60);// 水印文字成60度角倾斜,且页面居中展示  
  
                // 字体设置结束  
                under.endText();  
                stamp.alterContents();  
                copy.addPage(page);  
            }  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
            return -1;  
        }  
        finally  
        {  
            if (null != document)  
            {  
                document.close();  
            }  
        }  
        System.out.println("pdf totalpages:" + num);  
        return num;  
  
    }  
    
    /** 
     * 根据水印文字长度计算获取字体大小 
     * @param waterMarkName 
     * @return 
     */  
    private static int getFontSize(String waterMarkName){  
        int fontSize = 80;  
        if(null != waterMarkName && !"".equals(waterMarkName)){  
            int length = waterMarkName.length();  
            if(length <=26 && length >= 18){  
                fontSize = 26;  
            }else if(length <18 && length >= 8){  
                fontSize = 40;  
            }else if(length <8 && length >= 1){  
                fontSize = 80;  
            }else {  
                fontSize = 16;  
            }  
        }         
        return fontSize;  
    }  
}