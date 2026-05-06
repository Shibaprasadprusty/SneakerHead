'use client';

import Link from 'next/link';
import { Instagram, Twitter, Facebook } from 'lucide-react';
import { Button } from './ui/button';

export function Footer() {
    return (
        <footer className="border-t bg-muted/30 mt-20">
            <div className="container mx-auto px-4 sm:px-6 lg:px-8 py-12">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-8">
                    {/* Brand */}
                    <div>
                        <div className="flex items-center gap-2 mb-4">
                            <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-blue-600 to-blue-400 dark:from-lime-500 dark:to-lime-400 flex items-center justify-center">
                                <span className="text-white dark:text-black font-bold text-lg">S</span>
                            </div>
                            <span className="text-xl font-bold">Sneakerhead</span>
                        </div>
                        <p className="text-sm text-muted-foreground mb-4">
                            Crafting the future of footwear since 2024. Quality, style, and performance in every step.
                        </p>
                        <div className="flex gap-2">
                            <Button size="icon" variant="ghost" className="rounded-full">
                                <Instagram className="h-4 w-4" />
                            </Button>
                            <Button size="icon" variant="ghost" className="rounded-full">
                                <Twitter className="h-4 w-4" />
                            </Button>
                            <Button size="icon" variant="ghost" className="rounded-full">
                                <Facebook className="h-4 w-4" />
                            </Button>
                        </div>
                    </div>

                    {/* Shop Selection */}
                    <div>
                        <h3 className="font-bold mb-4 uppercase text-sm tracking-wider">Shop Selection</h3>
                        <ul className="space-y-2 text-sm">
                            <li><Link href="/new-arrivals" className="text-muted-foreground hover:text-foreground transition-colors">New Arrivals</Link></li>
                            <li><Link href="/best-sellers" className="text-muted-foreground hover:text-foreground transition-colors">Best Sellers</Link></li>
                            <li><Link href="/limited-editions" className="text-muted-foreground hover:text-foreground transition-colors">Limited Editions</Link></li>
                            <li><Link href="/collaborations" className="text-muted-foreground hover:text-foreground transition-colors">Collaborations</Link></li>
                            <li><Link href="/outlet" className="text-muted-foreground hover:text-foreground transition-colors">Outlet</Link></li>
                        </ul>
                    </div>

                    {/* Client Service */}
                    <div>
                        <h3 className="font-bold mb-4 uppercase text-sm tracking-wider">Client Service</h3>
                        <ul className="space-y-2 text-sm">
                            <li><Link href="/order-status" className="text-muted-foreground hover:text-foreground transition-colors">Order Status</Link></li>
                            <li><Link href="/returns" className="text-muted-foreground hover:text-foreground transition-colors">Returns & Exchanges</Link></li>
                            <li><Link href="/size-guide" className="text-muted-foreground hover:text-foreground transition-colors">Size Guide</Link></li>
                            <li><Link href="/contact" className="text-muted-foreground hover:text-foreground transition-colors">Contact</Link></li>
                        </ul>
                    </div>

                    {/* Newsletter */}
                    <div>
                        <h3 className="font-bold mb-4 uppercase text-sm tracking-wider">Newsletter</h3>
                        <p className="text-sm text-muted-foreground mb-4">
                            Get the latest drops, deals, new releases and more.
                        </p>
                        <div className="flex gap-2">
                            <input
                                type="email"
                                placeholder="Your email"
                                className="flex-1 h-10 px-3 rounded-lg border bg-background text-sm focus:outline-none focus:ring-2 focus:ring-ring"
                            />
                            <Button className="bg-gradient-to-r from-blue-600 to-blue-500 dark:from-lime-500 dark:to-lime-400 dark:text-black">
                                Join
                            </Button>
                        </div>
                    </div>
                </div>

                {/* Bottom */}
                <div className="pt-8 border-t flex flex-col md:flex-row justify-between items-center gap-4">
                    <p className="text-sm text-muted-foreground">
                        © 2024 Sneakerhead Inc. All rights reserved.
                    </p>
                    <div className="flex gap-6 text-sm">
                        <Link href="/privacy" className="text-muted-foreground hover:text-foreground transition-colors">
                            Privacy
                        </Link>
                        <Link href="/terms" className="text-muted-foreground hover:text-foreground transition-colors">
                            Terms
                        </Link>
                        <Link href="/cookies" className="text-muted-foreground hover:text-foreground transition-colors">
                            Cookies
                        </Link>
                    </div>
                </div>
            </div>
        </footer>
    );
}
