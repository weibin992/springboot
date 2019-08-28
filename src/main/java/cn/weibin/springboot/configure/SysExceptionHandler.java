package cn.weibin.springboot.configure;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ControllerAdvice
public class SysExceptionHandler {

	private final static Logger logger = LoggerFactory.getLogger(SysExceptionHandler.class);

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView hadleServerException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.error(e.getMessage(), e);
		int status = 500;
		String message = "系统错误, 请刷新页面重试";
		if(e instanceof UnauthorizedException) {
			status = 401;
			message = "您没有权限访问此链接";
		}
		ModelAndView modelAndView = new ModelAndView();
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {  
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("code", status * -1);
			param.put("message", message);
			modelAndView.setView(view);
		} else {
			modelAndView.addObject("message", message);
            modelAndView.setViewName("500");
		}
		response.setStatus(status);
		return modelAndView;
	}
}