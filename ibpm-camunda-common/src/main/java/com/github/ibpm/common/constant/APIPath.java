package com.github.ibpm.common.constant;

public class APIPath {

    public static final String API_PATH = "/api";

    private static final String SYS = "/sys";

    private static final String CORE = "/core";

    private static final String STATISTICS = "/statistics";

    private static final String EXECUTOR = "/executor";

    private static final String _GET = "/get";

    private static final String _PUBLISH = "/publish";

    public static final class LoginPath {
        public static final String SUB_PATH = "/login";

        public static final String $ = API_PATH + SYS + SUB_PATH;

        public static final String LOGIN_BY_ENCODED_USERNAME_PWD = $ + "/encoded";

        public static final String INFO = $ + "/info";
    }

    public static final class UserPath {
        public static final String SUB_PATH = "/user";

        public static final String $ = API_PATH + SYS + SUB_PATH;

        public static final String GET = $ + _GET;

        public static final String ROLE = $ + RolePath.SUB_PATH;

        public static final String PASSWORD = $ + "/password";
    }

    public static final class RolePath {
        public static final String SUB_PATH = "/role";

        public static final String $ = API_PATH + SYS + SUB_PATH;

        public static final String GET = $ + _GET;

        public static final String USER = $ + UserPath.SUB_PATH;

        public static final String RESOURCE = $ + "/resource";
    }

    public static final class JobPath {

        public static final String SUB_PATH = "/job";

        public static final String $ = API_PATH + CORE + SUB_PATH;

        public static final String GET = $ + _GET;

        public static final String ENABLE = $ + "/enable";

        public static final String DISABLE = $ + "/disable";

        public static final String PUBLISH = $ + _PUBLISH;

        public static final String CONTENT = $ + "/content";

        public static final String VERSIONS = $ + "/versions";

        public static final String COPY = $ + "/copy";

        public static final String EXCHANGE = $ + "/exchange";

        public static final String MANUAL_BATCH = $ + "/manual/batch";

        public static final String MANUAL = $ + "/manual";

        public static final String EXPORT_MODEL = $ + "/export/model";

        public static final String IMPORT_MODEL = $ + "/import/model";

        public static final String TRIGGER = $ + "/trigger";


    }

    public static final class CalendarPath {
        public static final String SUB_PATH = "/calendar";

        public static final String $ = API_PATH + CORE + SUB_PATH;

        public static final String DAY = $ + "/day";

        public static final String IMPORT = DAY + "/import";
    }

    public static final class InstancePath {
        private static final String SUB_PATH = "/instance";

        public static final String $ = API_PATH + CORE + SUB_PATH;

        public static final String CHILDREN = $ + "/children";

        public static final String RETRY = $ + "/retry";

        public static final String RETRY_CHILDREN = RETRY + "/children";

        public static final String TERMINATE = $ + "/terminate";

        public static final String ACT = $ + "/act";


    }

    public static final class CountPath {
        private static final String SUB_PATH = "/count";

        public static final String USER = API_PATH + STATISTICS + SUB_PATH + UserPath.SUB_PATH;

        public static final String JOB = API_PATH + STATISTICS + SUB_PATH + JobPath.SUB_PATH;

        public static final String INSTANCE = API_PATH + STATISTICS + SUB_PATH + InstancePath.SUB_PATH;
    }

    public static final class ToolPath {
        private static final String SUB_PATH = "/tool";

        public static final String GET_TIME_ZONE = API_PATH + CORE + SUB_PATH + "/timeZone";

        public static final String LIST_SCHEDULER_SERVICE = API_PATH + CORE + SUB_PATH + "/scheduler";

        public static final String LIST_EXECUTOR_SERVICE = API_PATH + CORE + SUB_PATH + "/executor";

        public static final String GET_SERVER_INFO = API_PATH + CORE + SUB_PATH + "/serverInfo";

        public static final String PING = API_PATH + CORE + SUB_PATH + "/ping";

        public static final String TIME = API_PATH + CORE + SUB_PATH + "/time";

        public static final String ARG_TYPES = API_PATH + CORE + SUB_PATH + "/argTypes";

        public static final String TRADE_DATE_UNITS = API_PATH + CORE + SUB_PATH + "/tradeDateUnits";

    }

    public static final class ExecutorPath {

        public static final String TERMINATE = API_PATH + EXECUTOR + "/terminate";
    }

    public static final class CommonPath {

        private static final String SUB_PATH = "/common";

        public static final String SERVER_INFO = API_PATH  + SUB_PATH + "/serverInfo";
    }
}
