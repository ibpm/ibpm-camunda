package com.github.ibpm.common.param.sys.user;

import com.github.ibpm.common.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

@Getter
@Setter
@ToString
public class UserSaveParam extends UserNameParam {

    protected String displayName;

    protected String password;

    protected String email;

    protected String mobile;

    protected int status;

    protected String sendConfig;

    private static Pattern emailPattern = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    public String validate() {
        if (StringUtils.isBlank(password)) {
            return "1522";
        }
        if (StringUtils.isBlank(displayName)) {
            return "5103";
        }
        if (StringUtils.isNotBlank(email)) {
            for (String emailStr : email.split(",")) {
                if (!emailPattern.matcher(emailStr).find()) {
                    return "5118";
                }
            }
        }
        if (UserStatus.get(status) == null) {
            return "5121";
        }
        return super.validate();
    }
}
