import { useEffect, useMemo, useState } from 'react';
import api from '../api';

function formatMoney(value) {
  const number = Number(value || 0);
  return new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR',
    maximumFractionDigits: 2,
  }).format(number);
}

export default function DashboardPage() {
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
            <a href="/add-money" className="bg-blue-600 text-white px-5 py-3 rounded-lg hover:bg-blue-700">Add Money</a>
            <a href="/send-money" className="bg-slate-900 text-white px-5 py-3 rounded-lg hover:bg-slate-800">Send Money</a>
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
