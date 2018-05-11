package org.pine.boot.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.pine.annotation.ServiceLog;
import org.pine.boot.dao.RecordFileMapper;
import org.pine.boot.entity.RecordFile;
import org.pine.common.criteria.Filter;
import org.pine.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordFileService {
	@Autowired
	private RecordFileMapper mapper;
	private Filter filter;

	public List<RecordFile> getRecordFileByIO(String path, String name, String date) {
		File file = new File(path);
		File[] tempList = file.listFiles();

		List<RecordFile> rfList = null;
		if (tempList != null && tempList.length > 0) {
			List<File> fileList = new ArrayList<File>();

			for (File file1 : tempList) {
				if (file1.isFile()) {
					String fname = FileUtil.getFileNameByFileName(file1.getName());
					String[] rets = fname.split("-");
					if (rets.length == 3) {
						if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(date)) {
							if (rets[0].equals(name) && rets[2].equals(date)) {
								fileList.add(file1);
							}
						} else if (!StringUtils.isEmpty(name)) {
							if (rets[0].equals(name)) {
								fileList.add(file1);
							}
						} else if (!StringUtils.isEmpty(date)) {
							if (rets[2].equals(date)) {
								fileList.add(file1);
							}
						} else {
							fileList.add(file1);
						}
					}
				}
			}

			if (fileList.size() > 0) {
				rfList = new ArrayList<RecordFile>();
				for (File file2 : fileList) {
					RecordFile rf = new RecordFile();
					rf.setGuid(UUID.randomUUID().toString());
					rf.setFilename(file2.getName());
					rf.setFilesize(file2.length() / (1024 * 1024));
					long time = file2.lastModified();
					String ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
					rf.setModified(ctime);
					rfList.add(rf);
				}
			}

		}
		return rfList;
	}

	@ServiceLog(description="test_getList")
	public List<RecordFile> getList(Filter filter) throws Exception {
		return mapper.selectByFilter(filter);
	}

	public List<RecordFile> getListByNameAndDate(String name, String date) throws Exception {
		String str = null;
		if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(date)) {
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(date)) {
				str = name + "%" + date + "%";
			} else if (!StringUtils.isEmpty(name)) {
				str = name + "%";
			} else if (!StringUtils.isEmpty(date)) {
				str = "%" + name + "%";
			}
			filter = new Filter("FILENAME like", str);
		} else {
			filter = null;
		}
		return getList(filter);
	}

	//开启springboot默认的ConcurrentMapCacheManager 
	@Cacheable("records")
	public RecordFile get(String guid) throws Exception {
		System.out.println("123123");
		Filter filter = new Filter("GUID =", guid);
		return mapper.selectByFilter(filter).get(0);
	}

	// 该注解用于添加事务，前提你用的是jdbc, jpa（Hibernate）, mybatis，这种常见的orm
	// rollbackFor-> 出现异常回滚（必须有throw/throws）
	@Transactional(rollbackFor = { Exception.class })
	public String insert(RecordFile record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		String guid = record.getGuid();
		if (StringUtils.isEmpty(guid)) {
			guid = UUID.randomUUID().toString();
			record.setGuid(guid);
		}
		if (mapper.insert(record) > 0) {
			return guid;
		} else {
			return null;
		}
	}

	public int insertList(List<RecordFile> recordList) throws Exception {
		if (recordList == null || recordList.size() <= 0) {
			throw new Exception("参数对象不能为空");
		} else {
			for (int i = 0; i < recordList.size(); i++) {
				String guid = recordList.get(i).getGuid();
				if (StringUtils.isEmpty(guid)) {
					guid = UUID.randomUUID().toString();
					recordList.get(i).setGuid(guid);
				}
			}
			return mapper.insertList(recordList);
		}
	}

	public int delete(String guid) throws Exception {
		if (StringUtils.isEmpty(guid)) {
			throw new Exception("参数值不能为空");
		}
		filter = new Filter("GUID =", guid);
		return mapper.deleteByFilter(filter);
	}

	public int updatePortion(RecordFile record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空123");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilterSelective(record, filter);
	}

	public int updateComplete(RecordFile record) throws Exception {
		if (record == null) {
			throw new Exception("参数对象不能为空");
		}
		filter = new Filter("GUID =", record.getGuid());
		return mapper.updateByFilter(record, filter);
	}
}
