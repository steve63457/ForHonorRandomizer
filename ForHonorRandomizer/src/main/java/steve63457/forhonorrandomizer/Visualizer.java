package steve63457.forhonorrandomizer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Visualizer extends JFrame implements ActionListener{
	
	private Randomizer randomizer;
	private JPanel cards;
	private JPanel mainPanel;
	private JPanel heroSelectionPanel;
	private ImagePanel imagePanel;
	private JCheckBox cbxHeroSelection;
	private JCheckBox cbxVanguard;
	private JCheckBox cbxHeavy;
	private JCheckBox cbxAssassin;
	private JCheckBox cbxHybrid;
	private JButton rollButton;
	private JButton heroSelectionButton;
	private JButton doneButton;
	private List<JCheckBox> heroSelectionCheckboxes;
	private static Map<String, BufferedImage> bufferedImageMap = new HashMap<String, BufferedImage>();
	private static final Color TEXT_COLOR = new Color(200,80,0);
	
	public Visualizer(){
		this.randomizer = Randomizer.getInstance();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(350, 500));
		this.setLayout(new BorderLayout());
		
		initMainPanel();
		initHeroSelectionPanel();
		
		cards = new JPanel(new CardLayout());
		cards.add(mainPanel, "mainPanel");
		cards.add(heroSelectionPanel, "heroSelectionPanel");
		getContentPane().add(cards, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	private void initMainPanel(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		BufferedImage image = getCachedImage("/splash.jpg");
		imagePanel = new ImagePanel(image);
		imagePanel.setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(imagePanel);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		initCheckBoxes();
		
		rollButton = new JButton("Roll");
		rollButton.addActionListener(this);
		mainPanel.add(rollButton, BorderLayout.PAGE_END);
	}
	
	private void initCheckBoxes(){
		JPanel cbPanel = new JPanel();
		cbPanel.setOpaque(false);
		cbPanel.setLayout(new BoxLayout(cbPanel, BoxLayout.Y_AXIS));
		imagePanel.add(cbPanel, BorderLayout.PAGE_END);
		
		JPanel vanguardPanel = new JPanel();
		vanguardPanel.setOpaque(false);
		vanguardPanel.setLayout(new BorderLayout());
		cbPanel.add(vanguardPanel, BorderLayout.WEST);
		cbxVanguard = new JCheckBox("Vanguard");
		cbxVanguard.setOpaque(false);
		cbxVanguard.setSelected(true);
		cbxVanguard.setForeground(TEXT_COLOR);
		vanguardPanel.add(cbxVanguard);

		JPanel heavyPanel = new JPanel();
		heavyPanel.setOpaque(false);
		heavyPanel.setLayout(new BorderLayout());
		cbPanel.add(heavyPanel, BorderLayout.WEST);
		cbxHeavy = new JCheckBox("Heavy");
		cbxHeavy.setOpaque(false);
		cbxHeavy.setSelected(true);
		cbxHeavy.setForeground(TEXT_COLOR);
		heavyPanel.add(cbxHeavy);
		
		JPanel assassinPanel = new JPanel();
		assassinPanel.setOpaque(false);
		assassinPanel.setLayout(new BorderLayout());
		cbPanel.add(assassinPanel, BorderLayout.WEST);
		cbxAssassin = new JCheckBox("Assassin");
		cbxAssassin.setOpaque(false);
		cbxAssassin.setSelected(true);
		cbxAssassin.setForeground(TEXT_COLOR);
		assassinPanel.add(cbxAssassin);
		
		JPanel hybridPanel = new JPanel();
		hybridPanel.setOpaque(false);
		hybridPanel.setLayout(new BorderLayout());
		cbPanel.add(hybridPanel, BorderLayout.WEST);
		cbxHybrid = new JCheckBox("Hybrid");
		cbxHybrid.setOpaque(false);
		cbxHybrid.setSelected(true);
		cbxHybrid.setForeground(TEXT_COLOR);
		hybridPanel.add(cbxHybrid);
		
		JPanel hsPanel = new JPanel();
		hsPanel.setOpaque(false);
		hsPanel.setLayout(new BorderLayout());
		cbPanel.add(hsPanel, BorderLayout.WEST);
		cbxHeroSelection = new JCheckBox("My heroes ");
		cbxHeroSelection.setOpaque(false);
		cbxHeroSelection.setSelected(false);
		cbxHeroSelection.setForeground(TEXT_COLOR);
		cbxHeroSelection.addActionListener(this);
		hsPanel.add(cbxHeroSelection, BorderLayout.WEST);
		JPanel hsbPanel = new JPanel();
		hsbPanel.setOpaque(false);
		hsbPanel.setLayout(new BorderLayout());
		hsPanel.add(hsbPanel, BorderLayout.CENTER);
		heroSelectionButton = new JButton("...");
		heroSelectionButton.addActionListener(this);
		hsbPanel.add(heroSelectionButton, BorderLayout.WEST);
	}
	
	private void initHeroSelectionPanel(){
		heroSelectionPanel = new JPanel();
		heroSelectionPanel.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(panel);
		heroSelectionPanel.add(scrollPane, BorderLayout.CENTER);
		
		HeroSelection heroSelection = HeroSelection.getInstance();
		heroSelectionCheckboxes = new ArrayList<JCheckBox>();

		for(Hero hero : randomizer.getHeroes()){
			JCheckBox cbx = new JCheckBox(hero.getName());
			cbx.setOpaque(false);
			cbx.setSelected(heroSelection.isHeroSelected(hero.getName()));
			cbx.setForeground(TEXT_COLOR);
			panel.add(cbx);
			heroSelectionCheckboxes.add(cbx);
		}
		
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		heroSelectionPanel.add(doneButton, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rollButton){
			Hero hero = randomizer.GetRandomHero(getSelectionCriteria());
			if(hero != null){
				this.setTitle(hero.getName());
				BufferedImage image = getCachedImage(hero.getImageLocation());
				imagePanel.setImage(image);
			}
			else{
				this.setTitle("Oops, no hero found!");
				BufferedImage image = getCachedImage("/splash.jpg");
				imagePanel.setImage(image);
			}
		}
		else if(e.getSource() == cbxHeroSelection){
			cbxVanguard.setEnabled(!cbxHeroSelection.isSelected());
			cbxHeavy.setEnabled(!cbxHeroSelection.isSelected());
			cbxAssassin.setEnabled(!cbxHeroSelection.isSelected());
			cbxHybrid.setEnabled(!cbxHeroSelection.isSelected());
		}
		else if(e.getSource() == heroSelectionButton){
			CardLayout cl = (CardLayout)cards.getLayout();
			cl.show(cards, "heroSelectionPanel");
		}
		else if(e.getSource() == doneButton){
			HeroSelection heroSelection = HeroSelection.getInstance();
			
			for(JCheckBox cbx : heroSelectionCheckboxes){
				heroSelection.updateHeroSelection(cbx.getText(), cbx.isSelected());
			}
			heroSelection.saveHeroSelection();
			
			CardLayout cl = (CardLayout)cards.getLayout();
			cl.show(cards, "mainPanel");
		}
	}
	
	public SelectionCriteria getSelectionCriteria(){
		SelectionCriteria selectionCriteria = new SelectionCriteria();
		selectionCriteria.setUseHeroSelection(cbxHeroSelection.isSelected());
		selectionCriteria.setIncludeVanguard(cbxVanguard.isSelected());
		selectionCriteria.setIncludeHeavy(cbxHeavy.isSelected());
		selectionCriteria.setIncludeAssassin(cbxAssassin.isSelected());
		selectionCriteria.setIncludeHybrid(cbxHybrid.isSelected());
		return selectionCriteria;
	}
	
	private BufferedImage getCachedImage(String imageLocation){
		if(bufferedImageMap.containsKey(imageLocation)){
			return bufferedImageMap.get(imageLocation);
		}
		else{
			URL imageUrl = this.getClass().getResource(imageLocation);
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageUrl);
				bufferedImageMap.put(imageLocation, image);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return image;
		}
	}
	
}
