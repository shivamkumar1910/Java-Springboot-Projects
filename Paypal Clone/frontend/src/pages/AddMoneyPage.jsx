import { useState } from 'react';
import api from '../api';

export default function AddMoneyPage() {
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
