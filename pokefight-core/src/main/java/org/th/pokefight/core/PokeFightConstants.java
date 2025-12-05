package org.th.pokefight.core;

/**
 * Holding application specific constants
 * 
 * @author th
 */
public final class PokeFightConstants {

    /**
     * The pokeAPI resource contains pokemons with id 1 to 1025
     */
    public static final Integer POKEMON_ID_MIN = 1;
    /**
     * The pokeAPI resource contains pokemons with id 1 to 1025
     */
    public static final Integer POKEMON_ID_MAX = 1025;

    /** Minimum power value defined in specification */
    public static final Integer POWER_MIN = 1;
    /** Maximum power value defined in specification */
    public static final Integer POWER_MAX = 20;

    /** Historical fights query limit defined in specification */
    public static final Integer LIMIT_FIGHTS = 20;
    public static final int TYPE_FIGHTING_MIN_POWER = 20;
    public static final int TYPE_FIGHTING_MAX_POWER = 30;
    public static final int TYPE_FLYING_MIN_POWER = 10;
    public static final int TYPE_FLYING_MAX_POWER = 40;
    public static final String TYPE_FIGHTING = "fighting";
    public static final String TYPE_FLYING = "flying";

    private PokeFightConstants() {
        // do not instantiate
    }

}
