package com.wooyoo.learning.service;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 登陆基类
 */
public interface AuthService {
    public abstract String getAccessToken(String code);
    public abstract String getOpenId(String accessToken);
    public abstract String refreshToken(String code);
    public abstract String getAuthorizationUrl() throws UnsupportedEncodingException;
    public abstract JSONObject getUserInfo(String accessToken, String openId);
}