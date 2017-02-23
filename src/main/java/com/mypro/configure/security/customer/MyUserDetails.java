package com.mypro.configure.security.customer;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.mypro.bean.entity.SysRole;
import com.mypro.bean.entity.SysUser;

public class MyUserDetails extends SysUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private List<SysRole> roles;
	
	public MyUserDetails(SysUser user, List<SysRole> roles){
        super(user);
        this.roles = roles;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 if(roles == null || roles.size() <1){
	            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
	        }
	        StringBuilder commaBuilder = new StringBuilder();
	        for(SysRole role : roles){
	            commaBuilder.append(role.getRoleNo()).append(",");
	        }
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
