package steve63457.forhonorrandomizer;

public class Hero {
	
	private String name;
	private String imageLocation;
	private HeroType heroType;

	public Hero(String name, String imageLocation, HeroType heroType){
		this.name = name;
		this.imageLocation = imageLocation;
		this.heroType = heroType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	
	public HeroType getHeroType() {
		return heroType;
	}

	public void setHeroType(HeroType heroType) {
		this.heroType = heroType;
	}
	
}
