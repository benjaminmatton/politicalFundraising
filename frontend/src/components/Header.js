// src/components/Header.js
import React from 'react';
import { Link } from 'react-router-dom';
import '../style/Header.css'; // Optional: If you have custom styles for the header

const Header = () => {
  return (
    <header className="header">
      <div className="logo">MyLogo</div>
      <nav className="navigation">
        <ul>
          <li><a href="#home">Home</a></li>
          <li><a href="#about">About</a></li>
          <li><a href="#services">Services</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
      </nav>
      <div className="auth-buttons">
        <Link to="/login"><button className="sign-in">Login</button></Link>
        <Link to="/signup"><button className="sign-up">Sign Up</button></Link>
      </div>
      <div className="profile">
        <img src="profile-pic-url" alt="Profile" />
      </div>
    </header>
  );
};

export default Header;
