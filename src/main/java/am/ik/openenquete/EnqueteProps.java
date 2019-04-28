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

	public EnqueteProps() {
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EnqueteProps)) {
			return false;
		}
		final EnqueteProps other = (EnqueteProps) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$applicationName = this.getApplicationName();
		final Object other$applicationName = other.getApplicationName();
		if (this$applicationName == null ? other$applicationName != null : !this$applicationName.equals(other$applicationName)) {
			return false;
		}
		final Object this$adminUsers = this.getAdminUsers();
		final Object other$adminUsers = other.getAdminUsers();
		if (this$adminUsers == null ? other$adminUsers != null : !this$adminUsers.equals(other$adminUsers)) {
			return false;
		}
		final Object this$bitly = this.getBitly();
		final Object other$bitly = other.getBitly();
		if (this$bitly == null ? other$bitly != null : !this$bitly.equals(other$bitly)) {
			return false;
		}
		final Object this$googl = this.getGoogl();
		final Object other$googl = other.getGoogl();
		if (this$googl == null ? other$googl != null : !this$googl.equals(other$googl)) {
			return false;
		}
		final Object this$qrCode = this.getQrCode();
		final Object other$qrCode = other.getQrCode();
		if (this$qrCode == null ? other$qrCode != null : !this$qrCode.equals(other$qrCode)) {
			return false;
		}
		return true;
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

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $applicationName = this.getApplicationName();
		result = result * PRIME + ($applicationName == null ? 43 : $applicationName.hashCode());
		final Object $adminUsers = this.getAdminUsers();
		result = result * PRIME + ($adminUsers == null ? 43 : $adminUsers.hashCode());
		final Object $bitly = this.getBitly();
		result = result * PRIME + ($bitly == null ? 43 : $bitly.hashCode());
		final Object $googl = this.getGoogl();
		result = result * PRIME + ($googl == null ? 43 : $googl.hashCode());
		final Object $qrCode = this.getQrCode();
		result = result * PRIME + ($qrCode == null ? 43 : $qrCode.hashCode());
		return result;
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

	public void setGoogl(Googl googl) {
		this.googl = googl;
	}

	public void setQrCode(QrCode qrCode) {
		this.qrCode = qrCode;
	}

	public String toString() {
		return "EnqueteProps(applicationName=" + this.getApplicationName() + ", adminUsers=" + this.getAdminUsers() + ", bitly=" + this.getBitly() + ", googl=" + this.getGoogl() + ", qrCode=" + this.getQrCode() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof EnqueteProps;
	}

	public static class Bitly {
		private String accessToken;

		public Bitly() {
		}

		public boolean equals(final Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof Bitly)) {
				return false;
			}
			final Bitly other = (Bitly) o;
			if (!other.canEqual((Object) this)) {
				return false;
			}
			final Object this$accessToken = this.getAccessToken();
			final Object other$accessToken = other.getAccessToken();
			if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken)) {
				return false;
			}
			return true;
		}

		public String getAccessToken() {
			return this.accessToken;
		}

		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final Object $accessToken = this.getAccessToken();
			result = result * PRIME + ($accessToken == null ? 43 : $accessToken.hashCode());
			return result;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String toString() {
			return "EnqueteProps.Bitly(accessToken=" + this.getAccessToken() + ")";
		}

		protected boolean canEqual(final Object other) {
			return other instanceof Bitly;
		}
	}

	public static class Googl {
		private String apiKey;

		public Googl() {
		}

		public boolean equals(final Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof Googl)) {
				return false;
			}
			final Googl other = (Googl) o;
			if (!other.canEqual((Object) this)) {
				return false;
			}
			final Object this$apiKey = this.getApiKey();
			final Object other$apiKey = other.getApiKey();
			if (this$apiKey == null ? other$apiKey != null : !this$apiKey.equals(other$apiKey)) {
				return false;
			}
			return true;
		}

		public String getApiKey() {
			return this.apiKey;
		}

		public int hashCode() {
			final int PRIME = 59;
			int result = 1;
			final Object $apiKey = this.getApiKey();
			result = result * PRIME + ($apiKey == null ? 43 : $apiKey.hashCode());
			return result;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public String toString() {
			return "EnqueteProps.Googl(apiKey=" + this.getApiKey() + ")";
		}

		protected boolean canEqual(final Object other) {
			return other instanceof Googl;
		}
	}

	public static class QrCode {
		private boolean enabled = true;
		@Min(32)
		private int size = 240;

		public QrCode() {
		}

		public boolean equals(final Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof QrCode)) {
				return false;
			}
			final QrCode other = (QrCode) o;
			if (!other.canEqual((Object) this)) {
				return false;
			}
			if (this.isEnabled() != other.isEnabled()) {
				return false;
			}
			if (this.getSize() != other.getSize()) {
				return false;
			}
			return true;
		}

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

		public String toString() {
			return "EnqueteProps.QrCode(enabled=" + this.isEnabled() + ", size=" + this.getSize() + ")";
		}

		protected boolean canEqual(final Object other) {
			return other instanceof QrCode;
		}
	}
}
