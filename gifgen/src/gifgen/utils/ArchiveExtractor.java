package gifgen.utils;

import java.io.File;
import java.io.IOException;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;


public class ArchiveExtractor {
	private String fname;

	public ArchiveExtractor(String fname){
		this.fname = fname;
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
		String mime_type = MimeTypeDetector.get_mime_type(fname);
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
