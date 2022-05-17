package com.yxjr.sqlitedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxjr.sqlitedemo.entity.SelectVo;
import com.yxjr.sqlitedemo.entity.TaskInfo;
import com.yxjr.sqlitedemo.entity.TaskInfoVO;

/**
 * (TaskInfo)表服务接口
 *
 * @author makejava
 * @since 2022-05-07 14:12:52
 */
public interface TaskInfoService extends IService<TaskInfo> {
    boolean saveTaskInfo(TaskInfoVO taskInfoVO);

    int count(SelectVo selectVo);
}

