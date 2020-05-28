package com.github.ibpm.common.param.sys.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RoleUserSaveParam extends RoleNameParam {

    protected List<String> userNames;

}
