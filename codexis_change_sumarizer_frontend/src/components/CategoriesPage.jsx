import { useEffect, useState } from "react";
import { getCategories, createCategory, deleteCategory } from "../api";
import "./../App.css";

export default function CategoriesPage({ onBack }) {
  const [categories, setCategories] = useState([]);
  const [name, setName] = useState("");

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    const data = await getCategories();
    setCategories(data);
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    if (!name.trim()) return;
    await createCategory({ name });
    setName("");
    fetchCategories();
  };

  const handleDelete = async (id) => {
    await deleteCategory(id);
    fetchCategories();
  };

  return (
    <div className="App">
      <h2>Kategorie</h2>

      <form onSubmit={handleAdd} className="form-container">
        <input
          type="text"
          placeholder="Název kategorie"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button type="submit" disabled={categories.length >= 5}>
          Přidat
        </button>
      </form>

      {categories.length >= 5 && (
        <p style={{ color: "red" }}>Můžeš mít maximálně 5 kategorií.</p>
      )}

      <ul className="categories-list">
        {categories.map((c) => (
          <li key={c.id} className="category-item">
            <span className="category-name">{c.name}</span>
            <button onClick={() => handleDelete(c.id)} className="delete-btn">
              Delete
            </button>
          </li>
        ))}
      </ul>

      <button onClick={onBack}>← Zpět</button>
    </div>
  );
}
