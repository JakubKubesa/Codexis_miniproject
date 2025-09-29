import { useEffect, useState } from "react";
import { getNotifications, deleteNotification } from "../api";
import "./../App.css";

export default function NotificationsPage({ onBack }) {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    const data = await getNotifications();
    setNotifications(data);
  };

  const handleDelete = async (id) => {
    await deleteNotification(id);
    fetchNotifications();
  };

  return (
    <div className="App">
      <h2>Upozornění</h2>

      {notifications.length === 0 && <p>Žádná upozornění</p>}

      <ul className="notifications-list">
        {notifications.map((n) => (
          <li key={n.id}>
            📜 {n.law?.title} → 📂 {n.category?.name}
            <button onClick={() => handleDelete(n.id)} className="delete-btn">
              Delete
            </button>
          </li>
        ))}
      </ul>

      <button onClick={onBack}>← Zpět</button>
    </div>
  );
}
