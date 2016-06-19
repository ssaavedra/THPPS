/*
 * THPPS
 * Copyright © 2016 - Santiago Saavedra López <ssaavedra@gpul.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.ssaavedra.thpps;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JarvisSession extends VoiceInteractionSession {
    private static final String TAG = "JarvisSession";


    private View mContentView;
    private TextView mText;
    private Button mStartButton;
    private Button mConfirmButton;
    private Button mCompleteButton;
    private Button mAbortButton;

    private int mState;


    public JarvisSession(Context context) {
        super(context);
    }

    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {
        super.onHandleAssist(data, structure, content);

        Log.i(TAG, "onHandleAssist with structure = " + structure);

        mText.setText("I will at some point assist you about " + structure
                .getActivityComponent().flattenToString());

        Toast.makeText(getContext(), "v2: Called inside " +
                structure.getActivityComponent()
                        .flattenToShortString(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public View onCreateContentView() {
        mContentView = getLayoutInflater().inflate(R.layout
                .voice_interaction_session, null);
        mText = (TextView) mContentView.findViewById(R.id.text);

        return mContentView;
    }
}
