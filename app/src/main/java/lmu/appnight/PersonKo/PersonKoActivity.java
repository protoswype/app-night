package lmu.appnight.PersonKo;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lmu.appnight.Classes.CardAdapter;
import lmu.appnight.R;

/**
 * Created by sebastianbinder on 08.11.14.
 */
public final class PersonKoActivity extends Activity {


    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;

    static final int PERSON_KO_EXPLANATION = 0;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardAdapter(createMenu(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
    }


    private List<CardBuilder> createMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();


        cards.add(PERSON_KO_EXPLANATION, new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.recovery_position_menu_text)
                .setIcon(R.drawable.ic_person_ko_menu)
                .setFootnote(R.string.person_ko_menu_desc));

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
