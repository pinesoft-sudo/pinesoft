package org.pine.ibaits.mapper.contract;

import tk.mybatis.mapper.common.MySqlMapper;

/**
 * mapper 本地化统一接口，扩展继承 MySql独有的通用方法
 * @author Administrator
 *
 * @param <T>
 */
public interface IMySqlMapper<T> extends IMapper<T>,MySqlMapper<T> {

}
