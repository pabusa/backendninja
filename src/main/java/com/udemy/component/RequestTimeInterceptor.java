package com.udemy.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter {

	private static final Log LOG = LogFactory.getLog(RequestTimeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Se ejecuta justo antes de entrar en el método del controlador
		request.setAttribute("startTime", System.currentTimeMillis());
		
		return true;
		//return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// Se llama justo antes de escupir la vista en el navegador
		
		long startTime = (long) request.getAttribute("startTime");
		
		LOG.info("Url to: '"+ request.getRequestURL().toString() + "' in : "+ (System.currentTimeMillis() - startTime) +"ms");

		super.afterCompletion(request, response, handler, ex);
	}

}
