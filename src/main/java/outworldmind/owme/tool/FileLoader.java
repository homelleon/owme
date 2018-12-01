package outworldmind.owme.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader implements DataLoader<StringBuilder> {
	
	public static final FileLoader INSTANCE = new FileLoader();
	
	private FileLoader() {}

	@Override
	public StringBuilder load(String path) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			InputStream in = FileLoader.class.getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null)
				shaderSource.append(line).append("\n");
			reader.close();
		} catch (IOException|NullPointerException e) {
			System.err.println("Couldn't read file from " + path);
			e.printStackTrace();
			System.exit(-1);
		}
		return shaderSource;
	}

}
