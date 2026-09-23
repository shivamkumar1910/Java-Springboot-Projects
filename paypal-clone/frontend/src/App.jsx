import { useEffect, useMemo, useState } from 'react';
import { Navigate, Route, Routes, useNavigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import api from './api';

function formatMoney(value) {
  const number = Number(value || 0);
  return new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR',
    maximumFractionDigits: 2,
  }).format(number);
}

function DashboardPage() {
  const [wallet, setWallet] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [user, setUser] = useState({ name: 'User' });

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        const [profileRes, walletRes, transactionRes] = await Promise.all([
          api.get('/users/me'),
          api.get('/wallet'),
          api.get('/transactions'),
        ]);

        setUser(profileRes.data);
        setWallet(walletRes.data);
        setTransactions(transactionRes.data.slice(0, 5));
      } catch (error) {
        console.error(error);
      }
    };

    fetchDashboard();
  }, []);

  const totalSent = useMemo(() => {
    return transactions
      .filter((t) => t.sender && t.sender.id === user.id)
      .reduce((sum, t) => sum + Number(t.amount), 0);
  }, [transactions, user.id]);

  const totalReceived = useMemo(() => {
    return transactions
      .filter((t) => t.receiver && t.receiver.id === user.id)
      .reduce((sum, t) => sum + Number(t.amount), 0);
  }, [transactions, user.id]);

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      <div className="grid md:grid-cols-3 gap-6">
        <div className="md:col-span-2 bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
          <p className="text-sm text-slate-500">Welcome, {user.name}</p>
          <h2 className="text-3xl font-bold mt-2">Wallet Balance</h2>
          <p className="text-4xl font-bold text-blue-700 mt-3">{wallet ? formatMoney(wallet.balance) : '₹0.00'}</p>

          <div className="mt-6 flex gap-3">
            <button
              onClick={() => window.location.href = '/add-money'}
              className="bg-blue-600 text-white px-5 py-3 rounded-lg hover:bg-blue-700"
            >
              Add Money
            </button>
            <button
              onClick={() => window.location.href = '/send-money'}
              className="bg-slate-900 text-white px-5 py-3 rounded-lg hover:bg-slate-800"
            >
              Send Money
            </button>
          </div>
        </div>

        <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
          <h3 className="font-semibold text-slate-700 mb-4">Quick Summary</h3>
          <div className="space-y-4">
            <div className="bg-slate-50 rounded-xl p-3">
              <p className="text-sm text-slate-500">Money Sent</p>
              <p className="text-xl font-bold">{formatMoney(totalSent)}</p>
            </div>
            <div className="bg-slate-50 rounded-xl p-3">
              <p className="text-sm text-slate-500">Money Received</p>
              <p className="text-xl font-bold">{formatMoney(totalReceived)}</p>
            </div>
            <div className="bg-slate-50 rounded-xl p-3">
              <p className="text-sm text-slate-500">Transactions</p>
              <p className="text-xl font-bold">{transactions.length}</p>
            </div>
          </div>
        </div>
      </div>

      <div className="mt-8 bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
        <h3 className="text-xl font-semibold mb-4">Recent Transactions</h3>
        <div className="space-y-3">
          {transactions.length === 0 ? (
            <p className="text-slate-500">No transactions yet.</p>
          ) : (
            transactions.map((transaction) => (
              <div key={transaction.id} className="flex items-center justify-between border border-slate-200 rounded-xl p-3">
                <div>
                  <p className="font-medium">
                    {transaction.type === 'DEPOSIT' ? 'Added Money' : transaction.type === 'SENT' ? `Sent to ${transaction.receiver?.username || 'user'}` : `Received from ${transaction.sender?.username || 'user'}`}
                  </p>
                  <p className="text-xs text-slate-500">{transaction.message || '—'}</p>
                </div>
                <p className={`font-bold ${transaction.type === 'SENT' ? 'text-red-600' : 'text-green-600'}`}>
                  {transaction.type === 'SENT' ? '-' : '+'}{formatMoney(transaction.amount)}
                </p>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

function AddMoneyPage() {
  const [amount, setAmount] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/wallet/add-money', { amount: Number(amount) });
      setMessage('₹' + Number(amount).toLocaleString() + ' added successfully!');
      setAmount('');
    } catch (error) {
      setMessage(error.response?.data?.message || 'Invalid amount');
    }
  };

  return (
    <div className="max-w-xl mx-auto px-4 py-10">
      <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
        <h2 className="text-2xl font-bold mb-5">Add Money</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Amount</label>
            <input
              type="number"
              min="1"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              className="w-full border border-slate-300 rounded-lg px-3 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="5000"
              required
            />
          </div>
          <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700">
            Add Money
          </button>
          {message && <p className="text-sm text-green-600 mt-3">{message}</p>}
        </form>
      </div>
    </div>
  );
}

function SendMoneyPage() {
  const [form, setForm] = useState({ receiverUsername: '', amount: '', message: '' });
  const [confirmation, setConfirmation] = useState(false);
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setConfirmation(true);
  };

  const confirmPayment = async () => {
    try {
      await api.post('/payments/send', {
        receiverUsername: form.receiverUsername,
        amount: Number(form.amount),
        message: form.message,
      });
      setSuccess('Payment Successful!');
      setConfirmation(false);
      setTimeout(() => navigate('/dashboard'), 1200);
    } catch (error) {
      setSuccess(error.response?.data?.message || 'Payment failed');
      setConfirmation(false);
    }
  };

  if (confirmation) {
    return (
      <div className="max-w-xl mx-auto px-4 py-10">
        <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
          <h2 className="text-2xl font-bold mb-4">Send ₹{Number(form.amount).toLocaleString()} to {form.receiverUsername}?</h2>
          <p className="text-slate-600 mb-3">Message: {form.message || 'No message'}</p>
          <div className="flex gap-3 mt-5">
            <button onClick={() => setConfirmation(false)} className="flex-1 border border-slate-300 rounded-lg py-3">Cancel</button>
            <button onClick={confirmPayment} className="flex-1 bg-blue-600 text-white rounded-lg py-3">Confirm</button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-xl mx-auto px-4 py-8">
      <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
        <h2 className="text-2xl font-bold mb-5">Send Money</h2>
        {success && <p className="mb-4 text-green-600">{success}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Recipient Username</label>
            <input
              name="receiverUsername"
              value={form.receiverUsername}
              onChange={handleChange}
              className="w-full border border-slate-300 rounded-lg px-3 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Amount</label>
            <input
              type="number"
              name="amount"
              min="1"
              value={form.amount}
              onChange={handleChange}
              className="w-full border border-slate-300 rounded-lg px-3 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Message</label>
            <input
              name="message"
              value={form.message}
              onChange={handleChange}
              className="w-full border border-slate-300 rounded-lg px-3 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Dinner"
            />
          </div>
          <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700">
            Send Money
          </button>
        </form>
      </div>
    </div>
  );
}

function TransactionsPage() {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    api.get('/transactions').then((res) => setTransactions(res.data)).catch(console.error);
  }, []);

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
        <h2 className="text-2xl font-bold mb-5">Transaction History</h2>
        <div className="overflow-x-auto">
          <table className="min-w-full text-left">
            <thead className="bg-slate-100">
              <tr>
                <th className="p-3 font-semibold">Description</th>
                <th className="p-3 font-semibold">Type</th>
                <th className="p-3 font-semibold">Amount</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map((t) => (
                <tr key={t.id} className="border-t border-slate-200">
                  <td className="p-3">
                    {t.type === 'DEPOSIT' ? 'Added Money' : t.type === 'SENT' ? `Sent to ${t.receiver?.username || 'user'}` : `Received from ${t.sender?.username || 'user'}`}
                  </td>
                  <td className="p-3">{t.type}</td>
                  <td className={`p-3 font-bold ${t.type === 'SENT' ? 'text-red-600' : 'text-green-600'}`}>
                    {t.type === 'SENT' ? '-' : '+'}{formatMoney(t.amount)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

function ProfilePage() {
  const [profile, setProfile] = useState({ name: '', username: '', email: '' });

  useEffect(() => {
    api.get('/users/me').then((res) => setProfile(res.data)).catch(console.error);
  }, []);

  return (
    <div className="max-w-xl mx-auto px-4 py-10">
      <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
        <h2 className="text-2xl font-bold mb-5">Profile</h2>
        <div className="space-y-3 text-slate-700">
          <p><span className="font-semibold">Name:</span> {profile.name}</p>
          <p><span className="font-semibold">Username:</span> {profile.username}</p>
          <p><span className="font-semibold">Email:</span> {profile.email}</p>
        </div>
      </div>
    </div>
  );
}

function ProtectedLayout({ onLogout }) {
  return (
    <>
      <Navbar onLogout={onLogout} />
      <Routes>
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/send-money" element={<SendMoneyPage />} />
        <Route path="/add-money" element={<AddMoneyPage />} />
        <Route path="/transactions" element={<TransactionsPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="*" element={<Navigate to="/dashboard" replace />} />
      </Routes>
    </>
  );
}

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      api.get('/users/me').then((res) => setUser(res.data)).catch(() => setUser(null));
    }
  }, []);

  const handleLogin = (userData) => setUser(userData);
  const handleLogout = () => setUser(null);

  return (
    <Routes>
      <Route path="/login" element={user ? <Navigate to="/dashboard" replace /> : <LoginPage onLogin={handleLogin} />} />
      <Route path="/register" element={user ? <Navigate to="/dashboard" replace /> : <RegisterPage onLogin={handleLogin} />} />
      <Route
        path="*"
        element={
          user ? (
            <ProtectedLayout onLogout={handleLogout} />
          ) : (
            <Navigate to="/login" replace />
          )
        }
      />
    </Routes>
  );
}

export default App;
