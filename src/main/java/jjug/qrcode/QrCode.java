package jjug.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import jjug.JjugProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty(name = "jjug.qr-code.enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
@CacheConfig(cacheNames = "qrcode")
@Component
@RequiredArgsConstructor
public class QrCode {
	private final QRCodeWriter writer = new QRCodeWriter();
	private final Map<EncodeHintType, ?> hints = Collections
			.singletonMap(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
	private final JjugProps props;

	@Cacheable
	public String dataUrl(String url) {
		int size = props.getQrCode().getSize();
		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, size, size,
					hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", output);
			return Base64.getEncoder().encodeToString(output.toByteArray());
		}
		catch (WriterException e) {
			log.warn("WriterException", e);
			return null;
		}
		catch (IOException e) {
			log.warn("IOException", e);
			return null;
		}
	}
}
