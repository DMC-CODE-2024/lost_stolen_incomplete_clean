package app.cleanup.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"app.cleanup.repository.app"}, entityManagerFactoryRef = "appEntityManagerFactory", transactionManagerRef = "appTransactionManager")


@EntityScan("app.cleanup.entity.app")

public class AppDbConfig {

    @Primary
    @Bean(name = "appEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(@Qualifier("appDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages("app.cleanup.entity.app").persistenceUnit("app") // CHANGE TO CEIR
                .properties(jpaProperties()).build();
        // builder.dataSource(dataSource).packages("com.javadevjournal.product.data").persistenceUnit("db2").build();

    }

    @Primary
    @Bean(name = "appTransactionManager")
    public PlatformTransactionManager appTransactionManager(@Qualifier("appEntityManagerFactory") LocalContainerEntityManagerFactoryBean appEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(appEntityManagerFactory.getObject()));
    }

    // DataSource Configs
    @Primary
    @Bean(name = "appDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
         props.put("hibernate.physical_naming_strategy", org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        return props;
    }

}
