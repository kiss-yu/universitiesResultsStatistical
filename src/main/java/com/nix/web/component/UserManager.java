package com.nix.web.component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nix.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserManager {
	private static final long TIMEOUT = 10L;//session过期时间（分）
	private final static String USER = "user";
	private final static String CREATE_TIME = "create_time";
	private final static ConcurrentHashMap<HttpSession,Map<String,Object>> sessionMap = new ConcurrentHashMap<>();

	/**
	 * 根据session获取当前登录的用户
	 * */
	public final static UserModel getCurrentUser(final HttpServletRequest request){
		sessionTimeout();
		if (sessionMap.containsKey(request.getSession()))
			return (UserModel) sessionMap.get(request.getSession()).get(USER);
		return null;
	}

	/**
	 * 用户登录时将用户信息与session以键值对存储（规模较小用内存存储）
	 * */
	public final static void addUser(final HttpServletRequest request,UserModel model){
		sessionTimeout();
		if (sessionMap.containsKey(request.getSession())){
			Map<String,Object> map = new HashMap<>();
			map.put(USER,model);
			map.put(CREATE_TIME,System.currentTimeMillis());
			sessionMap.replace(request.getSession(),map);
		}else {
			Map<String,Object> map = new HashMap<>();
			map.put(USER,model);
			map.put(CREATE_TIME,System.currentTimeMillis());
			sessionMap.put(request.getSession(),map);
		}
	}

	/**
	 * 用户注销或者session过期时删除session缓存
	 * */
	public final static void deleteUser(final HttpServletRequest request){
		sessionMap.remove(request.getSession());
	}

	/**
	 * 更新session
	 * */
	public static final void updateSession(final HttpServletRequest request){
		sessionMap.get(request).put(CREATE_TIME,System.currentTimeMillis());
	}


	/**
	 * 判断session缓存是否过期 并处理
	 * */
	public final static void sessionTimeout(){
		try {
			for (Map.Entry<HttpSession,Map<String,Object>> set:sessionMap.entrySet()){
				if ((long)(set.getValue().get(CREATE_TIME)) + TIMEOUT*60*1000*1000 < System.currentTimeMillis()){
					sessionMap.remove(set.getKey());
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
