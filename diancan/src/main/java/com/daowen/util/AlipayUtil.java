package com.daowen.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


public class AlipayUtil {



    private final String APP_ID = "2021000118608326";

    private final String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvUXj7pxoCtOW80jHHRFh/8FJU+Q9v9/7Zs5GcvQ3ZtXcP/R0OCyUQnNaSCvM0AP2GSeTCr3UCDPOe0XvaKeAv2K4VVOEgt8+Z9MZ7Q0BCKAEO+sjHZpewno40m9ioisictTeDi0Nx9NFNKI5pQ4H6w9hbOxYP5Maa8X6qstCDQt4CS4m6nkFm4WtZZ06xAoINkGqC1I6hmphoUPWrPRFx7CMhi408Kdfr921bhQddtcHw9WkYqRvXsTGEa4i2LunF4iL0kFJVOqaTklL4CTEHAlmgjhmzgqXGNt1z+TrBc7m9hfmgcyBXfQ9M4sW6R21g4i4Kl/LxDabbYcDL69gjQIDAQAB";
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCbKoMrumBbVsFiJFwXaAX0V5JbbtiOrIry7o54crwsgAqDlZwk29hERBDfhN2YgS7Oo3bbM7Z/IvGL9E+7pJI4W9sj5a9X8F0ogRG9zkHgSIj1Ttyvdgh6yIOwuEOhvrQ4F50/ilkCt0WrnwbNscUAsIEwDfgkXg1MxgZpUDFBIX+4zg94EZJoVaoZlC+KKT6E3W1sxjG7T6Q7IijivrQJpb9Km38hPw4l/uCbRGufpX58NnnWRHRtT12v9yWKsoI+H66DESiHOmXOMKcs6FywGUrK8dIBgnEUakm0XNNdNbl8Uc2TPW1HLnR2JKq8QLbNhUEWrJKtCCjxtRzrMBdzAgMBAAECggEAND3G9gUFBhuadmGJg/Po9VA6nYAfd0HOcmYxH4p5on3ljFHlxV8jXDCSgb7pILx1turATKMHkZyBOL6jzahl9oWurVJai8AIjWePQplcAphJTFYl5QlXZ4d1Fx2i36UmZjBFKCx7NXfhBBxBnSV6D3ZduIErh4sG7U9Kv1BZlPSdFXT0eE4H30zLS/WHQF24iWLGO4Ukb+D7j1H9yTeYHTVh83s9gmkTklhNC9y3ho/pSPwliin7wGN5lq3uyLXhFdxrfibLq9ZpJPztNQrFBfnKwUmUnl6g0xhwzvniTOt7KY1FTrVs4+1jAqvbYTZYSstDUGWsXeov70OxUC+1aQKBgQDN/Qn8bPTc3KkXnVU6gA3r3CBlQxkhZPMe3L2HwIkXtFp6ClWQJyx3X83OWjrJbguQftyMuMsa1VLeLOp/zHUDvYZmCCIepFuq29/MTm3JJlix59IzqADooLjXmwN1jk0O6i6Ldg5ZstLfXcyYALjxrOp8ilWWy+Z3Lu6ayQKEDQKBgQDA1quzAotDbHTu7Ai3CgadQmGWs902hQIumtVjjB7U+hKapNxsm7e4vX3Zb87yGw2phuaMjCDudKq9DC4USrvX3VcH90sLkqEKzI/ULM4Ei5xd9ZQaxV4h/0NdwXeEXT7qTPv5qMKUFILbLr/Lk36oEvoum1dngVCvP+KRK9SpfwKBgACUDUsgIy/bjr36OxsG8QMQgybFoXRujCAyDnYrHDds0jQ1gzf8ZCuRoUpLBo86N94X/RaIDkns/2aQSbhCIrH3/kCbFT1wyLEaHO696aD+8pDNTdHHTvlYOiygysmxtJWuIqNRmYi279CguspSdjJ3gdEHsmXR/Luo7hKRLeihAoGAIjt5i3OeKT/tmiRIf8XF2tDX5hrT4EPIWWGfE+bh6kjiWJV/k8Ao4yCwYJMal5v51uhdsQGHLw2C6cBpxaxmoDFoq79YkRtcf0BFIBM8F055Wayot//lb/+Sg+g+QT8UVh+Ic49jy0nA2Qv5fDgYudl0lCV7ZKJuIk4i+EZhoB0CgYBz6Z3KEj1P4o41aPX2+vyut4VFdmC4bRv6NNVPFaSgQiFkv+z9QhDSvrUl32tbZItERt0EappecDy1LaoXih/egsgloZhHo9goFN2dcgLxejuqZTJhrNiIn8ySRmi4iue4mSkVRZX0bRocbxW8+pmytLgwYq+DPipeYiWpA7AATg==";
    private final String CHARSET = "UTF-8";
    private final String APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyqDK7pgW1bBYiRcF2gF9FeSW27YjqyK8u6OeHK8LIAKg5WcJNvYREQQ34TdmIEuzqN22zO2fyLxi/RPu6SSOFvbI+WvV/BdKIERvc5B4EiI9U7cr3YIesiDsLhDob60OBedP4pZArdFq58GzbHFALCBMA34JF4NTMYGaVAxQSF/uM4PeBGSaFWqGZQviik+hN1tbMYxu0+kOyIo4r60CaW/Spt/IT8OJf7gm0Rrn6V+fDZ51kR0bU9dr/clirKCPh+ugxEohzplzjCnLOhcsBlKyvHSAYJxFGpJtFzTXTW5fFHNkz1tRy50diSqvEC2zYVBFqySrQgo8bUc6zAXcwIDAQAB";
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";

    private  final  String server="http://localhost:8080";

    private HttpServletRequest request;
    private HttpServletResponse response;
    public AlipayUtil(HttpServletRequest request,HttpServletResponse response){
        this.request=request;
        this.response=response;
    }

    public static class PaymentOrder{
        private String title;
        private String amount;
        private String orderno;

        private String returnurl;

        private String notifyurl;

        public String getNotifyurl() {
            return notifyurl;
        }

        public void setNotifyurl(String notifyurl) {
            this.notifyurl = notifyurl;
        }

        public String getReturnurl() {
            return returnurl;
        }

        public void setReturnurl(String returnurl) {
            this.returnurl = returnurl;
        }

        public PaymentOrder(String title, String amount, String orderno) {
            this.title = title;
            this.amount = amount;
            this.orderno = orderno;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public void pay(PaymentOrder order){

        if(order==null)
            return;
        SecureRandom r= new SecureRandom();

        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, APP_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        String returnurl=this.server+this.request.getContextPath()+"/payreturn";
        String notifyurl=  this.server+this.request.getContextPath()+"/notifyUrl";
        if(order.returnurl!=null)
            returnurl=order.returnurl;
        if(order.notifyurl!=null)
            notifyurl=order.notifyurl;
        request.setReturnUrl(returnurl);
        request.setNotifyUrl(notifyurl);
        request.setBizContent("{\"out_trade_no\":\""+ order.orderno +"\","
                + "\"total_amount\":\""+ order.amount +"\","
                + "\"subject\":\""+ order.title +"\","
                + "\"body\":\""+ order.title +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=" + CHARSET);
        try {
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        }catch (Exception e){

        }
    }

    public String getOrderno(){
        return request.getParameter("out_trade_no");
    }
    public String getAmount(){
        return request.getParameter("total_amount");
    }
    public boolean validate(){

        try {
            request.setCharacterEncoding("utf-8");//乱码解决，这段代码在出现乱码时使用
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for(String str :requestParams.keySet()){
            String name = str;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        try {
            return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2"); //调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;

    }

}
