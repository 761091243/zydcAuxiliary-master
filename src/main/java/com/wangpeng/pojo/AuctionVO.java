/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: AuctionVO
 * Author:   Administrator
 * Date:     2020/11/14 12:17
 * Description: 竞价参数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wangpeng.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈竞价参数〉
 *
 * @author Administrator
 * @create 2020/11/14
 * @since 1.0.0
 */
@Data
public class AuctionVO implements Serializable {

    private String userName;
    private String userPwd;
    // 排名
    private String adPositionId;
    // 金币
    private String currentYuanBaoNum;
    // 房源链接
    private String propUrl;

}