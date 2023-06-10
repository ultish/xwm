package not.bundle;

import xwm.pokemon.PokemonType;

public class NotBundle implements PokemonType {
    @Override
    public String name() {
        return "Not Bundle";
    }

    @Override
    public int transformations() {
        return 3;
    }
}
