package cn.weibin.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController extends BaseController {

	/**
	 * 默认主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/")
	public String defaultPage() {
		return "redirect:/index";
	}

	/**
	 * 登陆页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Object login(Model model) {
		model.addAttribute("userName", "admin");
		return "login";
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(String username, String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
		} catch (UnknownAccountException uae) {
			return failure("账户不存在");
		} catch (IncorrectCredentialsException ice) {
			return failure("密码不正确");
		} catch (LockedAccountException lae) {
			return failure("账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			return failure("用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			return failure("用户名或密码不正确");
		}
		return success();
	}

	/**
	 * 无权限页面
	 * @return
	 */
	@RequestMapping("/401")
	public Object error401(Model model) {
		model.addAttribute("message", "您没有权限访问此链接");
		return "401";
	}

	@RequestMapping("/home")
	public Object index() {
		return "home";
	}

	@RequiresRoles("admin222")
	@RequestMapping("/home2")
	public Object index2() {

		Subject subject = SecurityUtils.getSubject();

		System.out.println(subject.hasRole("admin"));
		return "home";
	}
}