package com.yenroc.ho.blogic.java.demo;

import com.github.pagehelper.PageHelper;
import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
import com.yenroc.ho.blogic.restDto.demo.DemoResp;
import com.yenroc.ho.blogic.restDto.demo.DemoRespS01;
import com.yenroc.ho.common.bean.PageInfo;
import com.yenroc.ho.common.bean.Pub;
import com.yenroc.ho.common.consts.CommonConsts;
import com.yenroc.ho.common.context.BLogContext;
import com.yenroc.ho.mapper.DemoDao;
import com.yenroc.ho.mapper.entity.Demo;
import com.yenroc.ho.utils.BeanCopierEx;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 测试Mybatis访问数据的效果
 * @author Admin_Vip
 *
 */
@Service("DemoService")
@Transactional(rollbackFor = Exception.class)
public class DemoService {
    
    private static final Logger log = LoggerFactory.getLogger(DemoService.class);
    
    @Autowired
    private DemoDao demoDao;

    public String insert(DemoReqt reqt){
        Demo demo = new Demo();
        BeanCopierEx.copy(reqt, demo);
        if (StringUtils.isBlank(demo.getId())) {
            demo.setId(UUID.randomUUID().toString());
        }
        int insertResult = demoDao.insert(demo);
        if (insertResult == 1) {
            return demo.getId();
        } else {
            log.error("insert error.");
            return "";
        }
    }

    public DemoResp selectAll() throws Exception {
        List<Demo> selectResult = demoDao.selectAll();
        ArrayList<DemoRespS01> list = BeanCopierEx.copy(selectResult, DemoRespS01.class);
        DemoResp result = new DemoResp();
        result.setMybatisDemoList(list);
        return result;
    }

    public DemoRespS01 getById(String id){
        Demo demo = new Demo();
        demo.setId(id);
        List<Demo> selectResult = demoDao.select(demo);
        if (selectResult.size() == 0) {
            return null;
        }
        DemoRespS01 result = new DemoRespS01();
        BeanCopierEx.copy(selectResult.get(0), result);
        return result;
    }
    
    /**
     * 自定义 queryDemo 查询
     * @param reqt
     * @return
     * @throws Exception
     */
    public DemoResp queryDemo(DemoReqt reqt) throws Exception {
        Demo demo = new Demo();
        BeanCopierEx.copy(reqt, demo);
        PageInfo pageInfo = (PageInfo)BLogContext.getValue(CommonConsts.PAGE_INFO);
        if (pageInfo != null) {
            Integer pageSize = pageInfo.getPageSize() == null ? 20: pageInfo.getPageSize();
            Integer pageNum = pageInfo.getPageNum() == null ? 1: pageInfo.getPageNum();
            String orderBy = pageInfo.getOrderBy();
            if(orderBy != null && !"".equals(orderBy)) {
                PageHelper.orderBy(orderBy);
            }
            PageHelper.startPage(pageNum, pageSize);

        }
        List<Demo> selectResult = demoDao.queryDemo(demo);
        Pub.setPageInfo(selectResult);
        ArrayList<DemoRespS01> list = BeanCopierEx.copy(selectResult, DemoRespS01.class);
        DemoResp result = new DemoResp();
        result.setMybatisDemoList(list);
        return result;
    }

}
