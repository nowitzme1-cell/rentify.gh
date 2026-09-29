import React from 'react';
import { useRentify } from './context/RentifyContext';
import { Header } from './components/Header';
import { BottomNav } from './components/BottomNav';
import { RentRequestModal } from './components/RentRequestModal';
import { HomePage } from './pages/HomePage';
import { ItemDetailPage } from './pages/ItemDetailPage';
import { PostItemPage } from './pages/PostItemPage';
import { RequestsPage } from './pages/RequestsPage';
import { ChatPage } from './pages/ChatPage';
import { ProfilePage } from './pages/ProfilePage';
import { VerificationPage } from './pages/VerificationPage';
import { AdminPage } from './pages/AdminPage';

export const App: React.FC = () => {
  const { currentRoute, requestModalItem, closeRentModal, toastMessage } = useRentify();

  const showHeader = currentRoute !== 'admin' && currentRoute !== 'chat' && currentRoute !== 'item';
  const showBottomNav = currentRoute === 'home' || currentRoute === 'requests' || currentRoute === 'profile';

  return (
    <div className="min-h-screen bg-[#FAFAFA] font-sans antialiased text-[#0F0F0F] selection:bg-[#635BFF] selection:text-white relative">
      {/* Toast Notification */}
      {toastMessage && (
        <div className="fixed top-4 left-1/2 -translate-x-1/2 z-50 bg-[#0F0F0F] text-white text-xs font-medium px-4 py-2.5 rounded-full shadow-2xl animate-in fade-in slide-in-from-top-3 flex items-center gap-2 max-w-sm text-center">
          <span>{toastMessage}</span>
        </div>
      )}

      {/* Main App Header */}
      {showHeader && <Header />}

      {/* Screen Router */}
      <main>
        {currentRoute === 'home' && <HomePage />}
        {currentRoute === 'item' && <ItemDetailPage />}
        {currentRoute === 'post' && <PostItemPage />}
        {currentRoute === 'requests' && <RequestsPage />}
        {currentRoute === 'chat' && <ChatPage />}
        {currentRoute === 'profile' && <ProfilePage />}
        {currentRoute === 'verify' && <VerificationPage />}
        {currentRoute === 'admin' && <AdminPage />}
      </main>

      {/* Floating Bottom Nav */}
      {showBottomNav && <BottomNav />}

      {/* Rent Request Bottom Sheet Modal */}
      {requestModalItem && (
        <RentRequestModal
          item={requestModalItem}
          onClose={closeRentModal}
        />
      )}
    </div>
  );
};
