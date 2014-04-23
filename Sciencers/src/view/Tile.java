package view;

public enum Tile {
	Sky('-'), Dirt('d'), Stone('x'), Iron('i'), Uranium('u'), Wood('w'), Leaves('l'), Path('`');

	public char value;

	Tile(char c) {
		this.value = c;
	}
}
