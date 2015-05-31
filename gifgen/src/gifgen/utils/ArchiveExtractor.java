package gifgen.utils;

import java.io.File;
import java.io.IOException;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

public class ArchiveExtractor {
	private String fname;
	
	private static final Detector DETECTOR = new DefaultDetector();

	public ArchiveExtractor(String fname){
		this.fname = fname;
	}

	private String get_mime_type(){
		TikaInputStream tikaIS = null;
	    try {
	        tikaIS = TikaInputStream.get(new File(fname));
	        final Metadata metadata = new Metadata();
	        return DETECTOR.detect(tikaIS, metadata).toString();
	    } catch (IOException e) {
	    	e.printStackTrace();
			return e.getMessage();
		} finally {
	        if (tikaIS != null) {
	            try {
					tikaIS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}

	public void extract(String dest){
		Archiver archiver = get_archiver();
		try {
			archiver.extract(new File(fname), new File(dest));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Archiver get_archiver(){
		String mime_type = get_mime_type();
		CompressionType compr_type = get_compression_type(mime_type);
		ArchiveFormat arch_format = get_archive_format(mime_type);
		if (compr_type != null){
			return ArchiverFactory.createArchiver(arch_format, compr_type);
		} else {
			return ArchiverFactory.createArchiver(arch_format);
		}
	}
	
	private CompressionType get_compression_type(String mime_type) {
		CompressionType comprType = null;
		switch(mime_type){
		case "application/gzip":
			comprType = CompressionType.GZIP;
			break;
		case "application/bzip2":
			comprType = CompressionType.BZIP2;
			break;
		case "application/x-xz":
			comprType = CompressionType.XZ;
			break;
		}
		return comprType;
	}

	private ArchiveFormat get_archive_format(String mime_type){
		ArchiveFormat archFormat = ArchiveFormat.ZIP;
		switch(mime_type){
		case "application/x-tar":
			archFormat = ArchiveFormat.TAR;
			break;
		case "application/zip":
			archFormat = ArchiveFormat.ZIP;
			break;
		case "application/x-7z-compressed":
			archFormat = ArchiveFormat.SEVEN_Z;
			break;
		}
		return archFormat;
	}
}
