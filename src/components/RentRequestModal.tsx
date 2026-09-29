import React, { useState } from 'react';
import { Plus, Minus, X, Check, Shield } from 'lucide-react';
import { RentalItem } from '../types';
import { useRentify } from '../context/RentifyContext';

interface RentRequestModalProps {
  item: RentalItem;
  onClose: () => void;
}

export const RentRequestModal: React.FC<RentRequestModalProps> = ({ item, onClose }) => {
  const { submitRentRequest, navigateTo } = useRentify();

  const [days, setDays] = useState(2);
  const [startDate, setStartDate] = useState('15 Nov');
  const [endDate, setEndDate] = useState('16 Nov');
  const [message, setMessage] = useState(
    `Hi ${item.ownerName.split(' ')[0]}, I need this for 2 days for an upcoming event, will handle with extreme care and return on time.`
  );
  const [agreed, setAgreed] = useState(true);

  const rentAmount = item.pricePerDay * days;
  const platformFee = Math.round(rentAmount * 0.10); // 10%
  const depositAmount = item.deposit;
  const totalCashOnMeet = rentAmount + platformFee + depositAmount;

  const handleDaysChange = (newDays: number) => {
    if (newDays >= 1) {
      setDays(newDays);
      setEndDate(`${14 + newDays} Nov`);
    }
  };

  const handleConfirm = () => {
    if (!agreed) return;
    submitRentRequest(item, days, startDate, endDate, message);
    navigateTo('requests');
  };

  return (
    <div className="fixed inset-0 z-50 flex items-end justify-center bg-black/50 backdrop-blur-sm transition-opacity">
      <div 
        className="w-full max-w-lg bg-white rounded-t-[24px] max-h-[92vh] overflow-y-auto p-6 shadow-2xl relative animate-in fade-in slide-in-from-bottom duration-200"
      >
        {/* Handle bar top */}
        <div className="w-9 h-1 rounded-full bg-[#E4E4E7] mx-auto mb-4" />

        {/* Close Button Top Right */}
        <button
          onClick={onClose}
          className="absolute top-5 right-5 w-8 h-8 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#71717A] hover:text-[#0F0F0F] transition"
        >
          <X className="w-4 h-4" />
        </button>

        {/* Title */}
        <h2 className="text-xl font-bold tracking-tight text-[#0F0F0F]">
          Send rent request
        </h2>

        {/* Mini Item Card */}
        <div className="mt-4 p-3 bg-[#F4F4F5] rounded-2xl flex items-center gap-3">
          <img
            src={item.images[0]}
            alt={item.title}
            className="w-12 h-12 rounded-xl object-cover shrink-0"
          />
          <div className="min-w-0 flex-1">
            <h4 className="text-sm font-semibold text-[#0F0F0F] truncate">
              {item.title}
            </h4>
            <p className="text-xs text-[#71717A] truncate">
              Owner: {item.ownerName} • 📍 {item.location}
            </p>
          </div>
        </div>

        {/* Duration & Dates */}
        <div className="mt-5">
          <label className="text-xs font-semibold text-[#0F0F0F] uppercase tracking-wider block mb-2">
            Rental Duration
          </label>
          <div className="flex items-center justify-between bg-[#F4F4F5] p-3 rounded-2xl">
            <div>
              <div className="text-sm font-semibold text-[#0F0F0F]">
                {startDate} – {endDate}
              </div>
              <div className="text-xs text-[#71717A]">
                {days} days rental duration
              </div>
            </div>

            {/* Stepper Buttons */}
            <div className="flex items-center gap-2 bg-white px-2 py-1 rounded-full border border-[#E4E4E7]">
              <button
                type="button"
                onClick={() => handleDaysChange(days - 1)}
                disabled={days <= 1}
                className="w-7 h-7 rounded-full flex items-center justify-center hover:bg-[#F4F4F5] disabled:opacity-40"
              >
                <Minus className="w-3.5 h-3.5" />
              </button>
              <span className="text-sm font-bold min-w-5 text-center">{days}d</span>
              <button
                type="button"
                onClick={() => handleDaysChange(days + 1)}
                className="w-7 h-7 rounded-full flex items-center justify-center hover:bg-[#F4F4F5]"
              >
                <Plus className="w-3.5 h-3.5" />
              </button>
            </div>
          </div>
        </div>

        {/* Auto Calculation Card */}
        <div className="mt-4 p-4 rounded-2xl bg-[#FAFAFA] border border-[#F0F0F0]">
          <div className="space-y-2 text-xs">
            <div className="flex justify-between text-[#71717A]">
              <span>{days} days rent (Rs. {item.pricePerDay.toLocaleString()} × {days})</span>
              <span className="font-semibold text-[#0F0F0F]">Rs. {rentAmount.toLocaleString()}</span>
            </div>
            <div className="flex justify-between text-[#71717A]">
              <span>Rentify platform fee (10%)</span>
              <span className="font-semibold text-[#635BFF]">Rs. {platformFee.toLocaleString()}</span>
            </div>
            <div className="flex justify-between text-[#71717A]">
              <span>Refundable deposit (cash)</span>
              <span className="font-semibold text-[#0F0F0F]">Rs. {depositAmount.toLocaleString()}</span>
            </div>
          </div>

          <div className="h-px bg-[#E4E4E7] my-3" />

          <div className="flex justify-between items-center">
            <div>
              <div className="text-sm font-bold text-[#0F0F0F]">Total Cash on Handover</div>
              <div className="text-[11px] font-medium text-[#00C950]">
                Includes Rs. {depositAmount.toLocaleString()} returned after return
              </div>
            </div>
            <div className="text-lg font-bold text-[#0F0F0F]">
              Rs. {totalCashOnMeet.toLocaleString()}
            </div>
          </div>
        </div>

        {/* Message Textarea */}
        <div className="mt-4">
          <label className="text-xs font-semibold text-[#0F0F0F] block mb-1.5">
            Message to Owner
          </label>
          <textarea
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            rows={3}
            placeholder="Tell owner what you need it for, proposed meeting time..."
            className="w-full bg-[#F4F4F5] rounded-xl p-3 text-xs text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F] resize-none"
          />
        </div>

        {/* Safety & Agreement Checkbox */}
        <div className="mt-4 flex items-start gap-2.5">
          <input
            type="checkbox"
            id="agree-checkbox"
            checked={agreed}
            onChange={(e) => setAgreed(e.target.checked)}
            className="mt-0.5 rounded text-[#0F0F0F] focus:ring-[#0F0F0F] cursor-pointer"
          />
          <label htmlFor="agree-checkbox" className="text-xs text-[#71717A] leading-relaxed cursor-pointer select-none">
            I agree to return on time, handle carefully, and present my original CNIC at the public meeting in Kashmore.
          </label>
        </div>

        {/* Confirm Button */}
        <div className="mt-6">
          <button
            onClick={handleConfirm}
            disabled={!agreed}
            className="w-full h-[52px] rounded-full bg-[#0F0F0F] text-white text-sm font-medium hover:scale-[0.99] active:scale-[0.97] disabled:opacity-50 transition"
          >
            Confirm • Send Request
          </button>
        </div>
      </div>
    </div>
  );
};
