'use client';

import { useState, useEffect } from 'react';
import Image from 'next/image';
import { ChevronLeft, ChevronRight, Truck, ShieldCheck, RefreshCw } from 'lucide-react';
import { Header } from '@/components/header';
import { Footer } from '@/components/footer';
import { ProductCard } from '@/components/product-card';
import { ProductCardSkeleton } from '@/components/product-card-skeleton';
import { BrandCard } from '@/components/brand-card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';

// Mock data for products
const products = [
  {
    id: '1',
    name: 'Nike Air Max 270',
    category: 'Performance Running',
    price: 150.00,
    image: '/placeholder-sneaker-1.svg',
    badge: 'BESTSELLER',
  },
  {
    id: '2',
    name: 'Adidas Ultraboost 22',
    category: 'Everyday Comfort',
    price: 190.00,
    image: '/placeholder-sneaker-2.svg',
    badge: 'NEW',
  },
  {
    id: '3',
    name: 'NB Classic 574',
    category: 'Heritage Lifestyle',
    price: 89.00,
    image: '/placeholder-sneaker-3.svg',
  },
  {
    id: '4',
    name: 'Nike Dunk Low Retro',
    category: 'Court Style',
    price: 110.00,
    image: '/placeholder-sneaker-4.svg',
  },
];

// Brand icons as SVG components
const NikeLogo = () => (
  <svg className="w-8 h-8" viewBox="0 0 24 24" fill="currentColor">
    <path d="M24 7.8L6.442 15.276c-1.456.616-2.679.925-3.668.925-1.12 0-1.933-.392-2.437-1.177-.317-.504-.41-1.143-.28-1.918.13-.775.476-1.6 1.036-2.478.467-.71 1.232-1.643 2.297-2.8a6.122 6.122 0 00-.784 1.848c-.28 1.008-.084 1.792.588 2.352.672.56 1.68.672 3.024.336L24 7.8z" />
  </svg>
);

const AdidasLogo = () => (
  <svg className="w-8 h-8" viewBox="0 0 24 24" fill="currentColor">
    <path d="M8.5 17.734l-5.476 3.27L1.5 17.734 6.976 14.5zM15.5 13.734l-5.476 3.27L8.5 13.734 13.976 10.5zM22.5 9.734l-5.476 3.27L15.5 9.734 20.976 6.5z" />
  </svg>
);

const NewBalanceLogo = () => (
  <svg className="w-8 h-8" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 2L2 7v10l10 5 10-5V7L12 2zm0 2.18L19.82 8 12 11.82 4.18 8 12 4.18zM4 9.48l7 3.5v7.84l-7-3.5V9.48zm16 0v7.84l-7 3.5v-7.84l7-3.5z" />
  </svg>
);

const PumaLogo = () => (
  <svg className="w-8 h-8" viewBox="0 0 24 24" fill="currentColor">
    <path d="M19.5 12c0-4.14-3.36-7.5-7.5-7.5S4.5 7.86 4.5 12s3.36 7.5 7.5 7.5 7.5-3.36 7.5-7.5zm-7.5 5.5c-3.03 0-5.5-2.47-5.5-5.5S8.97 6.5 12 6.5s5.5 2.47 5.5 5.5-2.47 5.5-5.5 5.5z" />
  </svg>
);

export default function HomePage() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Simulate loading
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 1500);

    return () => clearTimeout(timer);
  }, []);

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-1">
        {/* Hero Section */}
        <section className="relative bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 dark:from-black dark:via-gray-950 dark:to-black overflow-hidden">
          <div className="container mx-auto px-4 sm:px-6 lg:px-8 py-16 lg:py-24">
            <div className="grid lg:grid-cols-2 gap-12 items-center">
              <div className="space-y-6 z-10">
                <Badge className="bg-blue-600 dark:bg-lime-500 text-white dark:text-black uppercase tracking-wider">
                  Summer Collection 2024
                </Badge>
                <h1 className="text-5xl lg:text-7xl font-bold text-white leading-tight">
                  Step Into the Future:<br />
                  <span className="bg-gradient-to-r from-blue-400 to-purple-400 dark:from-lime-400 dark:to-green-400 bg-clip-text text-transparent">
                    New Arrivals
                  </span>
                </h1>
                <p className="text-gray-300 text-lg max-w-md">
                  Experience the ultimate blend of style and high-performance engineering with our latest curated drop.
                </p>
                <div className="flex gap-4">
                  <Button size="lg" className="bg-blue-600 hover:bg-blue-700 dark:bg-lime-500 dark:hover:bg-lime-600 dark:text-black text-white">
                    Shop Now
                  </Button>
                  <Button size="lg" variant="outline" className="border-white text-white hover:bg-white/10">
                    View Lookbook
                  </Button>
                </div>
              </div>

              {/* Hero Image */}
              <div className="relative h-[400px] lg:h-[500px]">
                <div className="absolute inset-0 bg-gradient-to-tr from-blue-600/20 to-purple-600/20 dark:from-lime-500/20 dark:to-green-500/20 rounded-3xl blur-3xl" />
                <div className="relative w-full h-full flex items-center justify-center">
                  {/* Placeholder for sneaker image */}
                  <div className="relative w-[90%] h-[90%] transform -rotate-12 hover:rotate-0 transition-transform duration-500">
                    <div className="w-full h-full bg-gradient-to-br from-gray-700 via-gray-600 to-gray-700 rounded-[100px] shadow-2xl flex items-center justify-center">
                      <span className="text-white/20 text-4xl font-bold">SNEAKER</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Decorative elements */}
          <div className="absolute top-1/4 left-1/4 w-64 h-64 bg-blue-600/10 dark:bg-lime-500/10 rounded-full blur-3xl" />
          <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-purple-600/10 dark:bg-green-500/10 rounded-full blur-3xl" />
        </section>

        {/* Featured Brands */}
        <section className="py-16 bg-background">
          <div className="container mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex justify-between items-center mb-8">
              <div>
                <p className="text-sm text-muted-foreground uppercase tracking-wider mb-2">Our Partners</p>
                <h2 className="text-3xl font-bold">Featured Brands</h2>
              </div>
              <Button variant="ghost" className="text-blue-600 dark:text-lime-500">
                View All
              </Button>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
              <BrandCard name="Nike" productCount="150+ Products" icon={<NikeLogo />} />
              <BrandCard name="Adidas" productCount="120+ Products" icon={<AdidasLogo />} />
              <BrandCard name="New Balance" productCount="85+ Products" icon={<NewBalanceLogo />} />
              <BrandCard name="Puma" productCount="95+ Products" icon={<PumaLogo />} />
            </div>
          </div>
        </section>

        {/* Trending Now */}
        <section className="py-16 bg-muted/30">
          <div className="container mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex justify-between items-center mb-8">
              <div>
                <p className="text-sm text-muted-foreground uppercase tracking-wider mb-2">Hottest picks for this week</p>
                <h2 className="text-3xl font-bold">Trending Now</h2>
              </div>
              <div className="flex gap-2">
                <Button variant="outline" size="icon" className="rounded-full">
                  <ChevronLeft className="h-5 w-5" />
                </Button>
                <Button variant="outline" size="icon" className="rounded-full">
                  <ChevronRight className="h-5 w-5" />
                </Button>
              </div>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {isLoading ? (
                <>
                  <ProductCardSkeleton />
                  <ProductCardSkeleton />
                  <ProductCardSkeleton />
                  <ProductCardSkeleton />
                </>
              ) : (
                products.map((product) => (
                  <ProductCard key={product.id} {...product} />
                ))
              )}
            </div>
          </div>
        </section>

        {/* Features */}
        <section className="py-16 bg-background">
          <div className="container mx-auto px-4 sm:px-6 lg:px-8">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
              <div className="text-center space-y-4">
                <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-blue-600/10 dark:bg-lime-500/10">
                  <Truck className="h-8 w-8 text-blue-600 dark:text-lime-500" />
                </div>
                <h3 className="text-xl font-bold">Free Fast Shipping</h3>
                <p className="text-muted-foreground">
                  Enjoy free shipping on all orders over $150. Fast delivery to your doorstep.
                </p>
              </div>

              <div className="text-center space-y-4">
                <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-blue-600/10 dark:bg-lime-500/10">
                  <ShieldCheck className="h-8 w-8 text-blue-600 dark:text-lime-500" />
                </div>
                <h3 className="text-xl font-bold">100% Authentic</h3>
                <p className="text-muted-foreground">
                  We guarantee the authenticity of every pair of shoes we sell, directly from brands.
                </p>
              </div>

              <div className="text-center space-y-4">
                <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-blue-600/10 dark:bg-lime-500/10">
                  <RefreshCw className="h-8 w-8 text-blue-600 dark:text-lime-500" />
                </div>
                <h3 className="text-xl font-bold">Easy 30-Day Returns</h3>
                <p className="text-muted-foreground">
                  Not the right fit? No problem. Return your shoes within 30 days for a full refund.
                </p>
              </div>
            </div>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
}
