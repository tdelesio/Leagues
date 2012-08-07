package com.delesio.web.panels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Resource;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

public abstract class UploadPanel extends Panel{
	
	private final FileListView fileListView;
	
	public UploadPanel(String id)
	{
		super (id);
		
		Folder uploadFolder = getUploadFolder();

        // Create feedback panels
        final FeedbackPanel uploadFeedback = new FeedbackPanel("uploadFeedback");

        // Add uploadFeedback to the page itself
        add(uploadFeedback);

        // Add simple upload form, which is hooked up to its feedback panel by
        // virtue of that panel being nested in the form.
        final FileUploadForm simpleUploadForm = new FileUploadForm("simpleUpload");
        simpleUploadForm.add(new UploadProgressBar("progress", simpleUploadForm));
        add(simpleUploadForm);

        // Add folder view
        add(new Label("dir", uploadFolder.getAbsolutePath()).setVisible(false));
        fileListView = new FileListView("fileList", new LoadableDetachableModel<List<File>>()
        {
            @Override
            protected List<File> load()
            {
                return Arrays.asList(getUploadFolder().listFiles());
            }
        });
        add(fileListView);
	}

	private class FileListView extends ListView<File>
    {
        /**
         * Construct.
         * 
         * @param name
         *            Component name
         * @param files
         *            The file list model
         */
        public FileListView(String name, final IModel<List<File>> files)
        {
            super(name, files);
        }

        /**
         * @see ListView#populateItem(ListItem)
         */
        @Override
        protected void populateItem(ListItem<File> listItem)
        {
            final File file = listItem.getModelObject();
            listItem.add(new Label("file", file.getName()));
            Link delLink = new Link("delete")
            {
                @Override
                public void onClick()
                {
                    deleteFile(file);
                }
            };
            listItem.add(delLink);
            
            Resource resource = new Resource() {
				
				@Override
				public IResourceStream getResourceStream() {
					IResourceStream stream = new FileResourceStream(file);
                    return stream;
				}
			};
                    
            Image image = new Image("image", resource);
            listItem.add(image);
        }
    }
	
	private class FileUploadForm extends Form<Void>
    {
        // collection that will hold uploaded FileUpload objects
        private final Collection<FileUpload> uploads = new ArrayList<FileUpload>();

        /**
         * TODO
         * 
         * @return Collection
         */
        public Collection<FileUpload> getUploads()
        {
            return uploads;
        }

        /**
         * Construct.
         * 
         * @param name
         *            Component name
         */
        public FileUploadForm(String name)
        {
            super(name);

            // set this form to multipart mode (allways needed for uploads!)
            setMultiPart(true);

            // Add one multi-file upload field
            add(new MultiFileUploadField("fileInput", new PropertyModel<Collection<FileUpload>>(
                this, "uploads"), 5));

            // Set maximum size to 100K for demo purposes
            setMaxSize(Bytes.kilobytes(5000));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        protected void onSubmit()
        {
            Iterator<FileUpload> it = uploads.iterator();
            while (it.hasNext())
            {
                final FileUpload upload = it.next();
                // Create a new file
                File newFile = new File(getUploadFolder(), upload.getClientFileName());

                // Check new file, delete if it allready existed
                checkFileExists(newFile);
                try
                {
                    // Save to new file
                    newFile.createNewFile();
                    upload.writeTo(newFile);

                    saveFile(newFile);
                    UploadPanel.this.info("saved file: " + upload.getClientFileName());
                }
                catch (Exception e)
                {
                    throw new IllegalStateException("Unable to write file");
                }
            }
        }
    }
	
	private void checkFileExists(File newFile)
    {
        if (newFile.exists())
        {
            // Try to delete the file
            if (!Files.remove(newFile))
            {
                throw new IllegalStateException("Unable to overwrite " + newFile.getAbsolutePath());
            }
        }
    }

	public void deleteFile(File file)
    {
    	Files.remove(file);
        info("Deleted " + file.getName());
    }
    
    public abstract Folder getUploadFolder();
    public abstract void saveFile(File file); 
    
//    {
//        return ((UploadApplication)Application.get()).getUploadFolder();
//    }
}
