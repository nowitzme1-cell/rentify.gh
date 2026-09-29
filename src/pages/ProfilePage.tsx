import React from 'react';
import {
  ArrowLeft,
  Check,
  Shield,
  Layers,
  MessageSquare,
  DollarSign,
  Settings,
  HelpCircle,
  ArrowLeftRight,
  ChevronRight,
  ShieldAlert
} from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const ProfilePage: React.FC = () => {
  const { currentUser, navigateTo, switchUser } = useRentify();

  const formattedEarned = (currentUser.totalEarned / 1000).toFixed(0);

  return (
    <div className="max-w-xl mx-auto px-4 pt-4 pb-28 min-h-screen">
      {/* Top Header */}
      <div className="flex items-center justify-between">
        <button
          onClick={() => navigateTo('home')}
          className="w-10 h-10 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#0F0F0F] hover:bg-[#E4E4E7] transition"
        >
          <ArrowLeft className="w-5 h-5" />
        </button>

        <h1 className="text-lg font-bold tracking-tight text-[#0F0F0F]">
          Profile
        </h1>

        {/* Switch Persona Pill */}
        <button
          onClick={() => {
            const nextId = currentUser.id === 'user_salman' ? 'user_bilal' : 'user_salman';
            switchUser(nextId);
          }}
          className="px-3 py-1.5 rounded-full bg-[#F4F4F5] hover:bg-[#E4E4E7] text-xs font-semibold text-[#0F0F0F] flex items-center gap-1 transition"
        >
          <ArrowLeftRight className="w-3.5 h-3.5 text-[#635BFF]" />
          <span>Switch Account</span>
        </button>
      </div>

      {/* Header Profile Info */}
      <div className="mt-6 flex flex-col items-center text-center">
        <div className="relative">
          <img
            src={currentUser.avatarUrl}
            alt={currentUser.name}
            className="w-20 h-20 rounded-full object-cover border-2 border-white shadow-md"
          />
          {currentUser.isVerified && (
            <div className="absolute bottom-0 right-0 w-6 h-6 rounded-full bg-[#0095F6] border-2 border-white flex items-center justify-center">
              <Check className="w-3.5 h-3.5 text-white stroke-[3]" />
            </div>
          )}
        </div>

        <h2 className="mt-3 text-xl font-bold tracking-tight text-[#0F0F0F]">
          {currentUser.name}
        </h2>
        <p className="text-xs text-[#71717A]">
          {currentUser.phone} • 📍 {currentUser.city}
        </p>
      </div>

      {/* Verification Status Card */}
      <div className="mt-6">
        {currentUser.isVerified ? (
          <div className="bg-[#F0FDF4] border border-[#BBF7D0] rounded-2xl p-4 flex items-center gap-3">
            <div className="w-8 h-8 rounded-full bg-[#0095F6] flex items-center justify-center shrink-0">
              <Check className="w-4 h-4 text-white stroke-[3]" />
            </div>
            <div>
              <h4 className="text-xs font-bold text-[#166534]">
                Verified • Blue Tick Active
              </h4>
              <p className="text-[11px] text-[#166534]/80 mt-0.5">
                Government CNIC verified by Rentify admin. 100% trust score in Kashmore.
              </p>
            </div>
          </div>
        ) : (
          <div className="bg-[#FFFBEB] border border-[#FDE68A] rounded-2xl p-4 flex items-center justify-between gap-3">
            <div>
              <h4 className="text-xs font-bold text-[#92400E]">
                Get Verified • Upload CNIC
              </h4>
              <p className="text-[11px] text-[#92400E]/80 mt-0.5">
                Get the blue tick to earn 3x more rental requests.
              </p>
            </div>
            <button
              onClick={() => navigateTo('verify')}
              className="px-4 py-2 rounded-full bg-[#0F0F0F] text-white text-xs font-semibold hover:scale-95 transition shrink-0"
            >
              Verify Now
            </button>
          </div>
        )}
      </div>

      {/* Stats Row (3 cards white border radius 16px) */}
      <div className="mt-5 grid grid-cols-3 gap-3 text-center">
        <div className="bg-white border border-[#F0F0F0] rounded-2xl p-3.5 shadow-soft">
          <div className="text-lg font-bold text-[#0F0F0F]">
            {currentUser.itemsCount}
          </div>
          <div className="text-xs text-[#71717A] mt-0.5">Items Listed</div>
        </div>

        <div className="bg-white border border-[#F0F0F0] rounded-2xl p-3.5 shadow-soft">
          <div className="text-lg font-bold text-[#0F0F0F]">
            Rs.{formattedEarned}k
          </div>
          <div className="text-xs text-[#71717A] mt-0.5">Earned</div>
        </div>

        <div className="bg-white border border-[#F0F0F0] rounded-2xl p-3.5 shadow-soft">
          <div className="text-lg font-bold text-[#0F0F0F]">
            {currentUser.rating} ★
          </div>
          <div className="text-xs text-[#71717A] mt-0.5">({currentUser.ratingCount} Reviews)</div>
        </div>
      </div>

      {/* Menu List Modern */}
      <div className="mt-6 bg-white border border-[#F0F0F0] rounded-2xl overflow-hidden divide-y divide-[#F4F4F5] shadow-soft">
        <div
          onClick={() => navigateTo('home')}
          className="p-4 flex items-center justify-between hover:bg-[#FAFAFA] cursor-pointer transition"
        >
          <div className="flex items-center gap-3">
            <Layers className="w-5 h-5 text-[#71717A]" />
            <span className="text-sm font-medium text-[#0F0F0F]">My Listings ({currentUser.itemsCount})</span>
          </div>
          <ChevronRight className="w-4 h-4 text-[#71717A]" />
        </div>

        <div
          onClick={() => navigateTo('requests')}
          className="p-4 flex items-center justify-between hover:bg-[#FAFAFA] cursor-pointer transition"
        >
          <div className="flex items-center gap-3">
            <MessageSquare className="w-5 h-5 text-[#71717A]" />
            <span className="text-sm font-medium text-[#0F0F0F]">My Requests</span>
          </div>
          <ChevronRight className="w-4 h-4 text-[#71717A]" />
        </div>

        <div
          onClick={() => navigateTo('verify')}
          className="p-4 flex items-center justify-between hover:bg-[#FAFAFA] cursor-pointer transition"
        >
          <div className="flex items-center gap-3">
            <Shield className="w-5 h-5 text-[#635BFF]" />
            <span className="text-sm font-medium text-[#0F0F0F]">CNIC Verification (Blue Tick)</span>
          </div>
          <ChevronRight className="w-4 h-4 text-[#71717A]" />
        </div>

        <div
          onClick={() => navigateTo('admin')}
          className="p-4 flex items-center justify-between hover:bg-[#FAFAFA] cursor-pointer transition"
        >
          <div className="flex items-center gap-3">
            <ShieldAlert className="w-5 h-5 text-[#0F0F0F]" />
            <span className="text-sm font-medium text-[#0F0F0F]">Admin Portal</span>
          </div>
          <ChevronRight className="w-4 h-4 text-[#71717A]" />
        </div>

        <div
          onClick={() => {
            const nextId = currentUser.id === 'user_salman' ? 'user_bilal' : 'user_salman';
            switchUser(nextId);
          }}
          className="p-4 flex items-center justify-between hover:bg-[#FAFAFA] cursor-pointer transition"
        >
          <div className="flex items-center gap-3">
            <ArrowLeftRight className="w-5 h-5 text-[#635BFF]" />
            <span className="text-sm font-medium text-[#635BFF]">
              Switch to {currentUser.id === 'user_salman' ? 'Bilal (Renter)' : 'Salman (Camera Owner)'}
            </span>
          </div>
          <ChevronRight className="w-4 h-4 text-[#71717A]" />
        </div>
      </div>
    </div>
  );
};
