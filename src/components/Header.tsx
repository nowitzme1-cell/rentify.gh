import React, { useState } from 'react';
import { MapPin, Bell, Shield, ArrowLeftRight, ChevronDown } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const Header: React.FC = () => {
  const { currentCity, setCurrentCity, currentUser, switchUser, navigateTo, showToast } = useRentify();
  const [showCityMenu, setShowCityMenu] = useState(false);

  const CITIES = [
    'Kashmore',
    'Usta Muhammad',
    'Kandhkot',
    'Sui',
    'Dera Allah Yar',
    'Jacobabad',
    'Shikarpur',
    'All Pakistan'
  ];

  return (
    <header className="sticky top-0 z-30 bg-white/85 backdrop-blur-md border-b border-[#F4F4F5] transition-all">
      <div className="max-w-6xl mx-auto px-4 h-16 flex items-center justify-between gap-2">
        {/* Left: Brand Logo "rentify." */}
        <div 
          onClick={() => navigateTo('home')}
          className="cursor-pointer select-none flex items-center"
        >
          <span className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
            rentify<span className="text-[#635BFF] font-black text-2xl">.</span>
          </span>
        </div>

        {/* Right Action Buttons */}
        <div className="flex items-center gap-2">
          {/* Switch User Demo Chip */}
          <button
            onClick={() => {
              const nextId = currentUser.id === 'user_salman' ? 'user_bilal' : 'user_salman';
              switchUser(nextId);
            }}
            title="Switch demo persona (Salman Owner / Bilal Renter)"
            className="hidden sm:flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-[#F4F4F5] hover:bg-[#E4E4E7] text-xs font-semibold text-[#0F0F0F] transition"
          >
            <ArrowLeftRight className="w-3.5 h-3.5 text-[#71717A]" />
            <span>{currentUser.name.split(' ')[0]} ({currentUser.id === 'user_salman' ? 'Owner' : 'Renter'})</span>
          </button>

          {/* Location Pill */}
          <div className="relative">
            <button
              onClick={() => setShowCityMenu(!showCityMenu)}
              className="flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-[#F4F4F5] hover:bg-[#E4E4E7] text-xs font-medium text-[#0F0F0F] transition"
            >
              <MapPin className="w-3.5 h-3.5 text-[#635BFF]" />
              <span>{currentCity}</span>
              <ChevronDown className="w-3 h-3 text-[#71717A]" />
            </button>

            {showCityMenu && (
              <div className="absolute right-0 mt-2 w-44 bg-white rounded-2xl shadow-lift border border-[#F0F0F0] py-2 z-50">
                <div className="px-3 py-1 text-[11px] font-semibold text-[#71717A] uppercase tracking-wider">
                  Target Cities
                </div>
                {CITIES.map((city) => (
                  <button
                    key={city}
                    onClick={() => {
                      setCurrentCity(city);
                      setShowCityMenu(false);
                      showToast(`Viewing listings in ${city}`);
                    }}
                    className={`w-full text-left px-3 py-2 text-xs transition flex items-center justify-between ${
                      city === currentCity
                        ? 'font-bold text-[#635BFF] bg-[#F4F4F5]'
                        : 'text-[#0F0F0F] hover:bg-[#FAFAFA]'
                    }`}
                  >
                    <span>{city}</span>
                    {city === currentCity && <span className="w-1.5 h-1.5 rounded-full bg-[#635BFF]" />}
                  </button>
                ))}
              </div>
            )}
          </div>

          {/* Admin Dashboard Button */}
          <button
            onClick={() => navigateTo('admin')}
            title="Open Admin Panel"
            className="w-9 h-9 rounded-full bg-[#F4F4F5] hover:bg-[#E4E4E7] flex items-center justify-center text-[#0F0F0F] transition"
          >
            <Shield className="w-4 h-4" />
          </button>

          {/* Notification Button */}
          <button
            onClick={() => showToast('No new notifications in ' + currentCity)}
            className="w-9 h-9 rounded-full bg-[#F4F4F5] hover:bg-[#E4E4E7] flex items-center justify-center text-[#71717A] transition"
          >
            <Bell className="w-4 h-4" />
          </button>
        </div>
      </div>
    </header>
  );
};
