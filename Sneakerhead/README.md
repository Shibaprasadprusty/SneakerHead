# Sneakerhead - Premium Sneaker Marketplace

A modern, responsive e-commerce platform for sneaker enthusiasts built with Next.js 14, TypeScript, and Tailwind CSS.

## ✨ Features

### 🎨 Design
- **Light & Dark Theme** - Seamless theme switching with persistent preferences
- **Responsive Layout** - Mobile-first design that works on all devices
- **Premium UI** - Modern glassmorphism, gradients, and smooth animations
- **Skeleton Loading** - Elegant loading states for better UX

### 🏠 Home Page Components
- **Hero Section** - Eye-catching banner with gradient backgrounds and call-to-action
- **Featured Brands** - Showcase of partner brands (Nike, Adidas, New Balance, Puma)
- **Trending Products** - Product grid with hover effects and wishlist functionality
- **Features Section** - Highlight shipping, authenticity, and return policies
- **Responsive Header** - Navigation with search, cart, and user actions
- **Comprehensive Footer** - Links, newsletter signup, and social media

### 🔐 Authentication
- **Login/Signup Page** - Beautiful split-screen design with form validation
- **Social Login** - Google and Apple ID integration ready
- **Password Toggle** - Show/hide password functionality
- **Remember Me** - Stay signed in option

### 🛠️ Technical Stack
- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS 4
- **UI Components**: shadcn/ui
- **Icons**: Lucide React
- **Fonts**: Geist Sans & Geist Mono

## 🚀 Getting Started

### Prerequisites
- Node.js 18+ 
- npm or yarn

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd Sneakerhead/frontend
```

2. Install dependencies
```bash
npm install
```

3. Run the development server
```bash
npm run dev
```

4. Open [http://localhost:3000](http://localhost:3000) in your browser

## 📁 Project Structure

```
frontend/
├── app/
│   ├── login/          # Login page
│   ├── page.tsx        # Home page
│   ├── layout.tsx      # Root layout with theme provider
│   └── globals.css     # Global styles and theme variables
├── components/
│   ├── ui/             # shadcn/ui components
│   │   ├── button.tsx
│   │   ├── card.tsx
│   │   ├── badge.tsx
│   │   └── skeleton.tsx
│   ├── header.tsx      # Site header
│   ├── footer.tsx      # Site footer
│   ├── product-card.tsx
│   ├── brand-card.tsx
│   ├── theme-provider.tsx
│   └── theme-toggle.tsx
└── public/             # Static assets
```

## 🎯 Pages

### Home Page (`/`)
- Hero section with featured sneaker
- Featured brands grid
- Trending products with loading states
- Feature highlights (shipping, authenticity, returns)

### Login Page (`/login`)
- Split-screen design
- Email/password authentication
- Social login options
- Form validation ready

## 🎨 Theme System

The app supports both light and dark themes:

- **Light Theme**: Clean, modern with blue accents
- **Dark Theme**: Sleek black with lime green accents
- **System Theme**: Follows OS preference

Toggle between themes using the sun/moon icon in the header.

## 🧩 Components

### UI Components (shadcn/ui)
- `Button` - Multiple variants and sizes
- `Card` - Container for content
- `Badge` - Labels and tags
- `Skeleton` - Loading placeholders

### Custom Components
- `ProductCard` - Product display with image, price, and hover effects
- `BrandCard` - Brand showcase with logo and product count
- `Header` - Navigation with search and cart
- `Footer` - Site footer with links and newsletter
- `ThemeToggle` - Light/dark mode switcher

## 🎭 Features in Detail

### Product Cards
- Hover animations and scale effects
- Wishlist heart button
- Badge support (NEW, BESTSELLER, etc.)
- Responsive image loading

### Skeleton Loading
- Smooth loading animations
- Matches actual content layout
- 1.5s simulated load time

### Theme Switching
- Instant theme changes
- localStorage persistence
- Smooth transitions
- System preference detection

## 🔜 Future Enhancements

- [ ] Product detail pages
- [ ] Shopping cart functionality
- [ ] User authentication backend
- [ ] Payment integration
- [ ] Order management
- [ ] User profile and wishlist
- [ ] Product filtering and search
- [ ] Reviews and ratings

## 📝 License

This project is licensed under the MIT License.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
