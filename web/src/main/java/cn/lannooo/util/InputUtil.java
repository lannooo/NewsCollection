package cn.lannooo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 51499 on 2017/5/21 0021.
 */
public class InputUtil {
    public static final int MIN_USERNAME_LENGTH = 5;
    public static final int MAX_USERNAME_LENGTH = 18;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;
    //TODO:Email valid check
    private static String emailPattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    public static boolean checkEmailValid(String email){
        if(null!=email && email.length()>0){
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }

    public static boolean checkUserNameValid(String username){
        if(null!=username &&
                username.length()>=MIN_USERNAME_LENGTH &&
                username.length()<=MAX_USERNAME_LENGTH) {
            //TODO:some invalid chars check
            return true;
        }
        return false;
    }

    public static boolean checkPasswordValid(String password){
        //TODO:check complex password constraints
        if(null!=password && password.length()>=MIN_PASSWORD_LENGTH && password.length()<=MAX_PASSWORD_LENGTH)
            return true;
        return false;
    }
}
