package org.th.pokefight.core.exception;

@SuppressWarnings("serial")
public class NoSuchPokemonException extends Exception {

    public NoSuchPokemonException(String name, Throwable cause) {
        super(String.format("Could not find pokemon with name <%s>", name), cause);
    }
}
