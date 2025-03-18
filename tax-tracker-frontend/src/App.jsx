import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Header } from "./components/Header.tsx";
import { Sidebar } from "./components/Sidebar.tsx";
import { Clients } from "./pages/Clients.tsx";

export default function App() {
  return (
    <Router> 
      <div>
        <Sidebar />
        <div className = "main-content">
        <Routes>
          <Route path="/" element={<h2>Home</h2>} />
          <Route path="/clients" element={< Clients />} />
          <Route path="/tax-returns" element={<h2>Tax Returns</h2>} />
          <Route path="/payments" element={<h2>Payments</h2>} />
        </Routes>
        </div>
      </div>
    </Router>
  );
}
