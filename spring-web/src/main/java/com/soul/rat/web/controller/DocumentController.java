package com.soul.rat.web.controller;


import com.soul.rat.biz.config.OssClientConfig;
import com.soul.rat.biz.dto.DocumentDTO;
import com.soul.rat.biz.repository.DocumentRepository;
import com.soul.rat.biz.service.DocumentService;
import com.soul.rat.common.api.BaseResult;
import com.soul.rat.common.utils.FileReadUtils;
import com.soul.rat.common.utils.MyUtils;
import com.soul.rat.dal.domain.DocumentDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


/**
 * <p>
 * 文档记录 前端控制器
 * </p>
 *
 * @author 朱家兴
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/document")
public class DocumentController {


    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private OssClientConfig ossClientConfig;

    @ApiOperation("上传文件并解析上传es&oss&db")
    @PostMapping("/upload")
    public BaseResult<?> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();

        MyUtils.checkParam(name, "文件名称不存在");

        assert name != null;

        String suffix = name.substring(name.lastIndexOf(".") + 1);

        InputStream inputStream = file.getInputStream();
        String content;
        if (MyUtils.isEquals(suffix, FileReadUtils.DOC_SUFFIX)) {
            content = FileReadUtils.docReadStream(inputStream);
        } else if (MyUtils.isEquals(suffix, FileReadUtils.PDF_SUFFIX)) {
            content = FileReadUtils.pdfReadStream(inputStream);
        } else {
            return BaseResult.failed().msg("暂不支持" + suffix + "格式文件");
        }

        String baseUrl = ossClientConfig.addFile(name, inputStream);

        DocumentDO documentDO = new DocumentDO();
        documentDO.setTitle(name);
        documentDO.setCategory(suffix);
        documentDO.setContent(content);
        documentDO.setImages(baseUrl);
        documentService.save(documentDO);

        DocumentDTO documentDTO = new DocumentDTO();
        BeanUtils.copyProperties(documentDO, documentDTO);

        documentRepository.save(documentDTO);

        return BaseResult.success().data(baseUrl);
    }

    @ApiOperation("获取信息")
    @GetMapping("/getOne")
    public BaseResult<?> getOne(long id) {
        DocumentDTO documentDTO = documentRepository.findById(id).orElse(null);
        return BaseResult.success().data(documentDTO);
    }

}
