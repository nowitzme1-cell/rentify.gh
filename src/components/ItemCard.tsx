import React from 'react';
import { MapPin, Check } from 'lucide-react';
import { RentalItem } from '../types';
import { useRentify } from '../context/RentifyContext';

interface ItemCardProps {
  item: RentalItem;
}

export const ItemCard: React.FC<ItemCardProps> = ({ item }) => {
  const { navigateTo } = useRentify();
  const formattedPrice = item.pricePerDay.toLocaleString();

  return (
    <div
      onClick={() => navigateTo('item', item.id)}
      className="group cursor-pointer bg-white border border-[#F0F0F0] rounded-[20px] shadow-soft hover:shadow-lift hover:-translate-y-0.5 active:scale-[0.98] transition-all duration-200 overflow-hidden flex flex-col"
    >
      {/* 1. Cover Image (180px, subtle hover zoom) */}
      <div className="relative h-[180px] w-full overflow-hidden bg-[#F4F4F5]">
        <img
          src={item.images[0]}
          alt={item.title}
          className="w-full h-full object-cover rounded-t-[20px] group-hover:scale-105 transition-transform duration-300 ease-out"
          loading="lazy"
        />

        {/* Category Pill Tag */}
        <div className="absolute top-3 left-3 bg-white/90 backdrop-blur-sm px-2.5 py-1 rounded-full text-[11px] font-semibold text-[#0F0F0F] shadow-sm">
          {item.category}
        </div>
      </div>

      {/* 2. Content Info */}
      <div className="p-3.5 flex flex-col flex-1">
        {/* Title (15px weight 600 black, 1 line ellipsis) */}
        <h3 className="text-[15px] font-semibold text-[#0F0F0F] tracking-tight truncate leading-snug">
          {item.title}
        </h3>

        {/* Owner Row: Avatar 24px, Name 13px grey, Instagram Blue Verified Tick */}
        <div className="mt-2 flex items-center gap-2">
          <div className="relative">
            <img
              src={item.ownerAvatar}
              alt={item.ownerName}
              className="w-6 h-6 rounded-full object-cover border-2 border-white shadow-sm"
            />
          </div>
          <span className="text-[13px] font-medium text-[#71717A] truncate">
            {item.ownerName}
          </span>
          {item.ownerVerified && (
            <div className="w-3.5 h-3.5 rounded-full bg-[#0095F6] flex items-center justify-center shrink-0" title="Verified Owner">
              <Check className="w-2.5 h-2.5 text-white stroke-[3]" />
            </div>
          )}
        </div>

        {/* Price Row: "Rs. 2,000 /day" (Black, premium, not red) */}
        <div className="mt-2.5">
          <span className="text-[15px] font-semibold text-[#0F0F0F]">
            Rs. {formattedPrice}
          </span>
          <span className="text-xs font-normal text-[#71717A] ml-1">
            /day
          </span>
        </div>

        {/* Location Row: Location outline icon 12px + Text "Kashmore • 1.2km" */}
        <div className="mt-1 flex items-center gap-1 text-[#71717A]">
          <MapPin className="w-3 h-3 text-[#71717A] shrink-0" />
          <span className="text-[11px] font-normal truncate">
            {item.location} • {item.distance}
          </span>
        </div>
      </div>
    </div>
  );
};
