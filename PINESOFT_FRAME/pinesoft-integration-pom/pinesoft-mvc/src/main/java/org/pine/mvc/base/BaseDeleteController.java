package org.pine.mvc.base;

import org.pine.soft.mapper.contract.IMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class BaseDeleteController<T> extends BaseMapperController<T> {

	public BaseDeleteController(IMapper<T> mapper) {
		super(mapper);
		// TODO Auto-generated constructor stub
	}

	@DeleteMapping(value = "condition")
	public int deleteByExample(@RequestBody Object condition) throws Exception {
		return mapper.deleteByExample(condition);
	}

	@DeleteMapping(value = "")
	public int delete(@RequestBody T record) throws Exception {
		return mapper.delete(record);
	}

	@DeleteMapping(value = { "{key}", "key/{key}" })
	public int deleteByPrimaryKey(@PathVariable String key) throws Exception {
		return mapper.deleteByPrimaryKey(key);
	}
}
