import React from 'react';
import { Wallet, Bell, Settings, LogOut } from 'lucide-react';
import '../styles/Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar glass-effect">
      <div className="nav-brand">
        <Wallet className="brand-icon" size={28} />
        <h2>DigiWallet</h2>
      </div>

      <div className="nav-actions">
        <button className="icon-btn" aria-label="Notifications">
          <Bell size={20} />
          <span className="badge"></span>
        </button>
        <button className="icon-btn" aria-label="Settings">
          <Settings size={20} />
        </button>
        <button className="icon-btn" aria-label="Logout">
          <LogOut size={20} />
        </button>
        <div className="user-avatar">U</div>
      </div>
    </nav>
  );
};

export default Navbar;
