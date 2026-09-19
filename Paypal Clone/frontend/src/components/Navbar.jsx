import { CreditCard, DollarSign, LogOut, ReceiptText, UserRound } from 'lucide-react';
import { NavLink, useNavigate } from 'react-router-dom';

const navItems = [
  { to: '/dashboard', label: 'Dashboard', icon: CreditCard },
  { to: '/send-money', label: 'Send Money', icon: DollarSign },
  { to: '/transactions', label: 'Transactions', icon: ReceiptText },
  { to: '/profile', label: 'Profile', icon: UserRound },
];

export default function Navbar({ onLogout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    onLogout();
    navigate('/login');
  };

  return (
    <nav className="bg-white border-b border-slate-200 sticky top-0 z-20">
      <div className="max-w-6xl mx-auto px-4 py-4 flex items-center justify-between gap-4">
        <div className="flex items-center gap-2 font-bold text-blue-700 text-xl">
          <div className="bg-blue-600 text-white p-2 rounded-xl">
            <CreditCard size={18} />
          </div>
          WalletFlow
        </div>

        <div className="hidden md:flex items-center gap-6">
          {navItems.map(({ to, label, icon: Icon }) => (
            <NavLink
              key={to}
              to={to}
              className={({ isActive }) =>
                `flex items-center gap-2 px-3 py-2 rounded-lg text-sm font-medium transition ${
                  isActive ? 'bg-blue-50 text-blue-700' : 'text-slate-600 hover:bg-slate-100'
                }`
              }
            >
              <Icon size={16} />
              {label}
            </NavLink>
          ))}
        </div>

        <button
          onClick={handleLogout}
          className="inline-flex items-center gap-2 bg-slate-900 text-white px-4 py-2 rounded-lg hover:bg-slate-800"
        >
          <LogOut size={16} />
          Logout
        </button>
      </div>
    </nav>
  );
}
