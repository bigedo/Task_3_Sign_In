package layout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigedo.task_3_sign_in.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    View view;

    //create button object
    Button navigationRegisterButton, navigationSigninButton, navigationHomeButton, registerButton, loginButton, logoutButton;

    //create text view
    TextView homeText;

    //create fragment object
    Fragment homeFragment, registerFragment, signinFragment, userFragment;

    //create fragment manager
    FragmentManager fm;

    //create fragment transaction
    FragmentTransaction ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.home_fragment, container, false);
        init();
        SharedPreferences get_shared_preference = getActivity().getSharedPreferences("authentication", getActivity().MODE_PRIVATE);
        String key = get_shared_preference.getString("token_authentication", "");
        homeText.append(" "+key);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.navigation_register_button:
                Toast.makeText(getActivity(), "register", Toast.LENGTH_SHORT).show();
                swapFragment("register");
                break;
            case R.id.navigation_signin_button:
                Toast.makeText(getActivity(), "sing in", Toast.LENGTH_SHORT).show();
                swapFragment("signin");
                break;
            case R.id.nav_home_button:
                Toast.makeText(getActivity(), "home", Toast.LENGTH_SHORT).show();
                swapFragment("home");
                break;
            default:
                Toast.makeText(getActivity(), "nothing to find", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){
        //init button
        navigationRegisterButton = (Button)view.findViewById(R.id.navigation_register_button);
        navigationSigninButton = (Button)view.findViewById(R.id.navigation_signin_button);

        //add listener
        navigationRegisterButton.setOnClickListener(this);
        navigationSigninButton.setOnClickListener(this);

        //init text view
        homeText = (TextView)view.findViewById(R.id.home_text);
    }


    private void swapFragment(String id){
        homeFragment = new HomeFragment();
        registerFragment = new RegisterFragment();
        signinFragment = new SignInFragment();
        userFragment = new UserFragment();

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        if(id.equals("home")){
            ft.replace(R.id.fragment_place, homeFragment);
        }
        else if(id.equals("register")){
            ft.replace(R.id.fragment_place, registerFragment);
        }
        else if(id.equals("signin")){
            ft.replace(R.id.fragment_place, signinFragment);
        }
        ft.commit();
    }
}
