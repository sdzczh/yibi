package com.yibi.orderapi.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

public class EventBusFactoryBean implements InitializingBean,FactoryBean<EventBus> {

	private EventBus eventBus;
	@Setter
	private String eventIdentifier;
	@Setter
	private Executor executor;
	
	private Set<Object> registerObject = new HashSet<>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Objects.requireNonNull(eventIdentifier);
		Objects.requireNonNull(executor);
		Objects.requireNonNull(registerObject, "注册对象不能为空");
		eventBus = new AsyncEventBus(eventIdentifier,executor);
		for(Object obj :registerObject){
			eventBus.register(obj);
		}
		
	}
	


	public void setRegisterObject(Object...objects) {
		for(Object obj:objects){
			registerObject.add(obj);
		}
	}
	
	@Override
	public EventBus getObject() throws Exception {
		// TODO Auto-generated method stub
		return eventBus;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return EventBus.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}



	

}
