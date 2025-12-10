package core;

/**
 * Interface for objects that can use a special ability.
 * Implemented by Player & Boss
 */
public interface SpecialAbilityCapable {

    /**
     * Activates the special ability of the object.
     * The effect of the ability depends on the implementing class.
     */
    void useSpecialAbility();
}
