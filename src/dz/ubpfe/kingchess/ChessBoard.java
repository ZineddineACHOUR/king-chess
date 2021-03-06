package dz.ubpfe.kingchess;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ChessBoard {
	private static final int EMPTY = 6;
	private static final int[] START_COLOR = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final int[] START_PIECE = { 3, 1, 2, 4, 5, 2, 1, 3, 0, 0, 0,
			0, 0, 0, 0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0,
			0, 3, 1, 2, 4, 5, 2, 1, 3 };
	private static final int[][] imageFilename = {
			{ R.drawable.white_pone, R.drawable.white_knight,
					R.drawable.white_bishop, R.drawable.white_castle,
					R.drawable.white_queen, R.drawable.white_king },
			{ R.drawable.black_pone, R.drawable.black_knight,
					R.drawable.black_bishop, R.drawable.black_castle,
					R.drawable.black_queen, R.drawable.black_king } };
	public static final int[] columnLabel = { R.string.a, R.string.b,
			R.string.c, R.string.d, R.string.e, R.string.f, R.string.g,
			R.string.h };
	public static final int[] rowLabel = { R.string.n8, R.string.n7,
			R.string.n6, R.string.n5, R.string.n4, R.string.n3, R.string.n2,
			R.string.n1 };
	private final Rectangle[][] square = new Rectangle[8][8];
	public boolean first = true;

	private static Drawable[][] pieceImage = new Drawable[2][6];
	private int startCol;
	private int startRow;
	private boolean moving = false;
	private final Bouger move = null;
	public boolean needTo = true;

	private final Activity mActivity;
	private final LinearLayout root;
	private final TableLayout table;
	public TextView status, profile;
	public TextView whiteMarker, blackMarker, whiteMove, blackMove;

	public ChessBoard(Activity a) {
		mActivity = a;
		loadImages();

		root = (LinearLayout) mActivity.findViewById(R.id.rootLayout);
		// if (root.getChildCount() != 0)
		root.removeAllViews();
		// set chess board as table
		table = new TableLayout(mActivity);
		root.addView(table);
		table.setStretchAllColumns(true);
		table.setLayoutParams(new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		table.setOrientation(LinearLayout.VERTICAL);

		// create rows
		// first row is column label such as "abcdefgh"
		//letterColumn();
		// rows for chess board
		for (int i = 0; i < 8; i++) {
			TableRow row = new TableRow(mActivity);
			table.addView(row);
			row.setLayoutParams(new TableLayout.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));

			// row label
			TextView tv = new TextView(mActivity);
			row.addView(tv);
			tv.setText(rowLabel[i]);
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			tv.setHeight(35);
			tv.setGravity(Gravity.CENTER);

			// chess board cells
			for (int j = 0; j < 8; j++) {
				square[i][j] = new Rectangle(i, j, this, mActivity);
				row.addView(square[i][j]);
				
			}
		}
		setupBoard();

		// profile
		profile = new TextView(mActivity);
		root.addView(profile);
		profile.setTextColor(0xff00ff00);
		profile.setBackgroundColor(0x00000000);


		// move marker
		TableLayout tb = new TableLayout(mActivity);
		root.addView(tb);
		table.setStretchAllColumns(true);
		table.setLayoutParams(new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		TableRow row1 = new TableRow(mActivity);
		tb.addView(row1);
		row1.setLayoutParams(new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		whiteMarker = new TextView(mActivity);
		row1.addView(whiteMarker);
		whiteMarker.setBackgroundColor(0xffffffff);
		whiteMarker.setTextColor(0xff000000);
		whiteMarker.setLayoutParams(new TableRow.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		whiteMarker.setText("Blancs debutent");
		whiteMarker.setGravity(Gravity.CENTER);

		blackMarker = new TextView(mActivity);
		row1.addView(blackMarker);
		blackMarker.setBackgroundColor(0xff000000);
		blackMarker.setTextColor(0xff000000);
		blackMarker.setLayoutParams(new TableRow.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		blackMarker.setText("noir bouge");
		blackMarker.setGravity(Gravity.CENTER);

		// moves
		TableRow row2 = new TableRow(mActivity);
		tb.addView(row2);
		row2.setLayoutParams(new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		whiteMove = new TextView(mActivity);
		row2.addView(whiteMove);
		whiteMove.setBackgroundColor(0x00000000);
		whiteMove.setTextColor(0xff00ff00);
		whiteMove.setLayoutParams(new TableRow.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		whiteMove.setText(" ");
		whiteMove.setGravity(Gravity.CENTER);

		blackMove = new TextView(mActivity);
		row2.addView(blackMove);
		blackMove.setBackgroundColor(0x00000000);
		blackMove.setTextColor(0xff00ff00);
		blackMove.setLayoutParams(new TableRow.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		blackMove.setText(" ");
		blackMove.setGravity(Gravity.CENTER);

		// status
		status = new TextView(mActivity);
		// root.addView(status);
		status.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT, 1));
		status.setText("\n");
		status.setTextColor(0xff00ff00);
		status.setBackgroundColor(0xff000000);

		// ad
		/*
		 * com.admob.android.ads.AdView av = new
		 * com.admob.android.ads.AdView(mActivity); root.addView(av);
		 * av.setLayoutParams(new LinearLayout.LayoutParams(
		 * ViewGroup.LayoutParams.FILL_PARENT,
		 * ViewGroup.LayoutParams.WRAP_CONTENT));
		 * av.setBackgroundColor(0xff000000); av.setTextColor(0xffffffff);
		 * av.setKeywords("game, music, car, video"); av.setRequestInterval(15);
		 */
		// ad

		RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 50);
		lParam.addRule(RelativeLayout.CENTER_VERTICAL);
	

		RelativeLayout rLayout = new RelativeLayout(mActivity);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);
		param.addRule(RelativeLayout.ALIGN_BOTTOM);
		rLayout.setLayoutParams(param);
		rLayout.setBackgroundResource(R.drawable.bg);
		
		root.addView(rLayout);
	}

	private void loadImages() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++)
				pieceImage[i][j] = mActivity.getResources().getDrawable(
						imageFilename[i][j]);
		}
	}

	private void letterColumn() {
		TableRow row_1 = new TableRow(mActivity);
		table.addView(row_1);
		for (int i = 0; i < 8; i++) {
			TextView tv = new TextView(mActivity);
			tv.setText(columnLabel[i]);
			tv.setWidth(35);
			tv.setTypeface(Typeface.SERIF);
			tv.setGravity(Gravity.CENTER);
			if (i == 0) {
				tv.setLayoutParams(new TableRow.LayoutParams(4));
			}
			row_1.addView(tv);
		}
	}

	public void setupBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int c = START_COLOR[j + 8 * i];
				if (c != 6) {
					int p = START_PIECE[j + 8 * i];
					square[i][j].setIcon(pieceImage[c][p]);
					square[i][j].empty = false;
				} else
					square[i][j].setIcon(null);
			}
		}
		first = true;
	}

	public void makeMove() {
		makeMove(move);
	}

	public void makeMove(FonctionBasique move) {
		int fromCol = move.getFromCol();
		int fromRow = move.getFromRow();
		square[move.getToRow()][move.getToCol()]
				.setIcon(square[fromRow][fromCol].getIcon());
		square[move.getToRow()][move.getToCol()].empty = false;

		setHighlight(fromRow, fromCol, false);
		square[fromRow][fromCol].empty = true;
		square[fromRow][fromCol].setIcon(null);
	}

	public void setHighlight(int row, int col, boolean highlight) {
		// square[row][col].setBackgroundDrawable(null);
		// square[row][col].select = highlight;
		square[row][col].setSelect(highlight);
		// square[row][col].postInvalidate();
	}

	public void makeMoveWithPromote(Bouger move, int promote, boolean whiteToMove) {
		square[move.getToRow()][move.getToCol()]
				.setIcon(pieceImage[whiteToMove ? 0 : 1][promote]);
		square[move.getFromRow()][move.getFromCol()].setIcon(null);
	}

	public void clear(int row, int col) {
		square[row][col].setIcon(null);
	}


	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean m) {
		moving = m;
	}

	public Bouger getMove() {
		return move;
	}

	public void selected(int row, int col, boolean empty) {
		if (!moving) {
			if (first) {
				if (!empty) {
					startCol = col;
					startRow = row;
					first = false;

					setHighlight(row, col, true);
				}
			} else {
				int from = (startRow << 3) + startCol;
				int to = (row << 3) + col;
				FonctionBasique newMove = new FonctionBasique(from, to);
				moving = true;
				try {
					// this.fireVetoableChange("move", null, newMove);
					((Jouer) mActivity).pieceChange(newMove);
					// move = (Move) newMove;
					// this.firePropertyChange("move", null, move);
				} catch (Exception e) {
					moving = false;
				}
				first = true;
			}
		}
	}

	public void switchMoveMarkers(boolean whiteToMove) {
		if (whiteToMove) {
			whiteMarker.setTextColor(0xff000000);
			whiteMarker.setText("Au tour des Blancs");
			blackMarker.setText("");
		} else {
			whiteMarker.setText("");
			blackMarker.setTextColor(0xffffffff);
			blackMarker.setText("Au tour des Noirs");
		}
	}

	public void showMove(String move, boolean whiteMoved) {
		if (whiteMoved) {
			whiteMove.setText(move);
			blackMove.setText(null);
		} else {
			whiteMove.setText(null);
			blackMove.setText(move);
		}
	}


}
