// Import necessary dependencies
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { loadStripe } from '@stripe/stripe-js';
import { Elements, CardElement, useStripe, useElements } from '@stripe/react-stripe-js';

// Initialize Stripe with the publishable key
const stripePromise = loadStripe('pk_test_51Q8OQEFMi0Xpv7wgPL2hkDgqDPFJ3d9vDpCzy8ZMd3YhEhx3XhgDh8AqWLHbe430M37ZmDAJuuY7UBh9ZRj6BW9900wn65BSIX');

// DonationForm component for handling donations
const DonationForm = ({ candidateId, userId }) => {
  // State variables for form handling
  const [amount, setAmount] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const stripe = useStripe();
  const elements = useElements();

  // Handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError(null);

    try {
      // Create PaymentIntent on the server
      const { data: { clientSecret } } = await axios.post('/api/payments/create-payment-intent', {
        amount: parseFloat(amount),
        currency: 'usd',
        candidateId,
        userId
      });

      // Confirm the payment on the client
      const { error: stripeError } = await stripe.confirmCardPayment(clientSecret, {
        payment_method: {
          card: elements.getElement(CardElement),
        }
      });

      if (stripeError) {
        setError(stripeError.message);
      } else {
        alert('Donation successful!');
        setAmount('');
      }
    } catch (err) {
      setError('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  // Render the donation form
  return (
    <form onSubmit={handleSubmit}>
      <input
        type="number"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        placeholder="Donation amount"
        required
      />
      <CardElement />
      <button type="submit" disabled={loading}>
        {loading ? 'Processing...' : 'Donate'}
      </button>
      {error && <div style={{ color: 'red' }}>{error}</div>}
    </form>
  );
};

// CandidateSearch component for searching and selecting candidates
const CandidateSearch = () => {
  // State variables for search functionality
  const [searchTerm, setSearchTerm] = useState('');
  const [candidates, setCandidates] = useState([]);
  const [selectedCandidate, setSelectedCandidate] = useState(null);

  // Effect hook to fetch candidates based on search term
  useEffect(() => {
    if (searchTerm) {
      axios.get(`/api/candidates/search?name=${searchTerm}`)
        .then(response => setCandidates(response.data))
        .catch(error => console.error('Error searching candidates:', error));
    } else {
      setCandidates([]);
    }
  }, [searchTerm]);

  // Render the candidate search and donation form
  return (
    <div>
      <input
        type="text"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        placeholder="Search for a candidate"
      />
      <ul>
        {candidates.map(candidate => (
          <li key={candidate.id} onClick={() => setSelectedCandidate(candidate)}>
            {candidate.name}
          </li>
        ))}
      </ul>
      {selectedCandidate && (
        <div>
          <h2>Donate to {selectedCandidate.name}</h2>
          <Elements stripe={stripePromise}>
            <DonationForm candidateId={selectedCandidate.id} />
          </Elements>
        </div>
      )}
    </div>
  );
};

// Main Donation component
export default function Donation() {
  return (
    <div>
      <h1>Find and Donate to a Candidate</h1>
      <CandidateSearch />
    </div>
  );
}
