package com.twosnail.frame.commin.util.kit.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import com.jfinal.log.Logger;
import com.twosnail.frame.commin.util.kit.file.FileKit;

public class ImageKit {

	private static final Logger logger = Logger.getLogger( ImageKit.class );

	/**
	 * 图片压缩（修改图片长和宽）
	 * @param source
	 * @param desc
	 * @param descWidth
	 * @param descHeight
	 * @param ratio
	 * @throws IOException
	 */
	public static void resize( File source, String desc, int descWidth, int descHeight, boolean ratio ) 
			throws IOException {
		
		
		resize( new FileInputStream( source ), desc, descWidth, descHeight, ratio );
	}

	/**
	 * 图片压缩（修改图片长和宽）
	 * @param source
	 * @param desc
	 * @param descWidth
	 * @param descHeight
	 * @param ratio
	 * @throws IOException
	 */
	public static void resize( InputStream source, String desc, final int descWidth, final int descHeight, boolean ratio ) 
			throws IOException {
		
		int width = descWidth;
		int height = descHeight;
		
	    try {
	    	// 读入文件
	    	MemoryCacheImageInputStream imageInput = new MemoryCacheImageInputStream( source );
			Iterator<ImageReader> it = ImageIO.getImageReaders( imageInput );
			ImageReader reader = it.next();
			reader.setInput( imageInput );
		    BufferedImage src = reader.read( 0 );
		    if( ratio ) {
		    	//等比压缩。计算比例
		    	int srcWidth = src.getWidth();
		    	int srcHeight = src.getHeight();
		    	//通过宽度计算所需的高度
		    	if( srcWidth > descWidth && descWidth > 0 ) {
		    		double rate = ( (double) descWidth )/srcWidth;
		    		width = descWidth;
		    		height = (int) ( rate * srcHeight );
		    	} else {
		    		width = srcWidth;
		    		height = srcHeight;
		    	}
		    	//对比计算出来的高度和想要的高度
		    	if( srcHeight > descHeight && descHeight > 0 && height > descHeight ) {
		    		//如果计算出的高度高于目标高度，则更具高度进行压缩
		    		double rate = ( (double) descHeight ) / srcHeight;
		    		height = descHeight;
		    		width = (int) ( rate * srcWidth );
		    	}
		    }
		    Image image = src.getScaledInstance( width, height,Image.SCALE_SMOOTH );
		    
		    int minWidth = descWidth > 0 ? Math.min( width, descWidth ) : width;
		    int minHeight = descHeight > 0 ? Math.min( height, descHeight ) : height;
		    BufferedImage tag = new BufferedImage( minWidth, minHeight, BufferedImage.TYPE_3BYTE_BGR );
		    
		    int x = 0;
			int y = 0;
			if( width > descWidth && descWidth > 0 ) {
				x = (int) ( ( width - descWidth ) / 2 );
			}
			if( height > descHeight && descHeight > 0 ) {
				y = (int) ( ( height - descHeight ) / 2 );
			}
		    
		    Graphics g = tag.getGraphics();
		    // 绘制缩小后的图
		    g.drawImage( image, 0, 0, minWidth, minHeight, x, y, x + minWidth, y + minHeight, null ); 
		    g.dispose();
		    File descFile = FileKit.createFile(desc) ;
		    ImageIO.write( tag, reader.getFormatName(), descFile );
	    } catch( IOException e ) {
	    	logger.warn( "压缩图片出错", e );
	    	throw e;
	    } finally {
	    	source.close();
	    }
		 
	}


	/**
	 * 图片裁剪
	 * @param source
	 * @param desc
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void cut( InputStream source, String desc, int x, int y, int width, int height ) 
			throws IOException {
	    
		try {
			Iterator<ImageReader> it = ImageIO.getImageReaders( new MemoryCacheImageInputStream( source ) );
			//Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
		    ImageReader reader = it.next();
		    //ImageInputStream iis = ImageIO.createImageInputStream( in );
		    //reader.setInput(iis, true);
		    ImageReadParam param = reader.getDefaultReadParam();
		    Rectangle rect = new Rectangle(x, y, width, height);
		    param.setSourceRegion(rect);
		    BufferedImage bi = reader.read(0, param);
		    ImageIO.write(bi, reader.getFormatName(), new File(desc));
		} catch( IOException e ) {
	    	logger.warn( "裁剪图片出错", e );
	    	throw e;
	    } finally {
	    	source.close();
	    }
		
	}
	
	/**
	 * 从中间切图
	 * @param source
	 * @param desc
	 * @param width
	 * @param height
	 * @throws IOException 
	 */
	public static void cutInCenter( InputStream source, String desc, int width, int height ) 
			throws IOException {
		
		try {
			int x = 0;
			int y = 0;
			
			// 读入文件
			MemoryCacheImageInputStream imageInput = new MemoryCacheImageInputStream( source );
			Iterator<ImageReader> it = ImageIO.getImageReaders( imageInput );
		    ImageReader reader = it.next();
		    reader.setInput( imageInput );
		    
	    	int srcWidth = reader.getWidth( 0 );
	    	int srcHeight = reader.getHeight( 0 );
	    	if( width >= srcWidth ) {
	    		width = srcWidth;
	    		x = 0;
	    	} else {
	    		x = (int) ( ( srcWidth - width ) / 2 );
	    	}
	    	
	    	if( height >= srcHeight ) {
	    		height = srcHeight;
	    		y = 0;
	    	} else {
	    		y = (int) ( ( srcHeight - height ) / 2 );
	    	}
	    	
	    	ImageReadParam param = reader.getDefaultReadParam();
		    Rectangle rect = new Rectangle( x, y, width, height );
		    param.setSourceRegion(rect);
		    BufferedImage bi = reader.read( 0, param );
		    ImageIO.write( bi, reader.getFormatName(), new File( desc ) );
		} catch( IOException e ) {
	    	logger.warn( "从中间裁剪图片出错", e );
	    	throw e;
	    } finally {
	    	source.close();
	    }
		
	}
	
}
