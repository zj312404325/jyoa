package com.jeeplus.common.watermark;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

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
public class OfficeToSwf {   
  
    /**  
     * @param args  
     * @throws InterruptedException 
     */  
    public static void main(String[] args) throws Exception {   
    	try {
			officeToPdf("d:/gtzy/1.txt","d:/gtzy/10.pdf",0);
    		//officeToHtml("d:/gtzy/5.pptx","d:/gtzy/5.html");
			addFooterAndWater("d:/gtzy/10.pdf", "d:/gtzy/777.pdf", "WordToPdf水印严禁复制", "WordToPdf页眉", "WordToPdf页脚");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
        
    // 将word格式的文件转换为pdf格式
    public static boolean officeToPdf(String srcPath, String desPath,int count) throws Exception{
        // 源文件目录
        File inputFile = new File(srcPath);
        if (!inputFile.exists()) {
            System.out.println("源文件不存在！");
            return false;
        }
        // 输出文件目录
        File outputFile = new File(desPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }
        OfficeManager officeManager=null;
        try {
	        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
	        if(!isWinOs()){
	        	config.setOfficeHome(Global.LINUX);
	        }else{
	        	config.setOfficeHome(Global.WIN);
	        }
			officeManager = startService(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(count<5){
				Thread.sleep(10000);
				System.out.println("等待10秒重新启动！");
				count++;
				officeToPdf(srcPath,desPath,count);
			}else{
				System.out.println("重试超过5次，结束");
				return false;
			}
		} 
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);  
        if (inputFile.exists()) {// 找不到源文件, 则返回  
            if (!outputFile.getParentFile().exists()) { // 假如目标路径不存在, 则新建该路径  
                outputFile.getParentFile().mkdirs();  
            }  
            try {
            	converter.convert(inputFile, outputFile);  
			} catch (Exception e) {
				e.printStackTrace();
			}
        }  
      
        stopService(officeManager);
        return true;
    }
    
    public static boolean isExistFile(String fileurl) {
		String root=Global.getWebRoot()+fileurl;
		File file=new File(root);
		boolean isExist = file.exists();
		System.out.println("判断文件是否存在："+fileurl);
		System.out.println("判断文件是否存在结果："+isExist);
		return isExist;
	}
    
    
    private static OfficeManager startService(
			DefaultOfficeManagerConfiguration configuration) {
    	System.out.println("准备启动openoffice服务....");
        configuration.setPortNumbers(8100); // 设置转换端口，默认为8100
        configuration.setTaskExecutionTimeout(1000 * 60 * 5L);//设置任务执行超时为5分钟  
        configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时  

        OfficeManager officeManager = configuration.buildOfficeManager();
        officeManager.start(); // 启动服务
        System.out.println("office转换服务启动成功!");
        return officeManager;
	}
    
    /**
     * 关闭OpenOffice软件
    * @Title: stopService 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @return void    返回类型 
    * @date 2014-8-19 上午11:15:24
     */
    public static void stopService(OfficeManager officeManager) {
        System.out.println("准备关闭openoffice转换服务....");
        if (officeManager != null) {
            officeManager.stop();
        }
        System.out.println("关闭openoffice转换服务成功!");
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
            BaseFont base=null;
            if(!isWinOs()){
            	base = BaseFont.createFont(Global.getWebRoot()+"/static/fonts/SIMSUN.TTC,0",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }else{
            	base = BaseFont.createFont("C:/Windows/Fonts/simfang.ttf",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
            
            num = reader.getNumberOfPages();  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));  
            document.open();  
            for (int i = 0; i < num;)  
            {  
                PdfImportedPage page = copy.getImportedPage(reader, ++i);  
                PageStamp stamp = copy.createPageStamp(page);  
                Font f = new Font(base);  
  
                // 添加页脚，左侧文字，右侧页码  
//                ColumnText.showTextAligned(stamp.getUnderContent(),  
//                        Element.ALIGN_RIGHT,  
//                        new Phrase(String.format("第 %d 页/共 %d 页", i, num), f),  
//                        550f, 28, 0);  
                ColumnText.showTextAligned(stamp.getUnderContent(),  
                        Element.ALIGN_LEFT, new Phrase(foot, f), 50f, 28, 0);  
  
                // 添加页眉 (文字页眉，居中)  
//                ColumnText.showTextAligned(stamp.getUnderContent(),  
//                        Element.ALIGN_CENTER, new Phrase(pageHeade, f), 150f,  
//                        800, 0);  
                  
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
        deleteFile(fileName);
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
    
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}