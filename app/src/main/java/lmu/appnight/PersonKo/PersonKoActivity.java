package lmu.appnight.PersonKo;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
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
import lmu.appnight.MainActivity;
import lmu.appnight.PersonKoExplanation.PersonKoExplanationActivity;
import lmu.appnight.R;

/**
 * Created by sebastianbinder on 08.11.14.
 */
public final class PersonKoActivity extends Activity {

    private CardScrollView mCardScroller;
    private boolean mVoiceMenuEnabled = true;

    static final int PERSON_KO_EXPLANATION = 0;
    static final int CALL_AMBULANCE = 1;

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
                getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);
            }
        });
        setContentView(mCardScroller);
        setCardScrollerListener();
    }



    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.person_ko_voice_menu, menu);
            return true;
        }
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person_ko_voice_menu, menu);
        return true;
    }


    private List<CardBuilder> createMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();


        cards.add(PERSON_KO_EXPLANATION, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.recovery_position_menu_text)
                .setIcon(R.drawable.ic_person_ko_menu));
        cards.add(CALL_AMBULANCE, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.emergency_call_menu_text)
                .setIcon(R.drawable.ic_emergency_call_menu));

        return cards;
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
            switch (item.getItemId()) {
                case R.id.menu_person_ko_reanimation:
                    startActivity(new Intent(PersonKoActivity.this, PersonKoExplanationActivity.class));
                    break;
                case R.id.menu_person_ko_emergency:
                    break;
                case R.id.menu_back:
                    startActivity(new Intent(PersonKoActivity.this, MainActivity.class));
                    break;
                default: return true;
            }
            mCardScroller.setAdapter(new CardAdapter(createMenu(this)));
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int soundEffect = Sounds.TAP;
                switch (position) {
                    case PERSON_KO_EXPLANATION:
                        startActivity(new Intent(PersonKoActivity.this, PersonKoExplanationActivity.class));
                        break;
                    case CALL_AMBULANCE:
                        break;
                    default:
                        soundEffect = Sounds.ERROR;
                }

                // Play sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        });
    }
}
