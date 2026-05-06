'use client';

import Link from 'next/link';
import { Search, Heart, ShoppingBag, User } from 'lucide-react';
import { ThemeToggle } from './theme-toggle';
import { Button } from './ui/button';

export function Header() {
    return (
        <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
            <div className="container mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex h-16 items-center justify-between">
                    {/* Logo */}
                    <Link href="/" className="flex items-center gap-2">
                        <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-blue-600 to-blue-400 dark:from-lime-500 dark:to-lime-400 flex items-center justify-center">
                            <span className="text-white dark:text-black font-bold text-lg">S</span>
                        </div>
                        <span className="text-xl font-bold">Sneakerhead</span>
                    </Link>

                    {/* Navigation */}
                    <nav className="hidden md:flex items-center gap-6">
                        <Link href="/new-arrivals" className="text-sm font-medium hover:text-primary transition-colors">
                            New Arrivals
                        </Link>
                        <Link href="/brands" className="text-sm font-medium hover:text-primary transition-colors">
                            Brands
                        </Link>
                        <Link href="/men" className="text-sm font-medium hover:text-primary transition-colors">
                            Men
                        </Link>
                        <Link href="/women" className="text-sm font-medium hover:text-primary transition-colors">
                            Women
                        </Link>
                        <Link href="/kids" className="text-sm font-medium hover:text-primary transition-colors">
                            Kids
                        </Link>
                    </nav>

                    {/* Right Actions */}
                    <div className="flex items-center gap-2">
                        <div className="hidden lg:flex items-center gap-2 mr-2">
                            <div className="relative">
                                <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                                <input
                                    type="search"
                                    placeholder="Search sneakers..."
                                    className="w-64 h-9 pl-9 pr-4 rounded-lg border bg-background text-sm focus:outline-none focus:ring-2 focus:ring-ring"
                                />
                            </div>
                        </div>

                        <Button variant="ghost" size="icon" className="rounded-full">
                            <Search className="h-5 w-5 lg:hidden" />
                        </Button>

                        <Button variant="ghost" size="icon" className="rounded-full">
                            <Heart className="h-5 w-5" />
                        </Button>

                        <Button variant="ghost" size="icon" className="rounded-full relative">
                            <ShoppingBag className="h-5 w-5" />
                            <span className="absolute -top-1 -right-1 h-4 w-4 rounded-full bg-primary text-primary-foreground text-xs flex items-center justify-center">
                                0
                            </span>
                        </Button>

                        <Button variant="ghost" size="icon" className="rounded-full">
                            <User className="h-5 w-5" />
                        </Button>

                        <ThemeToggle />
                    </div>
                </div>
            </div>
        </header>
    );
}
