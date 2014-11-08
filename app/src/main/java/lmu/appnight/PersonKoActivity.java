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

/**
 * Created by sebastianbinder on 08.11.14.
 */
public final class PersonKoActivity extends Activity {


    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardAdapter(createPersonKoMenu(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
    }


    private List<CardBuilder> createPersonKoMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.recovery_position_menu_text)
                .setIcon(R.drawable.ic_person_ko_menu)
                .setFootnote(R.string.recovery_position_menu_desc));
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.emergency_call_menu_text)
                .setIcon(R.drawable.ic_emergency_call_menu)
                .setFootnote(R.string.emergency_call_menu_desc));

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

}
