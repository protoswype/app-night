package lmu.appnight;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.media.Sounds;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import lmu.appnight.Classes.CardAdapter;
import lmu.appnight.FirstAidKit.FirstAidKitActivity;
import lmu.appnight.Hungry.HungryActivity;
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





    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardAdapter(createMainMenu(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();
    }


    static final int PERSON_KO = 0;
    static final int FIRST_AID = 1;
    static final int EMERGENCY_EXIT = 2;
    static final int HUNGRY = 3;


    /**
     * Creates list of cards that showcase different type of {@link CardBuilder} API.
    */
    private List<CardBuilder> createMainMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(PERSON_KO, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.person_ko_menu_text)
                .setIcon(R.drawable.ic_person_ko_menu)
                .setFootnote(R.string.person_ko_menu_desc));
        cards.add(FIRST_AID, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.first_aid_kit_menu_text)
                .setIcon(R.drawable.ic_first_aid_kit_menu)
                .setFootnote(R.string.first_aid_kit_menu_desc));
        cards.add(EMERGENCY_EXIT, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.emergency_exit_menu_text)
                .setIcon(R.drawable.ic_emergency_exit_menu)
                .setFootnote(R.string.emergency_exit_menu_desc));
        cards.add(HUNGRY, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.hungry_menu_text)
                .setIcon(R.drawable.ic_hungry_menu)
                .setFootnote(R.string.hungry_menu_desc));

        return cards;
    }





    private List<CardBuilder> createHungryLocationsMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.hungry_text)
                .addImage(R.drawable.img_hungry)
                .setFootnote(R.string.hungry_desc));

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
                    case HUNGRY:
                        startActivity(new Intent(MainActivity.this, HungryActivity.class));
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