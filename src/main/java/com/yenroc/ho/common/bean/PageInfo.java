package com.yenroc.ho.common.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述：分页的帮助类，分页的时候作为输入必须传进来，分页出去以后需要做为结果返回到界面
 * @version: 1.0.0
 * @author: 和彦鹏
 * @创建时间: 2018年7月22日21:18:32
 */
@Data
@ApiModel
public class PageInfo implements Serializable {
    
        private static final long serialVersionUID = 3470095034362814178L;
        
        /**
         * 当前页码 
         */
        @ApiModelProperty(value="当前页码 ")
        private Integer pageNum;
        
        /**
         * 每页显示的条数 
         */
        @ApiModelProperty(value="每页显示的条数 ")
        private Integer pageSize;
        
        /**
         * 总数量：需要回传 
         */
        @ApiModelProperty(value="总数量：需要回传 ")
        private Long totalRecordCount;

        /**
         * 总页数：需要回传 
         */
        @ApiModelProperty(value="总页数：需要回传")
        private Integer totalPages;
        
        /**
         * 排序条件：多个排序条件需要使用英文,分隔
         * 示例：EMP_ID asc,Birth_Date desc
         */
        @ApiModelProperty(value="排序条件：多个排序条件需要使用英文,分隔")
        private String orderBy;
}
