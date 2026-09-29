import React, { useState, useEffect, useRef } from 'react';
import { ArrowLeft, Send, Phone, Shield } from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const ChatPage: React.FC = () => {
  const {
    requests,
    chats,
    activeRequestId,
    currentUser,
    sendChatMessage,
    navigateTo
  } = useRentify();

  const [inputText, setInputText] = useState('');
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const request = requests.find((r) => r.id === activeRequestId) || requests[0];
  const messages = (activeRequestId && chats[activeRequestId]) || [];

  const otherUserName = currentUser.id === request?.ownerId ? request?.renterName : request?.ownerName;
  const otherUserPhone = currentUser.id === request?.ownerId ? request?.renterPhone : request?.ownerPhone;

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const handleSend = (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputText.trim() || !request) return;
    sendChatMessage(request.id, inputText);
    setInputText('');
  };

  return (
    <div className="flex flex-col h-screen bg-[#FAFAFA]">
      {/* Header Blur */}
      <header className="sticky top-0 z-30 bg-white/90 backdrop-blur-md border-b border-[#F4F4F5] px-4 py-2.5">
        <div className="max-w-2xl mx-auto flex items-center justify-between">
          <div className="flex items-center gap-3">
            <button
              onClick={() => navigateTo('requests')}
              className="w-9 h-9 rounded-full bg-[#F4F4F5] flex items-center justify-center text-[#0F0F0F] hover:bg-[#E4E4E7] transition"
            >
              <ArrowLeft className="w-4 h-4" />
            </button>

            {/* Avatar with Green Dot */}
            <div className="relative">
              <img
                src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200"
                alt=""
                className="w-10 h-10 rounded-full object-cover border border-[#F0F0F0]"
              />
              <span className="absolute bottom-0 right-0 w-2.5 h-2.5 rounded-full bg-[#00C950] border-2 border-white" />
            </div>

            <div>
              <h2 className="text-sm font-semibold text-[#0F0F0F] leading-tight">
                {otherUserName}
              </h2>
              <p className="text-[11px] text-[#71717A] truncate max-w-[200px]">
                {request?.itemTitle}
              </p>
            </div>
          </div>

          {/* Quick Call Button */}
          <a
            href={`tel:${otherUserPhone}`}
            className="w-9 h-9 rounded-full bg-[#F4F4F5] hover:bg-[#E4E4E7] flex items-center justify-center text-[#0F0F0F] transition"
            title="Call"
          >
            <Phone className="w-4 h-4" />
          </a>
        </div>
      </header>

      {/* Messages Scroll Area */}
      <div className="flex-1 overflow-y-auto px-4 py-6 max-w-2xl mx-auto w-full space-y-3">
        {messages.map((msg) => {
          if (msg.isSystem) {
            return (
              <div key={msg.id} className="flex justify-center my-3">
                <span className="bg-[#F4F4F5] text-[#71717A] text-[11px] font-medium px-4 py-1.5 rounded-full flex items-center gap-1.5 shadow-sm">
                  <Shield className="w-3 h-3 text-[#00C950]" />
                  <span>{msg.text}</span>
                </span>
              </div>
            );
          }

          const isMe = msg.senderId === currentUser.id;

          return (
            <div
              key={msg.id}
              className={`flex ${isMe ? 'justify-end' : 'justify-start'}`}
            >
              <div
                className={`max-w-[75%] px-3.5 py-2.5 text-[13.5px] leading-relaxed shadow-soft ${
                  isMe
                    ? 'bg-[#0F0F0F] text-white rounded-[20px] rounded-br-[4px]'
                    : 'bg-white text-[#0F0F0F] border border-[#F0F0F0] rounded-[20px] rounded-bl-[4px]'
                }`}
              >
                <p>{msg.text}</p>
                <span
                  className={`text-[9.5px] block text-right mt-1 ${
                    isMe ? 'text-zinc-400' : 'text-zinc-400'
                  }`}
                >
                  {new Date(msg.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                </span>
              </div>
            </div>
          );
        })}
        <div ref={messagesEndRef} />
      </div>

      {/* Floating Input Pill Bar */}
      <div className="p-3 bg-white/80 backdrop-blur-md border-t border-[#F0F0F0]">
        <form
          onSubmit={handleSend}
          className="max-w-2xl mx-auto flex items-center bg-[#F4F4F5] rounded-full pl-4 pr-1.5 py-1.5"
        >
          <input
            type="text"
            value={inputText}
            onChange={(e) => setInputText(e.target.value)}
            placeholder="Type message to agree on meeting place..."
            className="flex-1 bg-transparent text-sm text-[#0F0F0F] placeholder-[#71717A] border-none focus:outline-none"
          />

          <button
            type="submit"
            disabled={!inputText.trim()}
            className="w-9 h-9 rounded-full bg-[#0F0F0F] text-white flex items-center justify-center hover:scale-105 active:scale-95 disabled:opacity-30 transition ml-2 shrink-0"
          >
            <Send className="w-4 h-4 ml-0.5" />
          </button>
        </form>
      </div>
    </div>
  );
};
