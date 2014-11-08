package lmu.appnight.Hungry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

import lmu.appnight.Classes.CardAdapter;
import lmu.appnight.FirstAidKit.FirstAidKitActivity;
import lmu.appnight.PersonKo.PersonKoActivity;
import lmu.appnight.R;

/**
 * Created by sebastianbinder on 08.11.14.
 */
public final class HungryActivity extends Activity {


    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;

    static final int LICHTHOF = 0;
    static final int MC_DONALDS = 1;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardAdapter(createMenu(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();


    }

    private List<CardBuilder> createMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.hungry_text)
                .addImage(R.drawable.img_hungry));
        cards.add(MC_DONALDS, new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                .setText(R.string.hungry_mcd_text)
                .addImage(R.drawable.img_hungry));

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
                    case LICHTHOF:
                        break;
                    case MC_DONALDS:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:ll=48.157402,11.583742&mode=w")));
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
