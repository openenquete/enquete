package jjug;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig.PoolConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.Getter;
import lombok.Setter;

@Profile("cloud")
@Configuration
@ConfigurationProperties("cloud")
@Getter
@Setter
public class CloudConfig extends AbstractCloudConfig {
	private int minPoolSize = 0;
	private int maxPoolSize = 4;
	private int maxWaitTime = 3000;

	@Bean
	DataSource dataSource() {
		return connectionFactory().dataSource(new DataSourceConfig(
				new PoolConfig(minPoolSize, maxPoolSize, maxWaitTime), null));
	}
}