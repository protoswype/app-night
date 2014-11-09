package lmu.appnight;

import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.media.Sounds;


import android.app.Activity;
import android.net.Uri;
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
import lmu.appnight.FirstAidKit.FirstAidKitActivity;
import lmu.appnight.Hungry.HungryActivity;
import lmu.appnight.ItSupport.ItSupportActivity;
import lmu.appnight.PersonKo.PersonKoActivity;


/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p/>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */


/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class MainActivity extends Activity {

    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;

    private boolean mVoiceMenuEnabled = true;

    static final int PERSON_KO = 0;
    static final int FIRST_AID = 1;
    static final int EMERGENCY_EXIT = 2;
    static final int HUNGRY = 3;
    static final int IT_SUPPORT = 4;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createMainMenu(this)));
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
            getMenuInflater().inflate(R.menu.voice_menu, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.voice_menu, menu);
        return true;
    }

    /**
     * Creates list of cards that showcase different type of {@link CardBuilder} API.
     */
    private List<CardBuilder> createMainMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(PERSON_KO, new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.person_ko_menu_text)
                        .setIcon(R.drawable.ic_person_ko_menu)
        );
        cards.add(FIRST_AID, new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.first_aid_kit_menu_text)
                        .setIcon(R.drawable.ic_first_aid_kit_menu)
        );
        cards.add(EMERGENCY_EXIT, new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.emergency_exit_menu_text)
                        .setIcon(R.drawable.ic_emergency_exit_menu)
        );
        cards.add(HUNGRY, new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.hungry_menu_text)
                        .setIcon(R.drawable.ic_hungry_menu)
        );
        cards.add(IT_SUPPORT, new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.it_support_menu_text)
                        .setIcon(R.drawable.pc6)
        );

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
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            return mVoiceMenuEnabled;
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.menu_person_ko:
                    startActivity(new Intent(MainActivity.this, PersonKoActivity.class));
                    break;
                case R.id.menu_first_aid:
                    startActivity(new Intent(MainActivity.this, FirstAidKitActivity.class));
                    break;
                case R.id.menu_hungry:
                    startActivity(new Intent(MainActivity.this, HungryActivity.class));
                    break;
                case R.id.menu_it_support:
                    startActivity(new Intent(MainActivity.this, ItSupportActivity.class));
                    break;
                default:
                    return true;
            }
            mCardScroller.setAdapter(new CardAdapter(createMainMenu(this)));
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Different type of activities can be shown, when tapped on a card.
     */
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "Clicked view at position " + position + ", row-id " + id);
                int soundEffect = Sounds.TAP;
                switch (position) {
                    case PERSON_KO:
                        startActivity(new Intent(MainActivity.this, PersonKoActivity.class));
                        break;
                    case FIRST_AID:
                        startActivity(new Intent(MainActivity.this, FirstAidKitActivity.class));
                        break;
                    case EMERGENCY_EXIT:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:ll=48.128623,11.563547&mode=w")));
                        break;
                    case HUNGRY:
                        startActivity(new Intent(MainActivity.this, HungryActivity.class));
                        break;
                    case IT_SUPPORT:
                        startActivity(new Intent(MainActivity.this, ItSupportActivity.class));
                        break;
                    default:
                        soundEffect = Sounds.ERROR;
                        //Log.d(TAG, "Don't show anything");
                }

                // Play sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        });
    }


}