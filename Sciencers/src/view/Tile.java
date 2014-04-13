package view;

public enum Tile {
	Sky('S'), Dirt('d'), Stone('e'), Iron('i'), Uranium('u'), Sand('s');

	public char value;

	Tile(char c) {
		this.value = c;
	}
}