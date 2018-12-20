package outworldmind.owme.tools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader implements DataLoader<BufferedImage> {
	
	public static final ImageLoader INSTANCE = new ImageLoader();
	
	private ImageLoader() {}

	@Override
	public BufferedImage load(String path) {
		BufferedImage image = null;
		try {
			InputStream in = FileLoader.class.getResourceAsStream(path);
			image = ImageIO.read(in);
		} catch (IOException|NullPointerException|IllegalArgumentException e) {
			System.err.println("Couldn't read file from " + path);
			e.printStackTrace();
			System.exit(-1);
		}
		return image;
	}

}
