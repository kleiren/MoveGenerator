package entities;

import java.util.ArrayList;
import java.util.List;
/** MoveList object provides a list of Moves
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class MoveList {

	private List<Move> moves;

	public MoveList() {
		moves = new ArrayList<Move>();
	}

	public List<Move> getMoves() {
		return moves;
	}

	public int getSize() {
		return moves.size();
	}

	public Move getMove(int position) {
		return moves.get(position);
	}

	public void addMove(int position, Move move) {
		moves.add(position, move);
	}

	public void addMove(Move move) {
		moves.add(move);
	}

	public void deleteMove(int position) {
		if (moves.size() > 1)
			moves.remove(position);
	}

	public int getTotalTime() {
		int time = 0;
		for (Move move : moves) {
			time += move.getMoveTime();
		}
		return time;
	}

}
