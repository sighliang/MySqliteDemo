package com.yxjr.sqlitedemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.sqlitedemo.dao.DeviceInfoDao;
import com.yxjr.sqlitedemo.entity.DeviceInfo;
import com.yxjr.sqlitedemo.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * (DeviceInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-05-11 14:09:02
 */
@Service("deviceInfoService")
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoDao, DeviceInfo> implements DeviceInfoService {

    @Autowired
    DeviceInfoDao deviceInfoDao;


    @Override
    public DeviceInfo queryById(Integer id) {
        return deviceInfoDao.queryById(id);
    }


    @Override
    public int insert(DeviceInfo deviceInfo) {
        deviceInfo.setVisitTime(new Date());
        deviceInfo.setCreateTime(new Date());
        return deviceInfoDao.insert(deviceInfo);
    }

    @Override
    public boolean update(DeviceInfo deviceInfo) {
        return deviceInfoDao.update(deviceInfo);
    }

    @Override
    public boolean deleteById(Integer id) {
        return deviceInfoDao.deleteById(id);
    }

    @Override
    public DeviceInfo getByDevId(String devId) {
        return deviceInfoDao.queryByDevId(devId);
    }

    @Override
    public boolean updateVisitTime(DeviceInfo deviceInfo) {
        return deviceInfoDao.updateVisitTime(deviceInfo);
    }
}

