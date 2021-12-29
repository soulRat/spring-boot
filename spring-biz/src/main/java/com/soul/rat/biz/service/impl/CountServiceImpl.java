package com.soul.rat.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soul.rat.biz.service.CountService;
import com.soul.rat.dal.domain.CountDO;
import com.soul.rat.dal.mapper.CountMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 朱家兴
 * @since 2021-12-17
 */
@Service
public class CountServiceImpl extends ServiceImpl<CountMapper, CountDO> implements CountService {

}
