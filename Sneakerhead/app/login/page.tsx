'use client';

import { useState } from 'react';
import { Eye, EyeOff, Mail, Lock, ArrowRight } from 'lucide-react';
import Image from 'next/image';

export default function LoginPage() {
    const [showPassword, setShowPassword] = useState(false);
    const [isLogin, setIsLogin] = useState(true);
    const [staySignedIn, setStaySignedIn] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    return (
        <div className="min-h-screen flex">
            {/* Left Section - Hero */}
            <div className="hidden lg:flex lg:w-1/2 bg-gradient-to-br from-black via-gray-900 to-black relative overflow-hidden">
                {/* Logo */}
                <div className="absolute top-8 left-8 z-10 flex items-center gap-3">
                    <div className="w-10 h-10 rounded-full bg-gradient-to-br from-purple-600 to-purple-400 flex items-center justify-center">
                        <svg className="w-6 h-6 text-white" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M13 10V3L4 14h7v7l9-11h-7z" />
                        </svg>
                    </div>
                    <span className="text-white text-2xl font-bold">Sneakerhead</span>
                </div>

                {/* Content */}
                <div className="relative z-10 flex flex-col justify-between p-12 w-full">
                    <div className="mt-32">
                        <div className="inline-block mb-6">
                            <span className="text-purple-500 text-xs font-bold tracking-wider uppercase px-3 py-1 border border-purple-500 rounded-full">
                                Limited Edition
                            </span>
                        </div>
                        <h1 className="text-6xl font-bold text-white mb-6 leading-tight">
                            STEP INTO THE<br />FUTURE
                        </h1>
                        <p className="text-gray-400 text-lg max-w-md leading-relaxed">
                            Experience the next generation of comfort and style. Join the most exclusive sneaker community in the world.
                        </p>
                    </div>

                    {/* Stats */}
                    <div className="flex gap-12 mt-auto">
                        <div>
                            <div className="text-4xl font-bold text-white">50k+</div>
                            <div className="text-gray-400 text-sm">Collectors</div>
                        </div>
                        <div>
                            <div className="text-4xl font-bold text-white">120+</div>
                            <div className="text-gray-400 text-sm">Top Brands</div>
                        </div>
                    </div>
                </div>

                {/* Sneaker Image */}
                <div className="absolute right-0 bottom-0 w-[600px] h-[600px] opacity-90">
                    <div className="relative w-full h-full">
                        {/* Using a placeholder div with gradient for the sneaker */}
                        <div className="absolute inset-0 bg-gradient-to-tr from-gray-800/50 to-transparent" />
                        <div className="absolute bottom-0 right-0 w-full h-full">
                            {/* Sneaker silhouette effect */}
                            <div className="absolute bottom-12 right-12 w-[500px] h-[300px] bg-gradient-to-br from-gray-700 via-gray-800 to-gray-900 rounded-[100px] transform -rotate-12 shadow-2xl">
                                <div className="absolute bottom-4 right-8 w-32 h-8 bg-orange-600 rounded-full blur-sm opacity-60" />
                            </div>
                        </div>
                    </div>
                </div>

                {/* Decorative gradient orbs */}
                <div className="absolute top-1/4 left-1/4 w-64 h-64 bg-purple-600/20 rounded-full blur-3xl" />
                <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-blue-600/10 rounded-full blur-3xl" />
            </div>

            {/* Right Section - Form */}
            <div className="w-full lg:w-1/2 bg-gradient-to-br from-gray-950 via-gray-900 to-gray-950 flex items-center justify-center p-8">
                <div className="w-full max-w-md">
                    {/* Welcome Text */}
                    <div className="text-center mb-8">
                        <h2 className="text-4xl font-bold text-white mb-2">Welcome Back</h2>
                        <p className="text-gray-400">Please enter your details to access your vault.</p>
                    </div>

                    {/* Tab Switcher */}
                    <div className="flex gap-4 mb-8 bg-gray-900/50 p-1.5 rounded-full">
                        <button
                            onClick={() => setIsLogin(true)}
                            className={`flex-1 py-3 px-6 rounded-full font-medium transition-all duration-300 ${isLogin
                                    ? 'bg-gray-800 text-white shadow-lg'
                                    : 'text-gray-400 hover:text-white'
                                }`}
                        >
                            Login
                        </button>
                        <button
                            onClick={() => setIsLogin(false)}
                            className={`flex-1 py-3 px-6 rounded-full font-medium transition-all duration-300 ${!isLogin
                                    ? 'bg-gray-800 text-white shadow-lg'
                                    : 'text-gray-400 hover:text-white'
                                }`}
                        >
                            Sign Up
                        </button>
                    </div>

                    {/* Form */}
                    <form className="space-y-5">
                        {/* Email Input */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-2">
                                Email Address
                            </label>
                            <div className="relative">
                                <Mail className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-500" />
                                <input
                                    type="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder="name@domain.com"
                                    className="w-full bg-gray-900/50 border border-gray-800 rounded-xl py-4 pl-12 pr-4 text-white placeholder-gray-500 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 transition-all duration-300"
                                />
                            </div>
                        </div>

                        {/* Password Input */}
                        <div>
                            <div className="flex justify-between items-center mb-2">
                                <label className="text-gray-300 text-sm font-medium">Password</label>
                                <button
                                    type="button"
                                    className="text-purple-500 text-sm font-medium hover:text-purple-400 transition-colors"
                                >
                                    Forgot password?
                                </button>
                            </div>
                            <div className="relative">
                                <Lock className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-500" />
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    placeholder="••••••••"
                                    className="w-full bg-gray-900/50 border border-gray-800 rounded-xl py-4 pl-12 pr-12 text-white placeholder-gray-500 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 transition-all duration-300"
                                />
                                <button
                                    type="button"
                                    onClick={() => setShowPassword(!showPassword)}
                                    className="absolute right-4 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-300 transition-colors"
                                >
                                    {showPassword ? (
                                        <EyeOff className="w-5 h-5" />
                                    ) : (
                                        <Eye className="w-5 h-5" />
                                    )}
                                </button>
                            </div>
                        </div>

                        {/* Stay Signed In */}
                        <div className="flex items-center">
                            <input
                                type="checkbox"
                                id="staySignedIn"
                                checked={staySignedIn}
                                onChange={(e) => setStaySignedIn(e.target.checked)}
                                className="w-4 h-4 rounded border-gray-700 bg-gray-900 text-purple-600 focus:ring-purple-500 focus:ring-offset-gray-900"
                            />
                            <label htmlFor="staySignedIn" className="ml-3 text-gray-400 text-sm">
                                Stay signed in for 30 days
                            </label>
                        </div>

                        {/* Submit Button */}
                        <button
                            type="submit"
                            className="w-full bg-gradient-to-r from-purple-600 to-purple-500 hover:from-purple-500 hover:to-purple-400 text-white font-semibold py-4 rounded-xl transition-all duration-300 transform hover:scale-[1.02] hover:shadow-lg hover:shadow-purple-500/50 flex items-center justify-center gap-2 group"
                        >
                            Continue to Catalog
                            <ArrowRight className="w-5 h-5 group-hover:translate-x-1 transition-transform" />
                        </button>
                    </form>

                    {/* Divider */}
                    <div className="relative my-8">
                        <div className="absolute inset-0 flex items-center">
                            <div className="w-full border-t border-gray-800"></div>
                        </div>
                        <div className="relative flex justify-center text-sm">
                            <span className="px-4 bg-gray-950 text-gray-500 uppercase tracking-wider text-xs">
                                Or continue with
                            </span>
                        </div>
                    </div>

                    {/* Social Login Buttons */}
                    <div className="grid grid-cols-2 gap-4">
                        <button className="flex items-center justify-center gap-3 bg-gray-900/50 border border-gray-800 hover:border-gray-700 text-white py-3.5 rounded-xl transition-all duration-300 hover:bg-gray-900 group">
                            <svg className="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4" />
                                <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853" />
                                <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05" />
                                <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335" />
                            </svg>
                            <span className="font-medium">Google</span>
                        </button>
                        <button className="flex items-center justify-center gap-3 bg-gray-900/50 border border-gray-800 hover:border-gray-700 text-white py-3.5 rounded-xl transition-all duration-300 hover:bg-gray-900 group">
                            <svg className="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M17.05 20.28c-.98.95-2.05.8-3.08.35-1.09-.46-2.09-.48-3.24 0-1.44.62-2.2.44-3.06-.35C2.79 15.25 3.51 7.59 9.05 7.31c1.35.07 2.29.74 3.08.8 1.18-.24 2.31-.93 3.57-.84 1.51.12 2.65.72 3.4 1.8-3.12 1.87-2.38 5.98.48 7.13-.57 1.5-1.31 2.99-2.54 4.09l.01-.01zM12.03 7.25c-.15-2.23 1.66-4.07 3.74-4.25.29 2.58-2.34 4.5-3.74 4.25z" />
                            </svg>
                            <span className="font-medium">Apple ID</span>
                        </button>
                    </div>

                    {/* Terms */}
                    <p className="text-center text-gray-500 text-xs mt-8">
                        By continuing, you agree to our{' '}
                        <a href="#" className="text-gray-400 hover:text-purple-500 underline transition-colors">
                            Terms of Service
                        </a>{' '}
                        and{' '}
                        <a href="#" className="text-gray-400 hover:text-purple-500 underline transition-colors">
                            Privacy Policy
                        </a>
                        .
                    </p>
                </div>
            </div>
        </div>
    );
}
