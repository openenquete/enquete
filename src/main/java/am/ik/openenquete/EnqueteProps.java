package am.ik.openenquete;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
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

    private AdminClient adminClient = new AdminClient();

    private boolean coupon = false;

    private String responseForSessionWebhookUrl;

    public AdminClient getAdminClient() {
        return adminClient;
    }

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

    public String getResponseForSessionWebhookUrl() {
        return responseForSessionWebhookUrl;
    }

    public boolean isCoupon() {
        return coupon;
    }

    public void setAdminClient(AdminClient adminClient) {
        this.adminClient = adminClient;
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

    public void setResponseForSessionWebhookUrl(String responseForSessionWebhookUrl) {
        this.responseForSessionWebhookUrl = responseForSessionWebhookUrl;
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

    public static class AdminClient {

        private String clientId;

        private String clientSecret;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public User asUser() {
            User user = new User(this.clientId, "{noop}" + this.clientSecret, AuthorityUtils.createAuthorityList("ROLE_ADMIN_CLIENT"));
            return user;
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

        @Min(0)
        @Max(255)
        private int red = 0;

        @Min(0)
        @Max(255)
        private int blue = 0;

        @Min(0)
        @Max(255)
        private int green = 0;

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

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public int getBlue() {
            return blue;
        }

        public void setBlue(int blue) {
            this.blue = blue;
        }

        public int getGreen() {
            return green;
        }

        public void setGreen(int green) {
            this.green = green;
        }

        public int color() {
            return 0xFF << 24 | this.red << 16 | this.green << 8 | this.blue;
        }
    }
}
