package me.eluch.libgdx.DoJuMu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;

public class PopupForString {

	private boolean inProgress = false;
	private boolean forced = false;
	private String val = "";

	public PopupForString() {
	}

	public void call(String title, String content) {
		if (inProgress)
			return;
		inProgress = true;
		forced = false;
		TextInputListener inputListener = new TextInputListener() {

			@Override
			public void input(String text) {
				if (text.length() > 0) {
					val = text;
				}
				inProgress = false;
			}

			@Override
			public void canceled() {
				inProgress = false;
			}
		};

		Gdx.input.getTextInput(inputListener, title, content);
	}

	public String getValueIfAwailable() {
		if (val.length() > 0) {
			String tmp = val;
			val = "";
			return tmp;
		}
		return "";
	}

	public void setValForced(String val) {
		this.val = val;
		this.forced = true;
	}

	public boolean isForced() {
		return forced;
	}

}
