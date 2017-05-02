package br.com.edney.flappapagaio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.edney.flappapagaio.Constantes.screenx;
import static br.com.edney.flappapagaio.Constantes.screeny;
import static br.com.edney.flappapagaio.Constantes.velCanoX;

/**
 * Created by Desktop on 30/04/2017.
 */

public class Fundo {
    private Texture texture;
    private float posx1;
    private float posx2;

    public Fundo(){
        texture = new Texture("fundo.png");

        posx1 = 0;
        // final da tela
        posx2 = screenx;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, posx1, 0, screenx, screeny);
        batch.draw(texture, posx2, 0, screenx, screeny);


    }

    public void update(float time){
        posx1 += time * velCanoX;
        posx2 += time * velCanoX;

        if(posx1 + screenx <= 0){
            posx1 = screenx;
            posx2 = 0;
        }
        if(posx2 + screenx <= 0){
            posx2 = screenx;
            posx1 = 0;
        }
    }

    public void dispose(){
        texture.dispose();
    }
}
