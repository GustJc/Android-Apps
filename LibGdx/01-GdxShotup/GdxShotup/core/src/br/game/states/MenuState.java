package br.game.states;

import static br.game.handlers.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import br.game.GdxGame;
import br.game.entities.B2DSprite;
import br.game.entities.Cave;
import br.game.entities.Player;
import br.game.entities.Shot;
import br.game.entities.Slimy;
import br.game.handlers.GameInput;
import br.game.handlers.GameStateManager;

public class MenuState extends GameState {
	
	private Texture background;
	private Music music;
	private Rectangle rect;
	
	private ArrayList<B2DSprite> objects;
	private World world;
	private float timer = 0;
	private Shot shot;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		world = new World(new Vector2(0.0f,-2.8f), true);
		objects = new ArrayList<B2DSprite>();
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		Body b;
		// create slimys
		Slimy s;
		bdef.position.set(8 / PPM, 8 / PPM);
		b = world.createBody(bdef);
		s = new Slimy(b);
		objects.add(s);
		
		bdef.position.set( (8+120) / PPM, (8+20) / PPM);
		b = world.createBody(bdef);
		s = new Slimy(b);
		objects.add(s);
		
		bdef.position.set( (8+190) / PPM, (8+8) / PPM);
		b = world.createBody(bdef);
		s = new Slimy(b);
		objects.add(s);
		
		bdef.position.set( (8+260) / PPM, (8+2) / PPM);
		b = world.createBody(bdef);
		s = new Slimy(b);
		objects.add(s);
		
		bdef.position.set( (8+60) / PPM, (8+12) / PPM);
		b = world.createBody(bdef);
		s = new Slimy(b);
		objects.add(s);
		
		// create player
		Player p;
		bdef.position.set( (8+180) / PPM, (8+160) / PPM);
		b = world.createBody(bdef);
		p = new Player(b);
		p.setState(Player.ATTACK);
		objects.add(p);
		
		// cave
		Cave c;
		bdef.position.set( (8+160) / PPM, (8+16) / PPM);
		b = world.createBody(bdef);
		c = new Cave(b);
		objects.add(c);
		
		// Shot
		bdef.position.set( (8+190) / PPM, (8+140) / PPM);
		bdef.type = BodyType.DynamicBody;
		FixtureDef f = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10/PPM, 10/PPM);
		f.shape = shape;
		b = world.createBody(bdef);
		b.createFixture(f);
		shot = new Shot(b);
		objects.add(shot);
		
		
			
		rect = new Rectangle(88, 50, 75, 40);
		
		background = GdxGame.res.getTexture("menu background");
		music = GdxGame.res.getMusic("myday");
		music.setLooping(true);
		music.play();
	}

	@Override
	public void handleInput() {
		if(GameInput.isDown(Input.Keys.ENTER) ) {
			gsm.setState(GameStateManager.PLAY);
		}
		else 
		if(GameInput.isDown() && rect.contains(GameInput.x, GameInput.y) ) {
			gsm.setState(GameStateManager.PLAY);			
		}

	}

	@Override
	public void update(float dt) {
		handleInput();

		world.step(dt, 4, 2);
		
		for(B2DSprite s : objects) {
			s.update(dt);
		}
		
		timer += dt;
		if(timer > 9.6f && timer < 9.8f) {
			Slimy s = (Slimy) objects.get(2);
			s.playDeathAnimation();
		}
		
		if(shot.getPosition().y < 10 / PPM) {
			shot.getBody().setTransform(new Vector2(198/PPM, 170/PPM), 0);
			shot.getBody().setLinearVelocity(0, 0);
		}
		
	}

	@Override
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		sb.end();
		
		for(B2DSprite s : objects) {
			s.render(sb);
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
