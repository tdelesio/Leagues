package com.delesio.service.impl;

import java.util.List;

import com.delesio.dao.IImageDao;
import com.delesio.model.IImageMapper;
import com.delesio.model.ImagePath;
import com.delesio.service.IImageService;

public class ImageService extends AbstractService implements IImageService{

	private IImageDao imageDao;
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	public void setImageDao(IImageDao imageDao) {
		this.imageDao = imageDao;
	}


	public ImagePath getImagePathByImageAndMapper(IImageMapper imageMapper, String imageName)
	{
		return imageDao.getImagePathByImageAndMapper(imageMapper, imageName);
	}

	public void deleteImagePath(ImagePath imagePath)
	{
		dao.deleteObject(imagePath);
	}

	public void createOrUpdateImagePath(ImagePath imagePath) {
		dao.saveOrUpdate(imagePath);
	}

	public List<ImagePath> getImageForMapper(IImageMapper imageMapper) {
		return imageDao.getImageForMapper(imageMapper);
	}

}
