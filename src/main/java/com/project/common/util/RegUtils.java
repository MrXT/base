package com.project.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证工具
 */
public class RegUtils {
    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((16[0-9])|(19[0-9])|(17[0-9])|(13[0-9])|(14[0-9])|(15([0-9]))|(18[0-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    public static boolean checkMail(String mail) {
        String regex = "[a-zA-Z0-9\\-_]{1,}@[a-zA-Z0-9]{1,}\\.[a-zA-Z0-9]{1,}";
        return check(mail, regex);
    }

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
