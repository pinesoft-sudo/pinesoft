package org.pine.mvc.base;

import org.pine.mvc.MvcProperties;
import org.pine.soft.mapper.contract.IMapper;

public abstract class BaseMapperController<T> extends MvcProperties {
	public IMapper<T> mapper;

	public BaseMapperController(IMapper<T> mapper)
	{
		this.mapper=mapper;
	}
}
