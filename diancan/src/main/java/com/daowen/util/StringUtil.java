package com.daowen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


	/**
	 * 是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean containChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		//中文标点符号
		Pattern p2 = Pattern.compile("[\uFF01]|[\uFF0C-\uFF0E]|[\uFF1A-\uFF1B]|[\uFF1F]|[\uFF08-\uFF09]|[\u3001-\u3002]|[\u3010-\u3011]|[\u201C-\u201D]|[\u2013-\u2014]|[\u2018-\u2019]|[\u2026]|[\u3008-\u300F]|[\u3014-\u3015]");
		Matcher m = p.matcher(str);
		Matcher m2=p2.matcher(str);
		if (m.find()||m2.find()) {
			return true;
		}
		return false;
	}

	public  static String toFirstUpper(String org){
		  
		
		  if(org==null)
			  return "";
		  char [] c=org.toCharArray();
		  if(c[0]>='a'&&c[0]<='z'){
			   int x=c[0];
			   c[0]=(char)(x-32);
		  }
		  
		  for(int i=1;i<c.length;i++)
		  {
		   if(c[i]>='A'&&c[i]<='Z')    
		    {
		     int x=c[i];
		     c[i]=(char)(x+32);
		    }    
		  }
		return new String(c);
	}

	public static boolean isNumeric(String src){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}
}
