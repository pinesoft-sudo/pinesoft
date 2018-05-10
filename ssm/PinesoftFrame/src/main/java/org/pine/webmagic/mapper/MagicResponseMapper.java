package org.pine.webmagic.mapper;

import java.util.List;

import org.pine.common.criteria.Filter;
import org.pine.webmagic.entity.MagicResponse;


public interface MagicResponseMapper {
	long countByFilter(Filter filter);

	List<MagicResponse> selectByFilter(Filter filter);

	int deleteByFilter(Filter filter);

	int insert(MagicResponse record);
}
