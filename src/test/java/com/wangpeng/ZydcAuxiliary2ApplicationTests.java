package com.wangpeng;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZydcAuxiliary2ApplicationTests {

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

}
