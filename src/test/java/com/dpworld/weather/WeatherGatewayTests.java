package com.dpworld.weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class WeatherGatewayTests {

    @Autowired
    @Qualifier("bomResponseChannel")
    PollableChannel bomResponseChannel;

    @Autowired
    @Qualifier("bomRequestChannel")
    MessageChannel bomRequestChannel;

    @Test
    public void testHttpOutbound() {
        bomRequestChannel.send(MessageBuilder.withPayload("").build());
        Message<?> message = bomResponseChannel.receive();
        assertThat(message.getPayload(), is(notNullValue()));
    }


}
