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

    private PokeFightConstants() {
        // do not instantiate
    }

}
