package steve63457.forhonorrandomizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Randomizer {

	private static Randomizer randomizer;
	private List<Hero> heroes;
	private HeroSelection heroSelection;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Randomizer.getInstance();	
		
		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
        		Visualizer visualizer = new Visualizer();
            }
        });
	}
	
	private Randomizer(){
		initHeroes();
	}
	
	public static Randomizer getInstance(){
		if(randomizer == null){
			randomizer = new Randomizer();
			randomizer.heroSelection = HeroSelection.getInstance();
		}
		return randomizer;
	}
	
	public Hero GetRandomHero(SelectionCriteria selectionCriteria){
		List<Hero> filteredHeroes = new ArrayList<Hero>();
		for(Hero hero : heroes){
			if(selectionCriteria.isUseHeroSelection()){
				if(heroSelection.isHeroSelected(hero.getName())){
					filteredHeroes.add(hero);
				}
			}
			else{
				if(hero.getHeroType() == HeroType.VANGUARD && selectionCriteria.isIncludeVanguard()){
					filteredHeroes.add(hero);
				}
				else if(hero.getHeroType() == HeroType.HEAVY && selectionCriteria.isIncludeHeavy()){
					filteredHeroes.add(hero);
				}
				else if(hero.getHeroType() == HeroType.ASSASSIN && selectionCriteria.isIncludeAssassin()){
					filteredHeroes.add(hero);
				}
				else if(hero.getHeroType() == HeroType.HYBRID && selectionCriteria.isIncludeHybrid()){
					filteredHeroes.add(hero);
				}
			}
		}
		if(filteredHeroes.isEmpty()){
			return null;
		}
		return filteredHeroes.get(new Random().nextInt(filteredHeroes.size()));
	}
	
	private void initHeroes(){
		heroes = new ArrayList<Hero>();
		heroes.add(new Hero("Warden", "/warden.png", HeroType.VANGUARD));
		heroes.add(new Hero("Conqueror", "/conqueror.png", HeroType.HEAVY));
		heroes.add(new Hero("Peacekeeper", "/peacekeeper.png", HeroType.ASSASSIN));
		heroes.add(new Hero("Lawbringer", "/lawbringer.png", HeroType.HYBRID));
		heroes.add(new Hero("Raider", "/raider.png", HeroType.VANGUARD));
		heroes.add(new Hero("Warlord", "/warlord.png", HeroType.HEAVY));
		heroes.add(new Hero("Berserker", "/berserker.png", HeroType.ASSASSIN));
		heroes.add(new Hero("Valkyrie", "/valkyrie.png", HeroType.HYBRID));
		heroes.add(new Hero("Kensei", "/kensei.png", HeroType.VANGUARD));
		heroes.add(new Hero("Shugoki", "/shugoki.png", HeroType.HEAVY));
		heroes.add(new Hero("Orochi", "/orochi.png", HeroType.ASSASSIN));
		heroes.add(new Hero("Nobushi", "/nobushi.png", HeroType.HYBRID));
		heroes.add(new Hero("Centurion", "/centurion.png", HeroType.HYBRID));
		heroes.add(new Hero("Shinobi", "/shinobi.png", HeroType.ASSASSIN));
	}
	
	public List<Hero> getHeroes(){
		return heroes;
	}

}
