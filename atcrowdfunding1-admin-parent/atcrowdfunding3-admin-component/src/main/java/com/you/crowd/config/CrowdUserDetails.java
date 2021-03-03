package com.you.crowd.config;

import com.you.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author 游斌
 * @create 2020-07-10  23:33
 */
public class CrowdUserDetails extends User {
    private Admin originalAdmin;

    public CrowdUserDetails(Admin admin, Collection<? extends GrantedAuthority> authorities) {
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        originalAdmin = admin;
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        this.originalAdmin = originalAdmin;
    }
}
