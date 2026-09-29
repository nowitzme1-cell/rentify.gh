import React from 'react';
import { Search, X, ArrowRight, Upload, MessageCircle, ShieldCheck } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';
import { ItemCard } from '../components/ItemCard';

export const HomePage: React.FC = () => {
  const {
    approvedItems,
    searchQuery,
    setSearchQuery,
    selectedCategory,
    setSelectedCategory,
    currentCity
  } = useRentify();

  const CATEGORIES = [
    'All',
    'Camera',
    'AC',
    'Furniture',
    'Cars',
    'Tools',
    'Generators',
    'Wedding',
    'Dresses'
  ];

  const filteredItems = approvedItems.filter((item) => {
    const matchesQuery =
      searchQuery.trim() === '' ||
      item.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.category.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.description.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.location.toLowerCase().includes(searchQuery.toLowerCase());

    const matchesCategory =
      selectedCategory === 'All' || item.category.toLowerCase() === selectedCategory.toLowerCase();

    const matchesCity =
      currentCity === 'All Pakistan' || item.location.toLowerCase() === currentCity.toLowerCase();

    return matchesQuery && matchesCategory && matchesCity;
  });

  return (
    <div className="max-w-6xl mx-auto px-4 pt-4 pb-28">
      {/* 1. Search Bar */}
      <div className="relative">
        <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-4 h-4 text-[#71717A]" />
        <input
          id="search-input"
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder={`Search cameras, cars, ACs in ${currentCity}...`}
          className="w-full h-12 pl-11 pr-10 rounded-full bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:bg-white focus:ring-1 focus:ring-[#0F0F0F] focus:shadow-sm transition"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery('')}
            className="absolute right-4 top-1/2 -translate-y-1/2 text-[#71717A] hover:text-[#0F0F0F]"
          >
            <X className="w-4 h-4" />
          </button>
        )}
      </div>

      {/* 2. Categories Horizontal Scroll Pills (Text Only, Modern 2025) */}
      <div className="mt-4 flex items-center gap-2 overflow-x-auto no-scrollbar py-1">
        {CATEGORIES.map((cat) => {
          const isSelected = selectedCategory.toLowerCase() === cat.toLowerCase();
          return (
            <button
              key={cat}
              onClick={() => setSelectedCategory(cat)}
              className={`px-4 py-2 rounded-full text-xs whitespace-nowrap transition-all ${
                isSelected
                  ? 'bg-[#0F0F0F] text-white font-semibold shadow-sm'
                  : 'bg-[#F4F4F5] hover:bg-[#E4E4E7] text-[#0F0F0F] font-medium'
              }`}
            >
              {cat}
            </button>
          );
        })}
      </div>

      {/* 3. Section Title Row */}
      <div className="mt-6 flex items-center justify-between">
        <h2 className="text-lg font-semibold tracking-tight text-[#0F0F0F]">
          {selectedCategory === 'All' ? `Near you in ${currentCity}` : `${selectedCategory} in ${currentCity}`}
        </h2>
        <button
          onClick={() => {
            setSelectedCategory('All');
            setSearchQuery('');
          }}
          className="text-xs font-medium text-[#71717A] hover:text-[#0F0F0F] flex items-center gap-1 transition"
        >
          <span>See all</span>
          <ArrowRight className="w-3.5 h-3.5" />
        </button>
      </div>

      {/* 4. Items Grid (Mobile 2 cols, Tablet 3 cols, Desktop 4 cols) */}
      {filteredItems.length === 0 ? (
        <div className="mt-8 bg-white border border-[#F0F0F0] rounded-[20px] p-8 text-center max-w-md mx-auto">
          <div className="w-12 h-12 rounded-full bg-[#F4F4F5] flex items-center justify-center mx-auto text-[#71717A] mb-3">
            <Search className="w-5 h-5" />
          </div>
          <h3 className="text-base font-semibold text-[#0F0F0F]">No items available</h3>
          <p className="text-xs text-[#71717A] mt-1">
            Try switching cities or search for Sony camera, AC, Mehran, or drill.
          </p>
        </div>
      ) : (
        <div className="mt-4 grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 sm:gap-4">
          {filteredItems.map((item) => (
            <ItemCard key={item.id} item={item} />
          ))}
        </div>
      )}

      {/* 5. "How it works?" 3 steps modern minimal */}
      <div className="mt-12 bg-white border border-[#F0F0F0] rounded-[24px] p-6 shadow-soft">
        <h3 className="text-base font-bold tracking-tight text-[#0F0F0F]">
          How Rentify.pk Works
        </h3>
        <p className="text-xs text-[#71717A] mt-0.5">
          Temporary rent for days instead of buying permanent ownership.
        </p>

        <div className="mt-6 grid grid-cols-3 gap-3 text-center">
          <div className="flex flex-col items-center">
            <div className="w-12 h-12 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#635BFF] mb-2.5">
              <Upload className="w-5 h-5" />
            </div>
            <h4 className="text-xs font-semibold text-[#0F0F0F]">1. Post Item</h4>
            <p className="text-[11px] text-[#71717A] mt-1 leading-tight">
              List idle camera, AC, tools at home
            </p>
          </div>

          <div className="flex flex-col items-center">
            <div className="w-12 h-12 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#635BFF] mb-2.5">
              <MessageCircle className="w-5 h-5" />
            </div>
            <h4 className="text-xs font-semibold text-[#0F0F0F]">2. Get Requests</h4>
            <p className="text-[11px] text-[#71717A] mt-1 leading-tight">
              Chat in app & agree handover meeting
            </p>
          </div>

          <div className="flex flex-col items-center">
            <div className="w-12 h-12 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#635BFF] mb-2.5">
              <ShieldCheck className="w-5 h-5" />
            </div>
            <h4 className="text-xs font-semibold text-[#0F0F0F]">3. Earn & Return</h4>
            <p className="text-[11px] text-[#71717A] mt-1 leading-tight">
              Take cash + refundable deposit on meet
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
