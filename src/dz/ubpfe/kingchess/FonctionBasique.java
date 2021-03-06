package dz.ubpfe.kingchess;

public class FonctionBasique {
	protected final int from;
	protected final int to;

	public FonctionBasique(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public final int getFrom() {
		return from;
	}

	public final int getTo() {
		return to;
	}

	public final int getFromRow() {
		return from >> 3;
	}

	public final int getFromCol() {
		return from % 8;
	}

	public final int getToRow() {
		return to >> 3;
	}

	public final int getToCol() {
		return to % 8;
	}
}