//package com.yenroc.ho.rest.file;
//
//import com.yenroc.ho.blogic.java.fileService.FIleService;
//import com.yenroc.ho.common.bean.ResponseResult;
//import com.yenroc.ho.common.bean.SystemMessage;
//import com.yenroc.ho.common.exception.BizLogicException;
//import com.yenroc.ho.mapper.entity.FileInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.constraints.NotNull;
//import java.io.*;
//
//@RequestMapping("/api")
//@Api(value = "file")
//@RestController
//public class FileController {
//
//    private static final Logger log = LoggerFactory.getLogger(FileController.class);
//
//    @Autowired
//    private FIleService fIleService;
//
//
//    @GetMapping("/file/preview/{fileName}")
//    @ApiOperation(value="文件预览接口", notes="文件预览接口")
//    public void preview(@PathVariable @NotNull String fileName, HttpServletResponse response) throws Exception {
//        InputStream in = null;
//        ServletOutputStream sos = null;
//        File file = fIleService.preview(fileName);
//        try {
//            in = new FileInputStream(file);// TODO 中文可能出现乱码
//            sos = response.getOutputStream();
//            byte[] b = new byte[1024];
//            while (in.read(b) != -1) {
//                sos.write(b);    //输出
//            }
//            sos.flush();           //刷新
//        } catch (Exception ex) {
//            log.error("文件预览失败，异常信息=[{}]", ex);
//            ex.printStackTrace();
//        } finally {
//            try {
//                in.close(); //关闭文件读取流，输出流
//                sos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @GetMapping("/file/download/{fileName}")
//    @ApiOperation(value="文件下载", notes="文件下载")
//    public void download(@PathVariable @NotNull String fileName, HttpServletResponse response) throws Exception {
//        // 以流的形式下载文件。
//        FileInfo fileInfo = fIleService.getFileInfo(fileName);
//        InputStream in = null;
//        OutputStream os = null;
//        try {
//            File file = new File(fileInfo.getFileName());
//            in = new BufferedInputStream(new FileInputStream(fileInfo.getFileFullPath()));
//            byte[] buffer = new byte[in.available()];
//            in.read(buffer);
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            response.addHeader("Content-Length", "" + file.length());
//            response.setCharacterEncoding("utf-8");
//            os = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            os.write(buffer);
//            os.flush();
//        } catch (Exception ex) {
//            log.error("文件下载失败，异常信息=[{}]", ex);
//            ex.printStackTrace();
//        } finally {
//            in.close();
//            os.close();
//        }
//
//    }
//
//}
