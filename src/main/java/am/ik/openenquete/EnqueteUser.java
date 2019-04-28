package am.ik.openenquete;

import java.io.Serializable;

public class EnqueteUser implements Serializable {
	private final String name;
	private final String github;
	private final String email;
	private final String avatarUrl;

	public EnqueteUser(String name, String github, String email, String avatarUrl) {
		this.name = name;
		this.github = github;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public static EnqueteUserBuilder builder() {
		return new EnqueteUserBuilder();
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EnqueteUser)) {
			return false;
		}
		final EnqueteUser other = (EnqueteUser) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
			return false;
		}
		final Object this$github = this.getGithub();
		final Object other$github = other.getGithub();
		if (this$github == null ? other$github != null : !this$github.equals(other$github)) {
			return false;
		}
		final Object this$email = this.getEmail();
		final Object other$email = other.getEmail();
		if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
			return false;
		}
		final Object this$avatarUrl = this.getAvatarUrl();
		final Object other$avatarUrl = other.getAvatarUrl();
		if (this$avatarUrl == null ? other$avatarUrl != null : !this$avatarUrl.equals(other$avatarUrl)) {
			return false;
		}
		return true;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public String getEmail() {
		return this.email;
	}

	public String getGithub() {
		return this.github;
	}

	public String getName() {
		return this.name;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $github = this.getGithub();
		result = result * PRIME + ($github == null ? 43 : $github.hashCode());
		final Object $email = this.getEmail();
		result = result * PRIME + ($email == null ? 43 : $email.hashCode());
		final Object $avatarUrl = this.getAvatarUrl();
		result = result * PRIME + ($avatarUrl == null ? 43 : $avatarUrl.hashCode());
		return result;
	}

	public String toString() {
		return "EnqueteUser(name=" + this.getName() + ", github=" + this.getGithub() + ", email=" + this.getEmail() + ", avatarUrl=" + this.getAvatarUrl() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof EnqueteUser;
	}

	public static class EnqueteUserBuilder {

		private String avatarUrl;

		private String email;

		private String github;

		private String name;

		EnqueteUserBuilder() {
		}

		public EnqueteUser.EnqueteUserBuilder avatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
			return this;
		}

		public EnqueteUser build() {
			return new EnqueteUser(name, github, email, avatarUrl);
		}

		public EnqueteUser.EnqueteUserBuilder email(String email) {
			this.email = email;
			return this;
		}

		public EnqueteUser.EnqueteUserBuilder github(String github) {
			this.github = github;
			return this;
		}

		public EnqueteUser.EnqueteUserBuilder name(String name) {
			this.name = name;
			return this;
		}

		public String toString() {
			return "EnqueteUser.EnqueteUserBuilder(name=" + this.name + ", github=" + this.github + ", email=" + this.email + ", avatarUrl=" + this.avatarUrl + ")";
		}
	}
}
