package cn.weibin.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weibin.springboot.bean.ResponseBean;

public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * 成功响应
	 * @return
	 */
	protected ResponseBean success() {
		ResponseBean response = new ResponseBean();
		response.setCode(0);
		return response;
	}

	/**
	 * 构造响应实体
	 * @param data
	 * @return
	 */
	protected ResponseBean success(Object data) {
		ResponseBean response = new ResponseBean();
		response.setCode(0);
		response.setData(data);
		return response;
	}

	/**
	 * 失败响应
	 * @param message
	 * @return
	 */
	protected ResponseBean failure(String message) {
		ResponseBean response = new ResponseBean();
		response.setCode(-1);
		response.setMessage(message);
		return response;
	}

	/**
	 * 失败响应
	 * @param message
	 * @return
	 */
	protected ResponseBean failure(int code, String message) {
		ResponseBean response = new ResponseBean();
		response.setCode(code);
		response.setMessage(message);
		return response;
	}
}