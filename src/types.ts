export interface User {
  id: string;
  name: string;
  phone: string;
  city: string;
  avatarUrl: string;
  isVerified: boolean;
  verificationStatus: 'NONE' | 'PENDING' | 'VERIFIED' | 'REJECTED';
  cnicFront?: string;
  cnicBack?: string;
  cnicSelfie?: string;
  itemsCount: number;
  rating: number;
  ratingCount: number;
  totalEarned: number;
  isBlocked: boolean;
  joinedDate: string;
}

export interface RentalItem {
  id: string;
  title: string;
  category: 'Camera' | 'AC' | 'Furniture' | 'Cars' | 'Tools' | 'Generators' | 'Wedding' | 'Dresses';
  pricePerDay: number;
  pricePerWeek: number;
  deposit: number;
  ownerId: string;
  ownerName: string;
  ownerAvatar: string;
  ownerPhone: string;
  ownerVerified: boolean;
  location: string;
  distance: string;
  images: string[];
  description: string;
  specs: string[];
  availableFrom: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  createdAt: number;
}

export interface RentalRequest {
  id: string;
  itemId: string;
  itemTitle: string;
  itemImage: string;
  pricePerDay: number;
  ownerId: string;
  ownerName: string;
  ownerPhone: string;
  renterId: string;
  renterName: string;
  renterPhone: string;
  startDate: string;
  endDate: string;
  daysCount: number;
  rentAmount: number;
  serviceFee: number; // 10%
  depositAmount: number;
  totalAmount: number;
  message: string;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  createdAt: number;
}

export interface ChatMessage {
  id: string;
  requestId: string;
  senderId: string;
  senderName: string;
  text: string;
  timestamp: number;
  isSystem?: boolean;
}

export interface PlatformSettings {
  commissionRate: number; // default 10%
  adminEmail: string;
  maintenanceMode: boolean;
}
