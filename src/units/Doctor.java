package units;

import exceptions.UnitFullCapacityException;

public class Doctor extends Unit {

	public Doctor(int level, int maxSoldierConunt, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		super(level, maxSoldierConunt, idleUpkeep, marchingUpkeep, siegeUpkeep);
	}

	public double getHealingFactor() {
		int level = this.getLevel();
		if (level == 1) {
			return 0.2;
		} else if (level == 2) {
			return 0.3;
		} else if (level == 3) {
			return 0.4;
		} else {
			return 0;
		}
	}

	public void heal(Unit healedUnit) throws UnitFullCapacityException {
		if (healedUnit.getCurrentSoldierCount() == 0) {
			return;
		} else if (healedUnit.getCurrentSoldierCount() == healedUnit.getMaxSoldierCount()) {
			throw new UnitFullCapacityException("The Unit is already at full capacity");
		} else {
			double healingFactor = this.getHealingFactor();
			healedUnit.setCurrentSoldierCount(
					(int) (healedUnit.getCurrentSoldierCount() + (healedUnit.getMaxSoldierCount() * healingFactor)));
			if (healedUnit.getCurrentSoldierCount() > healedUnit.getMaxSoldierCount()) {
				healedUnit.setCurrentSoldierCount(healedUnit.getMaxSoldierCount());
			}
		}
	}

}
