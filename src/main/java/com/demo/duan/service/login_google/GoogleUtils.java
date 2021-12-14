package com.demo.duan.service.login_google;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;

@Component
public class GoogleUtils {
    @Autowired
    private Environment env;
    public GooglePojo getUserInfo(final String accessToken) throws IOException {
        String link = env.getProperty("google.link.get.user_info")+ accessToken.replace("\"","");
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
        return googlePojo;
    }

}
