import React, { useState } from 'react';
import {
  ArrowLeft,
  Check,
  Star,
  MapPin,
  Shield,
  MessageCircle,
  ChevronRight
} from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const ItemDetailPage: React.FC = () => {
  const {
    items,
    activeItemId,
    navigateTo,
    openRentModal,
    switchUser,
    requests,
    currentUser
  } = useRentify();

  const [activeImageIndex, setActiveImageIndex] = useState(0);

  const item = items.find((i) => i.id === activeItemId) || items[0];
  const images = item.images.length > 0 ? item.images : ['https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800'];
  const formattedPrice = item.pricePerDay.toLocaleString();
  const formattedWeekPrice = item.pricePerWeek.toLocaleString();
  const formattedDeposit = item.deposit.toLocaleString();
  const weeklySavings = item.pricePerDay * 7 - item.pricePerWeek;

  const handleChatClick = () => {
    // Check if request already exists between user and this item
    const existing = requests.find(
      (r) => r.itemId === item.id && (r.renterId === currentUser.id || r.ownerId === currentUser.id)
    );
    if (existing) {
      navigateTo('chat', existing.id);
    } else {
      openRentModal(item);
    }
  };

  return (
    <div className="min-h-screen bg-[#FAFAFA] pb-32">
      {/* 1. Image Slider with Back Button */}
      <div className="relative w-full max-w-xl mx-auto h-[340px] sm:h-[380px] bg-[#F4F4F5] sm:rounded-b-[24px] overflow-hidden">
        <img
          src={images[activeImageIndex]}
          alt={item.title}
          className="w-full h-full object-cover"
        />

        {/* Back Button Pill */}
        <button
          onClick={() => navigateTo('home')}
          className="absolute top-4 left-4 w-10 h-10 rounded-full bg-white/90 backdrop-blur-md shadow-md flex items-center justify-center text-[#0F0F0F] hover:bg-white transition"
          title="Back"
        >
          <ArrowLeft className="w-5 h-5" />
        </button>

        {/* Dots indicator bottom */}
        {images.length > 1 && (
          <div className="absolute bottom-4 left-1/2 -translate-x-1/2 bg-white/80 backdrop-blur-md px-3 py-1.5 rounded-full flex items-center gap-1.5 shadow-sm">
            {images.map((_, idx) => (
              <button
                key={idx}
                onClick={() => setActiveImageIndex(idx)}
                className={`transition-all rounded-full ${
                  activeImageIndex === idx
                    ? 'w-2.5 h-2.5 bg-[#0F0F0F]'
                    : 'w-1.5 h-1.5 bg-[#71717A]/60'
                }`}
              />
            ))}
          </div>
        )}
      </div>

      {/* 2. Content Info (Max-width 600px centered) */}
      <div className="max-w-[600px] mx-auto px-5 pt-5 space-y-5">
        {/* Title */}
        <h1 className="text-[22px] font-bold tracking-tight text-[#0F0F0F] leading-tight">
          {item.title}
        </h1>

        {/* Owner Card Modern */}
        <div
          onClick={() => {
            switchUser(item.ownerId);
            navigateTo('profile');
          }}
          className="cursor-pointer bg-white border border-[#F0F0F0] rounded-2xl p-3.5 flex items-center justify-between hover:shadow-soft transition"
        >
          <div className="flex items-center gap-3">
            <img
              src={item.ownerAvatar}
              alt={item.ownerName}
              className="w-11 h-11 rounded-full object-cover border-2 border-white shadow-sm"
            />
            <div>
              <div className="flex items-center gap-1.5">
                <span className="text-sm font-semibold text-[#0F0F0F]">
                  {item.ownerName}
                </span>
                {item.ownerVerified && (
                  <div className="w-3.5 h-3.5 rounded-full bg-[#0095F6] flex items-center justify-center" title="Verified Owner">
                    <Check className="w-2.5 h-2.5 text-white stroke-[3]" />
                  </div>
                )}
              </div>
              <div className="flex items-center gap-1 text-xs text-[#71717A] mt-0.5">
                <span>📍 {item.location} • 12 items • </span>
                <Star className="w-3 h-3 text-[#F59E0B] fill-[#F59E0B]" />
                <span className="font-medium text-[#0F0F0F]">4.9 (23)</span>
              </div>
            </div>
          </div>
          <ChevronRight className="w-4 h-4 text-[#71717A]" />
        </div>

        {/* Price Card Modern */}
        <div className="bg-white rounded-2xl border border-[#F0F0F0] p-4 space-y-3">
          <div className="flex items-center justify-between">
            <span className="text-xs text-[#71717A] font-medium">Per day rental</span>
            <span className="text-xl font-bold text-[#0F0F0F]">Rs. {formattedPrice}</span>
          </div>

          <div className="h-px bg-[#F0F0F0]" />

          <div className="flex items-center justify-between text-xs">
            <div className="flex items-center gap-2">
              <span className="text-[#71717A]">Per week (7 days)</span>
              {weeklySavings > 0 && (
                <span className="bg-[#DCFCE7] text-[#166534] font-bold px-2 py-0.5 rounded-full text-[10px]">
                  Save Rs. {weeklySavings.toLocaleString()}
                </span>
              )}
            </div>
            <span className="font-semibold text-[#0F0F0F]">Rs. {formattedWeekPrice}</span>
          </div>

          <div className="flex items-center justify-between text-xs">
            <span className="text-[#71717A]">Deposit (refundable cash)</span>
            <span className="font-medium text-[#0F0F0F]">Rs. {formattedDeposit}</span>
          </div>
        </div>

        {/* Description */}
        <div className="pt-2">
          <h3 className="text-base font-semibold text-[#0F0F0F] mb-2">
            About this item
          </h3>
          <p className="text-sm text-[#3F3F46] leading-relaxed">
            {item.description}
          </p>
        </div>

        {/* Specs List */}
        <div>
          <h3 className="text-base font-semibold text-[#0F0F0F] mb-2.5">
            Key Specifications
          </h3>
          <div className="space-y-2">
            {item.specs.map((spec, i) => (
              <div key={i} className="flex items-center gap-2 text-xs text-[#0F0F0F]">
                <div className="w-1.5 h-1.5 rounded-full bg-[#635BFF] shrink-0" />
                <span>{spec}</span>
              </div>
            ))}
          </div>
        </div>

        {/* Location Box */}
        <div>
          <h3 className="text-base font-semibold text-[#0F0F0F] mb-2">
            Handover Location
          </h3>
          <div className="bg-[#F4F4F5] rounded-xl p-4 flex items-center gap-3">
            <div className="w-10 h-10 rounded-full bg-white flex items-center justify-center text-[#635BFF] shrink-0 shadow-sm">
              <MapPin className="w-5 h-5" />
            </div>
            <div>
              <div className="text-sm font-semibold text-[#0F0F0F]">
                {item.location} City • {item.distance} away
              </div>
              <div className="text-xs text-[#71717A]">
                Public place near City Bus Stand • Exact address confirmed after booking
              </div>
            </div>
          </div>
        </div>

        {/* Safety Tips Card */}
        <div className="bg-[#F4F4F5] rounded-xl p-3.5 flex items-start gap-2.5">
          <Shield className="w-4 h-4 text-[#00C950] shrink-0 mt-0.5" />
          <p className="text-xs text-[#71717A] leading-relaxed">
            <strong className="text-[#0F0F0F]">Rentify Safety:</strong> Meet at a public place in {item.location}. Check and record video of item condition before taking. Keep photo of CNIC for security deposit safety.
          </p>
        </div>
      </div>

      {/* 3. Bottom Fixed Action Bar (Apple / Airbnb style glass) */}
      <div className="fixed bottom-0 left-0 right-0 z-40 bg-white/90 backdrop-blur-md border-t border-[#F0F0F0] py-3 px-4">
        <div className="max-w-[600px] mx-auto flex items-center gap-3">
          {/* Left 35%: Secondary Pill "Chat" */}
          <button
            onClick={handleChatClick}
            className="w-[35%] h-12 rounded-full border border-[#E4E4E7] bg-white text-[#0F0F0F] text-sm font-medium hover:bg-[#F4F4F5] flex items-center justify-center gap-1.5 transition active:scale-[0.98]"
          >
            <MessageCircle className="w-4 h-4" />
            <span>Chat</span>
          </button>

          {/* Right 65%: Primary Black Pill "Send Request • Rs. 2,000/day" */}
          <button
            onClick={() => openRentModal(item)}
            className="w-[65%] h-12 rounded-full bg-[#0F0F0F] text-white text-sm font-medium hover:scale-[0.99] active:scale-[0.97] transition shadow-md"
          >
            Send Request • Rs. {formattedPrice}/day
          </button>
        </div>
      </div>
    </div>
  );
};
