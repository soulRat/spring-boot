package com.soul.rat.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soul.rat.api.BaseResult;
import com.soul.rat.domain.CountDO;
import com.soul.rat.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 朱家兴
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    private CountService countService;

    @GetMapping("/get/one")
    public BaseResult<?> getOne() {
        LambdaQueryWrapper<CountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(CountDO::getNum)
                .eq(CountDO::getIsDeleted, 0);
        long data = countService.count(queryWrapper);
        return BaseResult.success(data);
    }

}
