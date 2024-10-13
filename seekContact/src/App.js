// Import necessary React and routing dependencies
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';

// Import page components
import Signup from './pages/Signup';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';

// Import authentication utility
import { isAuthenticated } from './services/auth';

// Main App component
function App() {
    return (
        // Set up the router
        <Router>
            {/* Define routes */}
            <Routes>
                {/* Public Routes */}
                <Route path="/signup" element={<Signup />} />
                <Route path="/login" element={<Login />} />
                
                {/* Protected Route */}
                <Route
                    path="/dashboard"
                    element={
                        // Check if user is authenticated
                        isAuthenticated() ? (
                            // If authenticated, render Dashboard
                            <Dashboard />
                        ) : (
                            // If not authenticated, redirect to login
                            <Navigate to="/login" />
                        )
                    }
                />
            </Routes>
        </Router>
    );
}

// Export the App component
export default App;
