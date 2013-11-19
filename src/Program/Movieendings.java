package Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Movieendings extends ArrayList<String> {
	private static final long serialVersionUID = 630148506139889166L;
	private BufferedReader br;
	private String ending;

	public Movieendings() {
		URL url = getClass().getResource("movieendings.txt");
		File file = new File(url.getPath());
		try {
			br = new BufferedReader(new FileReader(
					file));
			try {
				ending = br.readLine();
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
		}
		try {
			while (ending != null) {
				super.add(ending.trim());
				ending = br.readLine();
			}
			br.close();
		} catch (IOException e) {
		}

	}

	public boolean isMovieFile(String filename) {
		boolean found=false;
		for(String s :this){
			if(filename.endsWith(s)){
				found=true;
			}
		}
		return found;
		
		

	}

}
