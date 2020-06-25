package com.yenroc.ho.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 描述: 对象,集合与xml进行转换的util
 * @author 和彦鹏
 * @date 2018年7月22日21:54:03
 * @version v0.1
 */
@Service
public class XmlUtil {

    private static Logger log = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将xml字符串转化为CommonObjectReqtM01对象
     * @param <T>
     * @param xmlStr xml的字符串
     * @return CommonObjectReqtM01 对象后台输入dto
     * @throws Exception 转换过程中发生的异常
     */
    public <T> T getObjectByXml(String xmlStr, Class<T> clazz) throws Exception {
        if(StringUtils.isBlank(xmlStr)) {
            return null;
        }
        XmlMapper xmlMapper = new XmlMapper();
        //忽略不能解析的字段
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.setDateFormat(new SimpleDateFormat(DateUtil.getDatePattern()[1]));
        // 将xmlStr进行格式化，去掉空格等多余的字符
        OutputFormat formater = OutputFormat.createCompactFormat();
        Document document = readRootDocument(xmlStr);
        StringWriter out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, formater);
        try {
            writer.write(document);
            T t = xmlMapper.readValue(out.toString(), clazz);
            return t;
        } catch (IOException e) {
            log.error(MessageFormat.format("xml文件转对象失败[message={0}]：[xmlStr={1}]", e.getMessage() ,xmlStr), e);
            throw e;
        }
        finally {
            writer.close();
        }
    }
    
    /**
     * 将xml字符串转化为ArrayList<T>
     * @param <T>
     * @param xmlStr xml的字符串
     * @return ArrayList<T> 对象后台输入dto
     * @throws Exception 转换过程中发生的异常
     */
    @SuppressWarnings("unchecked")
	public <T> ArrayList<T> getGenericObjectByXml(String xmlStr, Class<T> clazz) throws Exception {
        if(StringUtils.isBlank(xmlStr)) {
            return null;
        }
        XmlMapper xmlMapper = new XmlMapper();
        JavaType javaType = xmlMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);  
        //忽略不能解析的字段
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.setDateFormat(new SimpleDateFormat(DateUtil.getDatePattern()[1]));
        // 将xmlStr进行格式化，去掉空格等多余的字符
        OutputFormat formater = OutputFormat.createCompactFormat();
        Document document = readRootDocument(xmlStr);
        StringWriter out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, formater);
        try {
            writer.write(document);
            ArrayList<T> t = (ArrayList<T>)xmlMapper.readValue(out.toString(), javaType);
            return t;
        } catch (IOException e) {
            log.error(MessageFormat.format("xml文件转对象失败[message={0}]：[xmlStr={1}]", e.getMessage() ,xmlStr), e);
            throw e;
        }
        finally {
            writer.close();
        }
    }
    
    /**
     * 将对象转化为xml文件
     * @param object 要被转化的对象
     * @return String 转化结果,xml字符串
     */
    public String transferXmlToStr(Object object) throws BizLogicException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setDateFormat(new SimpleDateFormat(DateUtil.getDatePattern()[1]));
            String xmlStr =  xmlMapper.writeValueAsString(object);
            return xmlStr;
        } catch (JsonProcessingException e) { 
            log.error(MessageFormat.format("对象转化成xml字符串失败，object对象：{0}", object), e);
            BizLogicException bizLogicException = new BizLogicException(new SystemMessage("XmlUtilMSGER01", MessageFormat.format("对象转化成xml字符串失败，object对象：{0}", object)));
            throw bizLogicException;
        }
    }
    
    public String getTextByXPath(String xmlStr, String xPath) throws BizLogicException {
        //根据xpath获取节点
        Element element = getSingleElementByXPath(xmlStr, xPath);
        if(element == null) {
            return null;
        }
        else {
            return element.getText();
        }
    }
    
    public String getXmlByXPath(String xmlStr, String xPath) throws BizLogicException {
        //根据xpath获取节点
        Element element = getSingleElementByXPath(xmlStr, xPath);
        if(element == null) {
            return null;
        }
        else {
            return element.asXML();
        }
    }
    
    /**
     * 根据xml字符串和xpath获取单个节点
     * @param xmlStr xml字符串
     * @param xPath xPath
     * @return Element xml节点
     */
    public Element getSingleElementByXPath(String xmlStr, String xPath) throws BizLogicException {
        Document rootDocument = readRootDocument(xmlStr);
        if(rootDocument == null) {
            return null;
        }
        return (Element)rootDocument.selectSingleNode(xPath);
    }
    
    /**
     * 设置xml节点的值，并返回字符串
     * @param xmlStr xml字符串
     * @param xPath xPath
     * @param value 要设置的节点的值
     * @return Element xml节点
     */
    public String setValueForXpath(String xmlStr, String xPath, String value) throws BizLogicException {
        Document rootDocument = readRootDocument(xmlStr);
        if(rootDocument == null) {
            return null;
        }
        Element element = (Element)rootDocument.selectSingleNode(xPath);
        //如果element为空，则创建一个节点
        if(element == null) {
            element = rootDocument.getRootElement().addElement(xPath.replace("//", ""));
        }
        if(StringUtils.isNoneBlank(value)) {
            element.setText(value);
        }
        return rootDocument.asXML();
    }
    
    private static Document readRootDocument(String xmlStr) throws BizLogicException {
        SAXReader saxReader = new SAXReader();
        try {
            saxReader.setValidation(false);
            //参考网页：http://www.thinksaas.cn/topics/0/166/166282.html
            saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            InputStream is = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
            
            Document rootDocument = saxReader.read(is);
            return rootDocument;
        } catch (DocumentException e) {
            log.error(MessageFormat.format("xml字符串转成xml对象出错[xmlStr={0}]", xmlStr), e);
            throw new BizLogicException(new SystemMessage("XmlUtilMSG01", MessageFormat.format("xml字符串转成xml对象出错[xmlStr={0}]", xmlStr)));
        } catch (SAXException e) {
            log.error(MessageFormat.format("根据xml字符串和xpath获取节点出错[xmlStr={0}]", xmlStr), e);
            throw new BizLogicException(new SystemMessage("XmlUtilMSG01", MessageFormat.format("根据xml字符串和xpath获取节点出错[xmlStr={0}]", xmlStr)));
        } catch (UnsupportedEncodingException e) {
            log.error(MessageFormat.format("xml字符串转成xml对象出错，不支持的格式转换[xmlStr={0}]", xmlStr), e);
            throw new BizLogicException(new SystemMessage("XmlUtilMSG01", MessageFormat.format("xml字符串转成xml对象出错，不支持的格式转换[xmlStr={0}]", xmlStr)));
        }
    }
}
