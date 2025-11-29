package org.th.pokefight.core.exception;

@SuppressWarnings("serial")
public class NoSuchPokemonException extends RuntimeException {

    public NoSuchPokemonException(String name) {
        super(String.format("Could not find pokemon with name or id <%s>", name));
    }
}
