package br.com.edney.flappapagaio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Desktop on 01/05/2017.
 */

public class Som {
    private Sound voa;
    private Sound hit;
    private Sound score;

    public Som(){
        voa = Gdx.audio.newSound(Gdx.files.internal("sons/somVoa.mp3"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sons/errou.mp3"));
        score = Gdx.audio.newSound(Gdx.files.internal("sons/birl.mp3"));
    }

    public void play(String som){
        if(som.equals("voa")){
            voa.play();
        }else if(som.equals("hit")){
            hit.play();
        }else if(som.equals("score")){
            score.play();
        }
    }

    public void dispose(){
        voa.dispose();
        hit.dispose();
        score.dispose();
    }
}
