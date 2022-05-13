package com.yxjr.sqlitedemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxjr.sqlitedemo.entity.DeviceInfo;
import com.yxjr.sqlitedemo.service.DeviceInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * (DeviceInfo)表控制层
 *
 * @author makejava
 * @since 2022-05-11 14:07:00
 */
@Controller
@RequestMapping("deviceInfo")
public class DeviceInfoController extends ApiController {

    private final String prefix="Device/";
    /**
     * 服务对象
     */
    @Resource
    private DeviceInfoService deviceInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param deviceInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ResponseBody
    public R selectAll(Page<DeviceInfo> page, DeviceInfo deviceInfo) {
        return success(this.deviceInfoService.page(page, new QueryWrapper<>(deviceInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ResponseBody
    public R selectOne(@PathVariable Serializable id) {
        return success(this.deviceInfoService.getById(id));
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return prefix+"add";
    }

    /**
     * 上传更新包
     * @return
     */
    @RequestMapping("/upload")
    public String upload(){
        return prefix+"uploadPackage";
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("deviceInfo",deviceInfoService.getById(id));
        return prefix+"edit";
    }


    /**
     * 新增数据
     *
     * @param deviceInfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ResponseBody
    public R insert(DeviceInfo deviceInfo) {
        return success(deviceInfoService.insert(deviceInfo));
    }


    /**
     * 修改数据
     *
     * @param deviceInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ResponseBody
    public R update(DeviceInfo deviceInfo) {
        return success(deviceInfoService.update(deviceInfo));
    }

    /**
     * 删除数据
     *
     * @param  id
     * @return 删除结果
     */
    @DeleteMapping
    @ResponseBody
    public R deleteById(Integer id) {
        return success(deviceInfoService.deleteById(id));
    }
}

