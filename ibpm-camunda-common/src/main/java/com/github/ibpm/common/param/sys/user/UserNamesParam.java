package com.github.ibpm.common.param.sys.user;

import com.github.ibpm.common.param.CommonParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class UserNamesParam implements CommonParam {

    protected List<String> userNames;

    public String validate() {
        if (userNames == null || userNames.isEmpty() || StringUtils.isBlank(userNames.get(0))) {
            return "5112";
        }
        return null;
    }

}
