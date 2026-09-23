import { useState } from 'react';
import api from '../api';

export default function SendMoneyPage() {
  const [form, setForm] = useState({ username: '', amount: '', message: '' });
  const [status, setStatus] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/payments/send', {
        receiverUsername: form.username,
        amount: Number(form.amount),
        message: form.message,
      });
      setStatus('Payment sent successfully.');
      setForm({ username: '', amount: '', message: '' });
    } catch (error) {
      setStatus(error.response?.data?.message || 'Transfer failed.');
    }
  };

  return (
    <div className="max-w-xl mx-auto px-4 py-10">
      <div className="bg-white rounded-2xl shadow-soft p-6 border border-slate-200">
        <h2 className="text-2xl font-bold mb-5">Send Money</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Receiver Username</label>
            <input
              name="username"
              value={form.username}
              onChange={handleChange}
              className="w-full border border-slate-300 rounded-lg px-3 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="bob"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Amount</label>
            <input
              name="amount"
              type="number"
              min="1"
              value={form.amount}
              onChange={handleChange}
              className="w-full border border-slate-300 rounded-lg px-3 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="250"
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
              placeholder="For dinner"
            />
          </div>

          <button type="submit" className="w-full bg-slate-900 text-white py-3 rounded-lg font-semibold hover:bg-slate-800">
            Send Money
          </button>
          {status && <p className="text-sm text-slate-700 mt-3">{status}</p>}
        </form>
      </div>
    </div>
  );
}
