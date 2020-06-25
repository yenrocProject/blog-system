package com.yenroc.ho.blogic.restDto.demo;

import java.io.Serializable;
import java.util.ArrayList;

import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.bean.PageInfo;
import lombok.Data;


@Data
public class DemoResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3631604453770834056L;
    
    private ArrayList<DemoRespS01> mybatisDemoList = null;

}
