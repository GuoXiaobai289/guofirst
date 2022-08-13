package com.qiansheng.reggie.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SMSUtil {
    //获取你的accessKeyId
    @Value("${SMSInf.accessKeyId}")
    private String accessKeyId;
    //获取你的accessKeySecret
    @Value("${SMSInf.accessKeySecret}")
    private String accessKeySecret;
    //获取你的短信模板CODE
    @Value("${SMSInf.TemplateCode}")
    private String TemplateCode;
    //获取你的短信签名名称
    @Value("${SMSInf.SignName}")
    private String SignName;
    /**
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public  void Main(String codeParam,String PhoneNumbers) throws Exception {
        Client client = SMSUtil.createClient(accessKeyId, accessKeySecret);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(PhoneNumbers)
                .setSignName(SignName)
                .setTemplateCode(TemplateCode)
                .setTemplateParam("{\"code\":" + codeParam+ "}");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
    }

    /**
     * @return 返回随机生成的验证码
     */
    public String NumberFour() {
        int TemplateParam;
        double random;
        random = Math.random();
        if (random > 0.1) {
            random = random * 10000;
        } else {
            random = random * 100000;
        }
        TemplateParam = (int) Math.floor(random);
        String coadParam = String.valueOf(TemplateParam);
        return coadParam;
    }
}
