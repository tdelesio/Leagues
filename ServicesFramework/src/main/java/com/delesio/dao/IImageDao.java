package com.delesio.dao;

import java.util.List;

import com.delesio.model.IImageMapper;
import com.delesio.model.ImagePath;

public interface IImageDao {

	public List<ImagePath> getImageForMapper(IImageMapper imageMapper);
	public ImagePath getImagePathByImageAndMapper(IImageMapper imageMapper, String imageName);
}
