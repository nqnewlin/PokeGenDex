package com.example.pokegendex.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.pokegendex.MainActivity;
import com.example.pokegendex.R;
import com.example.pokegendex.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final String ACTION_UPDATE_LAUNCH_BUTTON = "com.newlin.gendex.ACTION_UPDATE_LAUNCH_BUTTON";
    private static final String EXTRA_BUTTON_TEXT_RES_ID = "btn_txt_res_id";
    private static final String EXTRA_BUTTON_ENABLED = "btn_enabled";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final Button startButton = binding.buttonStart;
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).runStartButtonLogic();
                }
            }
        });

        final Button stopButton = binding.buttonStop;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void updateLaunchButtonText(@NonNull Context context,
                                              @StringRes int stringResId,
                                              @Nullable Boolean enabled) {
        Intent i = new Intent(ACTION_UPDATE_LAUNCH_BUTTON);
        i.putExtra(EXTRA_BUTTON_TEXT_RES_ID, stringResId);
        if (enabled != null) {
            i.putExtra(EXTRA_BUTTON_ENABLED, enabled);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcastSync(i);
    }

}