package tw.ym.fshra;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.retry.annotation.EnableRetry;
import tw.ym.fshra.util.Log;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@EnableRetry //spring boot 主程式，不行自動重跑
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        // 不要自動取得 db 設定，自行宣告 bean (以 orm 代管 hibernate)
        DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})

//we initialize the Servlet context required by Tomcat
//by implementing the SpringBootServletInitializer interface
public class FsrisApplication extends SpringBootServletInitializer {

    private Properties hibernateProperties = new Properties();
    private static final String Hibernate_EntityPackages = "tw.ym.fshra.bean";
    private static final String HibernateProperties = "hibernate.properties";

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    public static void main(String[] args) {
        SpringApplication.run(FsrisApplication.class, args);
    }

    @PostConstruct
    public void loadHibernateProperties() {
        Log.info("Initialized hibernateProperties....");
        try {
            hibernateProperties.load(new ClassPathResource(HibernateProperties).getInputStream());
            Log.info("Initialized hibernateProperties complete.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Initialized hibernateProperties exception: " + e.getMessage());
        }
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // See: application.properties
        dataSource.setDriverClassName(datasourceDriver);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory() {
        try {
            Log.info("Initialized hibernate sessionFactory....");

            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

            // Package contain entity classes
            factoryBean.setPackagesToScan(Hibernate_EntityPackages);
            factoryBean.setDataSource(dataSource());
            factoryBean.setHibernateProperties(hibernateProperties);
            factoryBean.afterPropertiesSet();
            Log.info("Initialized hibernate sessionFactory complete.");
            return factoryBean.getObject();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.error("Initialized hibernate sessionFactory exception: " + e.getMessage());
            return null;
        }
    }

    @Autowired // 取得 sessionFactory & HibernateTransactionManager
    @Primary //沒特別寫 value 以這個為主
    @Bean
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FsrisApplication.class);
    }

}
