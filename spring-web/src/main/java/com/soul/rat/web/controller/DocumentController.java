package com.soul.rat.web.controller;


import com.soul.rat.biz.config.OssClientConfig;
import com.soul.rat.biz.model.dto.DocumentDTO;
import com.soul.rat.biz.model.qo.PageQuery;
import com.soul.rat.biz.repository.DocumentRepository;
import com.soul.rat.biz.service.DocumentService;
import com.soul.rat.common.api.BaseResult;
import com.soul.rat.common.utils.FileReadUtils;
import com.soul.rat.common.utils.MyUtils;
import com.soul.rat.dal.domain.DocumentDO;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @ApiOperation("上传文件并解析上传es&oss&db")
    @PostMapping("/upload")
    public BaseResult<String> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        BaseResult<String> baseResult = new BaseResult<>();

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
            return baseResult.failed().msg("暂不支持" + suffix + "格式文件");
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

        return baseResult.success().data(baseUrl);
    }

    @ApiOperation("获取信息")
    @GetMapping("/getOne")
    public BaseResult<DocumentDTO> getOne(long id) {
        DocumentDTO documentDTO = documentRepository.findById(id).orElse(null);
        return new BaseResult<>(documentDTO);
    }


    @ApiOperation("模糊查询信息")
    @GetMapping("/page")
    public BaseResult<Page<DocumentDTO>> page(PageQuery pageQuery) {
        PageRequest pageRequest = PageRequest.of(
                pageQuery.getPageNum().intValue() - 1,
                pageQuery.getPageSize().intValue(),
                Sort.by("id"));
        Page<DocumentDTO> page = documentRepository.findAll(pageRequest);
        return new BaseResult<>(page);
    }

    @ApiOperation("关键字查询信息")
    @GetMapping("/pageByKeyword")
    public BaseResult<Page<DocumentDTO>> pageByKeyword(PageQuery pageQuery, String keyword) {
        // 添加查询字段
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", keyword));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content", keyword));

        // 如需构建高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<mark><strong>").postTags("</strong></mark>");
        highlightBuilder.field("title");
        highlightBuilder.field("content");

        //构建查询条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //1.排序
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("id").order(SortOrder.DESC);
        //2.分页
        Pageable pageable = PageRequest.of(pageQuery.getPageNum().intValue() - 1, pageQuery.getPageSize().intValue());

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);
        nativeSearchQueryBuilder.withSort(fieldSortBuilder);
        nativeSearchQueryBuilder.withPageable(pageable);

        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        SearchHits<DocumentDTO> search = elasticsearchRestTemplate.search(query, DocumentDTO.class);


        List<SearchHit<DocumentDTO>> searchHits = search.getSearchHits();

        List<DocumentDTO> documentList = new ArrayList<>();

        for (SearchHit<DocumentDTO> searchHit : searchHits) {
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            //将高亮的内容填充到content中
            searchHit.getContent().setTitle(highlightFields.get("title") == null ? searchHit.getContent().getTitle() : highlightFields.get("title").get(0));
            searchHit.getContent().setContent(highlightFields.get("content") == null ? searchHit.getContent().getContent() : highlightFields.get("content").get(0));
            //放到实体类中
            documentList.add(searchHit.getContent());
        }
        Page<DocumentDTO> data = new PageImpl<>(documentList, pageable, search.getTotalHits());
        return new BaseResult<>(data);
    }

}
