package br.com.edney.flappapagaio;

import com.badlogic.gdx.Gdx;

/**
 * Created by Desktop on 30/04/2017.
 */

public class Constantes {
    // Constantes de aparelho
    public static int screenx = Gdx.graphics.getWidth();
    public static int screeny = Gdx.graphics.getHeight();

    // Constantes de jogo
    public static float velCanoX = -0.3f * screenx;
    public static float gravidade = screeny/1.5f;
    public static float impulso = screeny/5f;
    public static float timeToNextCano = 3f;

    // Constantes do papagaio
    public static int papagaioRaio = (int) (0.06f * screenx);
    public static int papagaioIniX = (int) (0.2f * screenx);
    public static int papagaioIniY =  screeny /2;

    // Constantes do cano
    public static int canoLargura = (int) (0.2f * screenx);
    public static int canoAltura = screeny;
    public static int posMax = (int) (0.7f*screeny);
    public static int abertura = (int) (0.2f * screeny);

    // Constrantes de HUD
    public static float margemYPontos = 0.98f * screeny;
    public static int btnSize = (int) (0.4f * screenx);
    public static int btnX = (int) (0.3f * screenx);
    public static int btnY = (int) (screeny - btnSize)/2;

}
