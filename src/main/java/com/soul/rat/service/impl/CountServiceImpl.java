package com.soul.rat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soul.rat.domain.CountDO;
import com.soul.rat.mapper.CountMapper;
import com.soul.rat.service.CountService;
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
