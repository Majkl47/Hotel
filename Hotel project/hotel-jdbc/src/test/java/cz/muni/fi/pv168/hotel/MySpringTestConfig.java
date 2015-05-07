package cz.muni.fi.pv168.hotel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

@Configuration
@EnableTransactionManagement
public class MySpringTestConfig {

    @Bean
    public DataSource dataSource() {
        //embedded databáze
        return new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("classpath:schema-javadb.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public GuestManager guestManager() {
        return new GuestManagerImpl(dataSource());
    }

    @Bean
    public RoomManager roomManager() {
        return new RoomManagerImpl(new TransactionAwareDataSourceProxy(dataSource()));
    }

    @Bean
    public RegistrationManager registrationManager() {
        RegistrationManagerImpl registrationManager = new RegistrationManagerImpl(dataSource());
        registrationManager.setRoomManager(roomManager());
        registrationManager.setGuestManager(guestManager());
        return registrationManager;
    }
}
