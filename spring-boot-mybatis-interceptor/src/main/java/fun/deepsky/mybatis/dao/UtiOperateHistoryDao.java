package fun.deepsky.mybatis.dao;

import java.util.List;

import fun.deepsky.mybatis.model.UtiOperateHistory;

public interface UtiOperateHistoryDao {
    int insert(UtiOperateHistory record);
    
    List<UtiOperateHistory> selectAllHistories();
}
