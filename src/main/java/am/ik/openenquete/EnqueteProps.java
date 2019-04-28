package am.ik.openenquete;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.util.Set;

@ConfigurationProperties(prefix = "enquete")
@Component
public class EnqueteProps {

    private String applicationName = "OpenEnquete";

    private Set<String> adminUsers;

    private Bitly bitly = new Bitly();

    private Googl googl = new Googl();

    private QrCode qrCode = new QrCode();

    private boolean coupon = false;

    public Set<String> getAdminUsers() {
        return this.adminUsers;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public Bitly getBitly() {
        return this.bitly;
    }

    public Googl getGoogl() {
        return this.googl;
    }

    public QrCode getQrCode() {
        return this.qrCode;
    }

    public boolean isCoupon() {
        return coupon;
    }

    public void setAdminUsers(Set<String> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setBitly(Bitly bitly) {
        this.bitly = bitly;
    }

    public void setCoupon(boolean coupon) {
        this.coupon = coupon;
    }

    public void setGoogl(Googl googl) {
        this.googl = googl;
    }

    public void setQrCode(QrCode qrCode) {
        this.qrCode = qrCode;
    }

    public static class Bitly {

        private String accessToken;

        public String getAccessToken() {
            return this.accessToken;
        }


        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    public static class Googl {

        private String apiKey;

        public String getApiKey() {
            return this.apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

    public static class QrCode {

        private boolean enabled = true;

        @Min(32)
        private int size = 240;

        public int getSize() {
            return this.size;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + (this.isEnabled() ? 79 : 97);
            result = result * PRIME + this.getSize();
            return result;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
