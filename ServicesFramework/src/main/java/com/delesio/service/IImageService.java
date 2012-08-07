package com.delesio.service;

import java.util.List;

import com.delesio.model.IImageMapper;
import com.delesio.model.ImagePath;

public interface IImageService {

	public void createOrUpdateImagePath(ImagePath imagePath);
	public List<ImagePath> getImageForMapper(IImageMapper imageMapper);
	public void deleteImagePath(ImagePath imagePath);
	public ImagePath getImagePathByImageAndMapper(IImageMapper imageMapper, String imageName);
}
