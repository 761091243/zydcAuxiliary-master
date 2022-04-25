package com.wangpeng;

import com.alibaba.fastjson.JSONObject;
import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.AuctionVO;
import com.wangpeng.service.impl.AuctionImpl;
import com.wangpeng.utils.HttpUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ZydcAuxiliary2Application.class})
class ZydcAuxiliary2ApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AuctionImpl auctionService;

    private static final String IS_AUCTION = "Auction:";

    @Test
    void contextLoads() throws Exception {
   /*     String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvc3BHzxqkw6byAAeoxA6mGrkSjkGePof5pBgaPsPD08jO4/A25SQ/bgOQNbge+E6FEWtlKcKDTsxUqb3GIi8tVffVAWLWzOTtKms9zEagCQboxGOGs2TuCHsC0fdsiDy7Tfdc+i8n9NB3a+wxYtBCRTu+ddudJvlEFZRkDRONA35Zc84SU7FbG5pWNYlT7uE3VGfCHpuDya7gmtFlU9mHzp5yKMsaN+pvfZc37ewKhpBTwSgIKQOoinGYIBhk2Lp7U5BOTQxFeTW99gvWs6tj+AT81ViORWOKRUlzm60XD492yv4JLb41cFn1YNz86n2JWNLu0JoUYVce6Tjgm5QfAgMBAAECggEBAI8h9PKQMaNC/jok7hqvP2ghGuTZawzG5LUzOnqGw7xJVWP7LmgKTaT1gjCusnOJjL0gNeYEyGvI6AcVpv0xvB0XKiT6iYDFDOmylmSG9wLt+lYAiVVFmsxZ3MizNE+1YR5I/8k5TSkinsMeJ0Uu4Ml/o7rAeZZeSjJSDIoG5PE/D9yAdbpxudj8eVvbPWpuPbdtWFGS/JCnOYB0EgVZhFbLcCKtuIyjcRiMxHL9xVPBrFmScCmra0tKQ4rgzbRpophvIFw1sRmBFxAYKNt5AmGvI/1zS16McfBjMPUy9NwKKNxAa3REiw6rpZd/45JzRupajyr0SL9ir0t1LxJNLIECgYEA9WA6mrNY+QnC00xWodOMmiQQkBx162lVbLv85NJqjx6a7edNnIUUKIBZcj9Y9uv89IDlKl8DyyXxloTvlLwwr8G3ZLKXGkx3xVKIGFOOYWRlKc0JkO2iDE/i289vBVDysnz61uFT/e4TVV3EJVIOYmLFGkvptSB7/qzylVhZ798CgYEAtwwnhE5aRrqqgVmNf8lMyGVfVlovqn+DsxLjaGxDaRUgmXe1fQLgDdE3Oa5Zg/jaxI9xWsajRkcNrHw8V9TiGTkrIJCqBWqiC9uAJElUO6n7ghmMfFf85rsIXGlclHkF4WI/KduR82awZKvPqwoUB72+C9aLiY2A3fjx1+JF48ECgYBxO3Xv2+GsEuAQKAM2bGxO2WO3TRrxhstAAURDyAIah5IRRRMgrpzNz+T2/tqsH9y8bBrUwcH7ZkglILV6mpmREcDyvCNUpIME0QAy0aQ7t1ujvZWzhqWoLtlOfFwMRP2qdlg+/v6GiO3tD/crDcHLjy7L4so3poTx0pejaYEfWwKBgCSgCPQfHU8ToHQbMAXGiFd8eRP1HZ52vypAeEH3j5FiCdDLNAQcZCo/l1YyPXT7gQJdVSf9oMO6CdFeVlCYpti3KJrudyZ4TI4k0EHybik1/L3uUL68eDzOZYDgEAchI/0RxGOirFRQUrEB855ceN+HppQFzIusRYGkgvdYLfXBAoGBAOOoVpxpNWou6UK17ej/RN568cBWhrmof+u8Gpk948KoGBqfX+z4Ec2zOkp6CmeAqR50hW8hZPdqJSncdvvvnR9A8Rf5IODgcVqy9qdpGzhZmwz0hN7jnVMWsQxh14sX77FEk1+je78df7o0nFTyGTWLowY8dICZT8+hdt0znE3f";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr3NwR88apMOm8gAHqMQOphq5Eo5Bnj6H+aQYGj7Dw9PIzuPwNuUkP24DkDW4HvhOhRFrZSnCg07MVKm9xiIvLVX31QFi1szk7SprPcxGoAkG6MRjhrNk7gh7AtH3bIg8u033XPovJ/TQd2vsMWLQQkU7vnXbnSb5RBWUZA0TjQN+WXPOElOxWxuaVjWJU+7hN1Rnwh6bg8mu4JrRZVPZh86ecijLGjfqb32XN+3sCoaQU8EoCCkDqIpxmCAYZNi6e1OQTk0MRXk1vfYL1rOrY/gE/NVYjkVjikVJc5utFw+Pdsr+CS2+NXBZ9WDc/Op9iVjS7tCaFGFXHuk44JuUHwIDAQAB";
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2021002140610699",privateKey,"json","GBK",publicKey,"RSA2");
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"3142321423432\"," +
                "\"order_id\":\"20160627110070001502260006780837\"" +
                "  }");
        AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
*/
    }

    /**
     * 测试图鉴接口时长
     */
    @Test
    public void demo2() throws IOException {
        long startTime = System.currentTimeMillis();
        String username = "qq761091243";
        //你的密码
        String password = "19961314520";
        //图片转换过的base64编码
        String image = "R0lGODlhUAAjAPcAAAAAAAAAMwAAZgAAmQAAzAAA/wArAAArMwArZgArmQArzAAr/wBVAABVMwBVZgBVmQBVzABV/wCAAACAMwCAZgCAmQCAzACA/wCqAACqMwCqZgCqmQCqzACq/wDVAADVMwDVZgDVmQDVzADV/wD/AAD/MwD/ZgD/mQD/zAD//zMAADMAMzMAZjMAmTMAzDMA/zMrADMrMzMrZjMrmTMrzDMr/zNVADNVMzNVZjNVmTNVzDNV/zOAADOAMzOAZjOAmTOAzDOA/zOqADOqMzOqZjOqmTOqzDOq/zPVADPVMzPVZjPVmTPVzDPV/zP/ADP/MzP/ZjP/mTP/zDP//2YAAGYAM2YAZmYAmWYAzGYA/2YrAGYrM2YrZmYrmWYrzGYr/2ZVAGZVM2ZVZmZVmWZVzGZV/2aAAGaAM2aAZmaAmWaAzGaA/2aqAGaqM2aqZmaqmWaqzGaq/2bVAGbVM2bVZmbVmWbVzGbV/2b/AGb/M2b/Zmb/mWb/zGb//5kAAJkAM5kAZpkAmZkAzJkA/5krAJkrM5krZpkrmZkrzJkr/5lVAJlVM5lVZplVmZlVzJlV/5mAAJmAM5mAZpmAmZmAzJmA/5mqAJmqM5mqZpmqmZmqzJmq/5nVAJnVM5nVZpnVmZnVzJnV/5n/AJn/M5n/Zpn/mZn/zJn//8wAAMwAM8wAZswAmcwAzMwA/8wrAMwrM8wrZswrmcwrzMwr/8xVAMxVM8xVZsxVmcxVzMxV/8yAAMyAM8yAZsyAmcyAzMyA/8yqAMyqM8yqZsyqmcyqzMyq/8zVAMzVM8zVZszVmczVzMzV/8z/AMz/M8z/Zsz/mcz/zMz///8AAP8AM/8AZv8Amf8AzP8A//8rAP8rM/8rZv8rmf8rzP8r//9VAP9VM/9VZv9Vmf9VzP9V//+AAP+AM/+AZv+Amf+AzP+A//+qAP+qM/+qZv+qmf+qzP+q///VAP/VM//VZv/Vmf/VzP/V////AP//M///Zv//mf//zP///wAAAAAAAAAAAAAAACH5BAEAAPwALAAAAABQACMAAAj/AAHsA0CQ4MCCAhEmRHiwYEODCh8udBixIsOK+zJq3Mixo8ePIEOKHGlwpMmTKFN6LKmypcuXGVnCnEkTpMCaOHNqlKmz58ubPhvqVAh0ZVGTDm0SrUk0aUeeIiMabTqT4cOPUJVa5bhVaNSpXJ1uPBqSKtanWXc6nYiW7MGTZuGmVdsUqtixSOPmdRv2IlijJCneLTt4atquY/nShVgY79W9bvWqjZq08WLLfRVLHkiZJWaJKBE7jnl3rlSvnRUnnvt3dOaLPPl+fhxabNa6dUlHZi16b2LDuCvfNj0bq0ziwUsO7k3bONnSLUvbPTy7K2LWrRdrb6t69WbOKQvjN3Ye/jv2ts6Zx3Sp/nzfoD3d/4Y/lL59uffzl9XPP2z//28ByN9SFglmIGMHToTgggo2CBpBAQEAOw==";
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("password", password);
        //一、图片文字类型(默认 3 数英混合)：
        //1 : 纯数字
        //1001：纯数字2
        //2 : 纯英文
        //1002：纯英文2
        //3 : 数英混合
        //1003：数英混合2
        //4 : 闪动GIF
        //7 : 无感学习(独家)
        //11 : 计算题
        //1005:  快速计算题
        //16 : 汉字
        //32 : 通用文字识别(证件、单据)
        //66:  问答题
        //49 :recaptcha图片识别 参考 https://shimo.im/docs/RPGcTpxdVgkkdQdY
        //二、图片旋转角度类型：
        //29 :  旋转类型

        //三、图片坐标点选类型：
        //19 :  1个坐标
        //20 :  3个坐标
        //21 :  3 ~ 5个坐标
        //22 :  5 ~ 8个坐标
        //27 :  1 ~ 4个坐标
        //48 : 轨迹类型
        //四、缺口识别
        //18：缺口识别
        //五、拼图识别
        //53：拼图识别
        obj.put("typeid", "1001");
        obj.put("image", image);
        String url = "http://api.ttshitu.com/base64";
        String ret = HttpUtil.httpRequestData(url, obj);

        System.out.println(ret);

        long endTime = System.currentTimeMillis();
        System.out.println("用时：" + (endTime - startTime));
    }


    @Test
    public void redisDemo1(){
        redisTemplate.opsForValue().set(IS_AUCTION + "18820241680" + "666","cg",1, TimeUnit.HOURS);
    }

    @Test
    public void dee(){
        SysResult favorite = auctionService.favorite(new AuctionVO());

    }

}
