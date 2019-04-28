package am.ik.openenquete.qrcode;

import am.ik.openenquete.EnqueteProps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Component
public class QrCode {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QrCode.class);

    private final QRCodeWriter writer = new QRCodeWriter();

    private final Map<EncodeHintType, ?> hints = Collections
        .singletonMap(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

    private final EnqueteProps props;

    public QrCode(EnqueteProps props) {
        this.props = props;
    }

    public String dataUrl(String url) {
        int size = props.getQrCode().getSize();
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, size, size,
                hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", output);
            return Base64.getEncoder().encodeToString(output.toByteArray());
        } catch (WriterException e) {
            log.warn("WriterException", e);
            return null;
        } catch (IOException e) {
            log.warn("IOException", e);
            return null;
        }
    }
}
