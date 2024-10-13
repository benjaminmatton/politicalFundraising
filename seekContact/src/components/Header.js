import React from 'react';
//import './Header.css'; // Optionally, create a CSS file for styling

const Header = () => {
  return (
    <header>
      <nav className="navbar">
        <div className="logo">MyLogo</div>
        <ul className="nav-links">
          <li><a href="#home">Home</a></li>
          <li><a href="#features">Features</a></li>
          <li><a href="#pricing">Pricing</a></li>
          <li><a href="#contact">Contact</a></li>
        </ul>
        <button className="cta-button">Sign Up</button>
      </nav>
    </header>
  );
};

export default Header;
