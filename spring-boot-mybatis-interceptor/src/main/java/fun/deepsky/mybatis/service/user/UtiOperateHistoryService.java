package fun.deepsky.mybatis.service.user;

import com.github.pagehelper.PageInfo;

import fun.deepsky.mybatis.model.UtiOperateHistory;

public interface UtiOperateHistoryService {

    int addUtiOperateHistory(UtiOperateHistory record);

    PageInfo<UtiOperateHistory> findAllUtiOperateHistories(int pageNum, int pageSize);
}
