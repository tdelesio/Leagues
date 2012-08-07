package com.delesio.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;

public class SWFObject extends AbstractBehavior {

	private static final CompressedResourceReference SWFOBJECT_JS = new CompressedResourceReference( SWFObject.class, "swfobject.js");
	
	private int height = 480;
	private int width = 640;
	private String fileName = "MVI_0340.swf";
	private String version = "9";
	private String backgroundColor = "#ffffff";
	private String id;
	
	public SWFObject(String id, String fileName)
	{
		this.id = id;
		this.fileName = fileName;
	}
	
	public SWFObject(String id, String fileName, int height, int width)
	{
		this.id = id;
		this.fileName = fileName;
		this.height = height;
		this.width = width;
	}
	
	
	
	public void renderHead(IHeaderResponse response) {
        response.renderJavascriptReference(SWFOBJECT_JS);
    }
	
	public void onRendered(Component component) {
        Response response = component.getResponse();  // 1
//        final String id = component.getMarkupId();    // 2
//        final String id = "movie1f";
        final String swfVar = id + "SWF";

        response.write(JavascriptUtils.SCRIPT_OPEN_TAG);  // 3

        response.write("var " + swfVar + " = new SWFObject('" + fileName + "', '" 
          + id + "', '" + width + "', '" + height + "', '" + version + "', '" + backgroundColor + "');");  // 4
//        if (getParameters() != null && getParameters().size() > 0) {
//            for (Map.Entry<String, String> e : getParameters().entrySet()) {
//                response.write(swfVar + ".addParam('" + e.getKey() + "', '" + e.getValue() + "');");
//            }
//        }
//        if (getVariables() != null && getVariables().size() > 0) {
//            for (Map.Entry<String, String> e : getVariables().entrySet()) {
//                response.write(swfVar + ".addVariable('" + e.getKey() + "', '" + e.getValue() + "');");
//            }
//        }

        response.write(swfVar + ".write('" + id + "');");  // 5
        response.write(JavascriptUtils.SCRIPT_CLOSE_TAG);  // 6
    }
}
