package com.github.ibpm.common.param.sys.role;

import com.github.ibpm.common.param.CommonParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class RoleNamesParam implements CommonParam {

    protected List<String> roleNames;

    public String validate() {
        if (roleNames == null || roleNames.isEmpty() || StringUtils.isBlank(roleNames.get(0))) {
            return "5300";
        }
        return null;
    }

}
