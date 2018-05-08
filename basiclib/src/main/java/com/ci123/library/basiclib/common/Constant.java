package com.ci123.library.basiclib.common;

/**
 * Created by ZhangChao on 2017/10/12.
 */

public class Constant {
    public static int ERR_CODE_NET_DISCONNECT = -1;
    public static int ERR_CODE_PARAM_EXCEPTION = -2;
    public static int ERR_CODE_REQUEST_FAILED = -3;
    public static int ERR_CODE_DIR_CREATE_FAILED = -4;

    public static final String APP_SCHEME = "xmh://";

    public interface Scheme {
        String FILE = "file";
        String HTTPS = "https";
        String HTTP = "http";
        String WEEX = "weex";
        String NATIVE = "native";
    }

    public interface PageUrl {
        String LAUNCHER = "weex://host/loginPhone";
        String STUDENT_HOMEWORK_DETAIL = "weex://host/studentHomeworkDetail";
        String ADD_HOMEWORK_SINGLE = "weex://host/addHomeworkSingle";
        String HOMEWORK_COMMENT = "weex://host/homeworkComment";
        String TEACHER_HOME_PAGE = "weex://host/teacherHome";
        String MINE_PAGE = "weex://host/studentMine";
        String STUDENT_HOME_PAGE = "weex://host/studentHome";
    }

    public static String getWeexUrlByPath(String path) {
        if (path.startsWith("/")) {
            return "weex://host" + path;
        } else {
            return "weex://host/" + path;
        }
    }
}
