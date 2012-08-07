package info.makeyourpicks.web.components;

import info.makeyourpicks.web.layout.LightboxInfoPanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public class InfoModalWindow extends ModalWindow {

	public InfoModalWindow(String id, String message)
	{
		super(id);
		
		setTitle("Help");
//        setCookieName(id);
        setResizable(false);
//        setUseInitialHeight(true);
        setInitialWidth(200);
        setInitialHeight(200);
        
        setContent(new LightboxInfoPanel(getContentId(), message));
	}
	
	public AjaxLink getDisplayLink(String id)
	{
		return new AjaxLink(id)
        {
            public void onClick(AjaxRequestTarget target)
            {
                show(target);
            }
        };
	}
}
