package code.forms;

//This is used to make instances of VLJC media players.
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;

//This is used to manage a media player that can be embedded in a JPanel.
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import java.awt.Canvas;
import java.awt.BorderLayout;


public class Home extends JPanel
{
    private HomeOverlay homeOverlay;

    //This is used to create media players and other resources.
    private MediaPlayerFactory factory;

    //This is the actual VLJC media player that will play the video.
    private EmbeddedMediaPlayer mediaPlayer;

    public Home()
    {
        init();
    }

    private void init()
    {
        //This creates a new Media player instance responsible for producing media players.
        factory=new MediaPlayerFactory();

        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();

        //Creates a canvas object which is where the video will be displayed.
        Canvas canvas = new Canvas();

        //Sets canvas as the new surface to display video.
        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));

        setLayout(new BorderLayout());

        //Adds the canvas to the panel so it can be displayed.
        add(canvas);
    }

    public void initOverlay(JFrame frame)
    {
        homeOverlay = new HomeOverlay(frame);

        //Sets HomeOverlay as the video’s playing overlay.
        mediaPlayer.overlay().set(homeOverlay);

        mediaPlayer.overlay().enable(true);
    }

    public void play()
    {
        if(mediaPlayer.status().isPlaying())
        {
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play("video/video 3.mp4");

    }

    public void stop()
    {
        mediaPlayer.controls().stop();
        mediaPlayer.release();
        factory.release();
    }
}
