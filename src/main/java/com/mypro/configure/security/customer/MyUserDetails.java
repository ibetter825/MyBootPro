package com.mypro.configure.security.customer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import com.mypro.bean.constant.WebConstant;
import com.mypro.bean.entity.admin.SysUser;

public class MyUserDetails extends SysUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	//private List<SysRole> roles;
	private List<Map<String, Object>> opts;//用户拥有的所有操作，其格式为menu_id:opt_id
	
	/*public MyUserDetails(SysUser user, List<SysRole> roles){
        super(user);
        this.roles = roles;
    }*/
	public MyUserDetails(SysUser user, List<Map<String, Object>> opts){
        super(user);
        this.opts = opts;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		/*
		 if(roles == null || roles.size() <1){
	            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        }
        StringBuilder commaBuilder = new StringBuilder();
        for(SysRole role : roles){
            commaBuilder.append(role.getRoleId()).append(",");
        }
        String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        */
		if(opts == null || opts.size() < 1)
            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		StringBuilder commaBuilder = new StringBuilder();
        for(Map<String, Object> opt : opts)//将用户拥有的所有权限，以便进行权限判断
            commaBuilder.append(WebConstant.ADMIN_REQUEST_ROOT_PATH + "/" + opt.get("menu_route") + "/" + opt.get("opt_code")).append(",");
        String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	@Override
	public String getPassword() {
		return super.getUserPwd();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}
	
	public List<Map<String, Object>> getOpts() {
		return opts;
	}

	public void setOpts(List<Map<String, Object>> opts) {
		this.opts = opts;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return getUsername();
	}

	@Override
	public int hashCode() {
		return getUsername().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}

}
