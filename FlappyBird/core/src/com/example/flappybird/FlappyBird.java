package com.example.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private Texture[] birds = new Texture[2];
	private Texture topTube;
	private Texture bottomTube;
	private Texture gameover;
	private Texture button;
	private int birdstate=0;
	private float birdY=0;
	private float velocity;
	private float velocitydata;
	private int gameState=0;
	private float gravity=2;
	private float gap=450;
	private float[] tubeX;
	private float tubeDistance=0;
	private int tubeVelocity=5;
	private int numberOfTubes=4;
	private Random random;
	private float[] offset;
	private float maxOffset;
	private Circle birdCircle;
	private Rectangle topTubeRectangle;
	private Rectangle bottomTubeRectangle;
	private ShapeRenderer shapeRenderer;
	private int scorePosition=250;
	private int score=0;
	private int scoringTube=0;
	private String yourScoreName;
	int bestScore=0;
	private String best;
	private BitmapFont bestBitmapFont;
	private BitmapFont yourBitmapFontName;
	private OrthographicCamera camera;
	private int buttongap;
	Vector3 tmp;
	Rectangle textureBounds;
	Preferences preferences;



	@Override
	public void create () {

		batch = new SpriteBatch();
		preferences = Gdx.app.getPreferences("Best Score");
		birdCircle = new Circle();
		topTubeRectangle = new Rectangle();
		bottomTubeRectangle = new Rectangle();
		shapeRenderer = new ShapeRenderer();

		camera=new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		score = 0;
		yourScoreName = "Score: 0";
		yourBitmapFontName = new BitmapFont();
		yourBitmapFontName.setColor(Color.WHITE);

		bestScore=preferences.getInteger("Score");
		best="Best Score: "+bestScore;
		bestBitmapFont = new BitmapFont();
		bestBitmapFont.setColor(Color.GOLD);

		if (Gdx.graphics.getWidth()<800 || Gdx.graphics.getHeight()<1600){
			gap=278;
			scorePosition=200;
			gravity= (float) 1.2;
			tubeVelocity=3;
			velocitydata=-12;
			buttongap=114;
			yourBitmapFontName.getData().scale(2);
			bestBitmapFont.getData().scale(3);
			birds[0]= new Texture("bird_720.png");
			birds[1]= new Texture("bird2_720.png");
			topTube = new Texture("toptube_720.png");
			bottomTube = new Texture("bottomtube_720.png");
			background = new Texture("bg.png");
			gameover = new Texture("gameover_720.png");
			button = new Texture("play_720.png");
		}else {
			velocitydata=-20;
			buttongap=200;
			yourBitmapFontName.getData().scale(4);
			bestBitmapFont.getData().scale(5);
			birds[0]= new Texture("bird.png");
			birds[1]= new Texture("bird2.png");
			topTube = new Texture("toptube.png");
			bottomTube = new Texture("bottomtube.png");
			background = new Texture("bg.png");
			gameover = new Texture("gameover.png");
			button = new Texture("play.png");
		}
		tubeX=new float[4];
		offset=new float[4];
		maxOffset=Gdx.graphics.getHeight()/2-gap/2-100;
		random=new Random();

		startGame();

	}

	public void startGame(){
		birdY=Gdx.graphics.getHeight()/2-birds[birdstate].getHeight()/2;
		for (int i=0;i<numberOfTubes;i++){
			tubeX[i]=Gdx.graphics.getWidth()+tubeDistance;
			tubeDistance=tubeDistance+Gdx.graphics.getWidth()/2+topTube.getWidth()/2;
			offset[i]=(random.nextFloat()-0.5f)*maxOffset;
		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(birdstate==0){
			birdstate=1;
		}else {
			birdstate=0;
		}

		if (gameState==1){

			if(tubeX[scoringTube]<Gdx.graphics.getWidth()/2){

				if (birdY<Gdx.graphics.getHeight()+100){
					score++;
					yourScoreName =  "Score: "+String.valueOf(score);
					if (score>bestScore){
						bestScore++;
						best="Best Score: "+bestScore;
						preferences.putInteger("Score",bestScore);
						preferences.flush();
					}
					if(scoringTube<numberOfTubes-1){

						scoringTube++;
					}else {
						scoringTube=0;
					}
				}

			}



			for (int i=0;i<numberOfTubes;i++){

				if (tubeX[i]<-topTube.getWidth()){
					tubeX[i]=tubeDistance-topTube.getWidth();
					offset[i]=(random.nextFloat()-0.5f)*maxOffset;

				}else {
					tubeX[i]=tubeX[i]-tubeVelocity;

					batch.draw(topTube,tubeX[i],Gdx.graphics.getHeight()/2+gap/2+offset[i]);
					batch.draw(bottomTube,tubeX[i],Gdx.graphics.getHeight()/2-gap/2-bottomTube.getHeight()+offset[i]);

				}

			}



			if (Gdx.input.isTouched())
			{
				velocity=velocitydata;

			}
			if(birdY>0 || velocity<0){
				velocity=velocity+gravity;
				birdY=birdY-velocity;
			}
			if (birdY<50){
				gameState=2;
			}

			batch.draw(birds[birdstate],Gdx.graphics.getWidth()/2-birds[birdstate].getWidth()/2,birdY);

		}else if (gameState==2){

			batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2);
			batch.draw(button,Gdx.graphics.getWidth()/2-button.getWidth()/2,Gdx.graphics.getHeight()/2-buttongap);

			if (Gdx.input.isTouched()){

				tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(),1);
				camera.unproject(tmp);
				textureBounds=new Rectangle(Gdx.graphics.getWidth()/2-button.getWidth()/2,Gdx.graphics.getHeight()/2-buttongap,button.getWidth(),button.getHeight());
				// texture x is the x position of the texture
				// texture y is the y position of the texture
				// texturewidth is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
				// textureheight is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
				if(textureBounds.contains(tmp.x,tmp.y))
				{
					// you are touching your texture
					velocity=0;
					score=0;
					scoringTube=0;
					tubeDistance=0;
					yourScoreName = "";
					gameState=1;
					startGame();


				}



			}

		} else if (gameState==0){
			if (Gdx.input.isTouched())
			{
				gameState=1;
			}
			batch.draw(birds[birdstate],Gdx.graphics.getWidth()/2-birds[birdstate].getWidth()/2,birdY);
		}




		//yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(batch,yourScoreName,100,Gdx.graphics.getHeight()-scorePosition);
		bestBitmapFont.draw(batch,best,100,Gdx.graphics.getHeight()-100);






		batch.end();

		birdCircle.set(Gdx.graphics.getWidth()/2,birdY+birds[birdstate].getHeight()/2,birds[birdstate].getWidth()/2);
		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		shapeRenderer.end();*/
		for (int i=0;i<numberOfTubes;i++){
			topTubeRectangle.set(tubeX[i],Gdx.graphics.getHeight()/2+gap/2+offset[i],topTube.getWidth(),topTube.getHeight());
			bottomTubeRectangle.set(tubeX[i],Gdx.graphics.getHeight()/2-gap/2-bottomTube.getHeight()+offset[i],bottomTube.getWidth(),bottomTube.getHeight());
			/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.rect(topTubeRectangle.x,topTubeRectangle.y,topTubeRectangle.width,topTubeRectangle.height);
			shapeRenderer.rect(bottomTubeRectangle.x,bottomTubeRectangle.y,bottomTubeRectangle.width,bottomTubeRectangle.height);
			shapeRenderer.end();*/

			if (Intersector.overlaps(birdCircle,topTubeRectangle)||Intersector.overlaps(birdCircle,bottomTubeRectangle)){
				gameState=2;
			}
		}


	}

}
