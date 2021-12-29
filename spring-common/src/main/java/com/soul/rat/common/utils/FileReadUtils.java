package com.soul.rat.common.api.utils;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * 文件读取
 *
 * @author zhujx
 * @date 2021/12/28 11:16
 */
public class FileReadUtils {


    private static InputStream readUrl(String url) throws IOException {
        UrlResource urlResource = new UrlResource(new URL(url));
        return urlResource.getInputStream();
    }

    private static InputStream readFile(String file) throws IOException {
        return FileUtils.openInputStream(new File(file));
    }

    public static String pdfReadUrl(String url) throws IOException {
        return pdfReadStream(readUrl(url));
    }

    public static String pdfReadFile(String file) throws IOException {
        return pdfReadStream(readFile(file));
    }

    public static String pdfReadStream(InputStream inputStream) throws IOException {
        PDDocument document = PDDocument.load(inputStream);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        inputStream.close();
        return text.trim();
    }

    public static String docReadUrl(String url) throws IOException {
        return docReadStream(readUrl(url));
    }

    public static String docReadFile(String file) throws IOException {
        return docReadStream(readFile(file));
    }


    public static String docReadStream(InputStream inputStream) throws IOException {
        XWPFDocument doc = new XWPFDocument(inputStream);
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        StringBuilder stringBuilder = new StringBuilder();
        paragraphs.forEach(o -> stringBuilder.append(o.getText()));
        inputStream.close();
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        String s = pdfReadUrl("http://soul-rat.oss-cn-hangzhou.aliyuncs.com/%E5%B9%BF%E4%B8%9C%E7%9C%81%E5%8C%BB%E7%96%97HIS%E5%AF%B9%E6%8E%A5%E6%96%B9%E6%A1%88.pdf");
        System.out.println(s);
    }
}
