package com.sneakerhead.backend.models.factory;

import com.sneakerhead.backend.models.Shoe;
import com.sneakerhead.backend.models.enums.Brand;

/**
 * Abstract Factory interface for creating {@link Shoe} objects.
 *
 * <p>
 * Each concrete factory implementation is responsible for a specific
 * {@link Brand}
 * and knows how to create that brand's signature shoe variants:
 * <ul>
 * <li>Running shoes</li>
 * <li>Casual / lifestyle shoes</li>
 * <li>Training shoes</li>
 * </ul>
 *
 * <p>
 * This is the <b>Abstract Factory Pattern</b>: the client code depends only on
 * this interface, not on any brand-specific implementation.
 *
 * <p>
 * Design pattern relationships:
 * 
 * <pre>
 *   AbstractShoeFactory  ←  ShoeFactoryProvider (selects factory by Brand enum)
 *         ↑
 *   NikeShoeFactory
 *   AdidasShoeFactory
 *   PumaShoeFactory
 *   ... (one per brand)
 * </pre>
 */
public interface AbstractShoeFactory {

    /**
     * Returns the {@link Brand} this factory is responsible for.
     */
    Brand getBrand();

    /**
     * Creates a signature running shoe for this brand.
     *
     * @return a fully configured {@link Shoe} instance
     */
    Shoe createRunningShoe();

    /**
     * Creates a signature casual / lifestyle shoe for this brand.
     *
     * @return a fully configured {@link Shoe} instance
     */
    Shoe createCasualShoe();

    /**
     * Creates a signature training / gym shoe for this brand.
     *
     * @return a fully configured {@link Shoe} instance
     */
    Shoe createTrainingShoe();
}
