package steve63457.forhonorrandomizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class HeroSelection {
	
	private static HeroSelection heroSelection;
	private Map<String, Boolean> selection;
	private Randomizer randomizer;
	private static final String PROPERTY_FILE_NAME = "ForHonorRandomizer.properties";
	
	private HeroSelection(){
		randomizer = Randomizer.getInstance();
		selection = new HashMap<String, Boolean>();
		
		// default: put all heroes in initial selection
		for(Hero hero : randomizer.getHeroes()){
			selection.put(hero.getName(), true);
		}
		
		// if user selection is found on disk, overwrite
		loadHeroSelection();
	}
	
	public static HeroSelection getInstance(){
		if(heroSelection == null){
			heroSelection = new HeroSelection();
		}
		return heroSelection;
	}
	
	public boolean isHeroSelected(String name){
		return selection.get(name);
	}
	
	public void updateHeroSelection(String name, boolean selected){
		selection.put(name, selected);
	}
	
	private void loadHeroSelection(){
		Properties props = new Properties();
		InputStream is = null;
		
		// first check if a file exists
		try{
			File f = new File(PROPERTY_FILE_NAME);
			is = new FileInputStream(f);
		}
		catch(Exception ex){
			is = null;
		}
		
		try{
			if(is == null){
				// try loading from classpath
				is = getClass().getResourceAsStream(PROPERTY_FILE_NAME);
			}
			
			// try loading properties from file (if found)
			props.load(is);
		}
		catch(Exception ex){
			return;
		}
		
		for(Hero hero : randomizer.getHeroes()){
			try{
				selection.put(hero.getName(), new Boolean(props.getProperty(hero.getName())));
			}
			catch(Exception ex){
				selection.put(hero.getName(), false);
			}
		}
	}
	
	public void saveHeroSelection(){
		try{
			Properties props = new Properties();

			for(Entry<String, Boolean> entry : selection.entrySet()){
				props.setProperty(entry.getKey(), entry.getValue().toString());
			}
			
			File f = new File(PROPERTY_FILE_NAME);
			OutputStream out = new FileOutputStream(f);
			props.store(out, "User selection of preferred For Honor heroes");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
