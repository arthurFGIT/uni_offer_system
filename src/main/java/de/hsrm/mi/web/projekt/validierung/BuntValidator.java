package de.hsrm.mi.web.projekt.validierung;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class BuntValidator implements ConstraintValidator<Bunt, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String valuePattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        String valuePattern3 = "^#([A-Fa-f0-9]{3})$";
        String valuePattern6 = "^#([A-Fa-f0-9]{6})$";
        
        if(value == null){
            return false;
        }
        if(value == ""){
            return true;
        }

        Pattern pattern = Pattern.compile(valuePattern);
        Pattern pattern3 = Pattern.compile(valuePattern3);
        Pattern pattern6 = Pattern.compile(valuePattern6);

        Matcher matcher = pattern.matcher(value);
        Matcher matcher3 = pattern3.matcher(value);
        Matcher matcher6 = pattern6.matcher(value);


        if(matcher.matches() == true){
            String [] valueArray = value.replace("#","").split("");
            if(matcher3.matches() == true){
                for (int i = 0; i < valueArray.length; i++) {
                    if (i >= 1 && valueArray[i].equalsIgnoreCase(valueArray[i-1])) {
                        return false;
                    }
                }  
                return true;
            }
            else if(matcher6.matches() == true){
                String string1 = valueArray[0] + valueArray[1];
                String string2 = valueArray[2] + valueArray[3];
                String string3 = valueArray[4] + valueArray[5];
                if(string1.equals(string2) || string1.equals(string3) || string2.equals(string3) ){
                    return false;
                }
            }
            return true;
        } 
        return false;
    }
    
}
