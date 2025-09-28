package hyper.giga.ultra.gaming.menu.plus.cool;

import hyper.giga.ultra.gaming.menu.plus.ErrorHandler;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CoolSound
{
    private Clip clip;
    private FloatControl volumeControl;
    private boolean loop;
    
    public CoolSound(String filePath)
    {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exc) {
            ErrorHandler.handleError(exc, String.format("Unable to open audio file %s", filePath));
        }
    }
    
    public void play()
    {
        if (clip == null) {
            return;
        }

        clip.setFramePosition(0);
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        clip.start();
    }
    
    public void stop()
    {
        if (clip != null) {
            clip.stop();
        }
    }
    
    public void setVolume(float value)
    {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float gain = min + (max - min) * value;
            volumeControl.setValue(gain);
        }
    }
    
    public void setLooping(boolean loop)
    {
        this.loop = loop;
    }
}
