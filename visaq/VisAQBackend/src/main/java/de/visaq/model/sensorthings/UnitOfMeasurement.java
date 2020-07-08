package de.visaq.model.sensorthings;

import java.util.Objects;

/**
 * A Unit of Measurement describes the Unit in which data is presented.
 */
public class UnitOfMeasurement {
    public final String symbol;
    public final String name;
    public final String definition;

    /**
     * Constructs a new {@link UnitOfMeasurement}.
     *
     * @param name       The name of the {@link UnitOfMeasurement}
     * @param symbol     The symbol of the {@link UnitOfMeasurement}
     * @param definition The definition of the {@link UnitOfMeasurement}
     */
    public UnitOfMeasurement(String name, String symbol, String definition) {
        this.name = name;
        this.symbol = symbol;
        this.definition = definition;
    }

    /**
     * Two {@link UnitOfMeasurement}s are equal if they have the same name, symbol and definition.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UnitOfMeasurement)) {
            return false;
        }
        UnitOfMeasurement other = (UnitOfMeasurement) obj;
        return Objects.equals(definition, other.definition) && Objects.equals(name, other.name)
                && Objects.equals(symbol, other.symbol);
    }

}
