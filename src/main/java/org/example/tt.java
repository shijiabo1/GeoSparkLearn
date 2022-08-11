package org.example;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.params.BasicHttpParams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class tt {
    public static void main(String[] args) throws URISyntaxException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("https://restapi.amap.com/v3/config/district");
//        GetMethod getMethod = new GetMethod("https://restapi.amap.com/v3/config/district?key=4e32d0a602bd86be80bcd541f2e85314&keywords=山东&subdistrict=0&extensions=all");
        HttpClientParams params = new HttpClientParams();

//        URIBuilder uriBuilder=new URIBuilder("https://restapi.amap.com/v3/config/district");
        params.setParameter("key","4e32d0a602bd86be80bcd541f2e85314");
        params.setParameter("keywords","山东");
        params.setParameter("subdistrict","0");
        params.setParameter("extensions","all");
        //?key=4e32d0a602bd86be80bcd541f2e85314&keywords=山东&subdistrict=0&extensions=all
//        httpClient.setParams();
//        getMethod.add("consumerAppId", "test");
//        getMethod.setQueryString("?key=4e32d0a602bd86be80bcd541f2e85314");
//        getMethod.setQueryString("&keywords=山东");
//        getMethod.setQueryString("&subdistrict=0");
//        getMethod.setQueryString("&extensions=all");
        //必须设置下面这个Header
        //添加请求参数
        String result = "";
        httpClient.setParams(params);
        try {
            int code = httpClient.executeMethod(getMethod);
            if (code == 200){
                result = getMethod.getResponseBodyAsString();
                System.out.println("result:" + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
