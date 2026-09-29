import { User, RentalItem, RentalRequest, ChatMessage, PlatformSettings } from '../types';

export const INITIAL_USERS: User[] = [
  {
    id: 'user_salman',
    name: 'Salman Khan',
    phone: '0301-7894561',
    city: 'Kashmore',
    avatarUrl: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200',
    isVerified: true,
    verificationStatus: 'VERIFIED',
    cnicFront: 'https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=600',
    cnicBack: 'https://images.unsplash.com/photo-1589829545856-d10d557cf95f?w=600',
    cnicSelfie: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=400',
    itemsCount: 12,
    rating: 4.9,
    ratingCount: 23,
    totalEarned: 38000,
    isBlocked: false,
    joinedDate: 'Aug 2024'
  },
  {
    id: 'user_bilal',
    name: 'Bilal Ahmad',
    phone: '0300-1234567',
    city: 'Kashmore',
    avatarUrl: 'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=200',
    isVerified: false,
    verificationStatus: 'PENDING',
    cnicFront: 'https://images.unsplash.com/photo-1557804506-669a67965ba0?w=600',
    cnicBack: 'https://images.unsplash.com/photo-1557804506-669a67965ba0?w=600',
    cnicSelfie: 'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=400',
    itemsCount: 0,
    rating: 5.0,
    ratingCount: 4,
    totalEarned: 0,
    isBlocked: false,
    joinedDate: 'Oct 2024'
  },
  {
    id: 'user_tariq',
    name: 'Tariq Baloch',
    phone: '0333-8765432',
    city: 'Usta Muhammad',
    avatarUrl: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200',
    isVerified: true,
    verificationStatus: 'VERIFIED',
    itemsCount: 6,
    rating: 4.8,
    ratingCount: 15,
    totalEarned: 24500,
    isBlocked: false,
    joinedDate: 'Sep 2024'
  },
  {
    id: 'user_zainab',
    name: 'Zainab Noor',
    phone: '0345-9988776',
    city: 'Kandhkot',
    avatarUrl: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200',
    isVerified: true,
    verificationStatus: 'VERIFIED',
    itemsCount: 4,
    rating: 4.9,
    ratingCount: 11,
    totalEarned: 19000,
    isBlocked: false,
    joinedDate: 'Jul 2024'
  }
];

export const INITIAL_ITEMS: RentalItem[] = [
  {
    id: 'item_1',
    title: 'Sony Alpha A6400 with 16-50mm Lens - Excellent Condition',
    category: 'Camera',
    pricePerDay: 2000,
    pricePerWeek: 12000,
    deposit: 5000,
    ownerId: 'user_salman',
    ownerName: 'Salman Khan',
    ownerAvatar: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200',
    ownerPhone: '0301-7894561',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '1.2km',
    images: [
      'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800',
      'https://images.unsplash.com/photo-1502982720700-bfff97f2ecac?w=800',
      'https://images.unsplash.com/photo-1512790182412-b19e6d62bc39?w=800'
    ],
    description: 'Sony A6400 in excellent condition, bought 6 months ago from Karachi, used only for weddings, includes 1 battery, charger, original bag, 32GB card, bill available. No scratches, shutter count 2500 only.',
    specs: [
      'Brand Sony',
      'Model A6400 4K HDR',
      'Condition 9/10 (Mint)',
      'Available from tomorrow',
      'Pickup from Kashmore City Main Bus Stand'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 3
  },
  {
    id: 'item_2',
    title: 'Canon EOS 200D DSLR Camera with Kit Lens',
    category: 'Camera',
    pricePerDay: 1500,
    pricePerWeek: 9000,
    deposit: 4000,
    ownerId: 'user_salman',
    ownerName: 'Salman Khan',
    ownerAvatar: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200',
    ownerPhone: '0301-7894561',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '1.8km',
    images: [
      'https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=800',
      'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=800'
    ],
    description: 'Canon 200D with dual pixel autofocus and 18-55mm lens. Great for outdoor portraits, family wedding functions, and travel videos.',
    specs: [
      'Brand Canon',
      'Model 200D',
      'Dual Pixel CMOS AF',
      'Includes 64GB High Speed Card & Bag'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 4
  },
  {
    id: 'item_3',
    title: 'Dawlance 1.5 Ton Inverter AC (Cooling & Heating)',
    category: 'AC',
    pricePerDay: 1000,
    pricePerWeek: 6000,
    deposit: 6000,
    ownerId: 'user_tariq',
    ownerName: 'Tariq Baloch',
    ownerAvatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200',
    ownerPhone: '0333-8765432',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '2.4km',
    images: [
      'https://images.unsplash.com/photo-1621905251918-48416bd8575a?w=800'
    ],
    description: 'Dawlance Chrome 1.5 Ton T3 tropical inverter. Chills within 5 minutes. Available for summer wedding guests or temporary guest rooms in Kashmore.',
    specs: [
      'Dawlance Enercon T3',
      '1.5 Ton Capacity',
      'Low power consumption A++',
      'Remote and mounting bracket included'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 5
  },
  {
    id: 'item_4',
    title: 'Gree 1 Ton Energy Saver Split AC',
    category: 'AC',
    pricePerDay: 1200,
    pricePerWeek: 7200,
    deposit: 5000,
    ownerId: 'user_tariq',
    ownerName: 'Tariq Baloch',
    ownerAvatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200',
    ownerPhone: '0333-8765432',
    ownerVerified: true,
    location: 'Usta Muhammad',
    distance: '3.1km',
    images: [
      'https://images.unsplash.com/photo-1585338107529-13afc5f02586?w=800'
    ],
    description: 'Clean Gree Fairy series inverter AC. Spotless indoor unit, silent cooling, works on UPS/generator too.',
    specs: [
      'Brand Gree',
      '1 Ton Fast Cool',
      'Fire-proof electric box',
      'Available in Usta Muhammad city'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 6
  },
  {
    id: 'item_5',
    title: 'Royal Velvet 5-Seater Sofa Set for Events',
    category: 'Furniture',
    pricePerDay: 800,
    pricePerWeek: 4500,
    deposit: 3000,
    ownerId: 'user_zainab',
    ownerName: 'Zainab Noor',
    ownerAvatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200',
    ownerPhone: '0345-9988776',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '1.5km',
    images: [
      'https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=800',
      'https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?w=800'
    ],
    description: 'Emerald green velvet sofa set (3-seater + 2 single armchairs). Spotless, comfortable, gold metallic legs. Perfect for nikkah and engagement receptions.',
    specs: [
      '5 Seater Set',
      'Plush high density foam',
      'Matching cushions included',
      'Pickup from Kashmore or delivery arranged'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 7
  },
  {
    id: 'item_6',
    title: 'Suzuki Mehran 2019 White (Chilled AC)',
    category: 'Cars',
    pricePerDay: 2500,
    pricePerWeek: 15000,
    deposit: 10000,
    ownerId: 'user_tariq',
    ownerName: 'Tariq Baloch',
    ownerAvatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200',
    ownerPhone: '0333-8765432',
    ownerVerified: true,
    location: 'Kandhkot',
    distance: '4.2km',
    images: [
      'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800',
      'https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800'
    ],
    description: 'Suzuki Mehran VX in mint condition with working chilled air conditioner. Petrol driven, smooth suspension, brand new tubeless tyres. Valid smart card.',
    specs: [
      '2019 Model',
      'Factory Fitted AC Working',
      'Petrol 16 km/l average',
      'Original CNIC and driving license copy required'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 8
  },
  {
    id: 'item_7',
    title: 'Honda 3KV Heavy Duty Petrol Generator',
    category: 'Generators',
    pricePerDay: 1500,
    pricePerWeek: 9000,
    deposit: 8000,
    ownerId: 'user_tariq',
    ownerName: 'Tariq Baloch',
    ownerAvatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200',
    ownerPhone: '0333-8765432',
    ownerVerified: true,
    location: 'Usta Muhammad',
    distance: '2.0km',
    images: [
      'https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=800'
    ],
    description: 'Honda 3KV pure copper winding generator. Runs 1 inverter AC or lights + fans during load shedding. Essential for wedding season in Usta Muhammad & Kashmore.',
    specs: [
      '3.0 KVA Output',
      '100% Copper Winding',
      'Key Self-Start',
      '15 Liter tank (10 hours runtime)'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 9
  },
  {
    id: 'item_8',
    title: 'Bosch Professional Impact Drill Machine Kit',
    category: 'Tools',
    pricePerDay: 300,
    pricePerWeek: 1800,
    deposit: 1000,
    ownerId: 'user_salman',
    ownerName: 'Salman Khan',
    ownerAvatar: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200',
    ownerPhone: '0301-7894561',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '0.8km',
    images: [
      'https://images.unsplash.com/photo-1504148455328-c376907d081c?w=800'
    ],
    description: 'Heavy duty Bosch GSB 550W impact drill machine. Includes 20-piece concrete, wood, and metal bit set in sturdy toolbox. Perfect for DIY and home repairs.',
    specs: [
      '550W High Power',
      'Forward/Reverse Speed Control',
      'Hammer function for brick/concrete',
      'Full bit accessory box included'
    ],
    availableFrom: 'Today',
    status: 'APPROVED',
    createdAt: Date.now() - 86400000 * 10
  },
  // Items pending admin review
  {
    id: 'item_9',
    title: 'Bridal Sharara Designer Dress (Maroon & Zari)',
    category: 'Dresses',
    pricePerDay: 4500,
    pricePerWeek: 18000,
    deposit: 12000,
    ownerId: 'user_zainab',
    ownerName: 'Zainab Noor',
    ownerAvatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200',
    ownerPhone: '0345-9988776',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '1.0km',
    images: [
      'https://images.unsplash.com/photo-1566737236500-c8ac43014a67?w=800'
    ],
    description: 'Hand-embroidered pure chiffon bridal sharara with heavy dabka work. Worn once for 3 hours. Dry cleaned and ready for rental.',
    specs: [
      'Size Medium',
      'Pure Jamawar & Chiffon',
      'Complete 3-piece set with dupatta',
      'Dry Cleaned'
    ],
    availableFrom: 'Tomorrow',
    status: 'PENDING',
    createdAt: Date.now() - 3600000 * 2
  },
  {
    id: 'item_10',
    title: 'Stage Fairy Lights 100m Warm White Pack',
    category: 'Wedding',
    pricePerDay: 600,
    pricePerWeek: 3000,
    deposit: 1500,
    ownerId: 'user_salman',
    ownerName: 'Salman Khan',
    ownerAvatar: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200',
    ownerPhone: '0301-7894561',
    ownerVerified: true,
    location: 'Kashmore',
    distance: '1.3km',
    images: [
      'https://images.unsplash.com/photo-1519741497674-611481863552?w=800'
    ],
    description: 'Golden warm fairy lights for wedding stages, lawn trees, and rooftop decoration. IP65 waterproof.',
    specs: [
      '100 Meters length',
      'Warm Golden Glow',
      'Waterproof IP65',
      'Includes controller for 8 lighting modes'
    ],
    availableFrom: 'Today',
    status: 'PENDING',
    createdAt: Date.now() - 3600000 * 3
  }
];

export const INITIAL_REQUESTS: RentalRequest[] = [
  {
    id: 'req_101',
    itemId: 'item_1',
    itemTitle: 'Sony Alpha A6400 with 16-50mm Lens - Excellent Condition',
    itemImage: 'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800',
    pricePerDay: 2000,
    ownerId: 'user_salman',
    ownerName: 'Salman Khan',
    ownerPhone: '0301-7894561',
    renterId: 'user_bilal',
    renterName: 'Bilal Ahmad',
    renterPhone: '0300-1234567',
    startDate: '15 Nov',
    endDate: '16 Nov',
    daysCount: 2,
    rentAmount: 4000,
    serviceFee: 400, // 10%
    depositAmount: 5000,
    totalAmount: 9400,
    message: 'Hi Salman, I need camera for my sister wedding on 15-16 Nov, will take care of everything like my own item.',
    status: 'ACCEPTED',
    createdAt: Date.now() - 86400000 * 1
  }
];

export const INITIAL_CHATS: Record<string, ChatMessage[]> = {
  'req_101': [
    {
      id: 'msg_1',
      requestId: 'req_101',
      senderId: 'user_bilal',
      senderName: 'Bilal Ahmad',
      text: 'Salam Salman bhai! Is the Sony A6400 camera with battery charger available for 15-16 Nov?',
      timestamp: Date.now() - 3600000 * 5,
      isSystem: false
    },
    {
      id: 'msg_2',
      requestId: 'req_101',
      senderId: 'system',
      senderName: 'Rentify System',
      text: 'Salman accepted your rent request • Contact info unlocked',
      timestamp: Date.now() - 3600000 * 4,
      isSystem: true
    },
    {
      id: 'msg_3',
      requestId: 'req_101',
      senderId: 'user_salman',
      senderName: 'Salman Khan',
      text: 'Walaikum Assalam Bilal! Yes, it is fully charged with 32GB high-speed card. We can meet at Kashmore Main Bus Stand near City Chowk.',
      timestamp: Date.now() - 3600000 * 3,
      isSystem: false
    },
    {
      id: 'msg_4',
      requestId: 'req_101',
      senderId: 'user_bilal',
      senderName: 'Bilal Ahmad',
      text: 'Great! I have Rs. 4,000 rent and Rs. 5,000 security deposit cash ready. See you at 4 PM.',
      timestamp: Date.now() - 3600000 * 2,
      isSystem: false
    },
    {
      id: 'msg_5',
      requestId: 'req_101',
      senderId: 'user_salman',
      senderName: 'Salman Khan',
      text: 'Perfect! Please bring your original CNIC copy for verification. Safe handover.',
      timestamp: Date.now() - 3600000 * 1,
      isSystem: false
    }
  ]
};

export const INITIAL_SETTINGS: PlatformSettings = {
  commissionRate: 10,
  adminEmail: 'admin@rentify.pk',
  maintenanceMode: false
};
