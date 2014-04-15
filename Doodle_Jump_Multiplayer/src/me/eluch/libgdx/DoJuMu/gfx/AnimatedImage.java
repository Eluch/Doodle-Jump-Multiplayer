package me.eluch.libgdx.DoJuMu.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedImage {

	private final Texture fullTexture;
	private final Sprite[] sprites;
	private final int frameNumber;
	private int currentFrame;
	private final int prescalerTarget;
	private int prescaler;
	private boolean playedOnce;

	public AnimatedImage(String internalPath, int numRows, int numCols, int prescalerTarget) {
		currentFrame = 0;
		if (prescalerTarget < 1)
			prescalerTarget = 1;
		this.prescalerTarget = prescalerTarget;
		prescaler = 0;
		playedOnce = false;
		frameNumber = numRows * numCols;
		sprites = new Sprite[frameNumber];
		fullTexture = new Texture(Gdx.files.internal(internalPath));
		// temp vars
		int width = fullTexture.getWidth() / numCols;
		int height = fullTexture.getHeight() / numRows;
		int counter = 0;

		for (int row = 0; row < fullTexture.getHeight(); row += height) {
			for (int col = 0; col < fullTexture.getWidth(); col += width) {
				sprites[counter++] = new Sprite(fullTexture, col, row, width, height);
			}
		}
	}

	private boolean isPrescaled() {
		prescaler++;
		if (prescaler >= prescalerTarget) {
			prescaler = 0;
			return true;
		} else {
			return false;
		}
	}

	private void nextImage() {
		currentFrame++;
		if (currentFrame >= frameNumber) {
			currentFrame = 0;
			playedOnce = true;
		}
	}

	/**
	 * Returns true if the animation has played minimum once.
	 * 
	 * @return boolean
	 */
	public boolean hasPlayedOnce() {
		return playedOnce;
	}

	/**
	 * get the exact image indexed on the spritesheet
	 * 
	 * @param imgIndex
	 *            int
	 * @return Sprite
	 */
	public Sprite getSpecificImage(int imgIndex) {
		if (imgIndex < 0) {
			imgIndex = 0;
			System.out.println("WARNING: getSpecificImage - got less than 0 index.");
		}
		if (imgIndex > frameNumber - 1) {
			imgIndex = frameNumber - 1;
			System.out.println("WARNING: getSpecificImage - got greater number than the bigest index.");
		}
		return sprites[imgIndex];
	}

	public Sprite getCurrentImage() {
		return sprites[currentFrame];
	}

	/**
	 * This prescale the image getting speed.
	 * 
	 * @return Sprite
	 */
	public Sprite getNextImage() {
		Sprite img = getCurrentImage();
		jumpToNextFrame();
		return img;
	}

	/**
	 * This forces the program to jump to the next frame, even if the prescaler
	 * isn't ready.
	 * 
	 * @return Sprite
	 */
	public Sprite forcedGetNextImage() {
		Sprite img = getCurrentImage();
		forcedJumpToNextFrame();
		return img;
	}

	/**
	 * Jumps to the next frame. The prescaler is slowing it down.
	 */
	public void jumpToNextFrame() {
		if (isPrescaled()) {
			nextImage();
		}
	}

	/**
	 * Jumps to the next frame. Even prescaler doesn't matter.
	 */
	public void forcedJumpToNextFrame() {
		nextImage();
		prescaler = 0;
	}

	/**
	 * Sets the animation to the given frame index.
	 * 
	 * @param frame
	 */
	public void setCurrentFrame(int frame) {
		prescaler = 0;
		if (frame < 0 || frame >= frameNumber) {
			frame = 0;
			System.out.println("WARNING: setCurrentFrame - got invalid number.");
		}
	}

	public int getWidth() {
		return (int) sprites[currentFrame].getWidth();
	}

	public int getHeight() {
		return (int) sprites[currentFrame].getHeight();
	}
}
