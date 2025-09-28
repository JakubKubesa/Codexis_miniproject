import { useState } from "react";
import { getLawDiff } from "../api"; 
import "./../App.css";
import ReactMarkdown from "react-markdown";


export default function ViewLaws({ laws }) {
  const [selectedLaw, setSelectedLaw] = useState(null);
  const [diff, setDiff] = useState("");

  const handleSelectLaw = async (law) => {
    setSelectedLaw(law);
    setDiff(""); 

    if (law.originalId) {
      if (law.diffSummary) {
        setDiff(law.diffSummary);
      } else {
        const summary = await getLawDiff(law.id);
        setDiff(summary);
      }
    } else {
      setDiff("Tento zákon nemá žádnou předchozí verzi.");
    }
  };


  if (selectedLaw) {
    return (
      <div className="law-detail">
        <h3>Detail zákona</h3>
        <p><strong>Název:</strong> {selectedLaw.title}</p>
        <p><strong>Obsah:</strong> {selectedLaw.content}</p>
        <p><strong>Stav:</strong> {selectedLaw.state}</p>
    
        {selectedLaw.diffSummary && (
          <div className="law-diff">
            <h4>Rozdíl oproti původní verzi:</h4>
            <ReactMarkdown>{selectedLaw.diffSummary}</ReactMarkdown>
          </div>
        )}
  
        <button onClick={() => setSelectedLaw(null)}>← Zpět</button>
      </div>
    );
  }


  return (
    <table className="laws-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Název</th>
          <th>Stav</th>
        </tr>
      </thead>
      <tbody>
        {laws.map((law) => (
          <tr key={law.id} onClick={() => handleSelectLaw(law)} style={{ cursor: "pointer" }}>
            <td>{law.id}</td>
            <td>{law.title}</td>
            <td>{law.state}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
