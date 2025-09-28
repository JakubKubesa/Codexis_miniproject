import { useState } from "react";

export default function AddLaw({ laws, onLawAdded }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [originalId, setOriginalId] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title || !content) return;

    await onLawAdded({ title, content, originalId: originalId ? parseInt(originalId) : null });

    setTitle("");
    setContent("");
    setOriginalId("");
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: "2rem" }}>
      <h2 class="pridejZakon">Přidej zákon</h2>
      <div>
        <label>Název zákona:</label><br />
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          style={{ width: "100%", padding: "0.5rem", marginBottom: "0.5rem" }}
        />
      </div>

      <div>
        <label>Obsah zákona:</label><br />
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          rows={4}
          style={{ width: "100%", padding: "0.5rem", marginBottom: "0.5rem" }}
        />
      </div>

      <div>
        <label>Původní zákon (volitelné, pro novou verzi):</label><br />
        <select
          value={originalId}
          onChange={(e) => setOriginalId(e.target.value)}
          style={{ width: "100%", padding: "0.5rem", marginBottom: "0.5rem" }}
        >
          <option value="">--- nový zákon ---</option>
          {laws.map((law) => (
            <option key={law.id} value={law.id}>
              {law.title} ({law.state})
            </option>
          ))}
        </select>
      </div>

      <button type="submit" style={{ padding: "0.5rem 1rem" }}>Přidat zákon</button>
    </form>
  );
}
