package lmu.appnight.PersonKoExplanation;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import lmu.appnight.Classes.CardAdapter;
import lmu.appnight.FirstAidKit.FirstAidKitActivity;
import lmu.appnight.MainActivity;
import lmu.appnight.R;

public final class PersonKoExplanationActivity extends Activity {


    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;

    private boolean mVoiceMenuEnabled = true;

    static final int STEP1 = 0;
    static final int STEP2 = 1;
    static final int STEP3 = 2;
    int current_position = 0;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createMenu(this)));
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.TAP);
                mVoiceMenuEnabled = !mVoiceMenuEnabled;
                current_position = position;
                getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);
            }
        });
        setContentView(mCardScroller);
    }


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.next_menu, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_menu, menu);
        return true;
    }


    private List<CardBuilder> createMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .setText(R.string.person_ko_check)
                .addImage(R.drawable.person_ko_check));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .setText(R.string.person_ko_breath)
                .addImage(R.drawable.person_ko_breath));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .setText(R.string.person_ko_massage)
                .addImage(R.drawable.person_ko_massage));
        return cards;
    }

    /**
     * Navigates to card at given position.
     */
    private void navigateToCard(int position) {
        mCardScroller.animate(position, CardScrollView.Animation.NAVIGATION);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {

            Log.i("PersonKoExplanationActivity", "Log: " + current_position);
            switch (current_position) {

                case 0:
                    navigateToCard(1);
                    current_position = 1;
                    break;
                case 1:
                    navigateToCard(2);
                    current_position = 2;
                    break;
                case 2:
                    navigateToCard(0);
                    current_position = 0;
                    break;
                default:
                    return true;
            }
            mCardScroller.setAdapter(new CardAdapter(createMenu(this)));
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }


}
