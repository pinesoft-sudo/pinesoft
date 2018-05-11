package org.pine.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.pine.common.criteria.Filter;


/**
 * @author yangs:
 * @Description:
 * @date 创建时间：2016年11月16日 下午8:19:36
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public interface IMapper<T> {
	/**
	 * @Title: countByFilter
	 * @Description:根据条件获取个数
	 * @author yangs
	 * @param @param
	 *            filter
	 * @param @return
	 */
	long countByFilter(Filter filter);

	/**
	 * @Title: countByFilter
	 * @Description:根据条件获取个数
	 * @author yangs
	 * @param @param
	 *            filter
	 * @param @return
	 */
	Integer maxSort(Filter filter);

	/**
	 * @Title: deleteByFilter
	 * @Description:根据条件删除数据
	 * @author yangs
	 * @param @param
	 *            filter
	 * @param @return
	 */
	int deleteByFilter(Filter filter);

	/**
	 * @Title: insert
	 * @Description:插入数据
	 * @author yangs
	 * @param @param
	 *            record
	 * @param @return
	 */
	int insert(T record);

	/**
	 * @Title: insertList
	 * @Description:批量插入数据
	 * @author yangs
	 * @param @param
	 *            record
	 * @param @return
	 */
	int insertList(List<T> record);

	/**
	 * @Title: selectByFilter
	 * @Description:根据条件获取数据列表
	 * @author yangs
	 * @param @param
	 *            filter
	 * @param @return
	 */
	List<T> selectByFilter(Filter filter);

	/**
	 * @Title: updateByFilter
	 * @Description:修改数据
	 * @author yangs
	 * @param @param
	 *            record
	 * @param @param
	 *            filter
	 * @param @return
	 */
	int updateByFilter(@Param("record") T record, @Param("Filter") Filter filter);

	/**
	 * @Title: updateByFilterSelective
	 * @Description:选择性修改record有值的数据
	 * @author yangs
	 * @param @param
	 *            record
	 * @param @param
	 *            filter
	 * @param @return
	 */
	int updateByFilterSelective(@Param("record") T record, @Param("Filter") Filter filter);
}
