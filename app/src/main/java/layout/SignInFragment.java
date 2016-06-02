package layout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.bigedo.task_3_sign_in.R;

import handler.UserHandler;
import validation.SignInValidation;

import static xdroid.toaster.Toaster.toast;
import static xdroid.toaster.Toaster.toastLong;

/**
 * Created by bigedo on 5/11/2016.
 */
public class SignInFragment extends Fragment implements View.OnClickListener {
    View view;

    Button home_navigation_button, signInButton;

    //create edit text object

    private EditText passwordEditText;

    private EditText emailEditText;

    //create fragment manager
    FragmentManager fm;

    //create fragment transaction
    FragmentTransaction ft;

    //create fragment object
    Fragment homeFragment, registerFragment, signinFragment, userFragment;

    //validator
    AwesomeValidation mAwesomeValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signin_fragment, container, false);
        initFragment();
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        mAwesomeValidation.addValidation(getActivity(), R.id.email_edit_text, Patterns.EMAIL_ADDRESS, R.string.email_validation);
        return view;
    }

    SignInValidation signinValidation = new SignInValidation();
    UserHandler userHandler = new UserHandler();
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_home:
                Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
                swapFragment("home");
                break;
            case R.id.sign_in_button:
                UserHandler userHandler = new UserHandler();
                String password = passwordEditText.getText().toString();
                if(signinValidation.validate(emailEditText.getText().toString(),password)){
                    int id = Integer.parseInt(emailEditText.getText().toString());
                    userHandler.signIn(id, password, getActivity());
                    SharedPreferences get_shared_preference = getActivity().getSharedPreferences("authentication", getActivity().MODE_PRIVATE);
                    String key = get_shared_preference.getString("token_authentication", "");
                    if(key!="" || !key.isEmpty()){
                        swapFragment("user");
                    }
                    toast("data valid");
                }
                else{
                    toast("data tidak valid");
                }
                break;
            default:
                toast("errrrh");
        }

    }

    private void initFragment() {
        //init button
        home_navigation_button = (Button) view.findViewById(R.id.signin_home);
        signInButton = (Button) view.findViewById(R.id.sign_in_button);

        //add listener
        home_navigation_button.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        //init edit text
        emailEditText = (EditText)view.findViewById(R.id.email_edit_text);
        passwordEditText = (EditText)view.findViewById(R.id.password_edit_text);
    }

    private void swapFragment(String id) {
        homeFragment = new HomeFragment();
        registerFragment = new RegisterFragment();
        signinFragment = new SignInFragment();
        userFragment = new UserFragment();

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        if (id.equals("home")) {
            ft.replace(R.id.fragment_place, homeFragment);
        }
        else if (id.equals("user")){
            ft.replace(R.id.fragment_place, userFragment);
        }
        ft.commit();
    }
}
