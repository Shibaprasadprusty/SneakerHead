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
 * Concrete Factory for {@link Brand#PUMA} shoes.
 *
 * <p>
 * Uses the {@link Shoe.Builder} (Builder Pattern) internally to construct
 * each Puma shoe variant with brand-appropriate defaults.
 */
public class PumaShoeFactory implements AbstractShoeFactory {

    @Override
    public Brand getBrand() {
        return Brand.PUMA;
    }

    /**
     * Creates Puma's signature running shoe — Velocity Nitro 2 (Black/Silver
     * edition).
     */
    @Override
    public Shoe createRunningShoe() {
        return new Shoe.Builder("Puma Velocity Nitro 2", "PM-VN2-BS", Brand.PUMA)
                .price(new BigDecimal("11999.00"))
                .discount(12.0)
                .colors(List.of(Color.BLACK, Color.SILVER))
                .sizes(List.of(6.0, 7.0, 8.0, 9.0, 10.0, 11.0))
                .shoeType(ShoeType.RUNNING)
                .material(Material.MESH)
                .idealFor(IdealFor.MEN)
                .productDetails(
                        "Puma Velocity Nitro 2 features NITRO foam technology for " +
                                "lightweight cushioning and energy return. The engineered mesh " +
                                "upper provides targeted breathability. PUMAGRIP outsole delivers " +
                                "reliable traction on wet and dry surfaces. Perfect for daily training runs.")
                .build();
    }

    /**
     * Creates Puma's signature casual shoe — Suede Classic XXI (Navy/White
     * edition).
     */
    @Override
    public Shoe createCasualShoe() {
        return new Shoe.Builder("Puma Suede Classic XXI", "PM-SC21-NW", Brand.PUMA)
                .price(new BigDecimal("6499.00"))
                .discount(5.0)
                .colors(List.of(Color.NAVY_BLUE, Color.WHITE))
                .sizes(List.of(6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0))
                .shoeType(ShoeType.CASUAL)
                .material(Material.SUEDE)
                .idealFor(IdealFor.UNISEX)
                .productDetails(
                        "The Puma Suede Classic XXI is an icon of street culture. " +
                                "Its premium suede upper and formstrip branding have made it " +
                                "a streetwear staple since 1968. The rubber outsole provides " +
                                "durability and grip for everyday urban adventures.")
                .build();
    }

    /**
     * Creates Puma's signature training shoe — Tazon 7 (Black/Gold edition).
     */
    @Override
    public Shoe createTrainingShoe() {
        return new Shoe.Builder("Puma Tazon 7", "PM-TZ7-BG", Brand.PUMA)
                .price(new BigDecimal("5999.00"))
                .discount(20.0)
                .colors(List.of(Color.BLACK, Color.GOLD))
                .sizes(List.of(7.0, 8.0, 9.0, 10.0, 11.0))
                .shoeType(ShoeType.TRAINING)
                .material(Material.SYNTHETIC_LEATHER)
                .idealFor(IdealFor.MEN)
                .productDetails(
                        "Puma Tazon 7 is a versatile cross-training shoe built for " +
                                "gym workouts and light outdoor activities. The synthetic leather " +
                                "upper is durable and easy to clean. EcoOrthoLite sockliner provides " +
                                "cushioning and moisture management throughout your workout.")
                .build();
    }
}
