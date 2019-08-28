package cn.weibin.springboot.configure.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.weibin.springboot.entity.User;
import cn.weibin.springboot.entity.UserExample;
import cn.weibin.springboot.mapper.PermissionMapper;
import cn.weibin.springboot.mapper.RoleMapper;
import cn.weibin.springboot.mapper.UserMapper;


/**
 * 描述：
 *
 * @author caojing
 * @create 2019-01-27-13:57
 */
public class CustomRealm extends AuthorizingRealm {

	private UserMapper userMapper;

	private RoleMapper roleMapper;

	private PermissionMapper permissionMapper;
	
	public CustomRealm(UserMapper userMapper, RoleMapper roleMapper, PermissionMapper permissionMapper) {
		super();
		this.userMapper = userMapper;
		this.roleMapper = roleMapper;
		this.permissionMapper = permissionMapper;
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		Set<String> roleCodes = new HashSet<>(roleMapper.selectCodeByUserId(user.getId()));
		Set<String> permissions = new HashSet<>(permissionMapper.selectCodeByUserId(user.getId()));

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roleCodes);
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		String userName = (String) authenticationToken.getPrincipal();
		UserExample example = new UserExample();
		example.createCriteria().andUserNameEqualTo(userName);
		List<User> memberList = userMapper.selectByExample(example);
		if(memberList.isEmpty()) {
			return null;
		}
		User user = memberList.get(0);
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}
}