package cz.muni.fi.pv168.hotel_jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

/**
* Spring Java configuration class. See http://static.springsource.org/spring/docs/current/spring-framework-reference/html/beans.html#beans-java
*
* @author Martin Kuba makub@ics.muni.cz
*/
@Configuration  //je to konfigurace pro Spring
@EnableTransactionManagement //bude řídit transakce u metod označených @Transactional
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("classpath:schema-javadb.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean //potřeba pro @EnableTransactionManagement
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean //náš manager, bude obalen řízením transakcí
    public GuestManager customerManager() {
        return new GuestManagerImpl(dataSource());
    }

    @Bean
    public RoomManager bookManager() {
        return new RoomManagerImpl(new TransactionAwareDataSourceProxy(dataSource()));
    }

    @Bean
    public RegistrationManager leaseManager() {
    	RegistrationManager leaseManager = new RegistrationManager(dataSource());
        leaseManager.setRoomManager(bookManager());
        leaseManager.setGuestManager(customerManager());
        return leaseManager;
    }
}