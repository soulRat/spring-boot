package com.soul.rat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soul.rat.domain.CountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 朱家兴
 * @since 2021-12-17
 */
@Mapper
public interface CountMapper extends BaseMapper<CountDO> {

}
