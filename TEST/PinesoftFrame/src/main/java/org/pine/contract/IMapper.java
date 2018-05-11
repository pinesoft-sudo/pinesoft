package org.pine.contract;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pine.common.criteria.Filter;

/**
 * CRUD 基础数据处理接口
 * @author yangs
 *
 * @param <T>
 */
public interface IMapper<T> {

	long countByFilter(Filter filter);

	int deleteByFilter(Filter filter);

	int insert(T record);

	int insertList(List<T> record);

	List<T> selectByFilter(Filter filter);

	int updateByFilter(@Param("record") T record, @Param("Filter") Filter filter);

	int updateByFilterSelective(@Param("record") T record, @Param("Filter") Filter filter);
}
