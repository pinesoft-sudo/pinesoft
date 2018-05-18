package org.pine.mvc.base;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gisquest.MvcException;

/**
 * @author xier:
 * @Description:修改接口
 * @date 创建时间：2017年12月1日 下午3:25:41
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseUpdateController<T> extends BaseMapperController<T> {

	@PutMapping(value = "conditon/selective")
	public int updateByExampleSelective(@RequestBody Map<String, Object> map) throws Exception {
		final String recordKey = "record";
		final String conditonKey = "condition";
		Class<T> entityClass;
		try {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} catch (Exception e) {
			throw new MvcException("无法获取泛型T class！");
		}

		Object recordObj = map.get(recordKey);
		Object conditionObj = map.get(conditonKey);
		if (recordObj == null) {
			throw new MvcException("不存在record键值！");
		}
		if (conditionObj == null) {
			throw new MvcException("不存在condition键值！");
		}
		return 0;
	}

	@PutMapping(value = "key")
	public int updateByPrimaryKey(@RequestBody T record) throws Exception {

		return mapper.updateByPrimaryKey(record);
	}

	@PutMapping(value = "key/selective")
	public int updateByPrimaryKeySelective(@RequestBody T record) throws Exception {

		return mapper.updateByPrimaryKeySelective(record);
	}

	@PutMapping(value = "condition")
	public int updateByExample(@RequestBody Map<String, Object> map) throws Exception {

		return 0;
	}
}
