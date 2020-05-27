package com.aravind.projects.fwbackend;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class DataStoreServiceImpl {

	private static final String dataStorePath = "key_value_pair.json";
	private JSONObject storeMap = null;
	
	public DataStoreServiceImpl() {
		try {
			loadDataStoreResource();
		} catch (Exception e) {
			System.out.println("Failed to load data to DataStore");
			e.printStackTrace();
		}
	}

	private void loadDataStoreResource() throws ParseException, IOException {
		// read json file data to String
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(dataStorePath);
		Object obj = jsonParser.parse(reader);
		storeMap = (JSONObject) obj;
		
	}
	
	public boolean createKeyValue(String key, String value, Integer ttl)
	{
		if (storeMap.containsKey(key)) 
		{
			System.out.println("Key already exists, can't override");
			return false;
		}
		else
		{
			Key keyObject = new Key();
			keyObject.setTtl(ttl);
			keyObject.setValue(value);
			keyObject.setCreateTimeInMillis(System.currentTimeMillis());
			storeMap.put(key, keyObject);
			try {
				updateDataStore();
			} catch (IOException e) {
				System.out.println("Failed to create data in DataStore");
				storeMap.remove(key);
				e.printStackTrace();
			}
			System.out.println("Successfully added key to the file");
			return true;
		}
	}
	
	public JSONObject readKeyValue(String key)
	{
		if (isKeyExpired(key))
		{
			System.out.println("Key expired");
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, storeMap.get(key));
		return jsonObject;
	}
	
	public boolean deleteKeyValue(String key)
	{
		if (isKeyExpired(key))
		{
			System.out.println("Key expired");
			return false;
		}
		String deletedValue = (String) storeMap.remove(key);
		try {
			updateDataStore();
		} catch (IOException e) {
			System.out.println("Failed to create data in DataStore");
			storeMap.put(key, deletedValue);
			e.printStackTrace();
		}
		return true;
		
	}

	private void updateDataStore() throws IOException {
		FileWriter fileWriter = new FileWriter(dataStorePath, false);
		Gson gson = new Gson();
		String storeMapJson = gson.toJson(storeMap);
		fileWriter.write(storeMapJson);
		fileWriter.flush();
		fileWriter.close();
	}
	
	public boolean isKeyExpired(String key)
	{
		JSONObject jsonObject = (JSONObject) storeMap.get(key);
		Key keyObject = (Key) jsonObject.;
		Long currentTime = System.currentTimeMillis();
		if (currentTime > keyObject.getCreateTimeInMillis() + (keyObject.getTtl() * 1000))
		{
			return true;
		}
		return false;
	}
}
