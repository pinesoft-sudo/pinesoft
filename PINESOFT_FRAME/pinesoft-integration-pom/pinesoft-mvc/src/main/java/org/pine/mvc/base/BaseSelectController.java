package org.pine.mvc.base;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xier:
 * @Description:查询接口
 * @date 创建时间：2017年12月1日 下午3:24:06
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseSelectController<T> extends BaseMapperController<T> {

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
