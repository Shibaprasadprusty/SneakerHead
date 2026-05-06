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
 * Concrete Factory for {@link Brand#NIKE} shoes.
 *
 * <p>
 * Uses the {@link Shoe.Builder} (Builder Pattern) internally to construct
 * each Nike shoe variant with brand-appropriate defaults.
 */
public class NikeShoeFactory implements AbstractShoeFactory {

    @Override
    public Brand getBrand() {
        return Brand.NIKE;
    }

    /**
     * Creates Nike's signature running shoe — Air Max 270.
     * Colors vary per colorway; this factory produces the classic White/Black
     * edition.
     */
    @Override
    public Shoe createRunningShoe() {
        return new Shoe.Builder("Nike Air Max 270", "NK-AM270-WB", Brand.NIKE)
                .price(new BigDecimal("14999.00"))
                .discount(10.0)
                .colors(List.of(Color.WHITE, Color.BLACK))
                .sizes(List.of(6.0, 7.0, 7.5, 8.0, 8.5, 9.0, 10.0, 11.0))
                .shoeType(ShoeType.RUNNING)
                .material(Material.MESH)
                .idealFor(IdealFor.MEN)
                .productDetails(
                        "The Nike Air Max 270 features Nike's biggest heel Air unit yet " +
                                "for an all-day, comfortable ride. The lightweight mesh upper provides " +
                                "breathability, while the foam midsole offers cushioning and support. " +
                                "Ideal for long-distance running and everyday wear.")
                .build();
    }

    /**
     * Creates Nike's signature casual shoe — Air Force 1 (White edition).
     */
    @Override
    public Shoe createCasualShoe() {
        return new Shoe.Builder("Nike Air Force 1 '07", "NK-AF1-W", Brand.NIKE)
                .price(new BigDecimal("8999.00"))
                .discount(5.0)
                .colors(List.of(Color.WHITE))
                .sizes(List.of(6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0))
                .shoeType(ShoeType.CASUAL)
                .material(Material.LEATHER)
                .idealFor(IdealFor.UNISEX)
                .productDetails(
                        "The radically reshaped Nike Air Force 1 '07 brings you the same " +
                                "classic look in a modern design. The leather upper is durable and " +
                                "easy to clean. The Air-Sole unit cushions every step, making it " +
                                "perfect for streetwear and everyday casual use.")
                .build();
    }

    /**
     * Creates Nike's signature training shoe — Metcon 9.
     */
    @Override
    public Shoe createTrainingShoe() {
        return new Shoe.Builder("Nike Metcon 9", "NK-MC9-BG", Brand.NIKE)
                .price(new BigDecimal("12499.00"))
                .discount(0.0)
                .colors(List.of(Color.BLACK, Color.GREY))
                .sizes(List.of(7.0, 8.0, 9.0, 10.0, 11.0))
                .shoeType(ShoeType.TRAINING)
                .material(Material.SYNTHETIC_LEATHER)
                .idealFor(IdealFor.MEN)
                .productDetails(
                        "Nike Metcon 9 is built for the toughest workouts. " +
                                "The wide, flat heel provides stability for heavy lifts, " +
                                "while the forefoot flex grooves allow natural movement during " +
                                "cardio and agility drills. Rope-wrap zone protects the upper.")
                .build();
    }
}
