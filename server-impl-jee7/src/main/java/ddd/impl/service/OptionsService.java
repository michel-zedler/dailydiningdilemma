package ddd.impl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ddd.impl.dao.VotingDao;
import ddd.impl.dao.OptionDao;
import ddd.impl.entity.Voting;
import ddd.impl.entity.Option;
import ddd.impl.model.OptionModel;

@Stateless
public class OptionsService {
	
	@Inject
	private OptionDao optionDao;
	
	@Inject
	private VotingDao votingDao;

	public void saveOptionForVoting(Long votingId, List<OptionModel> optionModels) {
		
		for (OptionModel optionModel : optionModels) {
			Option option = new Option();
			option.setCoordinates(optionModel.getCoordinates());
			option.setCreated(new Date());
			option.setDescription(optionModel.getDescription());
			option.setName(optionModel.getName());
			option.setPhoneNumber(optionModel.getPhoneNumber());
			option.setUrl(optionModel.getUrl());
			option.setLastUsed(new Date());
			optionDao.persistOption(option);
				
			Voting voting = votingDao.findById(votingId);
			
			optionDao.addOptionToVoting(voting, option);
		}		
		
	}

	public void deleteOption(Long id) {
		optionDao.deleteOption(id);		
	}

	public List<OptionModel> getOptionsForVoting(Long votingId) {
		List<Option> options = optionDao.getOptionsForVoting(votingId);
		List<OptionModel> optionModels = new ArrayList<OptionModel>();
		for (Option option : options) {
			OptionModel optionModel = new OptionModel();
			optionModel.setCoordinates(option.getCoordinates());
			optionModel.setDescription(option.getDescription());
			optionModel.setName(option.getName());
			optionModel.setPhoneNumber(option.getPhoneNumber());
			optionModel.setUrl(option.getUrl());
			optionModel.setId(option.getId());
			optionModels.add(optionModel);
		}
		return optionModels;
	}
}
