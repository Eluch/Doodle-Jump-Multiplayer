package me.eluch.libgdx.DoJuMu.data;

import java.util.ArrayList;

public class CorePlayerContainer<T extends CorePlayer> {

	protected final ArrayList<T> players;
	protected T mySelf;

	public CorePlayerContainer() {
		players = new ArrayList<T>();
	}

	public ArrayList<T> getPlayers() {
		return players;
	}

	public T getMySelf() {
		return mySelf;
	}

	public void setMySelf(T myself) {
		if (mySelf == null) {
			this.mySelf = myself;
		} else {
			System.err.println("mySelf is already set!");
		}
	}

	public int getNumberOfPlayers() {
		return players.size();
	}

	public boolean isNameContained(String name) {
		boolean contains = false;
		for (CorePlayer player : players) {
			if (player.getName().equals(name)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	public void addPlayer(T player) {
		players.add(player);
	}

	public void removePlayer(T player) {
		players.remove(player);
	}

	public boolean isMyself(T player) {
		if (player.id == mySelf.id && player.name.equals(mySelf.name))
			return true;
		return false;
	}

	public T getPlayerByID(int id) {
		for (T player : players) {
			if (player.getId() == id) {
				return player;
			}
		}
		return null;
	}

	public void removeByID(int id) {
		T need2Delete = null;
		for (T player : players) {
			if (player.getId() == id) {
				need2Delete = player;
				break;
			}
		}
		if (need2Delete != null) {
			players.remove(need2Delete);
		}
	}
}
