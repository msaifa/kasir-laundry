package com.komputerkit.kasirlaundry.Utilitas;

import android.os.Environment;

/**
 * Created by SaifAlikhan on 31/05/2017.
 */

public class Config {
    private static Boolean inAppPurchase = true ;
    private static String AppName   = "KasirLaundry.db" ;
    private static String sessionName = "AppData" ;
    private static String defaultPath = Environment.getExternalStorageDirectory().toString() + "/Download/";

    private static final String BASE_URL = "" ;
    private static String layananAPI = "" ;
    private static final String ALGORITHM = "AES";
    private static final String Salt = "E/2uUecVAhoehk/Nlumvzw==" ; ////*SALT diatas Dari encrypt "komputerkit" dengan salt 'komputerkit-2017'*/
    private static final String BASE64ENCODE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm10o+Jx3t7Jhr629SLVeqDgSRuhZATXHzO9eOUyxhzSopY7i8BH7xcbHHd2DLG+EHjr8nYuxGvG55+HTFB6/O54ZjqvLjZGne/SQTErkFnTY31sxhb1zPrfE/vAPhd+8el0juo5kmrG+2FIZigaQzsIt6FFr2LPqmqNZx7Q0+hc+5PzerJDk6H/J1Ysv1CmGOF/N5upxomWTrNtvQ5ywbuCNtmA3OWWL9eC/xS8Dj2B97F/Css65RDo+qDsRIAi9WvwCbx/pRynNSd+PGcmzgLaY2wtKlg52WRP7hifBCaTXZi25rbeCAUTQWLNL/9BiIZcVzOwibIoI2ez+UGmUZQIDAQAB" ;

    private static final String IDFullVersion = "com.komputerkit.kasirlaundry.fullversion" ;
//    private static final String IDFullVersion = "android.test.purchased" ;

    private static final int jumMaster = 7 ;
    private static final int jumTrans = 11 ;
















    public static String getDefaultPath() {
        return defaultPath;
    }

    public static String getBASE64ENCODE() {
        return BASE64ENCODE;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setInAppPurchase(Boolean inAppPurchase) {
        Config.inAppPurchase = inAppPurchase;
    }

    public static void setAppName(String appName) {
        AppName = appName;
    }

    public static void setLayananAPI(String layananAPI) {
        Config.layananAPI = layananAPI;
    }

    public static void setSessionName(String sessionName) {
        Config.sessionName = sessionName;
    }

    public static Boolean getInAppPurchase() {
        return inAppPurchase;
    }

    public static String getAppName() {
        return AppName;
    }

    public static String getLayananAPI() {
        return layananAPI;
    }

    public static String getSessionName() {
        return sessionName;
    }

    public static String getALGORITHM() {
        return ALGORITHM;
    }

    public static String getSalt(){
        return Salt ;
    }

    public static String getAlgorithm(){
        return ALGORITHM ;
    }

    public static String getIDFullVersion() {
        return IDFullVersion;
    }

    public static int getJumMaster() {
        return jumMaster;
    }

    public static int getJumTrans() {
        return jumTrans;
    }
}
