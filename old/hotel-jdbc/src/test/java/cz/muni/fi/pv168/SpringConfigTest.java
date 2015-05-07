package cz.muni.fi.pv168;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.GuestManagerImpl;
import cz.muni.fi.pv168.hotel.RegistrationManager;
import cz.muni.fi.pv168.hotel.RegistrationManagerImpl;
import cz.muni.fi.pv168.hotel.RoomManager;
import cz.muni.fi.pv168.hotel.RoomManagerImpl;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

/**
* Spring Java configuration class. See http://static.springsource.org/spring/docs/current/spring-framework-reference/html/beans.html#beans-java
*
* @author Martin Kuba makub@ics.muni.cz
*/
@Configuration  //je to konfigurace pro Spring
@EnableTransactionManagement //bude řídit transakce u metod označených @Transactional
public class SpringConfigTest {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .addScript("classpath:createDatabase.sql")
                .addScript("classpath:deleteDatabase.sql")
                .build();
    }

    @Bean //potřeba pro @EnableTransactionManagement
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean //náš manager, bude obalen řízením transakcí
    public RoomManager roomManager() {
        return new RoomManagerImpl(dataSource());
    }

    @Bean
    public GuestManager guestManager() {
        return new GuestManagerImpl(new TransactionAwareDataSourceProxy(dataSource()));
    }

    @Bean
    public RegistrationManager leaseManager() {
        RegistrationManagerImpl leaseManager = new RegistrationManagerImpl(dataSource());
        leaseManager.setGuestManager(guestManager());
        leaseManager.setRoomManager(roomManager());
        return leaseManager;
    }
}
