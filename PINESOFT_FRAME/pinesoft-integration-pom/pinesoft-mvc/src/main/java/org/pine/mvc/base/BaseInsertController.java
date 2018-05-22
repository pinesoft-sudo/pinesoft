package org.pine.mvc.base;

import java.util.List;

import org.pine.ibaits.mapper.contract.IMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BaseInsertController<T> extends BaseMapperController<T> {

	public BaseInsertController(IMapper<T> mapper) {
		super(mapper);
		// TODO Auto-generated constructor stub
	}

	@PostMapping(value = "")
	public int insert(@Validated @RequestBody T record) throws Exception {

		return mapper.insert(record);
	}

	@PostMapping(value = "selective")
	public int insertSelective(@Validated @RequestBody T record) throws Exception {

		return mapper.insertSelective(record);
	}

	@Transactional
	@PostMapping(value = "all")
	public int insertAll(@Validated @RequestBody List<T> records) throws Exception {
		int count = 0;
		for (T record : records) {
			count = count + mapper.insert(record);
		}
		return count;
	}

	@Transactional
	@PostMapping(value = "all/selective")
	public int insertAllSelective(@RequestBody List<T> records) throws Exception {
		int count = 0;
		for (T record : records) {
			count = count + mapper.insertSelective(record);
		}
		return count;
	}
}
