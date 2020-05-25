package io.github.maxneutrino.igniteeventslistener.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public Ignite ignite() {
        var ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(List.of("localhost:47500"));

        var discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setIpFinder(ipFinder);

        var configuration = new IgniteConfiguration();
        configuration.setDiscoverySpi(discoverySpi);
        configuration.setClientMode(true);

        return Ignition.start(configuration);
    }

}
