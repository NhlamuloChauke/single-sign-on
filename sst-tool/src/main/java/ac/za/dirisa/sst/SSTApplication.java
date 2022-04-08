package ac.za.dirisa.sst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ac.za.dirisa.sst.properties.ApplicationProperties;
import ac.za.dirisa.sst.properties.DataSourceProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, DataSourceProperties.class})
public class SSTApplication {

	public static void main(String[] args) {

		SpringApplication.run(SSTApplication.class, args);
	}
}