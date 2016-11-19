package com.twosnail.frame.commin.util.kit.clazz;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 */
public class ClassKit {
	
	/**
	 * 给一个接口，返回这个接口的所有实现类
	 * @param c 接口
	 * @return 实现类
	 */
	public static List<Class<?>> getAllClassByInterface( Class<?> c, String packageRoot ) {
		//返回结果
		List<Class<?>> returnClassList = new ArrayList<Class<?>>();

		//如果不是一个接口，则不做处理
		if( c.isInterface() || ( c.getModifiers() & Modifier.ABSTRACT ) == Modifier.ABSTRACT ) {
			//获得当前的包名
			//String packageName = c.getPackage().getName(); 
			try {

				List<Class<?>> allClass = ClassKit.getClasses(packageRoot); //获得当前包下以及子包下的所有类

				//判断是否是同一个接口
				for( int i=0; i < allClass.size(); i++ ){
					//判断是不是一个接口
					if( c.isAssignableFrom( allClass.get( i ) ) ) { 
						//本身不加进去
						if( !c.equals( allClass.get( i ) ) ) { 

							returnClassList.add(allClass.get(i));
						}
					}
				}
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return returnClassList;
	}

	/**
	 * 获得包下的类  
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Class<?>> getClasses( String packageName )
			throws ClassNotFoundException, IOException {  
	
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();  
		assert classLoader != null;  
		String path = packageName.replace( '.', '/' );
		Enumeration<URL> resources = classLoader.getResources( path );
		List<File> dirs = new ArrayList<File>();
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		
		while( resources.hasMoreElements() ) {
			
			URL resource = resources.nextElement();
			String p = "";
			if( resource.getFile().indexOf( "!" ) >= 0 ) {
				// 在其他的jar文件中
				p = resource.getFile().substring(
						0,  
						resource.getFile().indexOf("!") ).replaceAll("%20", " ");
			} else {  
				//在classes目录中
				p = resource.getFile();
			} 
			
			if ( p.startsWith( "image:/" ) )
				//linux下边需要加上“/”
				if( p.charAt( 7 ) == ':' ) {
					//windows系统
					p = p.substring(6);
				} else {
					//linux系统
					p = p.substring(5);
				}
				
			if ( p.toLowerCase().endsWith( ".jar" ) ) {
				JarFile jarFile = new JarFile( p );
				Enumeration<JarEntry> enums = jarFile.entries();
				while( enums.hasMoreElements() ) {
					JarEntry entry = (JarEntry) enums.nextElement();
					String n = entry.getName();
	                
					if( n.endsWith( ".class" ) ) {
						n = n.replaceAll( "/", "." ).substring( 0, n.length() - 6 );
						if ( n.startsWith( packageName ) ) {
							classes.add( Class.forName( n ) );
						}
					}
				}
				jarFile.close();
			} else {  
				dirs.add( new File( p ) );
			}
		} 

		for( File directory : dirs ) {  
	    
			classes.addAll( findClasses( directory, packageName ) );
		} 
		
		return classes;
	}  
	
	
	/**
	 * 查找一个文件夹下的文件
	 * @param directory
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> findClasses( File directory, String packageName )
			throws ClassNotFoundException {  
	
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if( !directory.exists() ) {	
			return classes;
		}  
	    
		File[] files = directory.listFiles();
		
		for( File file : files ) {
			
			if( file.isDirectory() ) {
				
				assert !file.getName().contains( "." );
				classes.addAll( findClasses( file, packageName + "." + file.getName() ) ); 
			} else if( file.getName().endsWith(".class") ) {  
	            
	            classes.add( Class.forName( packageName + '.' + file.getName().substring( 0, file.getName().length() - 6) ) );  
			}  
		}  
	    
		return classes;
	}  
	
	
	
	/*public static void main( String args[] ) {
		
		try {
			List<Class<?>> list = ClassUtils.getClasses( "com.ccsuntel.util.timer" );
			for( Class<?> c : list ) {
				
				if( c == null ) {
				} else {
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}