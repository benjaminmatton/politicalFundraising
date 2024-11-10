import React, { useState } from 'react';
import axios from 'axios';

// Signup component for user registration
const Signup = () => {
    // State variables for form inputs and status
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    // Handle form submission
    const handleSignup = async (e) => {
        e.preventDefault();
        setError(null);
        try {
            // Send POST request to signup API
            const response = await axios.post('http://localhost:8080/api/auth/signup', {
                email,
                password,
            });
            setSuccess(true);
            console.log('Signup successful:', response.data);
        } catch (err) {
            setError('Signup failed. Please try again.');
            console.error('Error signing up:', err);
        }
    };

    return (
        <div>
            <h2>Sign Up</h2>
            <form onSubmit={handleSignup}>
                {/* Email input field */}
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                {/* Password input field */}
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Sign Up</button>
            </form>
            {/* Display error message if signup fails */}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {/* Display success message if signup is successful */}
            {success && <p style={{ color: 'green' }}>Signup successful! You can now log in.</p>}
        </div>
    );
};

export default Signup;
