package br.com.edney.flappapagaio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static br.com.edney.flappapagaio.Constantes.gravidade;
import static br.com.edney.flappapagaio.Constantes.impulso;
import static br.com.edney.flappapagaio.Constantes.papagaioRaio;
import static br.com.edney.flappapagaio.Constantes.screeny;
import static br.com.edney.flappapagaio.Constantes.velCanoX;

/**
 * Created by Desktop on 30/04/2017.
 */

public class Papagaio{

    public Circle corpo;
    private Texture[] sprites;
    private float auxSprites;
    private Vector2 velocidade;


    public Papagaio(int posx, int posy){
        corpo = new Circle(posx, posy, papagaioRaio);
        sprites =  new Texture[6];

        for (int i = 1; i <= 6; i++) {
            sprites[i - 1] = new Texture("felpudo/felpudoVoa" + i +".png");
        }

        velocidade = new Vector2(0,0);
    }

    public void draw(SpriteBatch batch){
        //(int)auxSprites % 6 => para que não passe de 6
        batch.draw(sprites[(int)auxSprites % 6], (corpo.x - corpo.radius), (corpo.y - corpo.radius), corpo.radius *2, corpo.radius *2);
    }

    public int update(float deltaTime){
        auxSprites += 6 * deltaTime;

        corpo.x += velocidade.x * deltaTime;
        corpo.y += velocidade.y * deltaTime;
        velocidade.y -= gravidade * deltaTime;

        // verifica se o papagaio passou do limite superior da tela
        if(corpo.y + corpo.radius >= screeny){
            corpo.y = screeny - corpo.radius;
            velocidade.y = -impulso;
        }
        // verifica se o papagaio bateu no chão
        if(corpo.y - corpo.radius <= 0){
            corpo.y = corpo.radius;
            velocidade.y = impulso;
        }

        // verifica se o papagaio saiu da tela pela esquerda
        if(corpo.x + corpo.radius <= 0){
            return 1;
        }
        return 0;
    }

    public void dispose(){
        for (int i = 0; i < 6; i++) {
            sprites[i].dispose();
        }
    }

    public void impulso() {
        velocidade.y += impulso;
    }

    public void impulsoParaTras(){
        velocidade.x = 2*velCanoX;
        velocidade.y = 0;
    }

    public void restart(int posX, int posY){
        corpo = new Circle(posX, posY, papagaioRaio);
        velocidade = new Vector2(0,0);
    }
}
