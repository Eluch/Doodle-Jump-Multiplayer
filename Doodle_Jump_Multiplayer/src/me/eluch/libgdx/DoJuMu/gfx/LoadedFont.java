package me.eluch.libgdx.DoJuMu.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class LoadedFont {

	private final BitmapFont font;
	private final int fontSize;
	
	private FreeTypeFontGenerator gen;

	/**
	 * Load a font to memory
	 * 
	 * @param internalPath
	 *            path to the file
	 * @param size
	 *            size in pixels
	 * @param color
	 *            color
	 */
	public LoadedFont(String internalPath, int size, Color color) {
		gen = new FreeTypeFontGenerator(Gdx.files.internal(internalPath));
		font = gen.generateFont(size);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font.setColor(color);
		fontSize = size;
	}

	/**
	 * Load a font to memory
	 * 
	 * @param internalPath
	 *            path to the file
	 * @param size
	 *            size in pixels
	 * @param hexColor
	 *            color in hexadecimal format, without #. example: "f8f8f8"
	 */
	public LoadedFont(String internalPath, int size, String hexColor) {
		gen = new FreeTypeFontGenerator(Gdx.files.internal(internalPath));
		font = gen.generateFont(size);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font.setColor(Color.valueOf(hexColor));
		fontSize = size;
	}
	
	public int getSize(){
		return fontSize;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void drawLeft(SpriteBatch spriteBatch, String str, float startX, float startY) {
		font.draw(spriteBatch, str, startX, startY + font.getBounds(str).height);
	}

	public void drawRight(SpriteBatch spriteBatch, String str, float endX, float endY) {
		font.draw(spriteBatch, str, endX - font.getBounds(str).width, endY + font.getBounds(str).height);
	}

	public void drawCenter(SpriteBatch spriteBatch, String str, float centerX, float centerY) {
		font.draw(spriteBatch, str, centerX - font.getBounds(str).width / 2, centerY + font.getBounds(str).height / 2);
	}
}