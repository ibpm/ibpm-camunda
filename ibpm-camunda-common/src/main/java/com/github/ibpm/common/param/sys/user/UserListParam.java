package com.github.ibpm.common.param.sys.user;

import com.github.ibpm.common.param.PageSortParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserListParam extends PageSortParam {

    protected String userName;

    protected String displayName;

}
