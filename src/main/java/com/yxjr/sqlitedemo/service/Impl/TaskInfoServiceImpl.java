package com.yxjr.sqlitedemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.sqlitedemo.dao.TaskInfoDao;
import com.yxjr.sqlitedemo.entity.DeviceInfo;
import com.yxjr.sqlitedemo.entity.SelectVo;
import com.yxjr.sqlitedemo.entity.TaskInfo;
import com.yxjr.sqlitedemo.entity.TaskInfoVO;
import com.yxjr.sqlitedemo.service.DeviceInfoService;
import com.yxjr.sqlitedemo.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * (TaskInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-05-07 14:12:52
 */
@Service("taskInfoService")
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoDao, TaskInfo> implements TaskInfoService {

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    TaskInfoDao taskInfoDao;

    @Override
    public boolean saveTaskInfo(TaskInfoVO taskInfoVO) {
        try{
           String devId=taskInfoVO.getDevId();
           //自动注册逻辑，查询设备编号是否存在，若不存在，注册，若存在，更新访问时间
           DeviceInfo deviceInfo = deviceInfoService.getByDevId(devId);
           if(deviceInfo == null){
               deviceInfo = new DeviceInfo();
               deviceInfo.setDevId(devId);
               deviceInfo.setBranchId(taskInfoVO.getBranchId());
               deviceInfo.setBranchName(taskInfoVO.getBranchName());
               deviceInfo.setCreateTime(new Date());
               deviceInfo.setVisitTime(new Date());
               deviceInfo.setIp(taskInfoVO.getIp());
               deviceInfoService.insert(deviceInfo);
           }else {
               Date lastVisitTime=deviceInfo.getVisitTime();
               Date nowTime=new Date();
               if(nowTime.getTime()-lastVisitTime.getTime()<24*60*60*1000){
                   return false;
               }
               deviceInfo.setVisitTime(new Date());
               deviceInfoService.updateVisitTime(deviceInfo);
           }
           List<TaskInfo> taskInfoList = taskInfoVO.getData();
           if(taskInfoList != null){
               for(TaskInfo taskInfo:taskInfoList){
                   taskInfo.setDevId(taskInfoVO.getDevId());
                   this.save(taskInfo);
               }
           }
           return true;
        }catch (Exception e){
          System.out.println("错误原因为："+e);
            return false;
        }
    }

    @Override
    public int count(SelectVo selectVo) {
        return taskInfoDao.count(selectVo);
    }
}

