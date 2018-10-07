package outworldmind.owme.tool;

import java.io.InputStream;

public class FileLoader implements DataLoader<InputStream> {

	@Override
	public InputStream load(String path) {
		return Class.class.getResourceAsStream(path);
	}

}
