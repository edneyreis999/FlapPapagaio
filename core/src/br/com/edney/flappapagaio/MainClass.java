package br.com.edney.flappapagaio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static br.com.edney.flappapagaio.Constantes.papagaioIniX;
import static br.com.edney.flappapagaio.Constantes.papagaioIniY;
import static br.com.edney.flappapagaio.Constantes.timeToNextCano;
import static br.com.edney.flappapagaio.Constantes.*;

public class MainClass extends ApplicationAdapter {

	private SpriteBatch batch;
	private Fundo fundo;
	private Papagaio papagaio;
	private List<Cano> canos;
	private List<ObjPontos> objsPontos;
	private float timeCano;
	private int pontos;
	private boolean pegouPonto;

	private BitmapFont fonte;
	private GlyphLayout glyphLayout;

	private Button btnStart;
	private Button btnRestart;
	private Som som;

	private int estado = 0;  // 0 - parado // 1 - rodando // 2 - perdeu // 3 - botao restart
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Fundo();
		papagaio = new Papagaio(papagaioIniX, papagaioIniY);
		canos = new ArrayList<Cano>();
		objsPontos = new ArrayList<ObjPontos>();
		pontos = 0;
		pegouPonto = false;
		btnStart = new Button(new Texture("botoes/BotaoPlay.png"), btnX, btnY, btnSize);
		btnRestart = new Button(new Texture("botoes/BotaoReplay.png"), btnX, btnY, btnSize);
		som = new Som();

		FreeTypeFontGenerator.setMaxTextureSize(2048);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int) (0.2f * screenx);
		parameter.color = new Color(1,1,1,1);
		fonte = generator.generateFont(parameter);
		generator.dispose();

		glyphLayout = new GlyphLayout();

		this.timeCano = timeToNextCano;
	}

	@Override
	public void render () {
		input();
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		draw();

		batch.end();
	}

	private void input() {
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX();
			int y = screeny - Gdx.input.getY();
			if(estado == 0){
				btnStart.verif(x,y);
			} else if(estado == 1){
				papagaio.impulso();
				som.play("voa");
			}else if(estado == 3){
				btnRestart.verif(x,y);
			}
		}else if(!Gdx.input.isTouched()){
			if(btnStart.high){
				estado =1;
				btnStart.high = false;
			}
			if(btnRestart.high){
				estado = 1;
				papagaio.restart(papagaioIniX, papagaioIniY);
				canos.clear();
				timeCano = timeToNextCano;
				pontos = 0;
				pegouPonto = false;
				objsPontos.clear();
				btnRestart.high = false;
			}

			Gdx.app.log("Log", "impulso");
		}
	}

	private void update(float deltaTime) {
		if(estado == 1){
			fundo.update(deltaTime);
			for (int i = 0; i < canos.size(); i++) {
				if(canos.get(i).update(deltaTime) == 1){
					canos.remove(i);
					i--;
				}
			}
			for (int i = 0; i < objsPontos.size(); i++) {
				if(objsPontos.get(i).update(deltaTime) == 1){
					objsPontos.remove(i);
					i--;
				}
			}

			timeCano -= deltaTime;
			if(timeCano <= 0){
				Random random = new Random();
				int pos = random.nextInt(posMax);
				// Para poder vir numeros negativos também
				pos -= posMax /2;

				// cano de cima
				canos.add(new Cano(screenx, screeny /2 + pos + abertura/2, true));
				// cano de baixo
				canos.add(new Cano(screenx, screeny /2 + pos - abertura/2, false));
				objsPontos.add(new ObjPontos(screenx + canoLargura + 2* papagaioRaio, screeny /2 + pos - abertura/2));
				timeCano = timeToNextCano;
			}

			for (Cano cano:canos) {
				if(Intersector.overlaps(papagaio.corpo, cano.corpo)){
					// morreu
					papagaio.impulsoParaTras();
					som.play("hit");
					estado = 2;
				}
			}
			boolean inter = false;
			for (ObjPontos ponto:objsPontos) {
				if(Intersector.overlaps(papagaio.corpo, ponto.corpo)){
					// soma pontos
					inter = true;
					if(!pegouPonto){
						pontos ++;
						som.play("score");
						Gdx.app.log("Log", String.valueOf(pontos));
						pegouPonto = true;
					}
				}
			}
			if(!inter){
				pegouPonto = false;
			}
		}
		if(estado == 1 || estado == 2){
			if(papagaio.update(deltaTime) == 1){
				estado = 3;
			}
		}
	}

	private void draw(){
		fundo.draw(batch);
		for (Cano cano:canos) {
			cano.draw(batch);
		}
		papagaio.draw(batch);
		fonte.draw(batch, String.valueOf(pontos), (screenx - getTamXTexto(fonte, String.valueOf(pontos)))/2,
				margemYPontos);

		if(estado == 0){
			btnStart.draw(batch);
		}else if(estado == 3){
			btnRestart.draw(batch);
		}
	}

	private float getTamXTexto(BitmapFont fonte, String texto){
		glyphLayout.reset();
		glyphLayout.setText(fonte, texto);
		return glyphLayout.width;
	}
	
	@Override
	public void dispose () {
		fundo.dispose();
		papagaio.dispose();
		for (Cano cano:canos) {
			cano.dispose();
		}
		fonte.dispose();
		som.dispose();
		btnStart.dispose();
		btnRestart.dispose();
		batch.dispose();
	}
}
