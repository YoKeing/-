package com.crowd.mvc.config;

import com.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityAdmin extends User {

    private static final long serialVersionUID = 1L;

    private Admin admin;

    public SecurityAdmin(Admin admin, List<GrantedAuthority> authorities){
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        this.admin = admin;
        this.admin.setUserPswd(null);
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
