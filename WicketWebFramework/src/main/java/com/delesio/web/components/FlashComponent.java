package com.delesio.web.components;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class FlashComponent extends WebMarkupContainer implements IResourceListener {
	
	private int WIDTH = 700;
	private int HEIGHT = 70;
	
	public FlashComponent(String id)
	{
		super(id);
	}

	public void onResourceRequested() {
		getRequestCycle().setRequestTarget(new IRequestTarget() {
            public void respond(RequestCycle requestCycle) {
            	try
            	{
            		requestCycle.getResponse().getOutputStream().write(getFlashByteArray());
            	}
            	catch (IOException exception)
            	{
            		exception.printStackTrace();
            	}
            }
            
            public void detach(RequestCycle cycle)
            {
            	
            }
        });
	}
	
	protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("width", WIDTH);
        tag.put("height", HEIGHT);
    }
	
	protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {

        StringBuffer flashBuffer = new StringBuffer();

         // in the following, we will append all the necessary body tags of flash markup
        flashBuffer.append("<param name=\"movie\" value=\"");

        // urlFor will  provide the link that will stream the flash byte array
        flashBuffer.append(urlFor(IResourceListener.INTERFACE));
        flashBuffer.append("\">");
        flashBuffer.append("<embed src=\"");
        flashBuffer.append(urlFor(IResourceListener.INTERFACE));
        flashBuffer.append("\" width=\"");
        flashBuffer.append(WIDTH);
        flashBuffer.append("\" height=\"");
        flashBuffer.append(HEIGHT);
        flashBuffer.append("' quality=\"high\" name=\"movie\" align=\"middle\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\">");
        replaceComponentTagBody(markupStream, openTag, flashBuffer);
    }
	
	protected byte[] getFlashByteArray()
	{
		byte[] bytes = new byte[10000];
		try
		{
			FileInputStream fstream = new  FileInputStream("C:/development/eclipse/svn-league/JNEWeb/src/main/webapp/sample05.swf");
			fstream.read(bytes);
			fstream.close();
			return bytes;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return null;
		}
	}

}
