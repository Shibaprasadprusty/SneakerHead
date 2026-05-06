'use client';

import { Card } from './ui/card';
import { Skeleton } from './ui/skeleton';

export function ProductCardSkeleton() {
    return (
        <Card className="overflow-hidden group">
            <div className="aspect-square relative bg-muted">
                <Skeleton className="w-full h-full" />
            </div>
            <div className="p-4 space-y-3">
                <Skeleton className="h-4 w-3/4" />
                <Skeleton className="h-3 w-1/2" />
                <Skeleton className="h-5 w-1/4" />
            </div>
        </Card>
    );
}
