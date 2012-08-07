package com.delesio.model;

import java.io.File;

public class ImagePath extends AbstractPersistantObject {

	/** 
	 * 
	 */
	private static final long serialVersionUID = -6652042205605938034L;
	private String imageName;

	private IImageMapper imageMapper;
	
	private File image;
	
	private boolean enabled=true;
	
	
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
//	public String getMemberDirectory()
//	{
//		return "//"+memberTO.getId();
//	}
	public String buildDirectoryPath()
	{
		return imageMapper.getDirectory()+"//"+imageName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}
	public IImageMapper getImageMapper() {
		return imageMapper;
	}
	public void setImageMapper(IImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
	
	
}
