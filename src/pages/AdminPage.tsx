import React, { useState } from 'react';
import {
  LayoutDashboard,
  Package,
  Users,
  Handshake,
  ShieldCheck,
  Settings,
  LogOut,
  Check,
  X,
  ArrowLeft,
  Lock,
  Mail,
  AlertCircle
} from 'lucide-react';
import { useRentify } from '../context/RentifyContext';

export const AdminPage: React.FC = () => {
  const {
    items,
    users,
    requests,
    settings,
    approveItem,
    rejectItem,
    verifyUser,
    toggleBlockUser,
    updateSettings,
    navigateTo,
    showToast
  } = useRentify();

  // Admin Authentication State
  const [isAdminLoggedIn, setIsAdminLoggedIn] = useState<boolean>(() => {
    return localStorage.getItem('admin_token') === 'rentify_authenticated';
  });

  const [adminEmail, setAdminEmail] = useState('admin@rentify.pk');
  const [adminPass, setAdminPass] = useState('admin123');
  const [loginError, setLoginError] = useState('');

  // Active Admin Sub-page
  const [activeTab, setActiveTab] = useState<'dashboard' | 'items' | 'users' | 'requests' | 'verifications' | 'settings'>('dashboard');
  const [itemFilter, setItemFilter] = useState<'ALL' | 'PENDING' | 'APPROVED' | 'REJECTED'>('PENDING');

  // Settings local state
  const [localCommission, setLocalCommission] = useState(settings.commissionRate);
  const [localEmail, setLocalEmail] = useState(settings.adminEmail);
  const [localMaintenance, setLocalMaintenance] = useState(settings.maintenanceMode);

  const pendingItems = items.filter((i) => i.status === 'PENDING');
  const pendingVerifications = users.filter((u) => u.verificationStatus === 'PENDING');
  const totalCommission = requests
    .filter((r) => r.status === 'ACCEPTED')
    .reduce((sum, r) => sum + r.serviceFee, 0);

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    if (adminEmail === 'admin@rentify.pk' && adminPass === 'admin123') {
      localStorage.setItem('admin_token', 'rentify_authenticated');
      setIsAdminLoggedIn(true);
      setLoginError('');
      showToast('Welcome Admin to Rentify.pk');
    } else {
      setLoginError('Invalid credentials. Use admin@rentify.pk / admin123');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('admin_token');
    setIsAdminLoggedIn(false);
    showToast('Logged out of Admin Portal');
  };

  // 1. ADMIN LOGIN VIEW (if not logged in)
  if (!isAdminLoggedIn) {
    return (
      <div className="min-h-screen bg-[#0F0F0F] flex items-center justify-center p-4">
        <div className="w-full max-w-sm bg-white rounded-[24px] p-8 shadow-2xl relative">
          <button
            onClick={() => navigateTo('home')}
            className="absolute top-5 left-5 text-xs text-[#71717A] hover:text-[#0F0F0F] flex items-center gap-1"
          >
            <ArrowLeft className="w-3.5 h-3.5" /> Back
          </button>

          <div className="text-center mt-4">
            <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
              Admin • rentify<span className="text-[#635BFF]">.</span>
            </h2>
            <p className="text-xs text-[#71717A] mt-1">
              Secret access for platform control
            </p>
          </div>

          <form onSubmit={handleLogin} className="mt-6 space-y-4">
            {loginError && (
              <div className="p-3 bg-[#FEE2E2] text-[#991B1B] text-xs rounded-xl flex items-center gap-2">
                <AlertCircle className="w-4 h-4 shrink-0" />
                <span>{loginError}</span>
              </div>
            )}

            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                Admin Email
              </label>
              <div className="relative">
                <Mail className="absolute left-3.5 top-1/2 -translate-y-1/2 w-4 h-4 text-[#71717A]" />
                <input
                  type="email"
                  value={adminEmail}
                  onChange={(e) => setAdminEmail(e.target.value)}
                  className="w-full h-11 pl-10 pr-4 rounded-full bg-[#F4F4F5] text-xs text-[#0F0F0F] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                  required
                />
              </div>
            </div>

            <div>
              <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                Password
              </label>
              <div className="relative">
                <Lock className="absolute left-3.5 top-1/2 -translate-y-1/2 w-4 h-4 text-[#71717A]" />
                <input
                  type="password"
                  value={adminPass}
                  onChange={(e) => setAdminPass(e.target.value)}
                  className="w-full h-11 pl-10 pr-4 rounded-full bg-[#F4F4F5] text-xs text-[#0F0F0F] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                  required
                />
              </div>
              <p className="text-[10px] text-[#71717A] mt-1">
                Demo credentials pre-filled (admin@rentify.pk / admin123)
              </p>
            </div>

            <button
              type="submit"
              className="w-full h-12 rounded-full bg-[#0F0F0F] text-white text-xs font-semibold hover:opacity-95 transition mt-2 shadow-md"
            >
              Sign In to Dashboard
            </button>
          </form>
        </div>
      </div>
    );
  }

  // 2. ADMIN MAIN DASHBOARD
  return (
    <div className="min-h-screen bg-[#0F0F0F] text-white flex flex-col md:flex-row">
      {/* Sidebar Desktop 260px */}
      <aside className="w-full md:w-64 bg-[#0F0F0F] border-b md:border-b-0 md:border-r border-[#27272A] p-5 shrink-0 flex flex-col justify-between">
        <div>
          {/* Logo */}
          <div className="flex items-center justify-between">
            <span className="text-xl font-bold tracking-tight text-white">
              rentify<span className="text-[#635BFF]">.</span> <span className="text-xs font-normal text-zinc-400">admin</span>
            </span>
            <button
              onClick={() => navigateTo('home')}
              className="text-xs text-zinc-400 hover:text-white flex items-center gap-1 md:hidden"
            >
              <ArrowLeft className="w-3.5 h-3.5" /> Back
            </button>
          </div>

          {/* Menu Items */}
          <nav className="mt-8 space-y-1.5 flex md:flex-col overflow-x-auto no-scrollbar pb-2 md:pb-0">
            <button
              onClick={() => setActiveTab('dashboard')}
              className={`w-full flex items-center gap-3 px-4 py-2.5 rounded-full text-xs font-medium transition whitespace-nowrap ${
                activeTab === 'dashboard'
                  ? 'bg-white text-[#0F0F0F] font-semibold'
                  : 'text-zinc-400 hover:text-white hover:bg-[#27272A]'
              }`}
            >
              <LayoutDashboard className="w-4 h-4" />
              <span>Dashboard</span>
            </button>

            <button
              onClick={() => setActiveTab('items')}
              className={`w-full flex items-center justify-between px-4 py-2.5 rounded-full text-xs font-medium transition whitespace-nowrap ${
                activeTab === 'items'
                  ? 'bg-white text-[#0F0F0F] font-semibold'
                  : 'text-zinc-400 hover:text-white hover:bg-[#27272A]'
              }`}
            >
              <div className="flex items-center gap-3">
                <Package className="w-4 h-4" />
                <span>Items Review</span>
              </div>
              {pendingItems.length > 0 && (
                <span className="w-5 h-5 rounded-full bg-[#EF4444] text-white text-[10px] font-bold flex items-center justify-center">
                  {pendingItems.length}
                </span>
              )}
            </button>

            <button
              onClick={() => setActiveTab('users')}
              className={`w-full flex items-center gap-3 px-4 py-2.5 rounded-full text-xs font-medium transition whitespace-nowrap ${
                activeTab === 'users'
                  ? 'bg-white text-[#0F0F0F] font-semibold'
                  : 'text-zinc-400 hover:text-white hover:bg-[#27272A]'
              }`}
            >
              <Users className="w-4 h-4" />
              <span>Users</span>
            </button>

            <button
              onClick={() => setActiveTab('requests')}
              className={`w-full flex items-center gap-3 px-4 py-2.5 rounded-full text-xs font-medium transition whitespace-nowrap ${
                activeTab === 'requests'
                  ? 'bg-white text-[#0F0F0F] font-semibold'
                  : 'text-zinc-400 hover:text-white hover:bg-[#27272A]'
              }`}
            >
              <Handshake className="w-4 h-4" />
              <span>Requests & Profit</span>
            </button>

            <button
              onClick={() => setActiveTab('verifications')}
              className={`w-full flex items-center justify-between px-4 py-2.5 rounded-full text-xs font-medium transition whitespace-nowrap ${
                activeTab === 'verifications'
                  ? 'bg-white text-[#0F0F0F] font-semibold'
                  : 'text-zinc-400 hover:text-white hover:bg-[#27272A]'
              }`}
            >
              <div className="flex items-center gap-3">
                <ShieldCheck className="w-4 h-4" />
                <span>CNIC Verifications</span>
              </div>
              {pendingVerifications.length > 0 && (
                <span className="w-5 h-5 rounded-full bg-[#635BFF] text-white text-[10px] font-bold flex items-center justify-center">
                  {pendingVerifications.length}
                </span>
              )}
            </button>

            <button
              onClick={() => setActiveTab('settings')}
              className={`w-full flex items-center gap-3 px-4 py-2.5 rounded-full text-xs font-medium transition whitespace-nowrap ${
                activeTab === 'settings'
                  ? 'bg-white text-[#0F0F0F] font-semibold'
                  : 'text-zinc-400 hover:text-white hover:bg-[#27272A]'
              }`}
            >
              <Settings className="w-4 h-4" />
              <span>Settings</span>
            </button>
          </nav>
        </div>

        <div className="pt-4 border-t border-[#27272A] hidden md:block">
          <button
            onClick={() => navigateTo('home')}
            className="w-full mb-2 flex items-center gap-3 px-4 py-2 text-xs text-zinc-400 hover:text-white transition"
          >
            <ArrowLeft className="w-4 h-4" />
            <span>Return to App</span>
          </button>
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-4 py-2 text-xs text-red-400 hover:text-red-300 transition"
          >
            <LogOut className="w-4 h-4" />
            <span>Logout</span>
          </button>
        </div>
      </aside>

      {/* Main Content Area */}
      <main className="flex-1 bg-[#FAFAFA] text-[#0F0F0F] p-5 md:p-8 overflow-y-auto">
        {/* TAB 1: OVERVIEW DASHBOARD */}
        {activeTab === 'dashboard' && (
          <div className="max-w-5xl mx-auto space-y-6">
            <div>
              <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
                Overview
              </h2>
              <p className="text-xs text-[#71717A]">
                Rentify.pk Balochistan & Sindh Marketplace Metrics
              </p>
            </div>

            {/* 4 Stats Cards */}
            <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
              <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-5 shadow-soft">
                <div className="text-xs font-medium text-[#71717A]">Total Users</div>
                <div className="text-2xl font-bold text-[#0F0F0F] mt-1">{users.length}</div>
                <div className="text-[11px] text-[#71717A] mt-1">Kashmore, Usta Muhammad</div>
              </div>

              <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-5 shadow-soft">
                <div className="text-xs font-medium text-[#71717A]">Total Items</div>
                <div className="text-2xl font-bold text-[#0F0F0F] mt-1">{items.length}</div>
                <div className="text-[11px] text-[#71717A] mt-1">{items.filter((i) => i.status === 'APPROVED').length} Live now</div>
              </div>

              <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-5 shadow-soft relative">
                <div className="text-xs font-medium text-[#71717A]">Pending Review</div>
                <div className="text-2xl font-bold text-[#EF4444] mt-1 flex items-center gap-2">
                  <span>{pendingItems.length}</span>
                  {pendingItems.length > 0 && (
                    <span className="w-2.5 h-2.5 rounded-full bg-[#EF4444] animate-pulse" />
                  )}
                </div>
                <div className="text-[11px] text-[#71717A] mt-1">Requires 1-click approval</div>
              </div>

              <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-5 shadow-soft">
                <div className="text-xs font-medium text-[#71717A]">10% Commission</div>
                <div className="text-2xl font-bold text-[#00C950] mt-1">
                  Rs. {totalCommission.toLocaleString()}
                </div>
                <div className="text-[11px] text-[#71717A] mt-1">Platform earned profit</div>
              </div>
            </div>

            {/* Pending Items Approvals */}
            <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-5 shadow-soft">
              <div className="flex items-center justify-between mb-4">
                <h3 className="text-base font-bold text-[#0F0F0F]">
                  Items Awaiting Approval ({pendingItems.length})
                </h3>
                <button
                  onClick={() => setActiveTab('items')}
                  className="text-xs text-[#635BFF] font-semibold hover:underline"
                >
                  View all items →
                </button>
              </div>

              {pendingItems.length === 0 ? (
                <div className="text-center py-8 text-xs text-[#71717A]">
                  All items are approved! Marketplace is clean and active.
                </div>
              ) : (
                <div className="space-y-3">
                  {pendingItems.map((item) => (
                    <div
                      key={item.id}
                      className="p-3.5 rounded-2xl bg-[#FAFAFA] border border-[#F0F0F0] flex items-center justify-between gap-3"
                    >
                      <div className="flex items-center gap-3 min-w-0">
                        <img
                          src={item.images[0]}
                          alt={item.title}
                          className="w-12 h-12 rounded-xl object-cover shrink-0"
                        />
                        <div className="min-w-0">
                          <h4 className="text-xs font-bold text-[#0F0F0F] truncate">
                            {item.title}
                          </h4>
                          <p className="text-[11px] text-[#71717A] truncate">
                            {item.category} • Rs. {item.pricePerDay}/day • Owner: {item.ownerName} ({item.location})
                          </p>
                        </div>
                      </div>

                      <div className="flex items-center gap-2 shrink-0">
                        <button
                          onClick={() => rejectItem(item.id)}
                          className="px-3 py-1.5 rounded-full border border-[#E4E4E7] text-xs font-semibold text-[#71717A] hover:bg-[#F4F4F5] transition"
                        >
                          Reject
                        </button>
                        <button
                          onClick={() => approveItem(item.id)}
                          className="px-4 py-1.5 rounded-full bg-[#0F0F0F] text-white text-xs font-semibold hover:scale-[0.98] transition"
                        >
                          Approve
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        )}

        {/* TAB 2: ITEMS REVIEW (MOST IMPORTANT) */}
        {activeTab === 'items' && (
          <div className="max-w-5xl mx-auto space-y-5">
            <div className="flex items-center justify-between">
              <div>
                <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
                  Items Management
                </h2>
                <p className="text-xs text-[#71717A]">
                  Approve, reject, or monitor items live on Rentify
                </p>
              </div>

              {/* Filter Tabs */}
              <div className="flex items-center gap-1.5 p-1 bg-[#F4F4F5] rounded-full text-xs font-medium">
                {(['ALL', 'PENDING', 'APPROVED', 'REJECTED'] as const).map((status) => (
                  <button
                    key={status}
                    onClick={() => setItemFilter(status)}
                    className={`px-3 py-1 rounded-full transition ${
                      itemFilter === status
                        ? 'bg-[#0F0F0F] text-white font-semibold'
                        : 'text-[#71717A] hover:text-[#0F0F0F]'
                    }`}
                  >
                    {status}
                  </button>
                ))}
              </div>
            </div>

            <div className="space-y-3">
              {items
                .filter((i) => itemFilter === 'ALL' || i.status === itemFilter)
                .map((item) => (
                  <div
                    key={item.id}
                    className="bg-white border border-[#F0F0F0] rounded-2xl p-4 shadow-soft flex flex-col sm:flex-row sm:items-center justify-between gap-4"
                  >
                    <div className="flex items-center gap-3 min-w-0">
                      <img
                        src={item.images[0]}
                        alt={item.title}
                        className="w-14 h-14 rounded-xl object-cover shrink-0"
                      />
                      <div className="min-w-0">
                        <div className="flex items-center gap-2">
                          <h4 className="text-sm font-semibold text-[#0F0F0F] truncate">
                            {item.title}
                          </h4>
                          <span
                            className={`text-[10px] font-bold px-2 py-0.5 rounded-full ${
                              item.status === 'APPROVED'
                                ? 'bg-[#DCFCE7] text-[#166534]'
                                : item.status === 'REJECTED'
                                ? 'bg-[#FEE2E2] text-[#991B1B]'
                                : 'bg-[#FEF3C7] text-[#92400E]'
                            }`}
                          >
                            {item.status}
                          </span>
                        </div>
                        <p className="text-xs text-[#71717A] mt-0.5">
                          {item.category} • Rs. {item.pricePerDay.toLocaleString()}/day • Deposit: Rs. {item.deposit.toLocaleString()}
                        </p>
                        <p className="text-xs text-[#71717A]">
                          Owner: {item.ownerName} ({item.ownerPhone}) • 📍 {item.location}
                        </p>
                      </div>
                    </div>

                    <div className="flex items-center gap-2 shrink-0 self-end sm:self-center">
                      {item.status !== 'APPROVED' && (
                        <button
                          onClick={() => approveItem(item.id)}
                          className="px-4 py-2 rounded-full bg-[#0F0F0F] text-white text-xs font-semibold hover:scale-[0.98] transition"
                        >
                          Approve
                        </button>
                      )}
                      {item.status !== 'REJECTED' && (
                        <button
                          onClick={() => rejectItem(item.id)}
                          className="px-4 py-2 rounded-full border border-[#E4E4E7] text-xs font-semibold text-[#71717A] hover:bg-[#F4F4F5] transition"
                        >
                          Reject
                        </button>
                      )}
                    </div>
                  </div>
                ))}
            </div>
          </div>
        )}

        {/* TAB 3: USERS */}
        {activeTab === 'users' && (
          <div className="max-w-5xl mx-auto space-y-5">
            <div>
              <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
                Users Management
              </h2>
              <p className="text-xs text-[#71717A]">
                Verify CNIC blue tick badges and monitor user accounts
              </p>
            </div>

            <div className="bg-white border border-[#F0F0F0] rounded-[20px] overflow-hidden shadow-soft">
              <table className="w-full text-left text-xs">
                <thead className="bg-[#F4F4F5] text-[#71717A] font-medium border-b border-[#F0F0F0]">
                  <tr>
                    <th className="p-3.5">User</th>
                    <th className="p-3.5">City</th>
                    <th className="p-3.5">Phone</th>
                    <th className="p-3.5">Items</th>
                    <th className="p-3.5">Verification</th>
                    <th className="p-3.5 text-right">Actions</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-[#F0F0F0]">
                  {users.map((u) => (
                    <tr key={u.id} className="hover:bg-[#FAFAFA]">
                      <td className="p-3.5 flex items-center gap-2.5">
                        <img
                          src={u.avatarUrl}
                          alt=""
                          className="w-8 h-8 rounded-full object-cover"
                        />
                        <div>
                          <div className="font-semibold text-[#0F0F0F] flex items-center gap-1">
                            <span>{u.name}</span>
                            {u.isVerified && (
                              <div className="w-3 h-3 rounded-full bg-[#0095F6] flex items-center justify-center text-white">
                                <Check className="w-2 h-2 stroke-[3]" />
                              </div>
                            )}
                          </div>
                          <div className="text-[10px] text-[#71717A]">ID: {u.id}</div>
                        </div>
                      </td>
                      <td className="p-3.5 text-[#0F0F0F] font-medium">{u.city}</td>
                      <td className="p-3.5 text-[#71717A]">{u.phone}</td>
                      <td className="p-3.5 font-semibold text-[#0F0F0F]">{u.itemsCount}</td>
                      <td className="p-3.5">
                        <span
                          className={`text-[10px] font-bold px-2 py-0.5 rounded-full ${
                            u.isVerified
                              ? 'bg-[#E0F2FE] text-[#0369A1]'
                              : u.verificationStatus === 'PENDING'
                              ? 'bg-[#FEF3C7] text-[#92400E]'
                              : 'bg-[#F4F4F5] text-[#71717A]'
                          }`}
                        >
                          {u.isVerified ? 'Verified' : u.verificationStatus}
                        </span>
                      </td>
                      <td className="p-3.5 text-right space-x-2">
                        <button
                          onClick={() => verifyUser(u.id, !u.isVerified)}
                          className={`px-3 py-1 rounded-full text-[11px] font-semibold transition ${
                            u.isVerified
                              ? 'border border-[#E4E4E7] text-[#71717A] hover:bg-[#F4F4F5]'
                              : 'bg-[#0095F6] text-white hover:opacity-95'
                          }`}
                        >
                          {u.isVerified ? 'Revoke' : 'Verify'}
                        </button>
                        <button
                          onClick={() => toggleBlockUser(u.id)}
                          className={`px-2.5 py-1 rounded-full text-[11px] font-semibold transition ${
                            u.isBlocked
                              ? 'bg-[#0F0F0F] text-white'
                              : 'text-[#EF4444] hover:bg-[#FEE2E2]'
                          }`}
                        >
                          {u.isBlocked ? 'Unblock' : 'Block'}
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* TAB 4: REQUESTS & COMMISSION */}
        {activeTab === 'requests' && (
          <div className="max-w-5xl mx-auto space-y-5">
            <div>
              <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
                Transactions & Commission
              </h2>
              <p className="text-xs text-[#71717A]">
                10% commission charged on completed handovers
              </p>
            </div>

            <div className="bg-white border border-[#F0F0F0] rounded-[20px] overflow-hidden shadow-soft">
              <table className="w-full text-left text-xs">
                <thead className="bg-[#F4F4F5] text-[#71717A] font-medium border-b border-[#F0F0F0]">
                  <tr>
                    <th className="p-3.5">Item</th>
                    <th className="p-3.5">Owner</th>
                    <th className="p-3.5">Renter</th>
                    <th className="p-3.5">Duration</th>
                    <th className="p-3.5">Rent</th>
                    <th className="p-3.5">Rentify 10%</th>
                    <th className="p-3.5">Status</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-[#F0F0F0]">
                  {requests.map((r) => (
                    <tr key={r.id} className="hover:bg-[#FAFAFA]">
                      <td className="p-3.5 font-semibold text-[#0F0F0F] max-w-[160px] truncate">
                        {r.itemTitle}
                      </td>
                      <td className="p-3.5 text-[#71717A]">{r.ownerName}</td>
                      <td className="p-3.5 text-[#71717A]">{r.renterName}</td>
                      <td className="p-3.5 text-[#71717A]">
                        {r.startDate} - {r.endDate} ({r.daysCount}d)
                      </td>
                      <td className="p-3.5 font-bold text-[#0F0F0F]">
                        Rs. {r.rentAmount.toLocaleString()}
                      </td>
                      <td className="p-3.5 font-bold text-[#635BFF]">
                        Rs. {r.serviceFee.toLocaleString()}
                      </td>
                      <td className="p-3.5">
                        <span
                          className={`text-[10px] font-bold px-2 py-0.5 rounded-full ${
                            r.status === 'ACCEPTED'
                              ? 'bg-[#DCFCE7] text-[#166534]'
                              : r.status === 'REJECTED'
                              ? 'bg-[#FEE2E2] text-[#991B1B]'
                              : 'bg-[#FEF3C7] text-[#92400E]'
                          }`}
                        >
                          {r.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* TAB 5: CNIC VERIFICATIONS INSPECTOR */}
        {activeTab === 'verifications' && (
          <div className="max-w-5xl mx-auto space-y-5">
            <div>
              <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
                CNIC Verifications Queue
              </h2>
              <p className="text-xs text-[#71717A]">
                Inspect CNIC front, back, and live selfie for Blue Tick approval
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {users
                .filter((u) => u.cnicFront)
                .map((u) => (
                  <div
                    key={u.id}
                    className="bg-white border border-[#F0F0F0] rounded-[20px] p-5 shadow-soft space-y-4"
                  >
                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-3">
                        <img
                          src={u.avatarUrl}
                          alt=""
                          className="w-10 h-10 rounded-full object-cover"
                        />
                        <div>
                          <div className="font-bold text-sm text-[#0F0F0F] flex items-center gap-1.5">
                            <span>{u.name}</span>
                            {u.isVerified && (
                              <div className="w-3.5 h-3.5 rounded-full bg-[#0095F6] flex items-center justify-center text-white">
                                <Check className="w-2.5 h-2.5 stroke-[3]" />
                              </div>
                            )}
                          </div>
                          <div className="text-xs text-[#71717A]">
                            {u.phone} • 📍 {u.city}
                          </div>
                        </div>
                      </div>

                      <button
                        onClick={() => verifyUser(u.id, !u.isVerified)}
                        className={`px-4 py-1.5 rounded-full text-xs font-semibold transition ${
                          u.isVerified
                            ? 'bg-[#F4F4F5] text-[#71717A] hover:bg-[#E4E4E7]'
                            : 'bg-[#0F0F0F] text-white hover:scale-[0.98]'
                        }`}
                      >
                        {u.isVerified ? 'Revoke Tick' : 'Approve Blue Tick'}
                      </button>
                    </div>

                    {/* 3 Photos Grid */}
                    <div className="grid grid-cols-3 gap-2">
                      <div>
                        <span className="text-[10px] font-semibold text-[#71717A] block mb-1">
                          Front CNIC
                        </span>
                        <img
                          src={u.cnicFront}
                          alt=""
                          className="w-full h-20 rounded-xl object-cover border border-[#E4E4E7]"
                        />
                      </div>
                      <div>
                        <span className="text-[10px] font-semibold text-[#71717A] block mb-1">
                          Back CNIC
                        </span>
                        <img
                          src={u.cnicBack}
                          alt=""
                          className="w-full h-20 rounded-xl object-cover border border-[#E4E4E7]"
                        />
                      </div>
                      <div>
                        <span className="text-[10px] font-semibold text-[#71717A] block mb-1">
                          Selfie with CNIC
                        </span>
                        <img
                          src={u.cnicSelfie}
                          alt=""
                          className="w-full h-20 rounded-xl object-cover border border-[#E4E4E7]"
                        />
                      </div>
                    </div>
                  </div>
                ))}
            </div>
          </div>
        )}

        {/* TAB 6: SETTINGS */}
        {activeTab === 'settings' && (
          <div className="max-w-2xl mx-auto space-y-5">
            <div>
              <h2 className="text-2xl font-bold tracking-tight text-[#0F0F0F]">
                Platform Settings
              </h2>
              <p className="text-xs text-[#71717A]">
                Configure commission fee and administration controls
              </p>
            </div>

            <div className="bg-white border border-[#F0F0F0] rounded-[20px] p-6 shadow-soft space-y-4">
              <div>
                <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                  Commission Percentage (%)
                </label>
                <input
                  type="number"
                  value={localCommission}
                  onChange={(e) => setLocalCommission(Number(e.target.value))}
                  className="w-full h-11 px-4 rounded-xl bg-[#F4F4F5] text-xs text-[#0F0F0F] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                />
                <p className="text-[11px] text-[#71717A] mt-1">
                  Currently taking {localCommission}% on each camera, AC, or tool rental transaction.
                </p>
              </div>

              <div>
                <label className="text-xs font-semibold text-[#0F0F0F] block mb-1">
                  Admin Alert Email
                </label>
                <input
                  type="email"
                  value={localEmail}
                  onChange={(e) => setLocalEmail(e.target.value)}
                  className="w-full h-11 px-4 rounded-xl bg-[#F4F4F5] text-xs text-[#0F0F0F] border-none focus:outline-none focus:ring-1 focus:ring-[#0F0F0F]"
                />
              </div>

              <div className="pt-2 flex items-center justify-between border-t border-[#F0F0F0]">
                <div>
                  <span className="text-xs font-semibold text-[#0F0F0F] block">
                    Maintenance Mode
                  </span>
                  <span className="text-[11px] text-[#71717A]">
                    Pause new booking requests temporarily
                  </span>
                </div>
                <input
                  type="checkbox"
                  checked={localMaintenance}
                  onChange={(e) => setLocalMaintenance(e.target.checked)}
                  className="rounded text-[#0F0F0F] focus:ring-[#0F0F0F] cursor-pointer"
                />
              </div>

              <button
                type="button"
                onClick={() => updateSettings(localCommission, localEmail, localMaintenance)}
                className="w-full h-11 rounded-full bg-[#0F0F0F] text-white text-xs font-semibold hover:opacity-95 transition mt-4"
              >
                Save Settings
              </button>
            </div>
          </div>
        )}
      </main>
    </div>
  );
};
