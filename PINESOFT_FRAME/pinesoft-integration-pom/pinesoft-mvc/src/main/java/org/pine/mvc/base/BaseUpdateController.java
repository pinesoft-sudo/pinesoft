package org.pine.mvc.base;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.pine.ibaits.mapper.contract.IMapper;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public  class BaseUpdateController<T> extends BaseMapperController<T> {

	public BaseUpdateController(IMapper<T> mapper) {
		super(mapper);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@PutMapping(value = "conditon/selective")
	public int updateByExampleSelective(@RequestBody Map<String, Object> map) throws Exception {
		final String recordKey = "record";
		final String conditonKey = "condition";
		Class<T> entityClass;
		try {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} catch (Exception e) {
			throw new Exception("无法获取泛型T class！");
		}

		Object recordObj = map.get(recordKey);
		Object conditionObj = map.get(conditonKey);
		if (recordObj == null) {
			throw new Exception("不存在record键值！");
		}
		if (conditionObj == null) {
			throw new Exception("不存在condition键值！");
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
