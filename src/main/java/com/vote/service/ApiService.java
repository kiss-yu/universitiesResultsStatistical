package com.vote.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public interface ApiService {
    boolean checkMd5(String md5);
    void saveSetting(String type,String[] keys,String title);
    void vote(HttpServletRequest request,String key);
}
