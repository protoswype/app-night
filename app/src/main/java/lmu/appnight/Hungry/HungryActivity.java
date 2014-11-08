package lmu.appnight.Hungry;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

import lmu.appnight.Classes.CardAdapter;
import lmu.appnight.R;

/**
 * Created by sebastianbinder on 08.11.14.
 */
public final class HungryActivity extends Activity {


    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardAdapter(locatingMenu(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);


        mAdapter = new CardAdapter(createMenu(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);


    }


    private List<CardBuilder> locatingMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

           cards.add(new CardBuilder(context, CardBuilder.Layout.COLUMNS)
                    .setText("Locating..."));
                    //.addImage(R.drawable.img_hungry)
                    //.setFootnote(R.string.hungry_desc));

        return cards;
    }

    private List<CardBuilder> createMenu(Context context) {
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

}
