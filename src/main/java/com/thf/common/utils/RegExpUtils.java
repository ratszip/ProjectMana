package com.thf.common.utils;


import org.apache.oro.text.regex.*;

public class RegExpUtils {
    public static Boolean useRegexp(String str,String filter) {
       // String filter="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern pattern;
        PatternMatcher matcher = new Perl5Matcher();
        PatternCompiler compiler = new Perl5Compiler();
        PatternMatcherInput input = new PatternMatcherInput(str);

        try {
            pattern = compiler.compile(filter);
            if (matcher.matches(input, pattern)) {
                return true;
            }
        } catch (MalformedPatternException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//       Boolean b= useRegexp("password1","^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
//       System.out.println(b);
//    }
}
