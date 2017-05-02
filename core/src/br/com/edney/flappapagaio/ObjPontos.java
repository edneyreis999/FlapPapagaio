package br.com.edney.flappapagaio;

import com.badlogic.gdx.math.Rectangle;
import static br.com.edney.flappapagaio.Constantes.*;
/**
 * Created by Desktop on 01/05/2017.
 */

public class ObjPontos {
    public Rectangle corpo;
    public ObjPontos(float posx, float posy){
        corpo = new Rectangle(posx, posy, 10, abertura);
    }

    public int update(float time){
        corpo.x += velCanoX * time;
        if(corpo.x + corpo.getWidth() < 0){
            return 1;
        }
        return 0;
    }
}
