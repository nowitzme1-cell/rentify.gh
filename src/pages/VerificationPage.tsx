import React, { useState } from 'react';
import { ArrowLeft, Check, CreditCard, User, Upload } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const VerificationPage: React.FC = () => {
  const { navigateTo, submitCnicVerification, currentUser } = useRentify();

  const [frontPhoto, setFrontPhoto] = useState(
    currentUser.cnicFront || 'https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=600'
  );
  const [backPhoto, setBackPhoto] = useState(
    currentUser.cnicBack || 'https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=600'
  );
  const [selfiePhoto, setSelfiePhoto] = useState(
    currentUser.cnicSelfie || 'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400'
  );

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    submitCnicVerification(frontPhoto, backPhoto, selfiePhoto);
  };

  return (
    <div className="max-w-xl mx-auto px-4 pt-4 pb-28 min-h-screen">
      <div className="flex items-center gap-3">
        <button
          onClick={() => navigateTo('profile')}
          className="w-10 h-10 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#0F0F0F] hover:bg-[#E4E4E7] transition"
        >
          <ArrowLeft className="w-5 h-5" />
        </button>
        <h1 className="text-xl font-bold tracking-tight text-[#0F0F0F]">
          Get Blue Tick Verified
        </h1>
      </div>

      <div className="mt-4 bg-[#F0FDF4] border border-[#BBF7D0] rounded-2xl p-4 flex items-center gap-3">
        <div className="w-8 h-8 rounded-full bg-[#0095F6] flex items-center justify-center text-white shrink-0">
          <Check className="w-4 h-4 stroke-[3]" />
        </div>
        <p className="text-xs text-[#166534] leading-relaxed">
          In Kashmore & Usta Muhammad, users only rent expensive items (like Rs. 250,000 cameras and cars) to verified users with Blue Tick.
        </p>
      </div>

      <form onSubmit={handleSubmit} className="mt-6 space-y-5">
        {/* Step 1: CNIC Front */}
        <div>
          <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
            1. CNIC Front Photo
          </label>
          <div className="h-32 rounded-2xl bg-[#F4F4F5] border border-[#E4E4E7] relative overflow-hidden flex items-center p-3 gap-4">
            <img
              src={frontPhoto}
              alt="CNIC Front"
              className="w-28 h-full object-cover rounded-xl shrink-0"
            />
            <div className="flex-1 min-w-0">
              <span className="text-xs font-semibold text-[#0F0F0F] block">Front Side Uploaded</span>
              <span className="text-[11px] text-[#71717A] block mt-0.5">Showing name & photo clearly</span>
              <button
                type="button"
                onClick={() => setFrontPhoto('https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=600')}
                className="mt-2 text-xs text-[#635BFF] font-semibold hover:underline"
              >
                Re-upload photo
              </button>
            </div>
          </div>
        </div>

        {/* Step 2: CNIC Back */}
        <div>
          <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
            2. CNIC Back Photo
          </label>
          <div className="h-32 rounded-2xl bg-[#F4F4F5] border border-[#E4E4E7] relative overflow-hidden flex items-center p-3 gap-4">
            <img
              src={backPhoto}
              alt="CNIC Back"
              className="w-28 h-full object-cover rounded-xl shrink-0"
            />
            <div className="flex-1 min-w-0">
              <span className="text-xs font-semibold text-[#0F0F0F] block">Back Side Uploaded</span>
              <span className="text-[11px] text-[#71717A] block mt-0.5">Showing permanent Kashmore address</span>
              <button
                type="button"
                onClick={() => setBackPhoto('https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=600')}
                className="mt-2 text-xs text-[#635BFF] font-semibold hover:underline"
              >
                Re-upload photo
              </button>
            </div>
          </div>
        </div>

        {/* Step 3: Selfie with CNIC */}
        <div>
          <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
            3. Live Selfie Holding CNIC
          </label>
          <div className="h-32 rounded-2xl bg-[#F4F4F5] border border-[#E4E4E7] relative overflow-hidden flex items-center p-3 gap-4">
            <img
              src={selfiePhoto}
              alt="Selfie with CNIC"
              className="w-28 h-full object-cover rounded-xl shrink-0"
            />
            <div className="flex-1 min-w-0">
              <span className="text-xs font-semibold text-[#0F0F0F] block">Face & ID Matched</span>
              <span className="text-[11px] text-[#71717A] block mt-0.5">Used for fraud prevention</span>
              <button
                type="button"
                onClick={() => setSelfiePhoto('https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400')}
                className="mt-2 text-xs text-[#635BFF] font-semibold hover:underline"
              >
                Retake photo
              </button>
            </div>
          </div>
        </div>

        <button
          type="submit"
          className="w-full h-12 rounded-full bg-[#0F0F0F] text-white text-sm font-semibold hover:scale-[0.99] active:scale-[0.97] transition mt-6"
        >
          Submit for Admin Review
        </button>
      </form>
    </div>
  );
};
