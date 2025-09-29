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


export async function getCategories() {
  const res = await fetch(`${API_URL}/categories`, {
    credentials: 'include'
  });
  if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
  return res.json();
}

export const createCategory = async (category) => {
  const res = await fetch(`${API_URL}/categories`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: 'include',
    body: JSON.stringify(category),
  });
  if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
  const text = await res.text();
  return text ? JSON.parse(text) : category;
};

export async function deleteCategory(id) {
  const res = await fetch(`${API_URL}/categories/${id}`, { 
    method: "DELETE",
    credentials: 'include'
  });
  if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
}


export async function getNotifications() {
  const res = await fetch("http://localhost:8080/api/notifications");
  return res.json();
}

export async function deleteNotification(id) {
  await fetch(`http://localhost:8080/api/notifications/${id}`, {
    method: "DELETE",
  });
}
