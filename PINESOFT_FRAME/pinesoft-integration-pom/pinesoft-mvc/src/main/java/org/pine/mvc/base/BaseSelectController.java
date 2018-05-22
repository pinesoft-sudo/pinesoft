package org.pine.mvc.base;

import java.util.List;

import org.pine.soft.mapper.contract.IMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public  class BaseSelectController<T> extends BaseMapperController<T> {

	public BaseSelectController(IMapper<T> mapper) {
		super(mapper);
		// TODO Auto-generated constructor stub
	}

	@GetMapping(value = "all")
	public List<T> selectAll() throws Exception {
		return mapper.selectAll();
	}

	@PostMapping(value = "record")
	public List<T> select(@RequestBody T record) throws Exception {
		return mapper.select(record);
	}

	@GetMapping(value = { "{key}", "key/{key}" })
	public T selectByPrimaryKey(@PathVariable String key) throws Exception {

		return mapper.selectByPrimaryKey(key);
	}

	@PostMapping(value = "one")
	public T selectOne(@RequestBody T record) throws Exception {

		return mapper.selectOne(record);
	}

	@PostMapping(value = "condition")
	public List<T> selectByExample(@RequestBody Object condition) throws Exception {

		return mapper.selectByExample(condition);
	}

	@PostMapping(value = "count")
	public int selectCount(@RequestBody T record) throws Exception {

		return mapper.selectCount(record);
	}

	@PostMapping(value = "count/condition")
	public int selectCountByExample(@RequestBody Object condition) {

		return mapper.selectCountByExample(condition);
	}

}
