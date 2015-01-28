package br.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import br.game.GdxGame;
import br.game.handlers.GameStateManager;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected GdxGame game;
	
	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	protected GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
		game = gsm.getGame();
		sb   = game.getSpriteBatch();
		cam  = game.getCamera();
		hudCam  = game.getHUDCamera();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
}
