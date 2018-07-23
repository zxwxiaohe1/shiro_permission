package com.sinux.ssh.Exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description: 自定义异常处理器
 */
public class LoginExceptionResolver implements HandlerExceptionResolver {

	/**
	 * Exception ex就是接收到异常信息
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// 输出异常
		ex.printStackTrace();
		// 统一异常处理代码
		String message = null;
		LoginException loginException = null;
		if (ex instanceof LoginException) {
			loginException = (LoginException) ex;
		} else {
			// 针对非认证异常，异常信息为“未知错误”
			loginException = new LoginException("未知错误");
		}

		// 错误 信息
		message = loginException.getMessage();
		request.setAttribute("message", message);

		try {
			// 转向到错误 页面
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (ServletException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView();
	}

}
