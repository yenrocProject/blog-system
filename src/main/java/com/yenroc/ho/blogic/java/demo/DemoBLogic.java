package com.yenroc.ho.blogic.java.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yenroc.ho.blogic.sqlDto.demo.DemoSQL02IM;
import com.yenroc.ho.blogic.sqlDto.demo.DemoSQL03IM;
import com.yenroc.ho.common.bean.PageInfo;
import com.yenroc.ho.common.bean.Pub;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.consts.CommonConsts;
import com.yenroc.ho.common.context.BLogContext;
import com.yenroc.ho.common.dao.UpdateDao;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.common.service.BizLogic;
import com.yenroc.ho.common.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
import com.yenroc.ho.blogic.restDto.demo.DemoResp;
import com.yenroc.ho.blogic.restDto.demo.DemoRespS01;
import com.yenroc.ho.blogic.sqlDto.demo.DemoSQL01IM;
import com.yenroc.ho.blogic.sqlDto.demo.DemoSQL01OM;
import com.yenroc.ho.common.dao.QueryDao;
import com.yenroc.ho.utils.BeanCopierEx;


/**
 * BLogic 类不借助mapper/*Dao 的接口查询，直接通过queryDao调用sqlId查询，用来处理复杂的业务逻辑
 * 测试Mybatis访问数据的效果
 * @author hyp
 *
 */
@Service("DemoBLogic")
@Transactional(rollbackFor = Exception.class)
public class DemoBLogic implements BizLogic<DemoReqt, DemoResp> {
    
    private static final Logger log = LoggerFactory.getLogger(DemoBLogic.class);
    
    @Autowired
    private QueryDao queryDao;

    @Autowired
    private UpdateDao updateDao;
    
    @Autowired
    private RedisService redisService;
    
    @Override
    public DemoResp execute(DemoReqt arg0) throws Exception {
        log.debug("DemoBLogic execute start..{}", arg0);
        return null;
    }

    /**
     * 查询
     * @param arg0
     * @return
     * @throws Exception
     */
    public DemoResp executeQuery(DemoReqt arg0) throws Exception {
        log.debug("DemoBLogic executeQuery start..{}", arg0);
        DemoResp result = new DemoResp();
        ArrayList<DemoRespS01> demoList = new ArrayList<DemoRespS01>();
        DemoSQL01IM demoSQL01IM = new DemoSQL01IM();
        BeanCopierEx.copy(arg0, demoSQL01IM);
        // 如果demoId 不是空,则进行id查询
        if (StringUtils.isNotBlank(demoSQL01IM.getId())) {
            DemoSQL01OM mybatisDemo = queryDao.executeForObject("DemoSQL01", demoSQL01IM, DemoSQL01OM.class);
            DemoRespS01 demo = new DemoRespS01();
            if (mybatisDemo != null) {
                BeanCopierEx.copy(mybatisDemo, demo);
                demoList.add(demo);
            }
        } else {
            List<DemoSQL01OM> mybatisDemoList = null;
            PageInfo pageInfo = (PageInfo) BLogContext.getValue(CommonConsts.PAGE_INFO);
            // 有分页信息
            if (pageInfo != null) {
                mybatisDemoList = queryDao.executeForObjectListByPage("DemoSQL01", demoSQL01IM, pageInfo);
                // 设置分页
                Pub.setPageInfo(mybatisDemoList);
            } else {
                mybatisDemoList = queryDao.executeForObjectList("DemoSQL01", demoSQL01IM);
            }
            demoList = BeanCopierEx.copy(mybatisDemoList, DemoRespS01.class);
        }
        result.setMybatisDemoList(demoList);

        return result;
    }

    /**
     * 插入
     * @param arg0
     * @return
     * @throws Exception
     */
    public String executeInsert(DemoReqt arg0) throws Exception {
        DemoSQL02IM sql02 = new DemoSQL02IM();
        BeanCopierEx.copy(arg0, sql02);
        if (StringUtils.isBlank(sql02.getId())) {
            sql02.setId(UUID.randomUUID().toString());
        }
        int sql02Result = updateDao.execute("DemoSQL02", sql02);
        log.info("Demo数据添加受影响行数=[{}].", sql02Result);
        return sql02.getId();
    }

    /**
     * 更新
     * @param arg0
     * @return
     * @throws Exception
     */
    public DemoRespS01 executeUpdate(DemoReqt arg0) throws Exception {
        if (StringUtils.isBlank(arg0.getId())) {
            log.error("demo 数据更新，Id字段不能为空。");
            throw new BizLogicException(new SystemMessage("DemoUpdateError","demo 数据更新，Id字段不能为空。"));
        }
        DemoSQL03IM sql03 = new DemoSQL03IM();
        BeanCopierEx.copy(arg0, sql03);
        int sql03Result = updateDao.execute("DemoSQL03", sql03);
        log.info("Demo数据更新受影响行数=[{}].", sql03Result);
        DemoRespS01 result = new DemoRespS01();
        return result;
    }
}
