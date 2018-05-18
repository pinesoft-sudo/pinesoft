package org.pine.mvc.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gisquest.drag.entity.DragContent;
import com.gisquest.ie.entity.ImportContent;

/**
 * @author xier:
 * @Description:
 * @date 创建时间：2017年12月20日 下午1:56:50
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseOtherController<T> extends BaseMapperController<T> {
	@GetMapping(value = "/copy/{key}")
	public int copyByPrimaryKey(@PathVariable("key") String key) throws Exception {
		return mapper.copyByPrimaryKey(key);
	}

	@GetMapping(value = "/export/{key}")
	public String export(@PathVariable("key") String key) throws Exception {
		return "";
	}

	/**
	 * 导入数据
	 * 
	 * @author xier
	 * @param @param
	 *            importContent 导入数据内容参数
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @date 2018年3月19日 下午2:49:09
	 */
	@PostMapping(value = "/import")
	public String importData(@RequestBody ImportContent importContent) throws Exception {
		return "";
	}

	/**
	 * 拖拽
	 * 
	 * @author xier
	 * @param @param
	 *            dragContent 拖拽内容参数
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @date 2018年3月19日 下午2:48:37
	 */
	@GetMapping(value = "/drop")
	public String drop(@RequestBody DragContent dragContent) throws Exception {
		return "";
	}

	/**
	 * 获取所有树形结构数据
	 * 
	 * @author xier
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @date 2018年3月19日 下午2:45:49
	 */
	@GetMapping(value = "/tree")
	public String selectTree() throws Exception {
		return "";
	}

	/**
	 * 根据主键获取树形数据
	 * 
	 * @author xier
	 * @param @param
	 *            key 主键
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @date 2018年3月19日 下午2:45:32
	 */
	@GetMapping(value = "/tree/{key}")
	public String selectTree(@PathVariable("key") String key) throws Exception {
		return "";
	}
}
