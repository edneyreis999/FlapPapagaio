package br.com.edney.flappapagaio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Desktop on 01/05/2017.
 */

public class Button {
    private Rectangle button;

    public boolean high = false;
    private float highf = 1.1f;

    private Texture texture;

    public Button(Texture texture, int posX, int posY, int size){
        this.texture = texture;
        button = new Rectangle(posX, posY, size, size);

    }

    public void draw(SpriteBatch batch){
        if(high){
            batch.draw(texture, button.x - (button.getWidth() * (highf -1))/2,
                    button.y - (button.getHeight() * (highf -1))/2,
                    button.getWidth() * highf, button.getHeight() * highf);
        }else{
            batch.draw(texture, button.x, button.y, button.getWidth(), button.getHeight());
        }
    }

    public boolean verif(int x, int y){
        if(button.x <= x && button.x + button.width >= x &&
                button.y <= y && button.y + button.height >= y){
            high = true;
        }else{
            high = false;
        }
        return high;
    }

    public void dispose(){
        texture.dispose();
    }
}
