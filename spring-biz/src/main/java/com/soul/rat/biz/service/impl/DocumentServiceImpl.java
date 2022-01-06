package com.soul.rat.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soul.rat.biz.service.DocumentService;
import com.soul.rat.dal.domain.DocumentDO;
import com.soul.rat.dal.mapper.DocumentDOMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文档记录 服务实现类
 * </p>
 *
 * @author 朱家兴
 * @since 2022-01-05
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentDOMapper, DocumentDO> implements DocumentService {

}
