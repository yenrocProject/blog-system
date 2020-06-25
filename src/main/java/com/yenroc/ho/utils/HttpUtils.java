//package com.hyp.demo.utils;
//
//import java.io.BufferedInputStream;
//import java.io.CharArrayWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//public class HttpUtils {
//
//    /**
//     * 获取网页数据
//     * @param urlStr  访问地址
//     * @param params  参数
//     * @param charset 字符编码
//     * @return
//     * @throws Exception
//     */
//    public static String httpGet(String urlStr, Map<String, String> params,String charset) throws Exception {
//        StringBuilder sb = new StringBuilder();
//        if (null != params && params.size() > 0) {
//            sb.append("?");
//            Entry<String, String> en;
//            for (Iterator<Entry<String, String>> ir = params.entrySet().iterator(); ir.hasNext();) {
//                en = ir.next();
//                sb.append(en.getKey() + "=" + URLEncoder.encode(en.getValue(),"utf-8") + (ir.hasNext() ? "&" : ""));
//            }
//        }
//        URL url = new URL(urlStr + sb);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setConnectTimeout(5000);
//        conn.setReadTimeout(5000);
//        conn.setRequestMethod("GET");
//        if (conn.getResponseCode() != 200){
//            throw new Exception("请求异常状态值:" + conn.getResponseCode());
//        }
//        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//        Reader reader = new InputStreamReader(bis,charset);
//        char[] buffer = new char[2048];
//        int len = 0;
//        CharArrayWriter caw = new CharArrayWriter();
//        while ((len = reader.read(buffer)) > -1)
//            caw.write(buffer, 0, len);
//        reader.close();
//        bis.close();
//        conn.disconnect();
//        return caw.toString();
//    }
//
//    /**
//     * 获取网页数据
//     * @param urlStr  访问地址
//     * @param params  参数
//     * @return
//     * @throws Exception
//     */
//    public static String httpGet(String urlStr, Map<String, String> params) throws Exception {
//        StringBuilder sb = new StringBuilder();
//        if (null != params && params.size() > 0) {
//            sb.append("?");
//            Entry<String, String> en;
//            for (Iterator<Entry<String, String>> ir = params.entrySet().iterator(); ir.hasNext();) {
//                en = ir.next();
//                sb.append(en.getKey() + "=" + URLEncoder.encode(en.getValue(),"utf-8") + (ir.hasNext() ? "&" : ""));
//            }
//        }
//        URL url = new URL(urlStr + sb);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setConnectTimeout(5000);
//        conn.setReadTimeout(5000);
//        conn.setRequestMethod("GET");
//        if (conn.getResponseCode() != 200)
//            throw new Exception("请求异常状态值:" + conn.getResponseCode());
//        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//        Reader reader = new InputStreamReader(bis,"gbk");
//        char[] buffer = new char[2048];
//        int len = 0;
//        CharArrayWriter caw = new CharArrayWriter();
//        while ((len = reader.read(buffer)) > -1)
//            caw.write(buffer, 0, len);
//        reader.close();
//        bis.close();
//        conn.disconnect();
//        //System.out.println(caw);
//        return caw.toString();
//    }
//
//
//    /**
//     * 从获得的网页的document中获取指定条件的内容
//     * @param document
//     * @param condition 条件
//     * @return
//     */
//    public static String catchInfomationFromDocument(Document document , String condition , int position){
//
//        if(document != null){
//            Iterator<Element> iterator = document.select(condition).iterator();
//            if(iterator.hasNext()){
//                String str = iterator.next().text();
//                return str.substring(position).trim();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 判断从获得的网页的document中<br/>
//     * 获取指定条件的内容是否存在
//     * @param document
//     * @param condition 条件
//     * @return
//     */
//    public static boolean isExistInfomation(Document document , String condition){
//
//        if(document != null){
//            Iterator<Element> iterator = document.select(condition).iterator();
//            if(iterator.hasNext()){
//                return true;
//            }
//        }
//        return false;
//    }
//}
