import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public abstract class Stat20EventHandlers {

	public static void handleSkills(ActionEvent e, Character character) {
		if (e.getSource() instanceof CheckBox) {
    		CheckBox cb = (CheckBox) e.getSource();
    		if (cb.isSelected()) {
    			character.applySkillRank();
    		}
    		else {
    			character.unApplySkillRank();
    		}
    	}
	}


}
