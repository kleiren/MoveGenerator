package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import entities.Move;
import entities.MoveList;

/**
 * FileManager class load, save and manage MoveList on files.
 * 
 * @author Rubén Vilches
 * @version 1.0 - Mar 2014.
 */
public class FileManager {

	// ------- MOVE LIST ----------

	public void SaveMovesFile(String name, MoveList moves) throws Exception {

		// Set up the FileWriter with our file name.
		FileWriter saveFile = new FileWriter(name);

		// Write the data to the file.
		for (int i = 0; i < moves.getSize(); i++) {

			if (i != 0)
				saveFile.write("\n");

			saveFile.write(Integer.toString(moves.getMove(i).getMoveTime()));
			saveFile.write(",");
			saveFile.write("{");
			saveFile.write("" + moves.getMove(i).getServos().get(0).getPosition());

			for (int j = 1; j < moves.getMove(i).getServos_Num(); j++) {
				saveFile.write(",");
				saveFile.write("" + moves.getMove(i).getServos().get(j).getPosition());
			}
			saveFile.write("}");

		}

		// All done, close the FileWriter.
		saveFile.close();

	}

	public MoveList LoadMovesFile(String name) throws Exception {

		String line;
		MoveList moves = new MoveList();

		BufferedReader loadFile = new BufferedReader(new FileReader(name));

		// Read data from the file.
		while ((line = loadFile.readLine()) != null) {
			String[] line2 = line.split(",\\{");
			Move move = new Move();
			move.setMoveTime(Integer.parseInt(line2[0]));
			setGeneratedString(move, "{" + line2[1]);
			moves.addMove(move);
		}

		// All done, close the FileWriter.
		loadFile.close();

		return moves;
	}

	// ------- MOVE ----------

	public String getGeneratedString(Move move) {
		String res = "{" + move.getServos().get(0).getPosition();
		for (int i = 1; i < move.getServos_Num(); i++) {
			res += "," + move.getServos().get(i).getPosition();
		}
		res += "}";
		return res;
	}

	private void setGeneratedString(Move move, String string) {
		String[] res = string.split("\\{|\\}");
		String[] vector = res[1].split(",");

		for (int i = 0; i < vector.length; i++) {
			move.addPosition(Core.getInstance().getServoList().getServos().get(i).getID(), Integer.parseInt(vector[i]));
		}
	}

}
