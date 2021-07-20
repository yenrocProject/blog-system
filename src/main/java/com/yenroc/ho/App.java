package com.yenroc.ho;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.yenroc.ho.common.context.SpringContextHolder;
import com.yenroc.ho.runner.MyMapperAutoConfiguredMyBatisRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Import(MyMapperAutoConfiguredMyBatisRegistrar.class)
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(App.class);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
                        + "External: \t{}://{}:{}\n\t"
                        + "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
                InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getActiveProfiles());

        String configServerStatus = env.getProperty("configserver.status");
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);

        System.out.println("TestDemoDao=" + SpringContextHolder.getBean("testDemoDao"));
    }
}
