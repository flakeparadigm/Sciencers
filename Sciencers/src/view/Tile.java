package view;

public enum Tile {
	Sky('-'), Dirt('d'), Stone('x'), Iron('i'), Uranium('u'), Path('`'), Agent('&'), Farm('F'), Warehouse('W');

	public char value;

	Tile(char c) {
		this.value = c;
	}
}
