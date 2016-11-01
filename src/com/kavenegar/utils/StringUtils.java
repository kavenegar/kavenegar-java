/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.utils;

import com.kavenegar.utils.*;

/**
 *
 * @author Mohsen
 */
public class StringUtils {
    public static String Join(String delimeter,Object[] items)
    {
        if(items==null)
            return "";
        String result = "";
        for(Object obj : items)
        {
            result+=obj + delimeter;
        }
        if (result.length() > 0 && result.charAt(result.length()-1)==',') {
            result = result.substring(0, result.length()-1);
        }
        return result;
    }

  
}

