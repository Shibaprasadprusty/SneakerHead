package com.sneakerhead.backend.models;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.Color;
import com.sneakerhead.backend.models.enums.IdealFor;
import com.sneakerhead.backend.models.enums.Material;
import com.sneakerhead.backend.models.enums.ShoeType;
import com.sneakerhead.backend.models.factory.AbstractShoeFactory;
import com.sneakerhead.backend.models.factory.ShoeFactoryProvider;

import java.math.BigDecimal;
import java.util.List;

/**
 * Demonstration class showing how the Builder Pattern and Abstract Factory
 * Pattern
 * work together in the Shoe model.
 *
 * <p>
 * This is NOT a production class — it is purely for illustration and manual
 * testing.
 * Run the {@code main} method to see the output.
 */
public class ShoeDemo {

    public static void main(String[] args) {

        System.out.println("=".repeat(70));
        System.out.println("  SNEAKERHEAD — Shoe Model Demo");
        System.out.println("  Builder Pattern + Abstract Factory Pattern");
        System.out.println("=".repeat(70));

        // ─────────────────────────────────────────────────────────────────────
        // 1. BUILDER PATTERN — manually build a custom shoe with all fields
        // ─────────────────────────────────────────────────────────────────────

        System.out.println("\n── 1. Builder Pattern: Custom Shoe ──────────────────────────────────");

        Shoe customShoe = new Shoe.Builder("Air Jordan 1 Retro High OG", "AJ1-RHOG-CB", Brand.NIKE)
                .price(new BigDecimal("18999.00"))
                .discount(0.0)
                .colors(List.of(Color.BLACK, Color.RED, Color.WHITE)) // multiple colors!
                .sizes(List.of(7.0, 8.0, 9.0, 10.0, 11.0, 12.0))
                .shoeType(ShoeType.SNEAKER)
                .material(Material.LEATHER)
                .idealFor(IdealFor.UNISEX)
                .productDetails(
                        "The Air Jordan 1 Retro High OG 'Chicago' brings back the " +
                                "iconic colorway that started it all. Full-grain leather upper " +
                                "with Nike Air cushioning. A collector's grail and streetwear essential.")
                .build();

        printShoe(customShoe);

        // ─────────────────────────────────────────────────────────────────────
        // 2. ABSTRACT FACTORY PATTERN — use brand factories to create shoes
        // ─────────────────────────────────────────────────────────────────────

        System.out.println("\n── 2. Abstract Factory Pattern ──────────────────────────────────────");

        Brand[] brandsToDemo = { Brand.NIKE, Brand.ADIDAS, Brand.PUMA };

        for (Brand brand : brandsToDemo) {
            System.out.println("\n  ▶ Factory: " + brand.getDisplayName());

            AbstractShoeFactory factory = ShoeFactoryProvider.getFactory(brand);

            Shoe running = factory.createRunningShoe();
            Shoe casual = factory.createCasualShoe();
            Shoe training = factory.createTrainingShoe();

            printShoe(running);
            printShoe(casual);
            printShoe(training);
        }

        // ─────────────────────────────────────────────────────────────────────
        // 3. isSupported() check
        // ─────────────────────────────────────────────────────────────────────

        System.out.println("\n── 3. Factory Support Check ─────────────────────────────────────────");
        for (Brand b : Brand.values()) {
            System.out.printf("  %-20s → %s%n",
                    b.getDisplayName(),
                    ShoeFactoryProvider.isSupported(b) ? "✅ Supported" : "⚠️  Not yet registered");
        }

        System.out.println("\n" + "=".repeat(70));
    }

    private static void printShoe(Shoe shoe) {
        System.out.println("\n  ┌─ " + shoe.getName() + " [" + shoe.getModel() + "]");
        System.out.println("  │  Brand       : " + shoe.getBrand().getDisplayName());
        System.out.println("  │  Type        : " + shoe.getShoeType().getDisplayName());
        System.out.println("  │  Material    : " + shoe.getMaterial().getDisplayName());
        System.out.println("  │  Ideal For   : " + shoe.getIdealFor().getDisplayName());
        System.out.println("  │  Colors      : " + shoe.getColors().stream()
                .map(c -> c.getDisplayName()).toList());
        System.out.println("  │  Sizes       : " + shoe.getSizes());
        System.out.printf("  │  Price       : ₹%.2f%n", shoe.getPrice());
        System.out.printf("  │  Discount    : %.1f%%%n", shoe.getDiscount());
        System.out.printf("  │  Eff. Price  : ₹%.2f%n", shoe.getEffectivePrice());
        System.out.println("  └─ Details     : " + shoe.getProductDetails().substring(0,
                Math.min(80, shoe.getProductDetails().length())) + "...");
    }
}
