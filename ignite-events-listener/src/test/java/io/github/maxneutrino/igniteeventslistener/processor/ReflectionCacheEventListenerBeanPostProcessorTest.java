package io.github.maxneutrino.igniteeventslistener.processor;

import io.github.maxneutrino.igniteeventslistener.IgniteEventsListenerApplication;
import io.github.maxneutrino.igniteeventslistener.listener.AnnotatedListener;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {IgniteTestConfig.class})
class ReflectionCacheEventListenerBeanPostProcessorTest {

    @Autowired
    private Ignite ignite;

    @MockBean
    private NoReflectionCacheEventsListenerBeanPostProcessor beanPostProcessor;

    @Test
    void postProcessAfterInitialization() {
        Mockito.verify(ignite.events(), Mockito.times(1)).localListen(Mockito.any(), Mockito.any());
        Mockito.verify(ignite.events(), Mockito.times(1)).remoteListen(Mockito.any(), Mockito.any(), Mockito.any());
    }
}