package org.pine.ibaits.mapper.contract;

import tk.mybatis.mapper.common.SqlServerMapper;
/**
 * mapper 本地化统一接口，扩展继承 SqlServer独有的通用方法
 * 
 * @author yangs
 *
 * @param <T>
 */
public interface ISqlServerMapper<T> extends IMapper<T>,SqlServerMapper<T> {

}
