package util;

import java.util.Random;

public class PasswordManage {
    public static String encryption(String str){
        Random random = new Random();
        int rnd = random.nextInt(10,50);
        String result = String.valueOf(rnd);

        for(int i = 0; i < str.length(); i++){
            result +=  (char) (str.charAt(i) + rnd);
        }
        return result;

    }

    public static String decryption(String enStr){
        String result = "";
        String strKey = "";
        strKey += enStr.charAt(0);
        strKey += enStr.charAt(1);
        int key = Integer.parseInt(strKey);

        for(int i = 2; i < enStr.length(); i++){
            result += (char)(enStr.charAt(i) - key);
        }
        return result;

    }
}
