package io.github.maxneutrino.igniteeventslistener.processor;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteEvents;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ComponentScan({
        "io.github.maxneutrino.igniteeventslistener.listener",
        "io.github.maxneutrino.igniteeventslistener.processor",
})
public class IgniteTestConfig {

    @Bean
    @Primary
    public Ignite ignite() {
        Ignite ignite = Mockito.mock(Ignite.class);
        IgniteEvents events = Mockito.mock(IgniteEvents.class);

        Mockito.when(ignite.events()).thenReturn(events);
        return ignite;
    }
}
