package lmu.appnight.PersonKoExplanation;

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

public final class PersonKoExplanationActivity extends Activity {


    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;


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

        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .setText(R.string.recovery_position_menu_text)
                .addImage(R.drawable.person_ko_check));
        //  .setFootnote(R.string.revive_check));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .setText(R.string.emergency_call_menu_text)
                .addImage(R.drawable.person_ko_breath));
        //  .setFootnote(R.string.revive_breath));
        cards.add(new CardBuilder(context, CardBuilder.Layout.TITLE)
                .setText(R.string.emergency_call_menu_text)
                .addImage(R.drawable.person_ko_massage));
        //  .setFootnote(R.string.revive_massage));
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
