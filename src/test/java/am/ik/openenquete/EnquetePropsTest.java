package am.ik.openenquete;

import am.ik.openenquete.EnqueteProps.QrCode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class EnquetePropsTest {

    @Test
    public void colorTest() {
        final QrCode qrCode = new QrCode();
        // Black
        qrCode.setRed(0);
        qrCode.setGreen(0);
        qrCode.setBlue(0);
        Assertions.assertThat(qrCode.color()).isEqualTo(0xFF000000);
        // Red
        qrCode.setRed(255);
        qrCode.setGreen(0);
        qrCode.setBlue(0);
        Assertions.assertThat(qrCode.color()).isEqualTo(0xFFFF0000);
        // Green
        qrCode.setRed(0);
        qrCode.setGreen(255);
        qrCode.setBlue(0);
        Assertions.assertThat(qrCode.color()).isEqualTo(0xFF00FF00);
        // Blue
        qrCode.setRed(0);
        qrCode.setGreen(0);
        qrCode.setBlue(255);
        Assertions.assertThat(qrCode.color()).isEqualTo(0xFF0000FF);
    }
}