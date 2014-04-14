package view;

public enum Tile {
	Sky('-'), Dirt('d'), Stone('X'), Iron('I'), Uranium('U');

	public char value;

	Tile(char c) {
		this.value = c;
	}
}
