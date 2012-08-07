package com.delesio.dao.hibernate;

import java.util.List;

import com.delesio.dao.IImageDao;
import com.delesio.model.IImageMapper;
import com.delesio.model.ImagePath;

public class ImageDao extends AbstractDao implements IImageDao {

	public List<ImagePath> getImageForMapper(IImageMapper imageMapper) {
		return getHibernateTemplate().find("from ImagePath ip where ip.imageMapper.id = ?", imageMapper.getId());
	}

	public ImagePath getImagePathByImageAndMapper(final IImageMapper imageMapper, final String imageName) {
		return (ImagePath)extractSingleElement(getHibernateTemplate().find("from ImagePath ip where ip.imageMapper.id = ? and ip.imageName=?", new Object[]{imageMapper.getId(), imageName}));
		
	}

	
}
