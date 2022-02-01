package com.company.learningplatform.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppEventPublisher implements ApplicationEventPublisher
{
	private ApplicationEventPublisher publisher;

	@Override
	public void publishEvent(Object event)
	{
		publisher.publishEvent(event);
	}
}
