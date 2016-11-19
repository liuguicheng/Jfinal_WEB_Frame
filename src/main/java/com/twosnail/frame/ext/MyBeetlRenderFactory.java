package com.twosnail.frame.ext;

import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.render.Render;

/**   
 * @Title: MyBeetlRenderFactory.java
 */
public class MyBeetlRenderFactory extends BeetlRenderFactory{
	
	@Override  
    public Render getRender(String view) {  
        BeetlRender render=new BeetlRender(groupTemplate, view);  
        return render;  
    }  
    @Override  
    public String getViewExtension() {  
        return ".html";  
    }

}
