package br.com.edney.flappapagaio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static br.com.edney.flappapagaio.Constantes.*;

/**
 * Created by Desktop on 01/05/2017.
 */

public class Cano {
    private Texture texture;
    public Rectangle corpo;
    private boolean cima;


    public Cano(float posX, float posY, boolean cima){
        this.cima = cima;
        if(cima){
            corpo = new Rectangle(posX, posY, canoLargura, canoAltura);
        }else{
            corpo = new Rectangle(posX, posY - screeny, canoLargura, canoAltura);
        }

        texture = new Texture("cano.png");
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, corpo.x, corpo.y, corpo.getWidth(), corpo.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), false, cima);
    }

    public int update(float deltaTime){
        corpo.x += velCanoX * deltaTime;
        if(corpo.x + corpo.getWidth() < 0){
            return 1;
        }
        return 0;
    }

    public void dispose(){
        texture.dispose();
    }
}
