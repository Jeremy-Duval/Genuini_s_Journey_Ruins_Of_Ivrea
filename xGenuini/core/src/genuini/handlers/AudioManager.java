/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * @author Adrien
 */
public class AudioManager {

    private Sound sound;
    private static long id_sound = -1; //initialise at the error id
    
    public AudioManager(String music){
        sound = Gdx.audio.newSound(Gdx.files.internal(music));
    }

    public AudioManager() {
        
    }
    
    
    
    /**
     * Initialise the music to play
     * @param music : String : location and name of the music : exemple : /sounds/music.mp3
     * @author Jérémy
     * @since 15/01/2017
     */
    public void setMusic(String music){
        sound = Gdx.audio.newSound(Gdx.files.internal(music));
    }
    
    /**
     * Play the music
     * @param volume : float : music's volume
     * @param loop : boolean : true to loop the music, false else
     * @author Jérémy
     * @param id_sound
     * @since 15/01/2017
     */
    public void playMusic(float volume, boolean loop, long id_sound){
        id_sound = sound.play(volume);
        if(id_sound>-1){ //if there is no error
            sound.setLooping(id_sound, loop);
        }
    }
    
    /**
     * Stop the music
     * @author Jérémy
     * @since 15/01/2017
     */
    public void stopMusic(){
        sound.stop(id_sound);
    }
    
    /**
     * Stop the music with the id in parameter
     * @param id_music : long : id of the music to stop
     * @author Jérémy
     * @since 15/01/2017
     */
    public void stopMusic(long id_music){
        sound.stop(id_music);
    }
    
    /**
     * Set the music in pause 
     * @author Jérémy
     * @since 15/01/2017
     */
    public void pauseMusic(){
        sound.pause(id_sound);
    }
    
    /**
     * Resume the music in pause 
     * @author Jérémy
     * @since 15/01/2017
     */
    public void resumeMusic(){
        sound.pause(id_sound);
    }
    
    /**
     * Release all ressources for sounds
     * @author Jérémy
     * @since 15/01/2017
     */
    public void dispose(){
        sound.dispose();
    }
    
    /**
     * Change the music's volume
     * @param volume : float : music's volume
     * @author Jérémy
     * @since 15/01/2017
     */
    public void setVolumeMusic(float volume){
        sound.setVolume(id_sound, volume);
    }
    
    /**
     * Return the current music's id
     * @return : id_sound : long
     * @author Jérémy
     * @since 15/01/2017
     */
    public long getIdMusic(){
        return id_sound;
    }
}
