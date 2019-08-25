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

import cn.weibin.springboot.entity.Member;
import cn.weibin.springboot.entity.MemberExample;
import cn.weibin.springboot.mapper.MemberMapper;

/**
 * 描述：
 *
 * @author caojing
 * @create 2019-01-27-13:57
 */
public class CustomRealm extends AuthorizingRealm {

	private MemberMapper memberMapper;

	public CustomRealm(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> stringSet = new HashSet<>();
		stringSet.add("user:show");
		stringSet.add("user:admin");
		info.setStringPermissions(stringSet);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		String userName = (String) authenticationToken.getPrincipal();
		MemberExample example = new MemberExample();
		example.createCriteria().andUserNameEqualTo(userName);
		List<Member> memberList = memberMapper.selectByExample(example);
		if(memberList.isEmpty()) {
			return null;
		}
		Member member = memberList.get(0);
		return new SimpleAuthenticationInfo(userName, member.getPassword(), getName());
	}
}