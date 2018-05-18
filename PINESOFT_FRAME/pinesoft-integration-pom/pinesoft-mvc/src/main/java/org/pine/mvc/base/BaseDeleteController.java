package org.pine.mvc.base;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xier:
 * @Description:删除接口
 * @date 创建时间：2017年12月1日 下午3:25:31
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseDeleteController<T> extends BaseMapperController<T> {

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
