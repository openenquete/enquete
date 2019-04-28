package am.ik.openenquete;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig.PoolConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("cloud")
@Configuration
@ConfigurationProperties("enquete.cloud")
public class CloudConfig extends AbstractCloudConfig {
	private int minPoolSize = 0;
	private int maxPoolSize = 4;
	private int maxWaitTime = 3000;

	public int getMaxPoolSize() {
		return this.maxPoolSize;
	}

	public int getMaxWaitTime() {
		return this.maxWaitTime;
	}

	public int getMinPoolSize() {
		return this.minPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	@Bean
	DataSource dataSource() {
		return connectionFactory().dataSource(new DataSourceConfig(
				new PoolConfig(minPoolSize, maxPoolSize, maxWaitTime), null));
	}
}