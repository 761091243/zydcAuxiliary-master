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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangpeng.config.SysResult;
import com.wangpeng.mapper.UserPowerLogMapper;
import com.wangpeng.mapper.UserPowerMapper;
import com.wangpeng.pojo.AuctionVO;
import com.wangpeng.pojo.UserPower;
import com.wangpeng.pojo.UserPowerLog;
import com.wangpeng.service.AuctionService;
import com.wangpeng.utils.HttpUtil;
import com.wangpeng.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private static final Logger log = LoggerFactory.getLogger(AuctionImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserPowerMapper userPowerMapper;
    @Autowired
    private UserPowerLogMapper userPowerLogMapper;

    private static final String IS_AUCTION = "Auction:";

    @Override
    public SysResult start(AuctionVO auctionVO) {
        // 验证金币不能超出200
        if (Integer.valueOf(auctionVO.getCurrentYuanBaoNum()) > 200) {
            return new SysResult(200, "金币不能大于200!", true);
        }
        // 验证账号密码是否正确
        log.info("开始竞价--------------------------------------");
        // 验证账号密码是否正确
        String token = RequestUtil.login(auctionVO.getUserName(), auctionVO.getUserPwd());
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }

        log.info("-----登录入参数={}",auctionVO.toString());

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
    public SysResult favorite(AuctionVO vo) {
        log.info("收藏夹竞标------请求入参数={}",vo.toString());
        if (StringUtils.isEmpty(vo.getUserName()) || StringUtils.isEmpty(vo.getUserPwd())){
            return new SysResult(200,"请输入账号密码！",true);
        }
        if (StringUtils.isEmpty(vo.getAdPositionId())){
            return new SysResult(200,"请输入收藏夹排序号！",true);
        }
        Object tokenValue = redisTemplate.opsForValue().get(vo.getUserName());
        if (StringUtils.isEmpty(tokenValue)){
            return new SysResult(200,"请先登录！",true);
        }
        String token = tokenValue.toString();

        // 获取收藏夹排序的信息
        String id = "";
        String favoriteStr = HttpUtil.httpGet("https://sz.centanet.com/partner/jifen/AdPosition/GetMyAdPreItems?pageIndex=1&pageSize=10", token);
        JSONObject favoriteult = JSONObject.parseObject(favoriteStr);
        Map<String, Object> resultMap = (Map<String, Object>) favoriteult.get("data");
        List<Map<String, Map<String, Object>>> favoriteList = (List<Map<String, Map<String, Object>>>) resultMap.get("list");
        if (Integer.parseInt(vo.getAdPositionId()) > favoriteList.size()){
            return new SysResult(200,"请输入正确的收藏夹排序号！",true);
        }
        Map<String, Map<String, Object>> map1 = favoriteList.get(Integer.parseInt(vo.getAdPositionId()) - 1);
        Map<String, Object> map2 = map1.get("AdPositionCompetePre");
        Object id1 = map2.get("Id");
        id = id1.toString();
        log.info("收藏夹id={},AdName={}",id,map2.get("AdName"));

        // 是否已竞拍过
        String isAuction = (String) redisTemplate.opsForValue().get(IS_AUCTION + vo.getUserName() + id);
        if (!StringUtils.isEmpty(isAuction)){
            log.info("已竞拍成功,此次不会覆盖");
            return new SysResult(200, "已竞拍成功,此次不会覆盖", true);
        }

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
        log.info("图片base64={}",image);
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("password", password);
        obj.put("typeid", "1001");
        obj.put("image", image);
        try {
            // 图鉴
            String url = "http://api.ttshitu.com/base64";
            String ret = HttpUtil.httpRequestData(url, obj);
            JSONObject jsonObject = JSONObject.parseObject(ret);
            if (jsonObject.getBoolean("success")) {
                String resultCode = jsonObject.getJSONObject("data").getString("result");
                log.info("识别成功结果为={}" + resultCode);

                //  竞标
                String url1 = "https://sz.centanet.com/partner/jifen/AdPosition/AuctionNewPre?id=" + id + "&verifyCode=" + resultCode;
                String resultJB = HttpUtil.httpGet(url1, token);

                // 记录成功 {"code":0,"msg":"success","data":{"IsSuccess":true,"Reamark":"参与成功，将有机会拍得此广告位！"}}
                JSONObject jbJson = JSON.parseObject(resultJB);
                if (jbJson.getJSONObject("data").getBoolean("IsSuccess")){
                    log.info("-------竞拍成功");
                    redisTemplate.opsForValue().set(IS_AUCTION + vo.getUserName() + id, resultJB, 1, TimeUnit.HOURS);
                }

                // 添加竞标记录
                UserPower userPower = userPowerMapper.selectOne(new QueryWrapper<UserPower>().eq("user_name", vo.getUserName()));
                UserPowerLog userPowerLog = new UserPowerLog();
                userPowerLog.setUserId(userPower.getId());
                userPowerLog.setUserNick(userPower.getUserNick());
                userPowerLog.setDetails(resultJB);
                userPowerLogMapper.insert(userPowerLog);
                return new SysResult(200, resultJB, true);

            } else {
                log.error("识别失败原因为={}" + jsonObject.getString("message"));
                return new SysResult(200, "验证码识别失败", true);
            }
        } catch (Exception e) {
            log.error("识别失败异常:");
            return new SysResult(200, "验证码识别异常", true);
        }

    }

    @Override
    public SysResult login(AuctionVO vo) {
        log.info("--------登录入参数={}" , vo.toString());
        if (StringUtils.isEmpty(vo.getUserName()) || StringUtils.isEmpty(vo.getUserPwd())){
            return new SysResult(200,"请输入账号密码！",true);
        }
        // 是否有权限
        UserPower userPower = userPowerMapper.selectOne(new QueryWrapper<UserPower>().eq("user_name", vo.getUserName()));
        if (StringUtils.isEmpty(userPower)){
            log.info("没有权限--------");
            return new SysResult(200, "没有权限，请联系管理员QQ:761091243", true);
        }
        // 验证账号密码是否正确
        String token = RequestUtil.login(vo.getUserName(), vo.getUserPwd());
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }
        // 添加token到redis（key是用户名）
        redisTemplate.opsForValue().set(vo.getUserName(), token, 1, TimeUnit.HOURS);
        log.info("登录成功---------------");
        return new SysResult(200, "登录成功，有效期为1个小时", true);
    }
}