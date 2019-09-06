package com.yumu.eventsapiserv.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.user.preferences.Choice;
import com.yumu.eventsapiserv.pojos.user.preferences.Preferences;
import com.yumu.eventsapiserv.pojos.user.preferences.Selection;
import com.yumu.eventsapiserv.repositories.PreferencesRepository;
import com.yumu.eventsapiserv.utils.PropertiesUtil;


@Component
public class UserPreferencesManager {

	@Autowired
	private PreferencesRepository prefsRepo;
	
	@Autowired
	private PropertiesUtil propsUtil;

	public Preferences initializePreferencesForUser(String userId){

		/*
		 * Manual way to initialize
		 */
		Preferences prefs  = new Preferences();
		prefs.setVersion(Preferences.Version._1_0);
		prefs.setYumuUserId(userId);
		prefs.setCreatedAt(new DateTime(DateTimeZone.UTC));
		
		List<Choice> choices = new ArrayList<>();
		prefs.setChoices(choices);
		
	
		List<String> categories = propsUtil.getStringList("preferences.categories");
		
		categories.forEach(cat -> {
			String key = "preferences.categories." + cat;
			List<String> subCats = propsUtil.getStringList(key);
			Choice choice = new Choice();
			choices.add(choice);
			choice.setCategory(cat);
			List<Selection> selections = new ArrayList<>();
			choice.setSelections(selections);
			subCats.forEach(sc -> {
				Selection s = new Selection();
				s.setName(sc);
				s.setSelected(false);
				selections.add(s);
			});
		});


		return prefsRepo.save(prefs);
	}

	
	public Preferences getPreferencesByUserId(String userId){
		return prefsRepo.findByYumuUserId(userId);
	}

	
	/*
	 * TODO: very manual way to update existing preferences with newer ones down the time line
	 * UC: You start with 3 categories Hobby, Sports, Music in version 1 and later.
	 * Adding more categories to preferences would require a code change here.
	 */
	/*
	 * Why is this update messed up?
	 * We do this because a batch could be running in the background and it could have added
	 * new preferences. So we cannot blindly overwrite storedChoices with new ones
	 * 
	 * 1. Get stored choices
	 * 2. Get new choices from api input
	 * 3. Update stored chocies with new ones from api
	 * 
	 */
	public Preferences updatePreferences(String userId, Preferences preferences, List<Choice> choices) {

		
		List<Choice> storedChoices = preferences.getChoices();
		/*
		 * convert list to map for easy search
		 */
		Map<String, List<Selection>> map = new HashMap<>();
		storedChoices.forEach(x -> {
			map.put(x.getCategory(), x.getSelections());
		});
		
		choices.forEach(choice -> {
			
			List<Selection> storedSelections = map.get(choice.getCategory());
			if(storedSelections!=null){
				Map<String, Boolean> selectionMap = new HashMap<>();
				choice.getSelections().forEach(s -> {
					selectionMap.put(s.getName(), s.getSelected());
				});
				storedSelections.forEach(s -> {
					Boolean bSelect = selectionMap.get(s.getName());
					if(bSelect!=null){
						s.setSelected(bSelect);
					}
				});
			}
			
		});
	
		preferences.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		return prefsRepo.save(preferences);
		
	}
	
}
