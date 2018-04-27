package com.example.dabutaizha.lines;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/4 下午8:22.
 */

public class Constant {

    /**
     *Description: 网络请求相关
     */

    public static final String HTTP_HEAD = "http:";

    public static final String BASE_URL = "http://www.juzimi.com";

    public static final String BASE_URL_ASSISTANT = "http://www.wufazhuce.com";

    public static final String BASE_URL_UPDATE = "https://www.coolapk.com";

    /**
     *Description: 首页UI相关
     */

    public static final String FRAGMENT_TITLE = "fragment_title";

    /**
     *Description: 搜索相关
     */
    public static final String SEARCH = "search";

    /**
     *Description: 作品板块页
     */
    public static final String ARTICLE_ID = "article_id";
    public static final String ARTICLE_TITLE = "article_title";
    public static final String ARTICLE_PAGE_URL = "article_page_url";
    public static final String ARTICLE_PAGE_INTRO = "article_page_intro";
    public static final String ARTICLE_PAGE_RELATED = "article_page_related";
    public static final String ARTICLE_URL_HEADER = "http://www.juzimi.com/article/";

    /**
     *Description: 加载页相关
     */
    public static final String SHARE_INFO = "share_info";
    public static final String IS_FIRST = "is_first";
    public static final String SPLASH_TAG = "splash_tag";

    /**
     *Description: 分享相关
     */
    public static final int SHARE_SELECT = 0;
    public static final String UPDATE_LOCAL_VERSION = "local_version";
    public static final String UPDATE_RECENT_VERSION = "recent_version";
    public static final String UPDATE_APK_SIZE = "apk_message";
    public static final String DOWNLOAD_URL = "https://www.coolapk.com/apk/com.example.dabutaizha.lines";
    public static final String LOGIN_REGISTER_URL = "http://www.juzimi.com/user/register";
    public static final String LOGIN_SUCCEED = "http://www.juzimi.com/u/";
    public static final String USER_STATE = "user_state";
    public static final String SAVE_IS_LOGINED = "save_is_logined";
    public static final String SAVE_USER_ID = "save_user_id";
    public static final String COOKIE_INTERCEPTOR_KEY = "login_cookie";
    public static final String USER_NAME = "user_name";
    public static final String SAVE_USER_NAME = "save_user_name";
    public static final int THEME_DEFAULT = 0;
    public static final int TRANSPARENT = 1;
    public static final String WIDGET_THEME = "widget_theme";
    public static final String WIDGET_THEME_TYPE = "widget_time_type";
    public static boolean IS_LOGINED = false;
    public static String USER_ID = "";


    /**
     *Description: 图片加载相关
     */

    public static long MB = 1024 * 1024;

    public static String CachePath = "linesImageCache";

    /**
     *Description: sp存储相关
     */

    public static String FRAGMENT_SEARCH = "fragment_search";

    public static String FRAGMENT_HOTPAGE_PIC = "fragment_hotpage_pic";

    public static String SEARCH_TAG = "search_tag";

    public static String PICS_SAVE = "pics_save";

    /**
     *Description: 登陆相关
     */

    // 开启登陆中弹窗
    public static int START_LOGIN_DIALOG = 0;
    // 关闭登陆中弹窗
    public static int CLOSE_LOGIN_DIALOG = 1;

    /**
     *Description: webview
     */

    public static String WEBVIEW_URL = "webview_url";

    /**
     *Description: 特殊字符
     */
    public static String SPLIT = "•";
    public static String SAVE_PATH = "/sdcard/Lines/";
}
