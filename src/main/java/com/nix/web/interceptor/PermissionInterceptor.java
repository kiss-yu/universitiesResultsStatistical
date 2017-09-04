package com.nix.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nix.model.PerssionModel;
import com.nix.model.UserModel;
import com.nix.service.imp.PerssionService;
import com.nix.web.component.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.lang.reflect.Method;

public class PermissionInterceptor implements HandlerInterceptor {
	@Autowired
	private PerssionService perssionService;
	@Autowired
	private UserManager userManager;
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		if (handler instanceof HandlerMethod) {
//			HandlerMethod handlerMethod = (HandlerMethod) handler;
//			Method method = handlerMethod.getMethod();
//			PerssionModel perssionModel = perssionService.methodIsOpen(method);
//			if (perssionModel == null)
//				return true;
//			if (!perssionService.isHavaPermissions(perssionModel,userManager.getCurrentUser(request))){
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//				return false;
//			}
//		}
		return true;
	}
}
