import { useEffect, useState } from "react";
import { getLaws, createLaw, createLawVersion } from "./api";
import AddLaw from "./components/AddLaw";
import ViewLaws from "./components/ViewLaws";
import "./App.css";

export default function App() {
  const [laws, setLaws] = useState([]);

  useEffect(() => {
    fetchLaws();
  }, []);

  const fetchLaws = async () => {
    const data = await getLaws();
    setLaws(data);
  };

  const handleLawAdded = async (law) => {
    if (law.originalId) {
      await createLawVersion(law);
    } else {
      await createLaw(law);
    }
    fetchLaws();
  };

  return (
    <div class="App">
      <h1>Zákony</h1>

      <AddLaw laws={laws} onLawAdded={handleLawAdded} />

      <h2 class="seznamZakonu">Seznam zákonů</h2>
      <ViewLaws laws={laws} />
    </div>
  );
}
