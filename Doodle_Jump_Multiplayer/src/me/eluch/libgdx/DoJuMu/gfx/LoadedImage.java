package me.eluch.libgdx.DoJuMu.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LoadedImage {

	private Texture texture;
	private Sprite sprite;

	public LoadedImage(String internalPath) {
		LoadedImageConstructorHelper(internalPath, false);
	}
	
	public LoadedImage(String internalPath, boolean repeat) {
		LoadedImageConstructorHelper(internalPath, repeat);
	}
	
	private void LoadedImageConstructorHelper(String internalPath, boolean repeat){
		texture = new Texture(Gdx.files.internal(internalPath));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		if (repeat)
			texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		sprite = new Sprite(texture);
	}

	public Sprite getImage() {
		return sprite;
	}
	
	public Texture getTexture(){
		return texture;
	}

	public float getWidth() {
		return sprite.getWidth();
	}

	public float getHeight() {
		return sprite.getHeight();
	}

}
