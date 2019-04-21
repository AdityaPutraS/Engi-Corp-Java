package com.engicorp.oop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.engicorp.oop.cell.Facility;
import com.engicorp.oop.cell.Land;
import com.engicorp.oop.livingbeing.Animal;
import com.engicorp.oop.livingbeing.Player;
import com.engicorp.oop.misc.Point;

import javax.sound.sampled.Mixer;

public class EngiCorp extends ApplicationAdapter {
	private SpriteBatch batch;
	private int w, h;
	private Texture bgWhiteBlue, bgBrown, texJudul;
	private Sprite sprWhiteBlue, sprBrown, sprJudul;
	private boolean pressedMixerUI, pressedUI;
	private float epsTimeMixerUI, epsUI;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//Buat Dunia
		World.init(11, 11);
		System.out.println("Dunia berhasil dibuat");
		//Load background
		bgWhiteBlue = new Texture("bg_white_blue_half.png");
		sprWhiteBlue = new Sprite(bgWhiteBlue);
		bgBrown = new Texture("bg_brown.png");
		sprBrown = new Sprite(bgBrown);
		sprWhiteBlue.setPosition(0,0);

		texJudul = new Texture("judulKelompok.png");
		sprJudul = new Sprite(texJudul);
		sprJudul.setPosition(800, 620);
		sprJudul.setSize(1280-800, 100);
	}
	private void checkKeyboard()
	{
		if(pressedUI || Player.getInstance().isAnimating()) {
			epsUI +=Gdx.graphics.getDeltaTime();
			if(epsUI >= 0.5f)
			{
				epsUI = 0;
				pressedUI = false;
			}
		}else {
			boolean pressedMove = false;
			boolean jumpInPlace = false;
			Point mov = new Point(0, 0, 0);
			int len = 0;
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				pressedUI = true;
				pressedMove = true;
				mov.x += -1;
				Player.getInstance().setLenMove(1);
				Player.getInstance().setDir(1);
				Player.getInstance().setDefaultWalk();
			} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				pressedUI = true;
				pressedMove = true;
				mov.x += 1;
				Player.getInstance().setLenMove(1);
				Player.getInstance().setDir(0);
				Player.getInstance().setDefaultWalk();
			} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				pressedUI = true;
				pressedMove = true;
				mov.y += 1;
				Player.getInstance().setLenMove(1);
				Player.getInstance().setDir(2);
				Player.getInstance().setDefaultWalk();
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				pressedUI = true;
				pressedMove = true;
				mov.y += -1;
				Player.getInstance().setLenMove(1);
				Player.getInstance().setDir(3);
				Player.getInstance().setDefaultWalk();
			} else if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
				pressedUI = true;
				pressedMove = true;
				mov.y += 2;
				Player.getInstance().setLenMove(2);
				Player.getInstance().setDir(2);
				Player.getInstance().setDefaultJump();
			} else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
				pressedUI = true;
				pressedMove = true;
				mov.x += 2;
				Player.getInstance().setLenMove(2);
				Player.getInstance().setDir(0);
				Player.getInstance().setDefaultJump();
			} else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
				pressedUI = true;
				pressedMove = true;
				mov.x += -2;
				Player.getInstance().setLenMove(2);
				Player.getInstance().setDir(1);
				Player.getInstance().setDefaultJump();
			} else if (Gdx.input.isKeyPressed(Input.Keys.C)) {
				pressedUI = true;
				pressedMove = true;
				mov.y += -2;
				Player.getInstance().setLenMove(2);
				Player.getInstance().setDir(3);

				Player.getInstance().setDefaultJump();
			} else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
//				MixerUI.getInstance().setShow(true);
			} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				Player.getInstance().setDir(0);
			} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				Player.getInstance().setDir(1);
			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				Player.getInstance().setDir(2);
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				Player.getInstance().setDir(3);
			} else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				pressedUI = true;
				//Interact
				//Cari animal di arah itu
				//Debug
				System.out.println(Player.getInstance().getPointDir().x +", "+Player.getInstance().getPointDir().y);
				//////
				Animal a = World.getInstance().getAnimal(Player.getInstance().getPointDir());
				if (a != null) {
					pressedMove = true;
					jumpInPlace = true;
					Player.getInstance().setLenMove(1);
					Player.getInstance().setDefaultJumpInPlace();
					a.interact();
				} else {
					//Cari fasilitas di arah itu
					Facility f = World.getInstance().getFacility(Player.getInstance().getPointDir());
					if (f != null) {
						pressedMove = true;
						jumpInPlace = true;
						Player.getInstance().setLenMove(1);
						Player.getInstance().setDefaultJumpInPlace();
						f.interact();
					} else {
						World.getInstance().addMsg("Tidak ada apa apa di arah itu");
					}
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
				pressedUI = true;
				//Kill
				//Cari animal di arah itu
				Animal a = World.getInstance().getAnimal(Player.getInstance().getPointDir());
				if (a != null) {
					pressedMove = true;
					jumpInPlace = true;
					Player.getInstance().setLenMove(1);
					Player.getInstance().setDefaultJumpInPlace();
					World.getInstance().addMsg("Kamu membunuh 1 hewan");
					Player.getInstance().kill(a);
				} else {
					World.getInstance().addMsg("Tidak ada sesuatu yang bisa di kill disana");
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.T)) {
				pressedUI = true;
				pressedMove = true;
				//Talk
				//Cari animal di arah itu
				Animal a = World.getInstance().getAnimal(Player.getInstance().getPointDir());
				if (a != null) {
					World.getInstance().addMsg(a.getAnimalSound());
				} else {
					World.getInstance().addMsg("Jangan bicara sendirian");
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.G)) {
				pressedUI = true;
				//Grow
				//Cari land di arah itu
				if (World.getInstance().isValidPoint(Player.getInstance().getPointDir())) {
					Land l = World.getInstance().getLand(Player.getInstance().getPointDir());
					//DEBUG
					//World.getInstance().printWorld();
					///////
					if (l.hasGrass()) {
						World.getInstance().addMsg("Sudah tumbuh rumput disana");
					} else {
						pressedMove = true;
						jumpInPlace = true;
						Player.getInstance().setLenMove(1);
						Player.getInstance().setDefaultJumpInPlace();
						World.getInstance().addMsg("Kamu berhasil menumbuhkan rumput disana");
						l.setGrass(true);
					}
					//DEBUG
//					System.out.println();
//					World.getInstance().printWorld();
					///////
				} else {
					World.getInstance().addMsg("Kamu menyiram ke mana?");
				}
			}
			if(pressedMove) {
				Point posBaru = Player.getInstance().getPos().add(mov);
				if (!Player.getInstance().isAnimating()) {
					if (pressedUI) {
						if (World.getInstance().isValidPoint(posBaru)) {
							//System.out.println("Cek terisi : "+posBaru.x+", "+posBaru.y);
							if (!World.getInstance().isTerisi(posBaru) || jumpInPlace) { ;
								World.getInstance().setTerisi(Player.getInstance().getPos(), false);
								World.getInstance().setTerisi(posBaru, true);
								Player.getInstance().setAnimatedHungry(true);
								Player.getInstance().startAnimate(posBaru);
								World.getInstance().updateAll();
							}
						}
					}
				}
			}
		}
	}

	private void checkKeyboardMixer()
	{
		if(pressedMixerUI)
		{
			epsTimeMixerUI +=Gdx.graphics.getDeltaTime();
			if(epsTimeMixerUI >= 0.5f)
			{
				epsTimeMixerUI = 0;
				pressedMixerUI = false;
			}
		}else {
			if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
				MixerUI.getInstance().setShow(false);
				MixerUI.getInstance().setSelectBahan(true);
				pressedMixerUI = true;
			} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				if(MixerUI.getInstance().isSelectBahan()) {
					MixerUI.getInstance().setSelect(MixerUI.getInstance().getSelect() - 1);
				}else{
					MixerUI.getInstance().setSelectHasil(MixerUI.getInstance().getSelectHasil() - 1);
				}
				pressedMixerUI = true;
			} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				if(MixerUI.getInstance().isSelectBahan()) {
					MixerUI.getInstance().setSelect(MixerUI.getInstance().getSelect() + 1);
				}else{
					MixerUI.getInstance().setSelectHasil(MixerUI.getInstance().getSelectHasil() + 1);
				}
				pressedMixerUI = true;
			} else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				if(MixerUI.getInstance().isSelectBahan()) {
					MixerUI.getInstance().mixBahanTerpilih();
					MixerUI.getInstance().setSelectBahan(false);
				}else{
					MixerUI.getInstance().kasihPlayerHasilnya();
					MixerUI.getInstance().setSelectBahan(true);
				}
				pressedMixerUI = true;
			} else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				if(MixerUI.getInstance().isSelectBahan()) {
					MixerUI.getInstance().addToTerpilih();
					pressedMixerUI = true;
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
				if(MixerUI.getInstance().isSelectBahan()) {
					MixerUI.getInstance().removeFromTerpilih();
				}else{
					MixerUI.getInstance().setSelectBahan(true);
				}
				pressedMixerUI = true;
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		boolean mixerActive = MixerUI.getInstance().isShowing();
		if(mixerActive) {
			checkKeyboardMixer();
		}else{
			checkKeyboard();
		}
		batch.begin();
		if(!mixerActive)
		{
			sprWhiteBlue.draw(batch);

		}
		sprJudul.draw(batch);
		World.getInstance().renderAll(batch);
		if(mixerActive)
		{
			sprBrown.draw(batch);
		}
		batch.end();
		UI.getInstance().render();
		MixerUI.getInstance().render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Player.getInstance().dispose();
		World.getInstance().disposeAll();
		bgWhiteBlue.dispose();
		UI.getInstance().dispose();
		MixerUI.getInstance().dispose();
		texJudul.dispose();
	}
}
