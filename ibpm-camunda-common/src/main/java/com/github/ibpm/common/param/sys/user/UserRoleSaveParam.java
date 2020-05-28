package com.github.ibpm.common.param.sys.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserRoleSaveParam extends UserNameParam {

    protected List<String> roleNames;

}
