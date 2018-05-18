package org.pine.mvc.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xier:
 * @Description:插入接口
 * @date 创建时间：2017年12月1日 下午3:25:19
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseInsertController<T> extends BaseMapperController<T> {

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
		return mapper.insertAll(records);
	}

	@Transactional
	@PostMapping(value = "all/selective")
	public int insertAllSelective(@RequestBody List<T> records) throws Exception {

		return mapper.insertAllSelective(records);
	}
}
