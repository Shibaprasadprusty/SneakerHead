package com.sneakerhead.backend.models.factory;

import com.sneakerhead.backend.models.Shoe;
import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.Color;
import com.sneakerhead.backend.models.enums.IdealFor;
import com.sneakerhead.backend.models.enums.Material;
import com.sneakerhead.backend.models.enums.ShoeType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Concrete Factory for {@link Brand#ADIDAS} shoes.
 *
 * <p>
 * Uses the {@link Shoe.Builder} (Builder Pattern) internally to construct
 * each Adidas shoe variant with brand-appropriate defaults.
 */
public class AdidasShoeFactory implements AbstractShoeFactory {

    @Override
    public Brand getBrand() {
        return Brand.ADIDAS;
    }

    /**
     * Creates Adidas's signature running shoe — Ultraboost 23 (Core Black edition).
     */
    @Override
    public Shoe createRunningShoe() {
        return new Shoe.Builder("Adidas Ultraboost 23", "AD-UB23-CB", Brand.ADIDAS)
                .price(new BigDecimal("17999.00"))
                .discount(15.0)
                .colors(List.of(Color.BLACK, Color.WHITE))
                .sizes(List.of(6.0, 7.0, 7.5, 8.0, 9.0, 10.0, 11.0))
                .shoeType(ShoeType.RUNNING)
                .material(Material.KNIT)
                .idealFor(IdealFor.MEN)
                .productDetails(
                        "Adidas Ultraboost 23 delivers incredible energy return with " +
                                "BOOST midsole technology. The Primeknit+ upper adapts to your foot " +
                                "for a sock-like fit. Linear Energy Push system propels you forward " +
                                "with every stride. Ideal for road running and long-distance training.")
                .build();
    }

    /**
     * Creates Adidas's signature casual shoe — Stan Smith (White/Green edition).
     */
    @Override
    public Shoe createCasualShoe() {
        return new Shoe.Builder("Adidas Stan Smith", "AD-SS-WG", Brand.ADIDAS)
                .price(new BigDecimal("7499.00"))
                .discount(0.0)
                .colors(List.of(Color.WHITE, Color.GREEN))
                .sizes(List.of(6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0))
                .shoeType(ShoeType.CASUAL)
                .material(Material.LEATHER)
                .idealFor(IdealFor.UNISEX)
                .productDetails(
                        "The Adidas Stan Smith is a timeless classic that has been a " +
                                "staple of street style since the 1970s. The clean leather upper " +
                                "and iconic perforated 3-Stripes make it instantly recognizable. " +
                                "OrthoLite sockliner provides cushioned comfort all day long.")
                .build();
    }

    /**
     * Creates Adidas's signature training shoe — Powerlift 5 (White/Silver
     * edition).
     */
    @Override
    public Shoe createTrainingShoe() {
        return new Shoe.Builder("Adidas Powerlift 5", "AD-PL5-WS", Brand.ADIDAS)
                .price(new BigDecimal("9999.00"))
                .discount(8.0)
                .colors(List.of(Color.WHITE, Color.SILVER))
                .sizes(List.of(7.0, 8.0, 9.0, 10.0, 11.0))
                .shoeType(ShoeType.TRAINING)
                .material(Material.SYNTHETIC_LEATHER)
                .idealFor(IdealFor.UNISEX)
                .productDetails(
                        "Adidas Powerlift 5 is engineered for weightlifting performance. " +
                                "The elevated heel promotes an upright torso position for squats and " +
                                "Olympic lifts. Wide hook-and-loop strap locks the midfoot in place. " +
                                "Non-slip rubber outsole ensures stability on the platform.")
                .build();
    }
}
