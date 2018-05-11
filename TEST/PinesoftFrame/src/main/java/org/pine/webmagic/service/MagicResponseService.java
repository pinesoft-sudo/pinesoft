package org.pine.webmagic.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.pine.common.criteria.Filter;
import org.pine.webmagic.entity.MagicResponse;
import org.pine.webmagic.mapper.MagicResponseMapper;
import org.springframework.stereotype.Service;


@Service
public class MagicResponseService {
	@Resource
	private MagicResponseMapper mapper;
	private Filter filter;

	public MagicResponse get(String guid) throws Exception {
		filter = new Filter("GUID =", guid);
		return mapper.selectByFilter(filter).get(0);
	}

	public List<MagicResponse> getListByRequestGuid(String guid) throws Exception {
		filter = new Filter("REQUEST_GUID =", guid);
		return mapper.selectByFilter(filter);
	}

	public long getCountByRequestGuid(String guid) throws Exception {
		filter = new Filter("REQUEST_GUID =", guid);
		return mapper.countByFilter(filter);
	}

	public String insert(MagicResponse record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}

		String newguid = record.getGuid();
		if (StringUtils.isEmpty(newguid)) {
			newguid = UUID.randomUUID().toString();
			record.setGuid(newguid);
		}

		if (mapper.insert(record) > 0) {
			return newguid;
		}
		else {
			return null;
		}
	}

	public long deleteListByRequestGuid(String ids) throws Exception {
		if (StringUtils.isEmpty(ids)) {
			throw new Exception("参数值不能为�?");
		}
		String[] idArry = ids.split(",");
		List<String> idList = Arrays.asList(idArry);

		filter = new Filter("REQUEST_GUID in", idList);
		return mapper.deleteByFilter(filter);
	}

	public long clear() throws Exception {
		return mapper.deleteByFilter(null);
	}

}
