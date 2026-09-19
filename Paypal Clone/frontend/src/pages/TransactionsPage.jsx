import { useEffect, useState } from 'react';
import api from '../api';

function formatMoney(value) {
  return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(Number(value || 0));
}

export default function TransactionsPage() {
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
