'use client';

import Image from 'next/image';
import { Card } from './ui/card';
import { Badge } from './ui/badge';
import { Heart } from 'lucide-react';
import { Button } from './ui/button';

interface ProductCardProps {
    id: string;
    name: string;
    category: string;
    price: number;
    image: string;
    badge?: string;
}

export function ProductCard({ id, name, category, price, image, badge }: ProductCardProps) {
    return (
        <Card className="overflow-hidden group cursor-pointer transition-all duration-300 hover:shadow-xl hover:scale-[1.02]">
            <div className="aspect-square relative bg-muted overflow-hidden">
                {badge && (
                    <Badge className="absolute top-3 left-3 z-10 bg-blue-600 dark:bg-lime-500 text-white dark:text-black uppercase tracking-wider">
                        {badge}
                    </Badge>
                )}
                <div className="absolute top-3 right-3 z-10 opacity-0 group-hover:opacity-100 transition-opacity">
                    <Button size="icon" variant="secondary" className="rounded-full h-8 w-8">
                        <Heart className="h-4 w-4" />
                    </Button>
                </div>
                <div className="relative w-full h-full flex items-center justify-center p-8 bg-gradient-to-br from-background to-muted">
                    <div className="relative w-full h-full">
                        <Image
                            src={image}
                            alt={name}
                            fill
                            className="object-contain transition-transform duration-500 group-hover:scale-110"
                        />
                    </div>
                </div>
            </div>
            <div className="p-4">
                <h3 className="font-bold text-base mb-1 line-clamp-1">{name}</h3>
                <p className="text-sm text-muted-foreground mb-2">{category}</p>
                <p className="text-lg font-bold">${price.toFixed(2)}</p>
            </div>
        </Card>
    );
}
