/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: AuctionImpl
 * Author:   Administrator
 * Date:     2020/11/14 12:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wangpeng.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.AuctionVO;
import com.wangpeng.service.AuctionService;
import com.wangpeng.utils.HttpUtil;
import com.wangpeng.utils.RequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020/11/14
 * @since 1.0.0
 */
@Service
public class AuctionImpl implements AuctionService {


    @Override
    public SysResult start(AuctionVO auctionVO) {
        // 验证金币不能超出200
        if (Integer.valueOf(auctionVO.getCurrentYuanBaoNum()) > 200) {
            return new SysResult(200, "金币不能大于200!", true);
        }
        // 验证账号密码是否正确
        System.out.println("开始竞价--------------------------------------");
        // 验证账号密码是否正确
        String token = RequestUtil.login(auctionVO.getUserName(), auctionVO.getUserPwd());
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        System.out.println("登录入参数-----" + auctionVO + dateFormat.format(new Date()));

        // 去掉手机网页中的m.
        auctionVO.setPropUrl(auctionVO.getPropUrl().replace("m.", ""));

        String adPositionId = auctionVO.getAdPositionId();
        String currentYuanBaoNum = auctionVO.getCurrentYuanBaoNum();
        String showObjectValue = "";
        String propId = "";
        String propName = "";
        String propUrl = auctionVO.getPropUrl();
        String estateName = "";

        // 获取房源信息值
        List<Map<String, String>> myHouseList = RequestUtil.myHouse(token);
        for (Map<String, String> myHouse : myHouseList) {
            if (propUrl.equalsIgnoreCase(myHouse.get("PropUrl"))) {
                showObjectValue = myHouse.get("AdsNo");
                propId = myHouse.get("PropId");
                propName = myHouse.get("PropName");
                estateName = myHouse.get("EstateName");
                break;
            }
        }
        // 没有获取到房源信息
        if ("" == showObjectValue || "" == propId || "" == propName) {
            return new SysResult(200, "房源未找到，重新录入", true);
        }

        // 执行竞价房源请求N次
        List<String> resultList = RequestUtil.auction(token, adPositionId, currentYuanBaoNum, showObjectValue, propId, propName, propUrl, estateName);
        return new SysResult(200, resultList.toString(), true);
    }


    @Override
    public SysResult favorite(AuctionVO auctionVO) {
        System.out.println("收藏夹竞标------------------------------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        System.out.println("请求入参数----------" + auctionVO + dateFormat.format(new Date()));
        // 验证账号密码是否正确
        String token = RequestUtil.login(auctionVO.getUserName(), auctionVO.getUserPwd());
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }

        // 获取收藏夹排序的信息
        String id = "";
        String favoriteStr = HttpUtil.httpGet("https://sz.centanet.com/partner/jifen/AdPosition/GetMyAdPreItems?pageIndex=1&pageSize=10", token);
        JSONObject favoriteult = JSONObject.parseObject(favoriteStr);
        Map<String, Object> resultMap = (Map<String, Object>) favoriteult.get("data");
        List<Map<String, Map<String,Object>>> favoriteList = (List<Map<String, Map<String, Object>>>) resultMap.get("list");
        Map<String, Map<String, Object>> map1 = favoriteList.get(Integer.valueOf(auctionVO.getAdPositionId()) - 1);
        Map<String, Object> map2 = map1.get("AdPositionCompetePre");
        Object id1 = map2.get("Id");
        id = id1.toString();
        System.out.println("收藏夹id为：" + id + map2.get("AdName"));


        // 获取验证码图片base64
        String base64Result = HttpUtil.httpGet("https://sz.centanet.com/partner/jifen/My/GetVerifyCodeBase64", token);
        JSONObject base64Object = JSONObject.parseObject(base64Result);
        String base64 = (String) base64Object.get("data");
        //你的用户名(图鉴网，识别验证码，http://www.ttshitu.com/user/index.html?spm=null)
        String username = "qq761091243";
        //你的密码
        String password = "19961314520";
        //图片转换过的base64编码
        String image = base64;
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("password", password);

        obj.put("image", image);
        try {
            String url = "http://api.ttshitu.com/base64";
            String ret = HttpUtil.httpRequestData(url, obj);
            JSONObject jsonObject = JSONObject.parseObject(ret);
            if (jsonObject.getBoolean("success")) {
                String resultCode = jsonObject.getJSONObject("data").getString("result");
                System.out.println("识别成功结果为:" + resultCode);

                //  竞标
                String url1 = "https://sz.centanet.com/partner/jifen/AdPosition/AuctionNewPre?id="+id+"&verifyCode="+resultCode;
                String resultJB = HttpUtil.httpGet(url1, token);
                System.out.println(resultJB);
                return new SysResult(200,resultJB,true);

            } else {
                System.out.println("识别失败原因为:" + jsonObject.getString("message"));
                return new SysResult(200,"验证码识别失败",true);
            }
        } catch (Exception e) {
            System.out.println("识别失败异常:");
            return new SysResult(200,"验证码识别异常",true);
        }

    }

    @Override
    public SysResult login(AuctionVO auctionVO) {
        System.out.println("登录保存token到redis-------------------------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        System.out.println("登录入参数----------" + auctionVO + dateFormat.format(new Date()));
        // 验证账号密码是否正确
        String token = RequestUtil.login(auctionVO.getUserName(), auctionVO.getUserPwd());
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }
        return null;
    }
}