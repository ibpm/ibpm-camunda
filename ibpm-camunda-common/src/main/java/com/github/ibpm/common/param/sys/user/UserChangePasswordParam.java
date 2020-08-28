package com.github.ibpm.common.param.sys.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
public class UserChangePasswordParam extends UserNameParam {

    protected String oldPassword;

    protected String newPassword;

    protected String doubleNewPassword;

    @Override
    public String validate() {
        if (StringUtils.isBlank(oldPassword)
                || StringUtils.isBlank(newPassword) || StringUtils.isBlank(doubleNewPassword)) {
            return "1522";
        }
        return super.validate();
    }
}
