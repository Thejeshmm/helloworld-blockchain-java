package com.xingkaichun.helloworldblockchain.node.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Security过滤器
 *
 * @author 邢开春 xingkaichun@qq.com
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
/*		UserDto userDto = SessionUtil.getUser(httpServletRequest);
		if(userDto == null){
			throw new RuntimeException("用户未登录，无操作权限，请登录!");
		}*/
		return true;
	}
}