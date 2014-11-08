package lmu.appnight.ItSupport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

import lmu.appnight.Classes.CardAdapter;
import lmu.appnight.FirstAidKit.FirstAidKitActivity;
import lmu.appnight.MainActivity;
import lmu.appnight.R;

public final class ItSupportActivity extends Activity {

    private CardScrollView mCardScroller;

    private boolean mVoiceMenuEnabled = true;

    static final int NETWORK = 0;
    static final int PRINTER = 1;
    static final int COMPUTER = 2;

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
            getMenuInflater().inflate(R.menu.it_support_voice_menu, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.it_support_voice_menu, menu);
        return true;
    }


    private List<CardBuilder> createMenu(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();

        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.it_support_network_menu_text)
                //.setIcon(R.drawable.ic_it_support_network_menu)
        );
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.it_support_printer_menu_text)
                //.setIcon(R.drawable.ic_it_support_printer_menu)
        );
        cards.add(new CardBuilder(context, CardBuilder.Layout.MENU)
                        .setText(R.string.it_support_computer_menu_text)
                //.setIcon(R.drawable.ic_it_support_computer_menu)
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

    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int soundEffect = Sounds.TAP;
                switch (position) {
                    case NETWORK:
                        break;
                    case PRINTER:
                        startActivity(new Intent(ItSupportActivity.this, FirstAidKitActivity.class));
                        break;
                    case COMPUTER:
                        break;
                    case R.id.menu_back:
                        startActivity(new Intent(ItSupportActivity.this, MainActivity.class));
                        break;
                    default:
                        soundEffect = Sounds.ERROR;
                }
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        });
    }

}
