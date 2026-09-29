import React, { useState } from 'react';
import { ArrowLeft, MessageCircle, Phone, Check, X, Inbox } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const RequestsPage: React.FC = () => {
  const { requests, currentUser, updateRequestStatus, navigateTo } = useRentify();
  const [activeTab, setActiveTab] = useState<'sent' | 'received'>('sent');

  const sentRequests = requests.filter((r) => r.renterId === currentUser.id);
  const receivedRequests = requests.filter((r) => r.ownerId === currentUser.id);

  const displayedRequests = activeTab === 'sent' ? sentRequests : receivedRequests;

  return (
    <div className="max-w-2xl mx-auto px-4 pt-4 pb-28 min-h-screen">
      {/* Header */}
      <div className="flex items-center gap-3">
        <button
          onClick={() => navigateTo('home')}
          className="w-10 h-10 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#0F0F0F] hover:bg-[#E4E4E7] transition"
        >
          <ArrowLeft className="w-5 h-5" />
        </button>
        <h1 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
          Requests
        </h1>
      </div>

      {/* Tabs Container (Modern Pill) */}
      <div className="mt-5 p-1 bg-[#F4F4F5] rounded-full flex items-center h-11 max-w-sm">
        <button
          onClick={() => setActiveTab('sent')}
          className={`flex-1 h-full rounded-full text-xs font-semibold transition-all ${
            activeTab === 'sent'
              ? 'bg-white text-[#0F0F0F] shadow-sm'
              : 'text-[#71717A] hover:text-[#0F0F0F]'
          }`}
        >
          Sent ({sentRequests.length})
        </button>

        <button
          onClick={() => setActiveTab('received')}
          className={`flex-1 h-full rounded-full text-xs font-semibold transition-all ${
            activeTab === 'received'
              ? 'bg-white text-[#0F0F0F] shadow-sm'
              : 'text-[#71717A] hover:text-[#0F0F0F]'
          }`}
        >
          Received ({receivedRequests.length})
        </button>
      </div>

      {/* Requests List */}
      <div className="mt-6 space-y-3">
        {displayedRequests.length === 0 ? (
          <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-8 text-center">
            <div className="w-12 h-12 rounded-full bg-[#F4F4F5] flex items-center justify-center mx-auto text-[#71717A] mb-3">
              <Inbox className="w-5 h-5" />
            </div>
            <h3 className="text-sm font-semibold text-[#0F0F0F]">
              {activeTab === 'sent' ? 'No rent requests sent yet' : 'No incoming requests right now'}
            </h3>
            <p className="text-xs text-[#71717A] mt-1">
              {activeTab === 'sent'
                ? 'Find cameras, tools, or ACs on the home feed and send a request.'
                : 'When someone in Kashmore requests your items, they appear here.'}
            </p>
          </div>
        ) : (
          displayedRequests.map((req) => (
            <div
              key={req.id}
              className="bg-white border border-[#F0F0F0] rounded-2xl p-3.5 shadow-soft flex flex-col gap-3"
            >
              <div className="flex gap-3">
                {/* Left image 64px radius 12px */}
                <img
                  src={req.itemImage}
                  alt={req.itemTitle}
                  className="w-16 h-16 rounded-xl object-cover shrink-0 bg-[#F4F4F5]"
                />

                {/* Right column */}
                <div className="flex-1 min-w-0">
                  <div className="flex items-start justify-between gap-2">
                    <h4 className="text-sm font-semibold text-[#0F0F0F] truncate">
                      {req.itemTitle}
                    </h4>

                    {/* Status Badge Pill */}
                    {req.status === 'PENDING' && (
                      <span className="shrink-0 bg-[#FEF3C7] text-[#92400E] text-[10px] font-bold px-2 py-0.5 rounded-full">
                        Pending
                      </span>
                    )}
                    {req.status === 'ACCEPTED' && (
                      <span className="shrink-0 bg-[#DCFCE7] text-[#166534] text-[10px] font-bold px-2 py-0.5 rounded-full">
                        Accepted
                      </span>
                    )}
                    {req.status === 'REJECTED' && (
                      <span className="shrink-0 bg-[#FEE2E2] text-[#991B1B] text-[10px] font-bold px-2 py-0.5 rounded-full">
                        Declined
                      </span>
                    )}
                  </div>

                  <div className="text-xs text-[#71717A] mt-0.5">
                    {activeTab === 'sent' ? `Owner: ${req.ownerName}` : `Renter: ${req.renterName}`}
                  </div>

                  <div className="text-xs text-[#71717A]">
                    {req.startDate} – {req.endDate} • {req.daysCount} days
                  </div>

                  <div className="text-xs font-semibold text-[#0F0F0F] mt-1">
                    Rs. {req.rentAmount.toLocaleString()} rent + Rs. {req.depositAmount.toLocaleString()} deposit
                  </div>
                </div>
              </div>

              {/* Message excerpt if any */}
              {req.message && (
                <div className="bg-[#F4F4F5] rounded-xl p-2.5 text-xs text-[#0F0F0F] italic">
                  "{req.message}"
                </div>
              )}

              {/* Action buttons */}
              <div className="pt-1 flex items-center justify-between border-t border-[#F4F4F5]">
                {activeTab === 'received' && req.status === 'PENDING' ? (
                  <div className="flex items-center gap-2 w-full justify-end">
                    <button
                      onClick={() => updateRequestStatus(req.id, 'REJECTED')}
                      className="px-4 py-2 rounded-full border border-[#E4E4E7] text-xs font-semibold text-[#71717A] hover:bg-[#F4F4F5] flex items-center gap-1 transition"
                    >
                      <X className="w-3.5 h-3.5" />
                      <span>Decline</span>
                    </button>
                    <button
                      onClick={() => updateRequestStatus(req.id, 'ACCEPTED')}
                      className="px-4 py-2 rounded-full bg-[#00C950] text-white text-xs font-semibold hover:opacity-95 flex items-center gap-1 shadow-sm transition"
                    >
                      <Check className="w-3.5 h-3.5 stroke-[3]" />
                      <span>Accept</span>
                    </button>
                  </div>
                ) : (
                  <>
                    {req.status === 'ACCEPTED' ? (
                      <div className="flex items-center gap-2 text-xs font-semibold text-[#166534]">
                        <Phone className="w-3.5 h-3.5" />
                        <span>
                          {activeTab === 'sent'
                            ? `Owner: ${req.ownerPhone}`
                            : `Renter: ${req.renterPhone}`}
                        </span>
                      </div>
                    ) : (
                      <div className="text-xs text-[#71717A]">
                        {req.status === 'PENDING' ? 'Awaiting owner review' : 'Request concluded'}
                      </div>
                    )}

                    {req.status === 'ACCEPTED' && (
                      <button
                        onClick={() => navigateTo('chat', req.id)}
                        className="px-4 py-2 rounded-full bg-[#0F0F0F] text-white text-xs font-semibold hover:scale-[0.98] transition flex items-center gap-1.5 shadow-sm"
                      >
                        <MessageCircle className="w-3.5 h-3.5" />
                        <span>Chat</span>
                      </button>
                    )}
                  </>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
