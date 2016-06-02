package validation;

/**
 * Created by bigedo on 5/19/2016.
 */
public class SignInValidation{
    public boolean validate(String email, String password){
        if(email.equals("")||email.isEmpty()){
            return false;
        }
        else if(password.equals("")||password.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
