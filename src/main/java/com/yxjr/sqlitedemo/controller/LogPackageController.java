package com.yxjr.sqlitedemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxjr.sqlitedemo.common.HttpClienUtil;
import com.yxjr.sqlitedemo.common.UploadConfig;
import com.yxjr.sqlitedemo.entity.LogPackage;
import com.yxjr.sqlitedemo.service.LogPackageService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * (LogPackage)表控制层
 *
 * @author makejava
 * @since 2022-05-09 11:03:24
 */
@RestController
@RequestMapping("logPackage")
public class LogPackageController extends ApiController {

    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UploadConfig uploadConfig;
    /**
     * 服务对象
     */
    @Resource
    private LogPackageService logPackageService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param logPackage 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<LogPackage> page, LogPackage logPackage) {
        return success(this.logPackageService.page(page, new QueryWrapper<>(logPackage)));
    }

    /**
     * 通过devId查询单条数据
     *
     * @param devId 设备编号
     * @return 单条数据
     */
    @GetMapping("{devId}")
    public R selectDev( @PathVariable(name = "devId")String devId) {
        return success(logPackageService.queryByDevId(devId));
    }

    /**
     * 新增数据
     *
     * @param
     * @return 新增结果
     */
    @PostMapping
    @ResponseBody
    public R insert(MultipartHttpServletRequest request) {
        try {
            String devId=request.getParameter("devId");
            if(devId == null || devId == ""){
                return failed("未获取到设备编号");
            }
            MultipartFile file=request.getFile("file");
            String packPath = uploadConfig.getLogPath()+devId;
            String fileName=file.getOriginalFilename();//文件名
            String pathString = null;
            if(file!=null){
                pathString = packPath+"/" + fileName;//new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +
            }else {
                return R.failed("文件为空");
            }
            //包创建
            File pack=new File(packPath);
            if(!pack.getParentFile().exists()){
                pack.getParentFile().mkdirs();
            }
            //文件创建
            File files=new File(pathString);
            //打印查看上传路径
            logger.info("pathString为："+pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), files);
            return success(logPackageService.saveInfo(fileName,devId));
        }catch (Exception e){
            logger.error("上传日志压缩包出现异常"+e);
            return R.failed("运行出现异常，异常原因为：["+e+"]");
        }
    }

    /**
     * 修改数据
     *
     * @param logPackage 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody LogPackage logPackage) {
        return success(this.logPackageService.updateById(logPackage));
    }

    /**
     * 提取设备日志
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("id") Integer id) {
        return success(this.logPackageService.removeById(id));
    }

    @PostMapping("/getLog")
    public R getLog(HttpServletRequest request,
                    HttpServletResponse response,@RequestParam("devId")String devId,@RequestParam("ip")String ip,@RequestParam("datetime") String dateTime){
        try {
            String url="http://"+ip+":8900/getFiles";
            HttpClienUtil httpClienUtil=new HttpClienUtil();
            JSONObject json=new JSONObject();
            json.put("devId",devId);
            json.put("datetime",dateTime);
            String ret= httpClienUtil.jsonPost(url,json);
            JSONObject retJson = JSONObject.fromObject(ret);
            if("OK".equals(retJson.get("retCode"))){
                String fileName=retJson.get("fileName").toString();
                CommonController commonController =new CommonController();
                String rec= commonController.downloadLogFile(request, response,devId,fileName);
                if(rec==null){
                    return success("提取日志成功");
                }
            }
            return R.failed("提取日志失败");
        }catch (Exception e){
            logger.error("转发提取设备日志出现异常"+e);
            return R.failed("转发提取设备日志出现异常"+e);
        }
    }
}

