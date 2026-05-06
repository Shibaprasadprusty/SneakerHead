package com.sneakerhead.backend.models.factory;

import com.sneakerhead.backend.models.enums.Brand;

import java.util.EnumMap;
import java.util.Map;

/**
 * Factory Provider (also known as a "Factory of Factories").
 *
 * <p>
 * This class acts as the single entry point for obtaining the correct
 * {@link AbstractShoeFactory} implementation for a given {@link Brand}.
 *
 * <p>
 * It uses an {@link EnumMap} to register all concrete factories at startup,
 * making it O(1) to look up a factory by brand.
 *
 * <p>
 * <b>How to use:</b>
 * 
 * <pre>{@code
 * // Get the factory for Nike
 * AbstractShoeFactory factory = ShoeFactoryProvider.getFactory(Brand.NIKE);
 *
 * // Use the factory to create shoes
 * Shoe runningShoe = factory.createRunningShoe();
 * Shoe casualShoe = factory.createCasualShoe();
 * Shoe gymShoe = factory.createTrainingShoe();
 * }</pre>
 *
 * <p>
 * <b>Adding a new brand:</b>
 * <ol>
 * <li>Add the brand to the {@link Brand} enum.</li>
 * <li>Create a new class implementing {@link AbstractShoeFactory}.</li>
 * <li>Register it in the {@code FACTORY_MAP} below.</li>
 * </ol>
 */
public final class ShoeFactoryProvider {

    /** Registry mapping each Brand to its concrete factory. */
    private static final Map<Brand, AbstractShoeFactory> FACTORY_MAP;

    static {
        FACTORY_MAP = new EnumMap<>(Brand.class);

        // Register all concrete factories here
        register(new NikeShoeFactory());
        register(new AdidasShoeFactory());
        register(new PumaShoeFactory());
        // TODO: register(new ReebokShoeFactory());
        // TODO: register(new NewBalanceShoeFactory());
        // TODO: register(new ConverseShoeFactory());
    }

    /** Utility class — no instances allowed. */
    private ShoeFactoryProvider() {
        throw new UnsupportedOperationException("ShoeFactoryProvider is a utility class");
    }

    /**
     * Returns the {@link AbstractShoeFactory} for the given {@link Brand}.
     *
     * @param brand the brand for which a factory is needed
     * @return the corresponding concrete factory
     * @throws IllegalArgumentException if no factory is registered for the given
     *                                  brand
     */
    public static AbstractShoeFactory getFactory(Brand brand) {
        if (brand == null)
            throw new IllegalArgumentException("Brand must not be null");

        AbstractShoeFactory factory = FACTORY_MAP.get(brand);
        if (factory == null) {
            throw new IllegalArgumentException(
                    "No shoe factory registered for brand: " + brand +
                            ". Please implement and register a factory for this brand.");
        }
        return factory;
    }

    /**
     * Checks whether a factory is registered for the given brand.
     *
     * @param brand the brand to check
     * @return {@code true} if a factory exists, {@code false} otherwise
     */
    public static boolean isSupported(Brand brand) {
        return brand != null && FACTORY_MAP.containsKey(brand);
    }

    /**
     * Helper to register a factory using its own
     * {@link AbstractShoeFactory#getBrand()} method.
     */
    private static void register(AbstractShoeFactory factory) {
        FACTORY_MAP.put(factory.getBrand(), factory);
    }
}
