package fun.deepsky.mybatis.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import fun.deepsky.mybatis.dao.UtiOperateHistoryDao;
import fun.deepsky.mybatis.model.User;
import fun.deepsky.mybatis.model.UtiOperateHistory;
import fun.deepsky.mybatis.service.user.UtiOperateHistoryService;

@Service(value = "utiOperateHistoryService")
public class UtiOperateHistoryServiceImpl implements UtiOperateHistoryService {

	@Autowired
	private UtiOperateHistoryDao utiOperateHistoryDao;

	@Override
	public int addUtiOperateHistory(UtiOperateHistory record) {
		utiOperateHistoryDao.insert(record);
		return 1;
	}

	@Override
	public PageInfo<UtiOperateHistory> findAllUtiOperateHistories(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<UtiOperateHistory> histories = utiOperateHistoryDao.selectAllHistories();
		PageInfo<UtiOperateHistory> result = new PageInfo<UtiOperateHistory>(histories);
		return result;
	}

}
