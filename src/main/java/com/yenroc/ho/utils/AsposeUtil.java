package com.yenroc.ho.utils;

//import com.aspose.cells.FontConfigs;
//import com.aspose.cells.Workbook;
//import com.aspose.pdf.*;
//import com.aspose.pdf.facades.FormattedText;
//import com.aspose.words.Document;
//import com.aspose.words.FontSettings;
//import com.aspose.words.License;
//import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;

/**
 * TODO  Aspose相关方法封装类
 * @author 和彦鹏
 * @date 2020年11月30日
 * @version 1.0
 */
@Slf4j
@Component
public class AsposeUtil {

    /**
     * 描述：处理Word转PDF，并加水印
     * 1.设置字体文件加载路径.
     * 2.获取aspose授权认证.
     * 3.将word文件转为pdf文件.
     * 4.根据ifAddWaterMark判断是否需要给转换后的pdf文件添加水印.
     * 5.返回处理后的文件流.
     */
    public OutputStream wordToPDF(boolean ifAddWaterMark, String addWatermarkText, InputStream in) throws Exception{
        return null;
    }

    /**
     * 描述：处理PDF加水印
     * 1.获取aspose授权认证.
     * 2.给转换后的pdf文件添加水印.
     * 3.返回处理后的文件流.
     */
    public OutputStream doPDF(String addWatermarkText, InputStream in) throws Exception{
        return null;
    }

    /**
     * 描述：处理Excel转PDF，并加水印
     * 1.设置字体文件加载路径.
     * 2.获取aspose授权认证.
     * 3.将excel文件转为pdf文件.
     * 4.根据ifAddWaterMark判断是否需要给转换后的pdf文件添加水印.
     * 5.返回处理后的文件流.
     */
    public OutputStream excelToPDF(boolean ifAddWaterMark, String addWatermarkText, InputStream in) throws Exception{
        return null;
    }

    /**
     * 描述：pdf添加水印
     * @param pdfDocument
     * @param watermarkText 水印内容,多行用逗号分隔
     * @param out 加上水印后的PDF文件输出流
     */
    public void addWatermark(Document pdfDocument, String watermarkText, OutputStream out) throws Exception{

    }

    /**
     * 获取License
     * @param licenseType:
     *  1--获取PDF的license
     *	2--获取word的license
     * 	3--获取Excel的license
     */
    public boolean getLicense(Integer licenseType) {
        return false;
    }

}
