import React, { createContext, useContext, useState, useEffect } from 'react';
import { User, RentalItem, RentalRequest, ChatMessage, PlatformSettings } from '../types';
import { INITIAL_USERS, INITIAL_ITEMS, INITIAL_REQUESTS, INITIAL_CHATS, INITIAL_SETTINGS } from '../data/mockData';

interface RentifyContextType {
  items: RentalItem[];
  approvedItems: RentalItem[];
  users: User[];
  requests: RentalRequest[];
  chats: Record<string, ChatMessage[]>;
  settings: PlatformSettings;
  currentUser: User;
  currentCity: string;
  searchQuery: string;
  selectedCategory: string;
  currentRoute: string;
  activeItemId: string | null;
  activeRequestId: string | null;
  requestModalItem: RentalItem | null;
  toastMessage: string | null;

  setCurrentCity: (city: string) => void;
  setSearchQuery: (query: string) => void;
  setSelectedCategory: (cat: string) => void;
  navigateTo: (route: string, id?: string) => void;
  switchUser: (userId: string) => void;
  openRentModal: (item: RentalItem) => void;
  closeRentModal: () => void;
  submitRentRequest: (
    item: RentalItem,
    days: number,
    startDate: string,
    endDate: string,
    message: string
  ) => string;
  updateRequestStatus: (requestId: string, status: 'ACCEPTED' | 'REJECTED') => void;
  sendChatMessage: (requestId: string, text: string) => void;
  postNewItem: (
    title: string,
    category: RentalItem['category'],
    pricePerDay: number,
    deposit: number,
    location: string,
    description: string,
    specs: string[],
    images: string[]
  ) => void;
  approveItem: (itemId: string) => void;
  rejectItem: (itemId: string) => void;
  verifyUser: (userId: string, verified: boolean) => void;
  toggleBlockUser: (userId: string) => void;
  submitCnicVerification: (front: string, back: string, selfie: string) => void;
  updateSettings: (rate: number, email: string, maintenance: boolean) => void;
  showToast: (msg: string) => void;
}

const RentifyContext = createContext<RentifyContextType | undefined>(undefined);

export const RentifyProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  // Load from LocalStorage or seed defaults
  const [users, setUsers] = useState<User[]>(() => {
    const saved = localStorage.getItem('rentify_users');
    return saved ? JSON.parse(saved) : INITIAL_USERS;
  });

  const [items, setItems] = useState<RentalItem[]>(() => {
    const saved = localStorage.getItem('rentify_items');
    return saved ? JSON.parse(saved) : INITIAL_ITEMS;
  });

  const [requests, setRequests] = useState<RentalRequest[]>(() => {
    const saved = localStorage.getItem('rentify_requests');
    return saved ? JSON.parse(saved) : INITIAL_REQUESTS;
  });

  const [chats, setChats] = useState<Record<string, ChatMessage[]>>(() => {
    const saved = localStorage.getItem('rentify_chats');
    return saved ? JSON.parse(saved) : INITIAL_CHATS;
  });

  const [settings, setSettings] = useState<PlatformSettings>(() => {
    const saved = localStorage.getItem('rentify_settings');
    return saved ? JSON.parse(saved) : INITIAL_SETTINGS;
  });

  const [currentUserId, setCurrentUserId] = useState<string>('user_bilal'); // Bilal (renter) by default
  const [currentCity, setCurrentCity] = useState<string>('Kashmore');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [selectedCategory, setSelectedCategory] = useState<string>('All');

  // Navigation route handling: 'home', 'item', 'post', 'requests', 'chat', 'profile', 'verify', 'admin'
  const [currentRoute, setCurrentRoute] = useState<string>('home');
  const [activeItemId, setActiveItemId] = useState<string | null>(null);
  const [activeRequestId, setActiveRequestId] = useState<string | null>(null);
  const [requestModalItem, setRequestModalItem] = useState<RentalItem | null>(null);
  const [toastMessage, setToastMessage] = useState<string | null>(null);

  // Sync to LocalStorage
  useEffect(() => {
    localStorage.setItem('rentify_users', JSON.stringify(users));
  }, [users]);

  useEffect(() => {
    localStorage.setItem('rentify_items', JSON.stringify(items));
  }, [items]);

  useEffect(() => {
    localStorage.setItem('rentify_requests', JSON.stringify(requests));
  }, [requests]);

  useEffect(() => {
    localStorage.setItem('rentify_chats', JSON.stringify(chats));
  }, [chats]);

  useEffect(() => {
    localStorage.setItem('rentify_settings', JSON.stringify(settings));
  }, [settings]);

  const currentUser = users.find((u) => u.id === currentUserId) || users[0];
  const approvedItems = items.filter((item) => item.status === 'APPROVED');

  const showToast = (msg: string) => {
    setToastMessage(msg);
    setTimeout(() => setToastMessage(null), 3500);
  };

  const navigateTo = (route: string, id?: string) => {
    if (route === 'item' && id) {
      setActiveItemId(id);
    } else if (route === 'chat' && id) {
      setActiveRequestId(id);
    }
    setCurrentRoute(route);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const switchUser = (userId: string) => {
    setCurrentUserId(userId);
    const target = users.find((u) => u.id === userId);
    showToast(`Switched account to: ${target?.name || 'User'} (${userId === 'user_salman' ? 'Camera Owner' : 'Renter'})`);
  };

  const openRentModal = (item: RentalItem) => {
    setRequestModalItem(item);
  };

  const closeRentModal = () => {
    setRequestModalItem(null);
  };

  const submitRentRequest = (
    item: RentalItem,
    days: number,
    startDate: string,
    endDate: string,
    message: string
  ): string => {
    const rentAmount = item.pricePerDay * days;
    const serviceFee = Math.round(rentAmount * (settings.commissionRate / 100));
    const depositAmount = item.deposit;
    const totalAmount = rentAmount + serviceFee + depositAmount;

    const newRequestId = `req_${Date.now()}`;
    const newRequest: RentalRequest = {
      id: newRequestId,
      itemId: item.id,
      itemTitle: item.title,
      itemImage: item.images[0] || '',
      pricePerDay: item.pricePerDay,
      ownerId: item.ownerId,
      ownerName: item.ownerName,
      ownerPhone: item.ownerPhone,
      renterId: currentUser.id,
      renterName: currentUser.name,
      renterPhone: currentUser.phone,
      startDate,
      endDate,
      daysCount: days,
      rentAmount,
      serviceFee,
      depositAmount,
      totalAmount,
      message,
      status: 'PENDING',
      createdAt: Date.now()
    };

    setRequests((prev) => [newRequest, ...prev]);
    setRequestModalItem(null);
    showToast(`Rent request sent to ${item.ownerName}! Track in Requests.`);
    return newRequestId;
  };

  const updateRequestStatus = (requestId: string, status: 'ACCEPTED' | 'REJECTED') => {
    setRequests((prev) =>
      prev.map((req) => (req.id === requestId ? { ...req, status } : req))
    );

    if (status === 'ACCEPTED') {
      // Add initial chat system unlock message
      const sysMsg: ChatMessage = {
        id: `msg_sys_${Date.now()}`,
        requestId,
        senderId: 'system',
        senderName: 'Rentify System',
        text: 'Owner accepted your rent request • Contact numbers unlocked for handover meet',
        timestamp: Date.now(),
        isSystem: true
      };
      setChats((prev) => ({
        ...prev,
        [requestId]: prev[requestId] ? [...prev[requestId], sysMsg] : [sysMsg]
      }));
      showToast('Request accepted! Phone number shared with renter.');
    } else {
      showToast('Request declined.');
    }
  };

  const sendChatMessage = (requestId: string, text: string) => {
    if (!text.trim()) return;
    const newMsg: ChatMessage = {
      id: `msg_${Date.now()}`,
      requestId,
      senderId: currentUser.id,
      senderName: currentUser.name,
      text: text.trim(),
      timestamp: Date.now(),
      isSystem: false
    };

    setChats((prev) => ({
      ...prev,
      [requestId]: [...(prev[requestId] || []), newMsg]
    }));
  };

  const postNewItem = (
    title: string,
    category: RentalItem['category'],
    pricePerDay: number,
    deposit: number,
    location: string,
    description: string,
    specs: string[],
    images: string[]
  ) => {
    const newItem: RentalItem = {
      id: `item_${Date.now()}`,
      title,
      category,
      pricePerDay,
      pricePerWeek: pricePerDay * 6,
      deposit,
      ownerId: currentUser.id,
      ownerName: currentUser.name,
      ownerAvatar: currentUser.avatarUrl,
      ownerPhone: currentUser.phone,
      ownerVerified: currentUser.isVerified,
      location: location || currentCity,
      distance: '0.8km',
      images: images.length > 0 ? images : ['https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800'],
      description,
      specs: specs.length > 0 ? specs : ['Condition 9/10', 'Verified Owner', 'Available for rent in Kashmore'],
      availableFrom: 'Today',
      status: 'PENDING',
      createdAt: Date.now()
    };

    setItems((prev) => [newItem, ...prev]);

    // Update user items count
    setUsers((prev) =>
      prev.map((u) => (u.id === currentUser.id ? { ...u, itemsCount: u.itemsCount + 1 } : u))
    );

    showToast('Submitted! Admin will review in 2 hours. Will be live after approval.');
    navigateTo('home');
  };

  const approveItem = (itemId: string) => {
    setItems((prev) =>
      prev.map((it) => (it.id === itemId ? { ...it, status: 'APPROVED' } : it))
    );
    showToast('Item approved! Now live on marketplace.');
  };

  const rejectItem = (itemId: string) => {
    setItems((prev) =>
      prev.map((it) => (it.id === itemId ? { ...it, status: 'REJECTED' } : it))
    );
    showToast('Item marked as rejected.');
  };

  const verifyUser = (userId: string, verified: boolean) => {
    setUsers((prev) =>
      prev.map((u) =>
        u.id === userId
          ? {
              ...u,
              isVerified: verified,
              verificationStatus: verified ? 'VERIFIED' : 'REJECTED'
            }
          : u
      )
    );
    // Also update ownerVerified flag on user's items
    setItems((prev) =>
      prev.map((it) => (it.ownerId === userId ? { ...it, ownerVerified: verified } : it))
    );
    showToast(verified ? 'User verified with Blue Tick!' : 'Verification revoked.');
  };

  const toggleBlockUser = (userId: string) => {
    setUsers((prev) =>
      prev.map((u) => (u.id === userId ? { ...u, isBlocked: !u.isBlocked } : u))
    );
    showToast('User account status updated.');
  };

  const submitCnicVerification = (front: string, back: string, selfie: string) => {
    setUsers((prev) =>
      prev.map((u) =>
        u.id === currentUser.id
          ? {
              ...u,
              verificationStatus: 'PENDING',
              cnicFront: front,
              cnicBack: back,
              cnicSelfie: selfie
            }
          : u
      )
    );
    showToast('CNIC submitted! Our Kashmore admin will verify and activate Blue Tick.');
    navigateTo('profile');
  };

  const updateSettings = (rate: number, email: string, maintenance: boolean) => {
    setSettings({
      commissionRate: rate,
      adminEmail: email,
      maintenanceMode: maintenance
    });
    showToast('Admin settings saved successfully.');
  };

  return (
    <RentifyContext.Provider
      value={{
        items,
        approvedItems,
        users,
        requests,
        chats,
        settings,
        currentUser,
        currentCity,
        searchQuery,
        selectedCategory,
        currentRoute,
        activeItemId,
        activeRequestId,
        requestModalItem,
        toastMessage,
        setCurrentCity,
        setSearchQuery,
        setSelectedCategory,
        navigateTo,
        switchUser,
        openRentModal,
        closeRentModal,
        submitRentRequest,
        updateRequestStatus,
        sendChatMessage,
        postNewItem,
        approveItem,
        rejectItem,
        verifyUser,
        toggleBlockUser,
        submitCnicVerification,
        updateSettings,
        showToast
      }}
    >
      {children}
    </RentifyContext.Provider>
  );
};

export const useRentify = () => {
  const context = useContext(RentifyContext);
  if (!context) throw new Error('useRentify must be used within RentifyProvider');
  return context;
};
