package ddd.impl.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.ConfigurationDao;
import ddd.impl.entity.Configuration;

@Stateless
public class ConfigurationService {

	@Inject
	private ConfigurationDao configurationDao;

	public String findByName(String name) {
		Configuration configuration = configurationDao.findByName(name);

		if (configuration != null) {
			return configuration.getValue();
		} else {
			return null;
		}
	}
}
