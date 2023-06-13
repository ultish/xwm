package eclipse.bundle;

import xwm.pokemon.PokemonType;

public class EclipsePokemon implements PokemonType {

	public EclipsePokemon() {
		System.out.println("CONSTruCT");
	}
	@Override
	public String name() {
		return "EclipsePokemon";
	}

	@Override
	public int transformations() {
		return 0;
	}
	@Override
	public String toString() {
		return "EclipsePokemon [name()=" + name() + ", transformations()=" + transformations() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
