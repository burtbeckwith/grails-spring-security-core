/* Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.springsecurity;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

/**
 * Configures a {@link LogoutFilter} given a list of {@link LogoutHandler}s.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class LogoutFilterFactoryBean implements FactoryBean<LogoutFilter>, InitializingBean {

	private List<LogoutHandler> _handlers;
	private LogoutFilter _logoutFilter;
	private String _logoutSuccessUrl;
	private String _filterProcessesUrl;

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public LogoutFilter getObject() {
		return _logoutFilter;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<LogoutFilter> getObjectType() {
		return LogoutFilter.class;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(_logoutSuccessUrl, "logoutSuccessUrl is required");
		Assert.notNull(_handlers, "handlers are required");

		_logoutFilter = new LogoutFilter(_logoutSuccessUrl,
				_handlers.toArray(new LogoutHandler[_handlers.size()]));
		_logoutFilter.setFilterProcessesUrl(_filterProcessesUrl);
	}

	/**
	 * Dependency injection for the logout success url.
	 * @param url  the url
	 */
	public void setLogoutSuccessUrl(final String url) {
		_logoutSuccessUrl = url;
	}

	/**
	 * Dependency injection for 'filterProcessesUrl.
	 * @param url  the url
	 */
	public void setFilterProcessesUrl(final String url) {
		_filterProcessesUrl = url;
	}

	/**
	 * Dependency injection for the handlers.
	 * @param handlers  the handlers
	 */
	public void setHandlers(final List<LogoutHandler> handlers) {
		_handlers = handlers;
	}

	/**
	 * For debugging.
	 * @return  the handlers
	 */
	public List<LogoutHandler> getHandlers() {
		return Collections.unmodifiableList(_handlers);
	}
}
