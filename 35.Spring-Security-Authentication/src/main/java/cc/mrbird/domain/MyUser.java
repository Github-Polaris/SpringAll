package cc.mrbird.domain;

import java.io.Serial;
import java.io.Serializable;

public class MyUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 3497935890426858541L;

    private String userName;

    private String password;

    /*
    * 账号是否过期
    * */
    private boolean accountNonExpired = true;

    /*
    * 账号是否锁定
    * */
    private boolean accountNonLocked= true;

    /*
    * 账号凭证是否过期
    * */
    private boolean credentialsNonExpired= true;

    /*
    * 用户是否可用
    * */
    private boolean enabled= true;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
