package io.github.maxneutrino.igniteeventslistener.processor;

import io.github.maxneutrino.igniteeventslistener.processor.noreflection.LocalIgniteEventsListener;
import io.github.maxneutrino.igniteeventslistener.processor.noreflection.NoReflectionCacheEventsListenerBeanPostProcessor;
import io.github.maxneutrino.igniteeventslistener.processor.noreflection.RemoteIgniteEventsListener;
import io.github.maxneutrino.igniteeventslistener.processor.reflection.ReflectionCacheEventListenerBeanPostProcessor;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteEvents;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {IgniteTestConfig.class})
class NoReflectionCacheEventsListenerBeanPostProcessorTest {

    @Autowired
    private Ignite ignite;

    @MockBean
    private ReflectionCacheEventListenerBeanPostProcessor reflectionCacheEventListenerBeanPostProcessor;

    @Test
    void postProcessAfterInitialization() {
        Mockito.verify(ignite.events(), Mockito.times(1)).localListen(Mockito.any(), Mockito.any());
        Mockito.verify(ignite.events(), Mockito.times(1)).remoteListen(Mockito.any(), Mockito.any(), Mockito.any());

    }
}