import { NavBar } from "./NavBar.tsx";
import React from 'react';
import '../css/sidebar.css';

export const Sidebar = () => {
    return (
        <div>
        <div className="sidebar-content">
          <span className="logo">Tax Tracker</span>
          { 
          // <span className="slogan"><em>Making your accounting less taxing</em></span> 
          }
        <NavBar />
        </div>

        </div>
    );
  }