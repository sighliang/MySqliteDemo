package com.yxjr.sqlitedemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.sqlitedemo.common.UploadConfig;
import com.yxjr.sqlitedemo.dao.LogPackageDao;
import com.yxjr.sqlitedemo.entity.LogPackage;
import com.yxjr.sqlitedemo.service.LogPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

/**
 * (LogPackage)表服务实现类
 *
 * @author makejava
 * @since 2022-05-09 11:03:25
 */
@Service("logPackageService")
public class LogPackageServiceImpl extends ServiceImpl<LogPackageDao, LogPackage> implements LogPackageService {


    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UploadConfig uploadConfig;

    @Autowired
    LogPackageDao logPackageDao;

    @Override
    public boolean saveInfo(String logName, String devId) {
        try {
            logger.info("保存上传的日志包数据");
            LogPackage logPackage = new LogPackage();
            logPackage.setLogName(logName);
            logPackage.setUploadTime(new Date());
            logPackage.setDevId(devId);
            InetAddress ip4 = Inet4Address.getLocalHost();
            String url="http://"+ip4.getHostAddress()+":"+uploadConfig.getPort()+"/common/downloadLog/"+devId+"/"+logName;
            logPackage.setUrl(url);
            return this.save(logPackage);
        }catch (Exception e){
            logger.error("保存上传日志数据出现异常，异常原因"+e);
            return false;
        }
    }

    @Override
    public List<LogPackage> queryByDevId(String devId) {
        return logPackageDao.queryByDevId(devId);
    }
}

