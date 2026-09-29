import React, { useState } from 'react';
import { ArrowLeft, Check, MapPin, Upload } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';
import { RentalItem } from '../types';

export const PostItemPage: React.FC = () => {
  const { postNewItem, navigateTo, currentCity, currentUser } = useRentify();

  const [step, setStep] = useState<1 | 2 | 3>(1);

  // Form Fields
  const [title, setTitle] = useState('');
  const [category, setCategory] = useState<RentalItem['category']>('Camera');
  const [description, setDescription] = useState('');
  const [location, setLocation] = useState(currentCity || 'Kashmore');
  const [specs, setSpecs] = useState('');
  const [pricePerDay, setPricePerDay] = useState('');
  const [deposit, setDeposit] = useState('');
  const [agreed, setAgreed] = useState(true);

  // Preset photo choices
  const PRESET_PHOTOS = [
    'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800', // Camera
    'https://images.unsplash.com/photo-1621905251918-48416bd8575a?w=800', // AC
    'https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800', // Sofa
    'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800', // Car
    'https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=800', // Generator
    'https://images.unsplash.com/photo-1504148455328-c376907d081c?w=800'  // Drill
  ];
  const [selectedPhoto, setSelectedPhoto] = useState(PRESET_PHOTOS[0]);

  const CATEGORIES: RentalItem['category'][] = [
    'Camera',
    'AC',
    'Furniture',
    'Cars',
    'Tools',
    'Generators',
    'Wedding',
    'Dresses'
  ];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!title || !pricePerDay) return;

    const specsArray = specs
      ? specs.split(',').map((s) => s.trim()).filter(Boolean)
      : ['Condition 9/10', 'Verified Owner', 'Available for rent'];

    postNewItem(
      title,
      category,
      Number(pricePerDay) || 2000,
      Number(deposit) || 5000,
      location,
      description || `Clean and well-maintained ${title} available for temporary rental in ${location}.`,
      specsArray,
      [selectedPhoto]
    );
  };

  return (
    <div className="max-w-[600px] mx-auto px-5 pt-4 pb-28 min-h-screen">
      {/* Top Header */}
      <div className="flex items-center gap-3">
        <button
          onClick={() => navigateTo('home')}
          className="w-10 h-10 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#0F0F0F] hover:bg-[#E4E4E7] transition"
        >
          <ArrowLeft className="w-5 h-5" />
        </button>
        <div>
          <h1 className="text-xl font-bold tracking-tight text-[#0F0F0F]">
            List your item
          </h1>
          <p className="text-xs text-[#71717A]">
            Earn money from unused items lying in cupboards
          </p>
        </div>
      </div>

      {/* Modern 3-Step Progress Indicator */}
      <div className="mt-6 flex items-center justify-between px-4">
        {/* Step 1 */}
        <div
          onClick={() => setStep(1)}
          className="flex items-center gap-2 cursor-pointer"
        >
          <div
            className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold transition-all ${
              step >= 1 ? 'bg-[#0F0F0F] text-white' : 'bg-[#F4F4F5] text-[#71717A]'
            }`}
          >
            1
          </div>
          <span className={`text-xs font-semibold ${step === 1 ? 'text-[#0F0F0F]' : 'text-[#71717A]'}`}>
            Photos
          </span>
        </div>

        <div className={`flex-1 h-0.5 mx-3 ${step > 1 ? 'bg-[#0F0F0F]' : 'bg-[#E4E4E7]'}`} />

        {/* Step 2 */}
        <div
          onClick={() => setStep(2)}
          className="flex items-center gap-2 cursor-pointer"
        >
          <div
            className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold transition-all ${
              step >= 2 ? 'bg-[#0F0F0F] text-white' : 'bg-[#F4F4F5] text-[#71717A]'
            }`}
          >
            2
          </div>
          <span className={`text-xs font-semibold ${step === 2 ? 'text-[#0F0F0F]' : 'text-[#71717A]'}`}>
            Details
          </span>
        </div>

        <div className={`flex-1 h-0.5 mx-3 ${step > 2 ? 'bg-[#0F0F0F]' : 'bg-[#E4E4E7]'}`} />

        {/* Step 3 */}
        <div
          onClick={() => setStep(3)}
          className="flex items-center gap-2 cursor-pointer"
        >
          <div
            className={`w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold transition-all ${
              step === 3 ? 'bg-[#0F0F0F] text-white' : 'bg-[#F4F4F5] text-[#71717A]'
            }`}
          >
            3
          </div>
          <span className={`text-xs font-semibold ${step === 3 ? 'text-[#0F0F0F]' : 'text-[#71717A]'}`}>
            Price
          </span>
        </div>
      </div>

      <form onSubmit={handleSubmit} className="mt-8 space-y-6">
        {/* STEP 1: PHOTOS */}
        {step === 1 && (
          <div className="space-y-4 animate-in fade-in duration-200">
            <div>
              <label className="text-sm font-semibold text-[#0F0F0F] block">
                Item Cover Photo
              </label>
              <p className="text-xs text-[#71717A] mt-0.5">
                First photo is shown on cards in Kashmore search
              </p>
            </div>

            {/* Selected Cover Preview */}
            <div className="relative h-56 w-full rounded-2xl overflow-hidden bg-[#F4F4F5] border border-[#E4E4E7]">
              <img
                src={selectedPhoto}
                alt="Selected"
                className="w-full h-full object-cover"
              />
              <div className="absolute bottom-3 left-3 bg-[#0F0F0F] text-white text-[11px] font-bold px-3 py-1 rounded-full shadow-sm">
                Cover Photo
              </div>
            </div>

            {/* Preset Thumbnails */}
            <div>
              <span className="text-xs font-semibold text-[#0F0F0F] block mb-2">
                Choose Sample Item Photo
              </span>
              <div className="grid grid-cols-3 sm:grid-cols-6 gap-2.5">
                {PRESET_PHOTOS.map((photo, index) => (
                  <div
                    key={index}
                    onClick={() => setSelectedPhoto(photo)}
                    className={`relative aspect-square rounded-xl overflow-hidden cursor-pointer border-2 transition-all ${
                      selectedPhoto === photo ? 'border-[#0F0F0F] scale-95' : 'border-transparent hover:border-[#E4E4E7]'
                    }`}
                  >
                    <img src={photo} alt="" className="w-full h-full object-cover" />
                    {selectedPhoto === photo && (
                      <div className="absolute top-1 right-1 w-4 h-4 rounded-full bg-[#0F0F0F] flex items-center justify-center">
                        <Check className="w-2.5 h-2.5 text-white stroke-[3]" />
                      </div>
                    )}
                  </div>
                ))}
              </div>
            </div>

            <button
              type="button"
              onClick={() => setStep(2)}
              className="w-full h-12 rounded-full bg-[#0F0F0F] text-white text-sm font-medium hover:scale-[0.99] active:scale-[0.97] transition mt-6"
            >
              Continue to Details →
            </button>
          </div>
        )}

        {/* STEP 2: DETAILS */}
        {step === 2 && (
          <div className="space-y-4 animate-in fade-in duration-200">
            {/* Title */}
            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                Item Title
              </label>
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="e.g. Sony A6400 Camera for Rent"
                className="w-full h-12 px-4 rounded-xl bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                required
              />
            </div>

            {/* Category Select Pills */}
            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-2">
                Category
              </label>
              <div className="flex flex-wrap gap-2">
                {CATEGORIES.map((cat) => (
                  <button
                    key={cat}
                    type="button"
                    onClick={() => setCategory(cat)}
                    className={`px-3.5 py-1.5 rounded-full text-xs transition ${
                      category === cat
                        ? 'bg-[#0F0F0F] text-white font-semibold'
                        : 'bg-[#F4F4F5] text-[#0F0F0F] hover:bg-[#E4E4E7]'
                    }`}
                  >
                    {cat}
                  </button>
                ))}
              </div>
            </div>

            {/* Location */}
            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                City / Location
              </label>
              <div className="relative">
                <MapPin className="absolute left-3.5 top-1/2 -translate-y-1/2 w-4 h-4 text-[#635BFF]" />
                <input
                  type="text"
                  value={location}
                  onChange={(e) => setLocation(e.target.value)}
                  placeholder="Kashmore"
                  className="w-full h-12 pl-10 pr-4 rounded-xl bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                />
              </div>
            </div>

            {/* Description */}
            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                Description
              </label>
              <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows={3}
                placeholder="Describe accessories included, battery, condition, usage tips..."
                className="w-full p-3 rounded-xl bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F] resize-none"
              />
            </div>

            {/* Specs */}
            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                Specs (comma separated)
              </label>
              <input
                type="text"
                value={specs}
                onChange={(e) => setSpecs(e.target.value)}
                placeholder="Brand Sony, Model A6400, Condition 9/10, With Bag"
                className="w-full h-12 px-4 rounded-xl bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
              />
            </div>

            <div className="flex gap-3 pt-2">
              <button
                type="button"
                onClick={() => setStep(1)}
                className="w-1/3 h-12 rounded-full border border-[#E4E4E7] text-sm font-medium hover:bg-[#F4F4F5]"
              >
                Back
              </button>
              <button
                type="button"
                onClick={() => setStep(3)}
                className="w-2/3 h-12 rounded-full bg-[#0F0F0F] text-white text-sm font-medium hover:scale-[0.99] transition"
              >
                Continue to Pricing →
              </button>
            </div>
          </div>
        )}

        {/* STEP 3: PRICE */}
        {step === 3 && (
          <div className="space-y-5 animate-in fade-in duration-200">
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                  Price Per Day (Rs.)
                </label>
                <input
                  type="number"
                  value={pricePerDay}
                  onChange={(e) => setPricePerDay(e.target.value)}
                  placeholder="2000"
                  className="w-full h-12 px-4 rounded-xl bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                  required
                />
              </div>

              <div>
                <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                  Refundable Deposit (Rs.)
                </label>
                <input
                  type="number"
                  value={deposit}
                  onChange={(e) => setDeposit(e.target.value)}
                  placeholder="5000"
                  className="w-full h-12 px-4 rounded-xl bg-[#F4F4F5] text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                  required
                />
              </div>
            </div>

            {/* Earnings estimate preview */}
            <div className="p-4 rounded-2xl bg-[#FAFAFA] border border-[#F0F0F0] text-xs space-y-1.5">
              <div className="flex justify-between text-[#71717A]">
                <span>Rent per day</span>
                <span className="font-semibold text-[#0F0F0F]">Rs. {pricePerDay || '0'}</span>
              </div>
              <div className="flex justify-between text-[#71717A]">
                <span>Rentify platform fee (10%)</span>
                <span className="font-semibold text-[#635BFF]">
                  Rs. {Math.round(Number(pricePerDay || 0) * 0.10)}
                </span>
              </div>
              <div className="flex justify-between text-[#00C950] font-bold pt-1 border-t border-[#E4E4E7]">
                <span>Your net earning per day</span>
                <span>Rs. {Math.round(Number(pricePerDay || 0) * 0.90)}</span>
              </div>
            </div>

            {/* Owner Terms Checkbox */}
            <div className="flex items-start gap-2.5">
              <input
                type="checkbox"
                id="post-terms"
                checked={agreed}
                onChange={(e) => setAgreed(e.target.checked)}
                className="mt-0.5 rounded text-[#0F0F0F] focus:ring-[#0F0F0F] cursor-pointer"
              />
              <label htmlFor="post-terms" className="text-xs text-[#71717A] leading-relaxed cursor-pointer select-none">
                I confirm I am the legitimate owner of this item and agree to meet renters in public places in {location}.
              </label>
            </div>

            <div className="flex gap-3 pt-4">
              <button
                type="button"
                onClick={() => setStep(2)}
                className="w-1/3 h-[52px] rounded-full border border-[#E4E4E7] text-sm font-medium hover:bg-[#F4F4F5]"
              >
                Back
              </button>
              <button
                type="submit"
                disabled={!agreed || !title || !pricePerDay}
                className="w-2/3 h-[52px] rounded-full bg-[#0F0F0F] text-white text-sm font-medium hover:scale-[0.99] active:scale-[0.97] transition disabled:opacity-50"
              >
                Post Item • Send for Review
              </button>
            </div>
          </div>
        )}
      </form>
    </div>
  );
};
