package dz.ubpfe.kingchess;

import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;


public class Jouer extends Activity implements Constants {
	private EchiquierVirtuelle echiquierVirtuelle = new EchiquierVirtuelle();
	private ChessBoard chessView;
	private final int moves = 0;
	private TextView status;
	private String statusStr = "";
	private Bouger curMove = null;
	private boolean whiteToMove = true;
	private boolean whiteMoved = true;
	private String moveStr;

	private final Handler handler = new Handler();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		playNewGame();


	}


	private void init() {
		chessView = new ChessBoard(this);
		status = chessView.status;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onStop();
		
	}

	/*@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
				|| newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
				|| newConfig.orientation == Configuration.ORIENTATION_SQUARE
				|| newConfig.orientation == Configuration.ORIENTATION_UNDEFINED) {

		}
	}*/

	
	public void playNewGame() {
		
		echiquierVirtuelle = new EchiquierVirtuelle();
		chessView.setupBoard();
	}


	public void showStatus(String s) {
		statusStr = "Status: " + s;
		handler.post(doShowStatus);
	}

	private final Runnable doShowStatus = new Runnable() {
		public void run() {
			status.setText(statusStr);
		}
	};

	private void setMove(Bouger m) {
		curMove = m;
		handler.post(doMakeMove);
	}
	// bouger les pieces sur l'echiquier
	private final Runnable doMakeMove = new Runnable() {
		public void run() {
			makeMove(curMove);
		}
	};
     
	private void switchMoveMarkers(boolean b) {
		whiteToMove = b;
		handler.post(doSwitchMoveMarkers);
		showStatus("");
	}
    //ecris quand le noir bouge mouved ou move
	private final Runnable doSwitchMoveMarkers = new Runnable() {
		public void run() {
			chessView.switchMoveMarkers(whiteToMove);
		}
	};
     // ecris les deplacement effectuer
	private void showMove(String move, boolean b) {
		moveStr = move;
		whiteMoved = b;
		handler.post(doShowMove);
	}

	private final Runnable doShowMove = new Runnable() {
		public void run() {
			chessView.showMove(moveStr, whiteMoved);
		}
	};

    // fonction pour deplacer les pieces sur l'echiquier
	public void pieceChange(FonctionBasique mo) {
		FonctionBasique move = mo;
		if (move == null)
			return;
		int promote = 0;
		int to = move.getTo();
		int from = move.getFrom();
		if ((((to < 8) && (echiquierVirtuelle.side == LIGHT)) || ((to > 55) && (echiquierVirtuelle.side == DARK)))
				&& (echiquierVirtuelle.getPiece(from) == PAWN)) {
			//promote = chessView.promotionDialog(board.side == LIGHT) ;
		}
		boolean found = false;
		Collection validMoves = echiquierVirtuelle.gen();
		Iterator i = validMoves.iterator();
		Bouger m = null;
		while (i.hasNext()) {
			m = (Bouger) i.next();
			if (m.getFrom() == from && m.getTo() == to && m.promote == promote) {
				found = true;
				break;
			}
		}
		if (!found || !echiquierVirtuelle.makeMove(m)) {
			showStatus("Illegal move");
			chessView.setHighlight(mo.getFromRow(), mo.getFromCol(), false);
			chessView.setMoving(false);
		} else {
			setMove(m);
			showMove(m.toString(), echiquierVirtuelle.side == DARK);
			switchMoveMarkers(echiquierVirtuelle.side == LIGHT);

			if (isResult())
				return;
			

				if (found || echiquierVirtuelle.makeMove(m)) {
			
				showStatus("Illegal move");
				chessView.setHighlight(mo.getFromRow(), mo.getFromCol(), false);
				chessView.setMoving(false);
			} else {
				setMove(m);
				showMove(m.toString(), echiquierVirtuelle.side == LIGHT);
				switchMoveMarkers(echiquierVirtuelle.side == DARK);
				}

		}
		
	}
// fonction qui fait bouger les pieces sur l'echiquier graphiquement
	private void makeMove(Bouger m) {
		int from = m.getFrom();
		int to = m.getTo();
		if (m.promote != 0) {
			chessView.makeMoveWithPromote(m, m.promote, echiquierVirtuelle.side != LIGHT);
		} else {
			if ((m.bits & 2) != 0) {
				if (from == E1 && to == G1)
					chessView.makeMove(new FonctionBasique(H1, F1));
				else if (from == E1 && to == C1)
					chessView.makeMove(new FonctionBasique(A1, D1));
				else if (from == E8 && to == G8)
					chessView.makeMove(new FonctionBasique(H8, F8));
				else if (from == E8 && to == C8)
					chessView.makeMove(new FonctionBasique(A8, D8));
			} else if ((m.bits & 4) != 0) {
				if (echiquierVirtuelle.xside == LIGHT)
					chessView.clear(m.getToRow() + 1, m.getToCol());
				else
					chessView.clear(m.getToRow() - 1, m.getToCol());
			}
			chessView.makeMove(m);
		}
	}

	private boolean isResult() {
		Collection validMoves = echiquierVirtuelle.gen();

		Iterator i = validMoves.iterator();
		boolean found = false;
		while (i.hasNext()) {
			if (echiquierVirtuelle.makeMove((Bouger) i.next())) {
				echiquierVirtuelle.takeBack();
				found = true;
				break;
			}
		}
		String message = null;
		if (!found) {
			if (echiquierVirtuelle.inCheck(echiquierVirtuelle.side)) {
				if (echiquierVirtuelle.side == LIGHT)
					message = "0 - 1 Black mates";
				else
					message = "1 - 0 White mates";
			} else
				message = "0 - 0 Stalemate";
		} else if (echiquierVirtuelle.reps() == 3)
			message = "1/2 - 1/2 Draw by repetition";
		else if (echiquierVirtuelle.fifty >= 100)
			message = "1/2 - 1/2 Draw by fifty move rule";
		if (message != null) {
			showStatus(message);
			AlertDialog.Builder alert1 = new AlertDialog.Builder(this).setTitle(
					"partie terminer").setMessage("Voulez vous rejouez?")
					.setPositiveButton("oui",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									playNewGame();
									//le code pour revenir au menu principale
								}
							});
				
                   alert1.show();
			
			return true;
		}
		if (echiquierVirtuelle.inCheck(echiquierVirtuelle.side))
			showStatus("Check!");
		return false;
	}

	/*private void choice4NewGame() {
		AlertDialog.Builder alert1 = new AlertDialog.Builder(this).setTitle(
				"Game Over").setMessage("Do you want to start a new game?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								playNewGame();
							}
						});.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Put your code in here for a negative response
							}
						});
		alert1.show();
	}*/

	
	private void nextMove() {
	}
}