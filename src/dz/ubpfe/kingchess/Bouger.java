package dz.ubpfe.kingchess;

import dz.ubpfe.kingchess.Constants;
import dz.ubpfe.kingchess.FonctionBasique;

public final class Bouger<T> extends FonctionBasique implements Constants, Comparable<T> {

	@Override
	public int compareTo(T arg0) {
		Bouger m = (Bouger) arg0;
        int mScore = m.getScore();
        return mScore - score; // Can't overflow so this should work.
	}
	
	final int promote, bits;
	final char pieceLetter;
	int score = 0;

	Bouger(int from, int to, int promote, int bits, char pieceLetter) {
		super(from, to);
		this.promote = promote;
		this.bits = bits;
		this.pieceLetter = pieceLetter;
	}

	int getScore() {
		return score;
	}

	void setScore(int i) {
		score = i;
	}

	public int hashCode() {
		
		return from + (to << 8) + (promote << 16);
	}

	public boolean equals(Object o) {
		Bouger m = (Bouger) o;
		return (m.from == from && m.to == to && m.promote == promote);
	}
	
    public String toString() {
		char c;
		StringBuffer sb = new StringBuffer();

		if ((bits & 32) != 0) {
			switch (promote) {
			case KNIGHT:
				c = 'n';
				break;
			case BISHOP:
				c = 'b';
				break;
			case ROOK:
				c = 'r';
				break;
			default:
				c = 'q';
				break;
			}
			sb.append((char) (getFromCol() + 'a'));
			sb.append(8 - getFromRow());
			sb.append("-");
			sb.append((char) (getToCol() + 'a'));
			sb.append(8 - getToRow());
			sb.append(c);
		} else {
			/*
			if (pieceLetter != 'P')
				sb.append((char) pieceLetter);
				*/
			sb.append((char) (getFromCol() + 'a'));
			sb.append(8 - getFromRow());
			sb.append("-");
			sb.append((char) (getToCol() + 'a'));
			sb.append(8 - getToRow());
		}
		return sb.toString();
	}
}