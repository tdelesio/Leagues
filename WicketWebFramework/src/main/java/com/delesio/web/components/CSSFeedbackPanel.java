package com.delesio.web.components;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class CSSFeedbackPanel extends FeedbackPanel {
    public CSSFeedbackPanel(final String id) {
        super(id);
        
        if (anyMessage(FeedbackMessage.INFO)) {
            add(new SimpleAttributeModifier("class", "feedbackinfo"));
        }
        else if (anyMessage(FeedbackMessage.ERROR)) {
        	add(new SimpleAttributeModifier("class", "feedbackerror"));
        }
        else 
        {
        	add(new SimpleAttributeModifier("class", "feedback"));
        }
    }
}
