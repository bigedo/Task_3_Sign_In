package layout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bigedo.task_3_sign_in.R;

import static xdroid.toaster.Toaster.toast;

/**
 * Created by bigedo on 5/11/2016.
 */
public class UserFragment extends Fragment implements View.OnClickListener{
    View view;
    Button logoutBtn;
    TextView authToken;
    Fragment homeFragment, registerFragment, signinFragment, userFragment;

    //create fragment manager
    FragmentManager fm;

    //create fragment transaction
    FragmentTransaction ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.user_fragment, container, false);
        initCmp();
        SharedPreferences get_shared_preference = getActivity().getSharedPreferences("authentication", getActivity().MODE_PRIVATE);
        String key = get_shared_preference.getString("token_authentication", "");
        if(key.isEmpty() || key.equals("")){
            authToken.setText("kosong");
        }
        else
        {
            authToken.setText(key);
        }
        return view;
    }

    // method for init fragment's component
    public void initCmp(){
        //init component
        logoutBtn = (Button)view.findViewById(R.id.logout_button);
        authToken = (TextView)view.findViewById(R.id.auth_token_text_view);

        //add listener
        logoutBtn.setOnClickListener(this);
        /*
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("sukses");
                swapFragment("home");
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logout_button:
                SharedPreferences set_shared_preference = getActivity().getSharedPreferences("authentication", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor sp_editor = set_shared_preference.edit();
                sp_editor.putString("token_authentication", "");
                sp_editor.commit();
                swapFragment("home");
                break;
            default:
                toast("nothing happened");
                break;
        }
    }

    public void swapFragment(String id){
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