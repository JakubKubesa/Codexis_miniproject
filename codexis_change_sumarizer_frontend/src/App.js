import { useEffect, useState } from "react";
import { getLaws, createLaw, createLawVersion } from "./api";
import AddLaw from "./components/AddLaw";
import ViewLaws from "./components/ViewLaws";
import CategoriesPage from "./components/CategoriesPage";
import NotificationsPage from "./components/NotificationsPage";

import "./App.css";

export default function App() {
  const [laws, setLaws] = useState([]);
  const [showCategories, setShowCategories] = useState(false);
  const [showNotifications, setShowNotifications] = useState(false);

  useEffect(() => {
    fetchLaws();
  }, []);

  const fetchLaws = async () => {
    const data = await getLaws();
    setLaws(data);
  };

  const handleLawAdded = async (law) => {
    let response;
    if (law.originalId) {
      response = await createLawVersion(law);
    } else {
      response = await createLaw(law);
    }
  
    fetchLaws();
  
    if (response.matchedCategories && response.matchedCategories.length > 0) {
      alert(
        `📢 Zákon "${response.law.title}" spadá do kategorií: ${response.matchedCategories.join(", ")}`
      );
    }
  };


  if (showCategories) {
    return <CategoriesPage onBack={() => setShowCategories(false)} />;
  }

  if (showNotifications) {
    return <NotificationsPage onBack={() => setShowNotifications(false)} />;
  }


  return (
    <div className="App">
      <h1>Zákony</h1>

      <div className="top-buttons">
        <button onClick={() => setShowCategories(true)}>📂 Kategorie</button>
        <button onClick={() => setShowNotifications(true)}>🔔 Upozornění</button>
      </div>

      <AddLaw laws={laws} onLawAdded={handleLawAdded} />

      <h2 className="seznamZakonu">Seznam zákonů</h2>
      <ViewLaws laws={laws} />
    </div>
  );
}
