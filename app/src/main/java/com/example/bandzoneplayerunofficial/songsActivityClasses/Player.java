package com.example.bandzoneplayerunofficial.songsActivityClasses;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import com.example.bandzoneplayerunofficial.helpers.PlayerHelper;
import com.example.bandzoneplayerunofficial.interfaces.BandProfileItem;
import com.example.bandzoneplayerunofficial.objects.Track;
import java.util.List;

public class Player {
    private MediaPlayer mediaPlayer;
    private List<BandProfileItem> items;
    private int currentTrack;
    private int lastTrack;
    private Uri uri;
    private Context context;
    private TracksAdapter adapterThis;

    public Player(Context c, List<BandProfileItem> i, TracksAdapter a) {
        context = c;
        items = i;
        adapterThis = a;
        lastTrack = items.size() - 1;
    }

    public int next() {
        return (currentTrack < lastTrack) ? (currentTrack+1) : (0);
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void toggle() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    public void play(int order) {
        currentTrack = order;
        lastTrack = items.size() - 1; // because on construction length is 0
        if (items.get(currentTrack).getClass() != Track.class) {
            play(next());
        }
        uri = Uri.parse(((Track) items.get(currentTrack)).getHref());

        PlayerHelper.updatePlayState(items, (Track) items.get(currentTrack));
        adapterThis.notifyDataSetChanged();

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create((Activity) context, uri);
        } else {
            killMediaPlayer();
            mediaPlayer = MediaPlayer.create((Activity) context, uri);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play(next());
            }
        });

        mediaPlayer.start();
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
