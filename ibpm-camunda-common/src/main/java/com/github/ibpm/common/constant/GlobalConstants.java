package com.github.ibpm.common.constant;

public interface GlobalConstants {

	String defaultContextPath = "ibpm";

	String basePackageLocation = "com.github." + defaultContextPath + ".";

	String mybatisPlusMapperLocation = basePackageLocation + "**.dao*";

	String statusPropertiesName = "status";

}
