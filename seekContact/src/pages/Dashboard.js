// Import necessary dependencies
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

// Dashboard component definition
const Dashboard = () => {
    // State variables
    const [user, setUser] = useState(null);
    const [donations, setDonations] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    // Effect hook to fetch user data and donations
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                // Check for authentication token
                const token = localStorage.getItem('authToken');
                if (!token) {
                    navigate('/login');
                    return;
                }

                // Fetch user profile data
                const userResponse = await axios.get('http://localhost:8080/api/user/profile', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setUser(userResponse.data);

                // Fetch user donations
                const donationResponse = await axios.get('http://localhost:8080/api/donations', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setDonations(donationResponse.data);
            } catch (err) {
                setError('Failed to load data. Please try again later.');
                console.error(err);
            }
        };

        fetchUserData();
    }, [navigate]);

    // Handler for user logout
    const handleLogout = () => {
        localStorage.removeItem('authToken');
        navigate('/login');
    };

    // Render the dashboard
    return (
        <div className="dashboard">
            <h2>Welcome to Your Dashboard</h2>
            {user ? (
                // Display user profile information
                <div className="user-profile">
                    <h3>Hello, {user.name}</h3>
                    <p>Email: {user.email}</p>
                    <p>Total Donations: ${user.totalDonations || 0}</p>
                </div>
            ) : (
                <p>Loading user data...</p>
            )}

            {/* Display user's donation history */}
            <div className="donation-list">
                <h3>Your Recent Donations</h3>
                {donations.length > 0 ? (
                    <ul>
                        {donations.map((donation) => (
                            <li key={donation.id}>
                                <p>Candidate: {donation.candidateName}</p>
                                <p>Amount: ${donation.amount}</p>
                                <p>Date: {new Date(donation.date).toLocaleDateString()}</p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No donations found.</p>
                )}
            </div>

            {/* Display error message if any */}
            {error && <p style={{ color: 'red' }}>{error}</p>}

            {/* Logout button */}
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default Dashboard;
