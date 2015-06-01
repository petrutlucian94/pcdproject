package gifgen.utils;

import java.io.File;
import java.io.IOException;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

public class MimeTypeDetector {
    private static final Detector DETECTOR = new DefaultDetector();

    public static String get_mime_type(String fname) throws IOException{
        TikaInputStream tikaIS = null;
        try {
            tikaIS = TikaInputStream.get(new File(fname));
            final Metadata metadata = new Metadata();
            return DETECTOR.detect(tikaIS, metadata).toString();
        } finally {
            if (tikaIS != null) {
                tikaIS.close();
            }
        }
    }
}
