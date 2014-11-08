package lmu.appnight;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


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
/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class MainActivity extends Activity {

    private CardScrollView mCardScroller;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        setContentView(mCardScroller);
    }

    /**
     * Creates list of cards that showcase different type of {@link CardBuilder} API.
     */
    private List<CardBuilder> createCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        // Add cards that demonstrate TEXT layouts.
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.person_ko_menu_text)
                .setIcon(R.drawable.ic_person_ko_menu)
                .setFootnote(R.string.person_ko_menu_desc));
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.first_aid_kit_menu_text)
                .setIcon(R.drawable.ic_first_aid_kit_menu)
                .setFootnote(R.string.first_aid_kit_menu_desc));
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                .setText(R.string.emergency_exit_menu_text)
                .setIcon(R.drawable.ic_emergency_exit_menu)
                .setFootnote(R.string.emergency_exit_menu_desc));

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
