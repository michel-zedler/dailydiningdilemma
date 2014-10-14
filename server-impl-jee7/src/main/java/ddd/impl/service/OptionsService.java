package ddd.impl.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.DecisionDao;
import ddd.impl.dao.OptionDao;
import ddd.impl.entity.Decision;
import ddd.impl.entity.Option;
import ddd.impl.model.OptionModel;

@Stateless
public class OptionsService {
	
	@Inject
	private OptionDao optionDao;
	
	@Inject
	private DecisionDao decisionDao;

	public void saveOptionForDecision(Long decisionId, OptionModel optionModel) {
		Option option = new Option();
		option.setCoordinates(optionModel.getCoordinates());
		option.setCreated(new Date());
		option.setDescription(optionModel.getDescription());
		option.setName(optionModel.getName());
		option.setPhoneNumber(optionModel.getPhoneNumber());
		option.setUrl(optionModel.getUrl());
		option.setLastUsed(new Date());
		optionDao.persistOption(option);
			
		Decision decision = decisionDao.findById(decisionId);
		
		optionDao.addOptionToDecision(decision, option);		
	}
}
