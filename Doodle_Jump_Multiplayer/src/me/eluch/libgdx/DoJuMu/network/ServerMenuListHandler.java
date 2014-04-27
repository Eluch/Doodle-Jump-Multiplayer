package me.eluch.libgdx.DoJuMu.network;

import java.util.ArrayList;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.network.packets.ServerItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ServerMenuListHandler {

	private ArrayList<ServerItem> items;
	private ArrayList<ServerItemPos> itemsPos;
	private int selected = -1;
	private boolean leftClicked;

	private final int startHostX;
	private final int startIpX;
	private final int startPlayersX;
	private final int startY;

	public ServerMenuListHandler(int startHostX, int startIpX, int startPlayersX, int startY) {
		items = new ArrayList<ServerItem>();
		itemsPos = new ArrayList<ServerItemPos>();
		this.startHostX = startHostX;
		this.startIpX = startIpX;
		this.startPlayersX = startPlayersX;
		this.startY = startY;
		leftClicked = false;
	}

	public void addServerItem(ServerItem item) {
		itemsPos.add(new ServerItemPos(startHostX, startIpX, startPlayersX, startY - items.size() * 25));
		items.add(item);
		if (selected == -1)
			selected = 0;
	}

	public void clearServerItems() {
		items.clear();
		itemsPos.clear();
	}

	public void draw(SpriteBatch batch) {
		for (int i = 0; i < items.size(); i++) {
			ServerItem item = items.get(i);
			ServerItemPos pos = itemsPos.get(i);

			if (item.equals(items.get(selected))) {
				batch.draw(Res._serverPicker.getTexture(), pos.hosterX - 45, pos.allY);
			}
			Res._serverListFont.drawLeft(batch, item.hoster, pos.hosterX, pos.allY);
			Res._serverListFont.drawLeft(batch, item.ip, pos.ipX, pos.allY);
			Res._serverListFont.drawLeft(batch, item.players, pos.playersX, pos.allY);
		}
	}

	public void handle(OrthographicCamera camera, int currentWidth, int currentHeight) {
		if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && leftClicked)
			leftClicked = false;
		if (items.size() < 1)
			return;
		float widthScale = camera.viewportWidth / currentWidth;
		float heightScale = camera.viewportHeight / currentHeight;
		Rectangle mouseRect = new Rectangle(Gdx.input.getX() * widthScale, currentHeight * heightScale - Gdx.input.getY() * heightScale, 1, 1);
		searcher: for (ServerItemPos pos : itemsPos) {
			if (pos.rect.overlaps(mouseRect) && !leftClicked && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				selected = itemsPos.indexOf(pos);
				leftClicked = true;
				break searcher;
			}
		}
	}

	public String getSelectedIp() {
		if (selected == -1)
			return "";
		return items.get(selected).ip;
	}

	private class ServerItemPos {

		private final int hosterX;
		private final int ipX;
		private final int playersX;
		private final int allY;
		private final Rectangle rect;

		public ServerItemPos(int hosterX, int ipX, int playersX, int allY) {
			this.hosterX = hosterX;
			this.ipX = ipX;
			this.playersX = playersX;
			this.allY = allY;
			this.rect = new Rectangle(hosterX, allY, 760, Res._buttonFont.getSize());
		}
	}

}
