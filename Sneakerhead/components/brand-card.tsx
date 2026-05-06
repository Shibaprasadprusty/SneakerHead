'use client';

import { ArrowRight } from 'lucide-react';
import { Card } from './ui/card';

interface BrandCardProps {
    name: string;
    productCount: string;
    icon: React.ReactNode;
}

export function BrandCard({ name, productCount, icon }: BrandCardProps) {
    return (
        <Card className="p-6 cursor-pointer transition-all duration-300 hover:shadow-lg hover:scale-[1.02] group">
            <div className="flex items-center justify-between">
                <div className="flex items-center gap-4">
                    <div className="w-12 h-12 rounded-full bg-foreground dark:bg-background flex items-center justify-center">
                        {icon}
                    </div>
                    <div>
                        <h3 className="font-bold text-lg">{name}</h3>
                        <p className="text-sm text-muted-foreground">{productCount}</p>
                    </div>
                </div>
                <ArrowRight className="h-5 w-5 text-muted-foreground group-hover:text-foreground group-hover:translate-x-1 transition-all" />
            </div>
        </Card>
    );
}
