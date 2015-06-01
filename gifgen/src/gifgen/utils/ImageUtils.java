package gifgen.utils;

import gifgen.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ImageUtils {
	public static boolean is_image(String path){
		String mime_type = MimeTypeDetector.get_mime_type(path);
		String[] split = mime_type.split("/");
		return split[0].equals("image");
	}

	public static List<String> get_images(String dirPath){
		List<String> images = new ArrayList<String>();
		File fDir = new File(dirPath);
		ArrayList<File> contents = new ArrayList<File>(Arrays.asList(fDir.listFiles()));
		for(File f: contents){
			if (f.isFile() && is_image(f.getAbsolutePath())){
				images.add(f.getAbsolutePath());
			}
		}
		return images;
	}

	public static void resizeImage(String imagePath, int xres, int yres) throws Exception{
		String normImagPath = imagePath.replace("/", "\\");
		String bin = Config.imagickLocation.replace("/", "\\") + "convert";
		String cmd = String.format("%s %s -resize %dx%d! %s",
				                   bin, normImagPath, Config.gif_x_res,
				                   Config.gif_y_res, normImagPath);
		
		// TODO: check output
		ExecUtils.executeCommand(cmd);
	}

	public static void generateGif(List<String> srcImages, String destGif) throws Exception{
		String bin = Config.imagickLocation.replace("/", "\\") + "convert";
		StringBuffer cmd = new StringBuffer(bin);
		String opts = String.format("-delay %d -size %dx%d -loop 0",
				                    Config.gif_delay, Config.gif_x_res, Config.gif_y_res);
		cmd.append(" " + opts);
		for (String srcImage: srcImages){
			String pageOpt = String.format("-page %dx%d %s",
					                       Config.gif_x_res, Config.gif_y_res, srcImage);
			cmd.append(" " + pageOpt);
		}
		cmd.append(" " + destGif);
		ExecUtils.executeCommand(cmd.toString());
	}
}
