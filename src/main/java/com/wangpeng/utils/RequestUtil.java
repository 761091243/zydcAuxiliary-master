package com.wangpeng.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengwangpeng
 * @dete 2020/11/12 - 21:24
 */
public class RequestUtil {

    // 登录
    public static String login(String username, String pwd) {
        String loginUrl = "https://sz.centanet.com/partner/jifen/Auth/Login";
        Map<String, String> map = new HashMap<>();
        map.put("UserName", username);
        map.put("UserPwd", pwd);
        String loginJson = JSONObject.toJSONString(map);

        // 发送登录请求
        String loginResultStr = HttpUtil.httpPost(loginUrl, loginJson, "");

        JSONObject loginResult = JSONObject.parseObject(loginResultStr);
        Map<String, String> data = (Map<String, String>) loginResult.get("data");
        String token = data.get("Token");
        return token;
    }

    // 我的房源
    public static List<Map<String, String>> myHouse(String token) {
        String myHouseUrl = "https://sz.centanet.com/partner/jifen/ZhongyuanHouse/GetStaffPostEstates?estateType=0&pageSize=45";

        // 发送请求
        String myHouseResultStr = HttpUtil.httpGet(myHouseUrl, token);
        JSONObject myHouseResult = JSONObject.parseObject(myHouseResultStr);
        Map<String, Object> result = (Map<String, Object>) myHouseResult.get("result");
        List<Map<String, String>> myHouseList = (List<Map<String, String>>) result.get("Result");
        return myHouseList;
    }

    // 竞价房源
    public static List<String> auction(String token, String adPositionId, String currentYuanBaoNum, String showObjectValue,
                               String propId, String propName, String propUrl, String estateName) {
        String auctionUrl = "https://sz.centanet.com/partner/jifen/AdPosition/Auction";
        Map<String, String> map = new HashMap<String, String>();
        map.put("AdPositionId", adPositionId);
        map.put("CurrentYuanBaoNum", currentYuanBaoNum);
        map.put("ShowObjectValue", showObjectValue);
        map.put("PropId", propId);
        map.put("PropName", propName);
        map.put("PropUrl", propUrl);
        map.put("EstateName", estateName);
        String auctionPar = JSONObject.toJSONString(map);

        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            // 发送请求
            String auctionResult = HttpUtil.httpPost(auctionUrl, auctionPar, token);
            arrayList.add(auctionResult + "第" + (i+1) + "次");
            System.out.println(auctionResult + "第" + (i+1) + "次");
            if (auctionResult.contains("已被竞拍") || auctionResult.contains("恭喜")
                    || auctionResult.contains("已结束")  || auctionResult.contains("成功")){

                break;
            }
        }
        arrayList.add("执行完成！");
        return arrayList;
    }

    // 签到
    public static String sign(String token) {
        String loginUrl = "https://sz.centanet.com/partner/jifen/My/Sign";

        // 发送签到请求
        String loginResultStr = HttpUtil.httpPost(loginUrl, "", token);

        JSONObject loginResult = JSONObject.parseObject(loginResultStr);
        Map<String, String> data = (Map<String, String>) loginResult.get("data");
        String reamark = data.get("Reamark");
        return reamark;
    }


}
