package org.pine.ibaits;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pine.ibaits.mapper.SqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TestSqlMapper {
	@Autowired
	private SqlMapper sqlMapper;
	final String SQL = "select * from zhzs_topic";

	@Test
	public void sqlTest() {
		List<Map<String, Object>> maps = sqlMapper.selectList(SQL);
		System.out.println(maps.toString());
	}

	@Test
	public void sqlPageTest() {
		int count = sqlMapper.selectCount(SQL);
		PageHelper.startPage(1, 100, false);

		List<Map<String, Object>> countInfo = sqlMapper.selectList(SQL);
		Assert.assertEquals(count, countInfo.size());
	}
}
