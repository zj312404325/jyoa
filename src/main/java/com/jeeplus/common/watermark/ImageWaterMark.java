package com.jeeplus.common.watermark;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageWaterMark
{
	//设定要显示的字符
    String hex_chr = "0123456789abcdef";
    private ImageWaterMark()
    {
        
    }
    public static ImageWaterMark getInstance()
    {
        return new ImageWaterMark();
    }

	
	//测试
	public static void main(String[] args)
	{		//System.out.print(MD5.getInstance().calcMD5("000000"));
		pressImage("D:/gtzy/2.jpg","D:/gtzy/1.jpg", 0, 0);
		//pressImage2("G:/2.jpg","G:/1.png" ,"G:/3.jpg",10,10);
		//pressText("老子金赢网的","D:/gtzy/1.jpg" ,"TimesRoman", Font.BOLD, 10,10,30,0);
	}
	
	public static void pressText(String pressText, String targetImg,
            String fontName, int fontStyle, int color, int fontSize, int x,
            int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            
            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));
 
            g.drawString(pressText, wideth - fontSize - x, height - fontSize
                    / 2 - y);
            g.dispose();
            FileOutputStream ut = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(ut);
            encoder.encode(image);
            ut.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

	
	public final static void pressImage(String pressImg, String targetImg,
            int x, int y) {
        try {
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int  wideth_biao= src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            
            double l =(double)height_biao/(double) wideth_biao;
            
            int wtem = (int) (wideth*0.1);
            int htem = (int) (height*0.1);
            int widethtem = (int) (wideth*0.8);
            int heighttem = (int) (height*0.8);
            
            
            int w =widethtem/2;
            int h =(int) (w*l);
            
            g.drawImage(src_biao, wtem,
            		htem, w, h, null);
            g.drawImage(src_biao, (wideth/2),
            		htem, w, h, null);
            g.drawImage(src_biao, wtem+wtem,
            		(height/2), w, h, null);
            g.drawImage(src_biao,(wideth/2),
            		htem+(height/2), w, h, null);
            //水印文件
           /* File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
//            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
//                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            int focuswidth = wideth>wideth_biao?(wideth - wideth_biao)/2:0;
            int focusheight = height>height_biao?(height - height_biao)/2:0;
            int imgwidth = wideth>wideth_biao?wideth_biao:wideth;
            int imgheight = height>height_biao?height_biao:height;
            g.drawImage(src_biao, focuswidth,
            		focusheight, imgwidth, imgheight, null);*/
            //水印文件结束
            g.dispose();
            FileOutputStream ut = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(ut);
            encoder.encode(image);
            ut.close();
        } catch (Exception e) {
        	System.out.println(e);
        }
	}
	
	/**
	 * 质保书敲章
	 * @param pressImg 章图片
	 * @param targetImg 原图片
	 * @param newImg 新图片
	 * @param x  
	 * @param y
	 * @throws Exception 
	 */
	public final static void pressImage2(String pressImg, String targetImg,String newImg,
            int x, int y) throws Exception {
        try {
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int  wideth_biao= src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            
            int w = (int) (wideth*0.133);
            double l =(double)w/(double) wideth_biao;
            
            
            int h = (int) (height_biao*l);            
            g.drawImage(src_biao, x,y, w, h, null);
            g.dispose();
            FileOutputStream ut = new FileOutputStream(newImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(ut);
            encoder.encode(image);
            ut.close();
        } catch (Exception e) {
        	throw e;
        }
	}
}

