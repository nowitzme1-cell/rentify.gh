import React from 'react';
import { Home, Search, Plus, MessageSquare, User as UserIcon } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const BottomNav: React.FC = () => {
  const { currentRoute, navigateTo, requests, currentUser } = useRentify();

  const pendingRequestsCount = requests.filter(
    (r) => (r.ownerId === currentUser.id || r.renterId === currentUser.id) && r.status === 'PENDING'
  ).length;

  return (
    <nav className="fixed bottom-4 left-4 right-4 max-w-sm mx-auto z-40">
      <div className="bg-white/90 backdrop-blur-xl border border-black/5 rounded-full shadow-float h-16 px-3 flex items-center justify-between">
        {/* Tab 1: Home */}
        <button
          onClick={() => navigateTo('home')}
          className={`w-11 h-11 rounded-full flex items-center justify-center transition-all ${
            currentRoute === 'home'
              ? 'bg-[#0F0F0F] text-white shadow-sm'
              : 'text-[#71717A] hover:text-[#0F0F0F]'
          }`}
          title="Home"
        >
          <Home className="w-5 h-5" />
        </button>

        {/* Tab 2: Search */}
        <button
          onClick={() => {
            navigateTo('home');
            setTimeout(() => {
              const el = document.getElementById('search-input');
              el?.focus();
            }, 50);
          }}
          className={`w-11 h-11 rounded-full flex items-center justify-center transition-all ${
            currentRoute === 'search'
              ? 'bg-[#0F0F0F] text-white'
              : 'text-[#71717A] hover:text-[#0F0F0F]'
          }`}
          title="Search"
        >
          <Search className="w-5 h-5" />
        </button>

        {/* Tab 3: Post Center Big Button */}
        <button
          onClick={() => navigateTo('post')}
          className="relative -top-3 w-14 h-14 rounded-full bg-gradient-to-b from-[#0F0F0F] to-[#27272A] border-4 border-white shadow-lg flex items-center justify-center text-white hover:scale-105 active:scale-95 transition-transform"
          title="List an item for rent"
        >
          <Plus className="w-6 h-6 stroke-[2.5]" />
        </button>

        {/* Tab 4: Requests */}
        <button
          onClick={() => navigateTo('requests')}
          className={`relative w-11 h-11 rounded-full flex items-center justify-center transition-all ${
            currentRoute === 'requests'
              ? 'bg-[#0F0F0F] text-white'
              : 'text-[#71717A] hover:text-[#0F0F0F]'
          }`}
          title="Requests"
        >
          <MessageSquare className="w-5 h-5" />
          {pendingRequestsCount > 0 && (
            <span className="absolute top-1 right-1 w-2.5 h-2.5 rounded-full bg-[#635BFF] border-2 border-white" />
          )}
        </button>

        {/* Tab 5: Profile */}
        <button
          onClick={() => navigateTo('profile')}
          className={`w-11 h-11 rounded-full flex items-center justify-center transition-all ${
            currentRoute === 'profile' || currentRoute === 'verify'
              ? 'bg-[#0F0F0F] text-white'
              : 'text-[#71717A] hover:text-[#0F0F0F]'
          }`}
          title="Profile"
        >
          <UserIcon className="w-5 h-5" />
        </button>
      </div>
    </nav>
  );
};
