package pl.sda.customersafterkurs.service.validation;


import java.util.regex.Pattern;

public class PeselValidation {

    private final static String pattern = "[0-9]{11}";

    public static boolean peselIsValid(String peselToValidate) {
        return peselToValidate.matches(pattern);
                //Pattern.compile(pattern).matcher(peselToValidate).matches(); //drugie podejście do tematu
        //

    }
}
