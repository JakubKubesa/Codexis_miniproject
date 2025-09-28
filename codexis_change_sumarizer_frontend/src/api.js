const API_URL = "http://localhost:8080/api";

export const getLaws = async () => {
  const res = await fetch(`${API_URL}/laws`);
  return res.json();
};

export const createLaw = async (law) => {
  const res = await fetch(`${API_URL}/laws`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(law),
  });
  const text = await res.text();
  return text ? JSON.parse(text) : null;
};

export const createLawVersion = async (law) => {
  const res = await fetch(`${API_URL}/laws/version`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(law),
  });
  const text = await res.text();
  return text ? JSON.parse(text) : null;
};

export const getLawDiff = async (id) => {
  const res = await fetch(`${API_URL}/laws/${id}/diff`, {
    method: "POST",
  });
  const data = await res.json();
  return data.summary;
};