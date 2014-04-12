package view;

public enum Tile {
	Sky('s'), Dirt('d'), Stone('e'), Iron('i'), Uranium('u'), Sand('s');

	public int value;

	Tile(char c) {
		this.value = c;
	}
}
