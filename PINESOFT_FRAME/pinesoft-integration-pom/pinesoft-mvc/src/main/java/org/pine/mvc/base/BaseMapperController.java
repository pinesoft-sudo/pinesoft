package org.pine.mvc.base;

import com.gisquest.mybatis.IMapper;

/**
 * @author xier:
 * @Description:
 * @date 创建时间：2017年12月1日 下午3:30:13
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseMapperController<T> extends BaseController {
	public IMapper<T> mapper;

	public void setMapper(IMapper mapper) {
		this.mapper = mapper;
	}
}
