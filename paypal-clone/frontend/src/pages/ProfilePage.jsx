import { useEffect, useState } from 'react';
import api from '../api';

export default function ProfilePage() {
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
